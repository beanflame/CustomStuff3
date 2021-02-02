package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class Window extends ControlContainer implements IValueListener
{
    protected static final int BACK = 1;
    protected static final int CANCEL = 2;
    protected static final int NEW = 4;
    protected static final int CREATE = 8;
    protected static final int DELETE = 16;
    protected static final int EDIT = 32;
    protected static final int SELECT = 64;

    public String title;
    public String tag = null;
    public boolean drawSlots = false;

    protected Button btnBack;
    protected Button btnCancel;
    protected Button btnNew;
    protected Button btnCreate;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSelect;
    protected Button btnPlus;

    protected boolean drawBackground = true;

    private final int usedControls;
    private Control focusedControl = null;
    public List<Control> tabStopControls = Lists.newArrayList();

    private List<IValidityControl> validityControls = Lists.newArrayList();

    public Window(int width, int height)
    {
        this(null, 0, width, height);
    }

    public Window(int usedControls, int width, int height)
    {
        this(null, usedControls, width, height);
    }

    public Window(String title, int width, int height)
    {
        this(title, 0, width, height);
    }

    public Window(String title, int usedControls, int width, int height)
    {
        super(width, height, null, 0, 0, null);
        this.usedControls = usedControls;
        this.title = title;

        init();
    }

    private void init()
    {
        controls.clear();
        validityControls.clear();

        if ((usedControls & BACK) == BACK)
        {
            btnBack = button(underline("Back", 2)).right(7).bottom(7).add();
        }
        if ((usedControls & CANCEL) == CANCEL)
        {
            btnCancel = button(underline("Cancel", 0)).right(7).bottom(7).add();
        }
        if ((usedControls & SELECT) == SELECT)
        {
            btnSelect = button(underline("Select", 0)).left(7).bottom(7).add();
        }
        int xOffset = 0;
        if ((usedControls & NEW) == NEW)
        {
            btnNew = button(underline("New", 0)).left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & CREATE) == CREATE)
        {
            btnCreate = button(underline("Create", 1)).left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & EDIT) == EDIT)
        {
            btnEdit = button(underline("Edit", 0)).left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & DELETE) == DELETE)
        {
            btnDelete = button(underline("Delete", 0)).left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
    }

    private String underline(String s, int pos)
    {
        return s.substring(0, pos) + "\u00a7n" + s.substring(pos, pos + 1) + "\u00a7r" + s.substring(pos + 1);
    }

    public void addValidityControl(IValidityControl vc)
    {
        validityControls.add(vc);
        vc.setValueChangedListener(this);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (key == Keyboard.KEY_E && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnEdit != null && btnEdit.isEnabled())
                handleDefaultButtonClick(btnEdit);
        } else if (key == Keyboard.KEY_C && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnCancel != null && btnCancel.isEnabled())
                handleDefaultButtonClick(btnCancel);
            else if (btnBack != null && btnBack.isEnabled())
                handleDefaultButtonClick(btnBack);
        } else if (key == Keyboard.KEY_D && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnDelete != null && btnDelete.isEnabled())
                controlClicked(btnDelete, btnDelete.getX() + 1, btnDelete.getY() + 1);
        } else if (key == Keyboard.KEY_N && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnNew != null && btnNew.isEnabled())
                controlClicked(btnNew, btnNew.getX() + 1, btnNew.getY() + 1);
        } else if (key == Keyboard.KEY_R && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnCreate != null && btnCreate.isEnabled())
                controlClicked(btnCreate, btnCreate.getX() + 1, btnCreate.getY() + 1);
        } else if (key == Keyboard.KEY_S && Keyboard.isKeyDown(Keyboard.KEY_LMENU))
        {
            if (btnSelect != null && btnSelect.isEnabled())
                controlClicked(btnSelect, btnSelect.getX() + 1, btnSelect.getY() + 1);
        } else if (key == Keyboard.KEY_TAB)
        {
            if (tabStopControls.isEmpty()) return;

            Control next;
            if (focusedControl == null || !tabStopControls.contains(focusedControl))
                next = tabStopControls.get(0);
            else
                next = tabStopControls.get((tabStopControls.indexOf(focusedControl) + 1) % tabStopControls.size());
            claimFocus(next);
        } else
        {
            super.keyTyped(c, key);
        }
    }

    public Control getFocusedControl()
    {
        return focusedControl;
    }

    public boolean claimFocus(Control c)
    {
        if (focusedControl != null && !focusedControl.canReleaseFocus())
            return false;
        if (focusedControl != null)
            focusedControl.onUnfocus();
        focusedControl = c;
        if (focusedControl != null)
            focusedControl.onFocus();
        return true;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        claimFocus(null);

        super.mouseClicked(mouseX, mouseY, button, intoControl);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        handleDefaultButtonClick(c);
    }

    /**
     * Handles mouse-click on edit, back and cancel buttons, if c is one of those.
     *
     * @param c The clicked control.
     */
    protected void handleDefaultButtonClick(Control c)
    {
        if (c == btnBack)
        {
            GuiBase.openPrevWindow();
        } else if (c == btnCancel)
        {
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            handleEditButtonClicked();
        }
    }

    protected void handleEditButtonClicked()
    {

    }

    @Override
    public void onValueChanged(Control c)
    {
        updateValidation();
    }

    protected void updateValidation()
    {
        boolean allValidValues = true;
        for (IValidityControl c : validityControls)
        {
            if (!c.hasValidValue())
            {
                allValidValues = false;
                break;
            }
        }

        if (btnCreate != null)
            btnCreate.setEnabled(allValidValues);

        if (btnEdit != null)
            btnEdit.setEnabled(allValidValues);
    }


    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (drawBackground)
        {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            mc.renderEngine.bindTexture(Textures.BG);
            GuiHelper.drawRectSliced(bounds, 0, 0, 256, 256);
        }

        if (title != null)
        {
            mc.renderEngine.bindTexture(Textures.CONTROLS2);
            int width = mc.fontRenderer.getStringWidth(title) + 14;

            drawTexturedModalRect(bounds.getX() + (bounds.getWidth() - width) / 2, bounds.getY() - 15, 156, 0, width / 2, 18);
            drawTexturedModalRect(bounds.getX() + (bounds.getWidth() - width) / 2 + width / 2, bounds.getY() - 15, 256 - width / 2, 0, width / 2, 18);

            mc.fontRenderer.drawString(title, bounds.getX() + (bounds.getWidth() - mc.fontRenderer.getStringWidth(title)) / 2, bounds.getY() - 10, Color.BLACK);
        }

        super.draw(mouseX, mouseY, renderTick);
    }

    public void onGuiClosed()
    {

    }

}
