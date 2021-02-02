package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class TextBox extends Control implements IValidityControl
{
    private IValidityProvider validityProvider;
    private IValueListener valueChangedListener;
    private String validityMessage;
    private boolean isValid = true;
    private Rectangle validityRect;
    private boolean numbersOnly = false;

    private int maxLength = Integer.MAX_VALUE;

    // From GuiTextFielld
    private String text = "";
    private int cursorCounter;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor = 0xe0e0e0;
    private int disabledColor = 0x707070;

    public TextBox(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        tabStop = true;
        validityRect = new Rectangle(bounds.getX() + bounds.getWidth() - 12, bounds.getY() + (bounds.getHeight() - 9) / 2, 9, 9);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String value)
    {
        if (numbersOnly)
        {
            value = value.replaceAll("[\\D]", "");
        }
        if (value.length() > maxLength)
        {
            text = value.substring(0, maxLength);
        } else
        {
            text = value;
        }
        setCursorPositionEnd();
        valueChanged();
    }

    public void setNumbersOnly(boolean numbersOnly)
    {
        this.numbersOnly = numbersOnly;
        setText(text);
    }

    @Override
    public boolean hasValidValue()
    {
        return isValid;
    }

    @Override
    public void setValueChangedListener(IValueListener listener)
    {
        valueChangedListener = listener;
        valueChanged();
    }

    public void setValidityProvider(IValidityProvider validityProvider)
    {
        this.validityProvider = validityProvider;
    }

    public void setMaxLength(int value)
    {
        maxLength = value;
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        validityRect = new Rectangle(bounds.getX() + bounds.getWidth() - 12, bounds.getY() + (bounds.getHeight() - 9) / 2, 9, 9);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (!hasFocus()) return;

        switch (c)
        {
            case 1:
                this.setCursorPositionEnd();
                this.setSelectionPos(0);
                break;
            case 3: // Ctrl + c
                GuiScreen.setClipboardString(this.getSelectedText());
                break;
            case 22: // Ctrl + v
                if (this.isEnabled())
                {
                    String s = GuiScreen.getClipboardString();
                    if (numbersOnly)
                        s = s.replaceAll("[\\D]", "");
                    this.writeText(s);
                }

                break;
            case 24: // Ctrl + x
                GuiScreen.setClipboardString(this.getSelectedText());

                if (this.isEnabled())
                {
                    this.writeText("");
                }

                break;
            default:
                switch (key)
                {
                    case Keyboard.KEY_BACK:
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            if (this.isEnabled())
                            {
                                this.deleteWords(-1);
                            }
                        } else if (this.isEnabled())
                        {
                            this.deleteFromCursor(-1);
                        }

                        break;
                    case Keyboard.KEY_HOME:
                        if (GuiScreen.isShiftKeyDown())
                        {
                            this.setSelectionPos(0);
                        } else
                        {
                            this.setCursorPositionZero();
                        }

                        break;
                    case Keyboard.KEY_LEFT:
                        if (GuiScreen.isShiftKeyDown())
                        {
                            if (GuiScreen.isCtrlKeyDown())
                            {
                                this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                            } else
                            {
                                this.setSelectionPos(this.getSelectionEnd() - 1);
                            }
                        } else if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setCursorPosition(this.getNthWordFromCursor(-1));
                        } else
                        {
                            this.moveCursorBy(-1);
                        }

                        break;
                    case Keyboard.KEY_RIGHT:
                        if (GuiScreen.isShiftKeyDown())
                        {
                            if (GuiScreen.isCtrlKeyDown())
                            {
                                this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                            } else
                            {
                                this.setSelectionPos(this.getSelectionEnd() + 1);
                            }
                        } else if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setCursorPosition(this.getNthWordFromCursor(1));
                        } else
                        {
                            this.moveCursorBy(1);
                        }

                        break;
                    case Keyboard.KEY_END:
                        if (GuiScreen.isShiftKeyDown())
                        {
                            this.setSelectionPos(this.text.length());
                        } else
                        {
                            this.setCursorPositionEnd();
                        }

                        break;
                    case Keyboard.KEY_DELETE:
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            if (this.isEnabled())
                            {
                                this.deleteWords(1);
                            }
                        } else if (this.isEnabled())
                        {
                            this.deleteFromCursor(1);
                        }

                        break;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(c) && (!numbersOnly || Character.isDigit(c)))
                        {
                            if (this.isEnabled())
                            {
                                this.writeText(Character.toString(c));
                            }

                            break;
                        } else
                        {
                            break;
                        }
                }
        }

        if (getText().length() > maxLength)
        {
            setText(getText().substring(0, getText().length() - 1));
        }
        if (numbersOnly && getText().matches(".*[\\D]+.*"))
        {
            setText(getText().replaceAll("[\\D]", ""));
        }

        valueChanged();
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        rootControl.claimFocus(this);

        if (hasFocus() && button == 0)
        {
            int l = mouseX - getX() - 4;

            String s = mc.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(mc.fontRenderer.trimStringToWidth(s, l).length() + this.lineScrollOffset);
        }
    }

    private void valueChanged()
    {
        if (validityProvider != null)
        {
            validityMessage = validityProvider.checkValidity(this);
            isValid = validityMessage == null;
        }

        if (valueChangedListener != null)
        {
            valueChangedListener.onValueChanged(this);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (!isValid)
        {
            mc.renderEngine.bindTexture(Textures.CONTROLS);
            zLevel += 10.0f;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            drawTexturedModalRect(validityRect.getX(), validityRect.getY(), 200, 72, validityRect.getWidth(), validityRect.getHeight());
            zLevel -= 10.0f;
        }

        GuiBase.drawRect(this.getX() - 1, this.getY() - 1, this.getX() + this.getWidth() + 1, this.getY() + this.getHeight() + 1, -6250336);
        GuiBase.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -16777216);

        int i = this.isEnabled() ? this.enabledColor : this.disabledColor;
        int j = this.cursorPosition - this.lineScrollOffset;
        int k = this.selectionEnd - this.lineScrollOffset;
        String s = mc.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
        boolean flag = j >= 0 && j <= s.length();
        boolean flag1 = this.hasFocus() && this.cursorCounter / 6 % 2 == 0 && flag;
        int l = this.getX() + 4;
        int i1 = this.getY() + (this.getHeight() - 8) / 2;
        int j1 = l;

        if (k > s.length())
        {
            k = s.length();
        }

        if (s.length() > 0)
        {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = mc.fontRenderer.drawStringWithShadow(s1, l, i1, i);
        }

        boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= maxLength;
        int k1 = j1;

        if (!flag)
        {
            k1 = j > 0 ? l + this.getWidth() : l;
        } else if (flag2)
        {
            k1 = j1 - 1;
            --j1;
        }

        if (s.length() > 0 && flag && j < s.length())
        {
            mc.fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, i);
        }

        if (flag1)
        {
            if (flag2)
            {
                Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + mc.fontRenderer.FONT_HEIGHT, -3092272);
            } else
            {
                mc.fontRenderer.drawStringWithShadow("_", k1, i1, i);
            }
        }

        if (k != j)
        {
            int l1 = l + mc.fontRenderer.getStringWidth(s.substring(0, k));
            this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + mc.fontRenderer.FONT_HEIGHT);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (!isValid && validityRect.contains(mouseX, mouseY))
        {
            GuiHelper.drawHoveringText(Lists.newArrayList(validityMessage), mouseX, mouseY, mc.fontRenderer);
        }
    }

    // From GuiTextField

    public String getSelectedText()
    {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    public void writeText(String p_146191_1_)
    {
        String s1 = "";
        String s2 = ChatAllowedCharacters.filerAllowedCharacters(p_146191_1_);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxLength - this.text.length() - (i - this.selectionEnd);
        boolean flag = false;

        if (this.text.length() > 0)
        {
            s1 = s1 + this.text.substring(0, i);
        }

        int l;

        if (k < s2.length())
        {
            s1 = s1 + s2.substring(0, k);
            l = k;
        } else
        {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (this.text.length() > 0 && j < this.text.length())
        {
            s1 = s1 + this.text.substring(j);
        }

        this.text = s1;
        this.moveCursorBy(i - this.selectionEnd + l);
    }

    public void deleteWords(int p_146177_1_)
    {
        if (this.text.length() != 0)
        {
            if (this.selectionEnd != this.cursorPosition)
            {
                this.writeText("");
            } else
            {
                this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int p_146175_1_)
    {
        if (this.text.length() != 0)
        {
            if (this.selectionEnd != this.cursorPosition)
            {
                this.writeText("");
            } else
            {
                boolean flag = p_146175_1_ < 0;
                int j = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
                int k = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
                String s = "";

                if (j >= 0)
                {
                    s = this.text.substring(0, j);
                }

                if (k < this.text.length())
                {
                    s = s + this.text.substring(k);
                }

                this.text = s;

                if (flag)
                {
                    this.moveCursorBy(p_146175_1_);
                }
            }
        }
    }

    public int getCursorPosition()
    {
        return this.cursorPosition;
    }

    public int getNthWordFromCursor(int p_146187_1_)
    {
        return this.getNthWordFromPos(p_146187_1_, this.getCursorPosition());
    }

    /**
     * gets the position of the nth word. N may be negative, then it looks backwards. params: N, position
     */
    public int getNthWordFromPos(int p_146183_1_, int p_146183_2_)
    {
        return this.func_146197_a(p_146183_1_, this.getCursorPosition(), true);
    }

    public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
    {
        int k = p_146197_2_;
        boolean flag1 = p_146197_1_ < 0;
        int l = Math.abs(p_146197_1_);

        for (int i1 = 0; i1 < l; ++i1)
        {
            if (flag1)
            {
                while (p_146197_3_ && k > 0 && this.text.charAt(k - 1) == 32)
                {
                    --k;
                }

                while (k > 0 && this.text.charAt(k - 1) != 32)
                {
                    --k;
                }
            } else
            {
                int j1 = this.text.length();
                k = this.text.indexOf(32, k);

                if (k == -1)
                {
                    k = j1;
                } else
                {
                    while (p_146197_3_ && k < j1 && this.text.charAt(k) == 32)
                    {
                        ++k;
                    }
                }
            }
        }

        return k;
    }

    public int getSelectionEnd()
    {
        return this.selectionEnd;
    }

    /**
     * Moves the text cursor by a specified number of characters and clears the selection
     */
    public void moveCursorBy(int p_146182_1_)
    {
        this.setCursorPosition(this.selectionEnd + p_146182_1_);
    }

    /**
     * sets the cursors position to the beginning
     */
    public void setCursorPositionZero()
    {
        this.setCursorPosition(0);
    }

    public void setCursorPositionEnd()
    {
        setCursorPosition(text.length());
    }

    public void setCursorPosition(int p_146190_1_)
    {
        cursorPosition = p_146190_1_;
        int j = text.length();

        if (cursorPosition < 0)
        {
            cursorPosition = 0;
        }

        if (cursorPosition > j)
        {
            cursorPosition = j;
        }

        setSelectionPos(cursorPosition);
    }

    public void setSelectionPos(int position)
    {
        int j = this.text.length();

        if (position > j)
        {
            position = j;
        }

        if (position < 0)
        {
            position = 0;
        }

        this.selectionEnd = position;

        if (mc.fontRenderer != null)
        {
            if (this.lineScrollOffset > j)
            {
                this.lineScrollOffset = j;
            }

            int k = this.getWidth();
            String s = mc.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), k);
            int l = s.length() + this.lineScrollOffset;

            if (position == this.lineScrollOffset)
            {
                this.lineScrollOffset -= mc.fontRenderer.trimStringToWidth(this.text, k, true).length();
            }

            if (position > l)
            {
                this.lineScrollOffset += position - l;
            } else if (position <= this.lineScrollOffset)
            {
                this.lineScrollOffset -= this.lineScrollOffset - position;
            }

            if (this.lineScrollOffset < 0)
            {
                this.lineScrollOffset = 0;
            }

            if (this.lineScrollOffset > j)
            {
                this.lineScrollOffset = j;
            }
        }
    }

    private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
    {
        int i1;

        if (p_146188_1_ < p_146188_3_)
        {
            i1 = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i1;
        }

        if (p_146188_2_ < p_146188_4_)
        {
            i1 = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = i1;
        }

        if (p_146188_3_ > this.getX() + this.getWidth())
        {
            p_146188_3_ = this.getX() + this.getWidth();
        }

        if (p_146188_1_ > this.getX() + this.getWidth())
        {
            p_146188_1_ = this.getX() + this.getWidth();
        }

        Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glLogicOp(GL11.GL_OR_REVERSE);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) p_146188_1_, (double) p_146188_4_, 0.0D);
        tessellator.addVertex((double) p_146188_3_, (double) p_146188_4_, 0.0D);
        tessellator.addVertex((double) p_146188_3_, (double) p_146188_2_, 0.0D);
        tessellator.addVertex((double) p_146188_1_, (double) p_146188_2_, 0.0D);
        tessellator.draw();
        GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
