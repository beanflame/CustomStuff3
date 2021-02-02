package cubex2.cs3.ingame.gui.control;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Point;

import java.util.ArrayList;
import java.util.List;

public class TextField extends Control
{
    private static final int COMMENT_COLOR = Color.DARK_GREY;
    private static final int STRING_COLOR = 0xff5f8651;
    private static final int NUMBER_COLOR = 0xff6897bb;
    private static final int KEYWORD_COLOR = 0xffb3602d;

    private final FontRenderer fontRenderer;

    private boolean canLoseFocus = true;
    private boolean isFocused;
    private int horizonzalScroll = 0;
    private int verticalScroll = 0;
    private int linesToDisplay;
    private int colsToDisplay;

    // Content
    private List<String> lines = Lists.newArrayList("");
    private final List<List<Integer>> colors = Lists.newArrayList();
    private boolean syntaxHighlighting = true;

    // Cursor
    private boolean replaceMode = false;
    private int cursorX = 0;
    private int cursorY = 0;
    private int counter = 0;
    private int selectionEndX = -1;
    private int selectionEndY = -1;

    public TextField(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        fontRenderer = mc.fontRenderer;
        linesToDisplay = getHeight() / 10;
        colsToDisplay = getWidth() / 6;
    }

    public void disableSyntaxHighlighting()
    {
        syntaxHighlighting = false;
    }

    public String getText()
    {
        return Joiner.on('\n').join(lines);
    }

    public void setText(String text)
    {
        if (text == null) text = "";
        setTextNoCursorReset(text);

        cursorX = 0;
        cursorY = 0;
        verticalScroll = 0;
        horizonzalScroll = 0;
    }

    private void setTextNoCursorReset(String text)
    {
        lines = Lists.newArrayList(text.split("\n"));
        textChanged();
    }

    public String getSelectedText()
    {
        if (selectionEndX == -1)
            return "";

        int selStartX = cursorX;
        int selStarY = cursorY;
        int selEndX = selectionEndX;
        int selEndY = selectionEndY;
        if (getOneDimPosition(cursorX, cursorY) > getOneDimPosition(selectionEndX, selectionEndY))
        {
            selStartX = selectionEndX;
            selStarY = selectionEndY;
            selEndX = cursorX;
            selEndY = cursorY;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selEndY - selStarY + 1; i++)
        {
            int startIdx = i == 0 ? selStartX : 0;
            int endIdx = i == selEndY - selStarY ? selEndX : lines.get(i + selStarY).length();
            sb.append(lines.get(i + selStarY).substring(startIdx, endIdx));
            if (i < selEndY - selStarY)
                sb.append('\n');
        }
        return sb.toString();
    }

    private void textChanged()
    {
        if (!syntaxHighlighting)
            return;

        boolean inMultiLineComment = false;
        boolean inQuotes = false;
        char quoteChar = 0;
        colors.clear();
        for (int i = 0; i < lines.size(); i++)
        {
            colors.add(new ArrayList<Integer>());
            for (int j = 0; j < lines.get(i).length(); j++)
            {
                char c = lines.get(i).charAt(j);
                boolean isLastChar = j == lines.get(i).length() - 1;
                char nextChar = isLastChar ? 0 : lines.get(i).charAt(j + 1);

                if (inMultiLineComment)
                {
                    colors.get(i).add(COMMENT_COLOR);
                    if (c == '*' && !isLastChar && nextChar == '/')
                    {
                        colors.get(i).add(COMMENT_COLOR);
                        inMultiLineComment = false;
                        j++;
                    }
                    continue;
                }
                boolean quoteStateChanged = false;
                if ((c == '"' && quoteChar != '\'') || (c == '\'' && quoteChar != '"'))
                {
                    if (!inQuotes || (inQuotes && numBackslashes(j, i) % 2 == 0))
                    {
                        inQuotes = !inQuotes;
                        quoteStateChanged = true;
                    }
                    quoteChar = inQuotes ? c : 0;
                }
                if (!inQuotes)
                {
                    if (c == '/' && !isLastChar && nextChar == '*')
                    {
                        colors.get(i).add(COMMENT_COLOR);
                        colors.get(i).add(COMMENT_COLOR);
                        inMultiLineComment = true;
                        j++;
                        continue;
                    }
                    if (c == '/' && !isLastChar && nextChar == '/')
                    {
                        while (true)
                        {
                            colors.get(i).add(COMMENT_COLOR);
                            if (++j == lines.get(i).length())
                                break;
                        }
                        break;
                    } else if (c == ',' || c == ';')
                    {
                        colors.get(i).add(KEYWORD_COLOR);
                        continue;
                    } else if (Character.isDigit(c) &&
                            (j == 0 || !Character.isJavaIdentifierPart(lines.get(i).charAt(j - 1))))
                    {
                        while (true)
                        {
                            colors.get(i).add(NUMBER_COLOR);

                            if (++j == lines.get(i).length())
                                break;

                            char c1 = lines.get(i).charAt(j);
                            if (!Character.isLetterOrDigit(c1) && c1 != '.')
                                break;
                        }
                        j--;

                        continue;
                    } else
                    {
                        int length = keyWordLength(i, j);
                        if (length != 0)
                        {
                            for (int k = 0; k < length; k++)
                            {
                                colors.get(i).add(KEYWORD_COLOR);
                                j++;
                            }
                            j--;
                            continue;
                        }
                    }
                }
                colors.get(i).add(inQuotes || quoteStateChanged ? STRING_COLOR : Color.WHITE);
            }
            inQuotes = false;
        }
    }

    private static final String[] RESERVED_WORDS = new String[]{"break", "case", "catch", "continue",
            "debugger", "default", "delete", "do", "else", "finally", "for", "function", "if", "in",
            "instanceof", "new", "return", "switch", "this", "throw", "try", "typeof", "var", "void",
            "while", "with", "true", "false"};

    private int keyWordLength(int line, int pos)
    {
        int length = lines.get(line).length();
        for (String reservedWord : RESERVED_WORDS)
        {
            if (pos + reservedWord.length() <= length) // Enoguh space for word
            {
                String wordInLine = lines.get(line).substring(pos, pos + reservedWord.length());
                if (wordInLine.equals(reservedWord)) // matches word
                {
                    if (pos == 0 || !Character.isJavaIdentifierPart(lines.get(line).charAt(pos - 1))) // Check previous char
                    {
                        if (pos + reservedWord.length() == length || !Character.isJavaIdentifierPart(lines.get(line).charAt(pos + reservedWord.length()))) // Check next char
                        {
                            return reservedWord.length();
                        }
                    }
                }
            }
        }

        return 0;
    }

    private int numBackslashes(int pos, int line)
    {
        for (int i = pos - 1; i >= 0; i--)
        {
            if (lines.get(line).charAt(i) != '\\')
            {
                return pos - 1 - i;
            }
        }

        return 0;
    }

    private void deleteSelection()
    {
        int selStartX = cursorX;
        int selStarY = cursorY;
        int selEndX = selectionEndX;
        int selEndY = selectionEndY;
        if (getOneDimPosition(cursorX, cursorY) > getOneDimPosition(selectionEndX, selectionEndY))
        {
            selStartX = selectionEndX;
            selStarY = selectionEndY;
            selEndX = cursorX;
            selEndY = cursorY;
        }

        String textBeforeSel = null;
        String textAfterSel = null;
        try
        {
            textBeforeSel = lines.get(selStarY).substring(0, selStartX);
            textAfterSel = lines.get(selEndY).substring(selEndX);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < selEndY - selStarY + 1; i++)
        {
            lines.remove(selStarY);
        }
        lines.add(selStarY, textBeforeSel + textAfterSel);

        selectionEndX = -1;
        selectionEndY = -1;

        textChanged();
    }

    private void writeText(String s)
    {
        if (selectionEndX != -1)
        {
            deleteSelection();
            setCursorPosition(cursorX, cursorY);
        }

        if (s.equals("\n"))
        {
            String beforeNewLine = lines.get(cursorY).substring(0, cursorX);
            lines.set(cursorY, lines.get(cursorY).substring(cursorX));
            lines.add(cursorY, beforeNewLine);
            cursorY++;
            cursorX = 0;
        } else
        {
            s = filterAllowedCharacters(s);

            String prev = lines.get(cursorY);
            lines.set(cursorY, prev.substring(0, cursorX) + s + prev.substring(replaceMode && cursorX < lines.get(cursorY).length() ? cursorX + 1 : cursorX, prev.length()));
            cursorX += s.length();
        }
        scrollToCursor();

        textChanged();
    }

    private String filterAllowedCharacters(String s)
    {
        StringBuilder stringbuilder = new StringBuilder();
        char[] achar = s.toCharArray();
        int i = achar.length;

        for (int j = 0; j < i; ++j)
        {
            char c0 = achar[j];

            if (c0 == 167 || ChatAllowedCharacters.isAllowedCharacter(c0))
            {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    private Point getLeftWordPosition()
    {
        if (cursorX == 0 && cursorY == 0)
            return new Point(0, 0);

        int x = cursorX;
        int y = cursorY;

        if (x == 0)
        {
            while (y > 0 && lines.get(--y).length() == 0) ;
            x = lines.get(y).length();
        } else
        {
            while (x > 0 && lines.get(y).charAt(x - 1) == ' ')
            {
                --x;
            }

            boolean passedNonWhiteSpace = false;
            while (x > 0 && lines.get(y).charAt(x - 1) != ' ')
            {
                passedNonWhiteSpace = true;
                --x;
            }

            if (x == 0 && !passedNonWhiteSpace)
            {
                while (lines.get(--y).length() == 0) ;
                x = lines.get(y).length();
            }
        }

        return new Point(x, y);
    }

    private Point getRightWordPosition()
    {
        if (cursorY == lines.size() - 1 && cursorX == lines.get(cursorY).length())
            return new Point(cursorX, cursorY);

        int x = cursorX;
        int y = cursorY;

        if (x == lines.get(y).length())
        {
            while (y < lines.size() - 1 && lines.get(++y).length() == 0) ;
            x = 0;
        }

        int lineLength = lines.get(y).length();
        x = lines.get(y).indexOf(32, x);

        if (x == -1)
        {
            x = lineLength;
        } else
        {
            while (x < lineLength && lines.get(y).charAt(x) == 32)
            {
                x++;
            }
        }

        return new Point(x, y);
    }

    private int getOneDimPosition(int x, int y)
    {
        int pos = 0;
        for (int i = 0; i < y; i++)
        {
            pos += lines.get(i).length() + 1;
        }
        pos += x;

        return pos;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        if (intoControl)
        {
            isFocused = true;
            int relX = mouseX - getX() - 2;
            int relY = mouseY - getY() - 2;
            setCursorPosition(relX / 6 + horizonzalScroll, relY / 9 + verticalScroll);
        } else if (canLoseFocus)
        {
            isFocused = false;
        }
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (isFocused)
        {
            int newX;
            int newY;
            switch (key)
            {
                case Keyboard.KEY_LEFT:
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        Point p = getLeftWordPosition();
                        setCursorPosition(p.getX(), p.getY());
                    } else
                    {
                        newX = cursorX - 1;
                        newY = cursorY;
                        if (newX < 0)
                        {
                            if (cursorY == 0)
                                newX = 0;
                            else
                            {
                                newX = Integer.MAX_VALUE;
                                newY--;
                            }
                        }
                        setCursorPosition(newX, newY);
                    }
                    break;

                case Keyboard.KEY_RIGHT:
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        Point p = getRightWordPosition();
                        setCursorPosition(p.getX(), p.getY());
                    } else
                    {
                        newX = cursorX + 1;
                        newY = cursorY;
                        if (newX > lines.get(cursorY).length())
                        {
                            if (cursorY == lines.size() - 1)
                                newX--;
                            else
                            {
                                newX = 0;
                                newY++;
                            }
                        }
                        setCursorPosition(newX, newY);
                    }
                    break;

                case Keyboard.KEY_UP:
                    newX = cursorY == 0 ? 0 : cursorX;
                    newY = cursorY - 1;
                    setCursorPosition(newX, newY);
                    break;

                case Keyboard.KEY_DOWN:
                    newX = cursorY == lines.size() - 1 ? lines.get(cursorY).length() : cursorX;
                    newY = cursorY + 1;
                    setCursorPosition(newX, newY);
                    break;

                case Keyboard.KEY_END:
                    newX = GuiScreen.isCtrlKeyDown() ? Integer.MAX_VALUE : lines.get(cursorY).length();
                    newY = GuiScreen.isCtrlKeyDown() ? Integer.MAX_VALUE : cursorY;
                    setCursorPosition(newX, newY);
                    break;

                case Keyboard.KEY_HOME:
                    newX = 0;
                    newY = GuiScreen.isCtrlKeyDown() ? 0 : cursorY;
                    setCursorPosition(newX, newY);
                    break;

                case Keyboard.KEY_PRIOR:
                    newX = cursorY == 0 ? 0 : cursorX;
                    newY = cursorY - linesToDisplay + 1;
                    int prevScroll = verticalScroll;
                    setCursorPosition(newX, newY);
                    verticalScroll = Math.max(0, prevScroll - linesToDisplay + 1);
                    break;

                case Keyboard.KEY_NEXT:
                    newX = cursorY == lines.size() - 1 ? lines.get(cursorY).length() : cursorY;
                    newY = cursorY + linesToDisplay - 1;
                    setCursorPosition(newX, newY);
                    verticalScroll = Math.min(cursorY, lines.size() - linesToDisplay);
                    break;

                case Keyboard.KEY_RETURN:
                    writeText("\n");
                    break;

                case Keyboard.KEY_BACK:
                    if (selectionEndX != -1)
                        deleteSelection();
                    else
                    {
                        if (cursorX == 0 && cursorY != 0)
                        {
                            String newLine = lines.get(cursorY - 1) + lines.get(cursorY);
                            lines.remove(cursorY);
                            lines.set(cursorY - 1, newLine);
                            setCursorPosition(Integer.MAX_VALUE, cursorY - 1);
                            textChanged();
                        } else if (cursorX > 0)
                        {
                            String currentLine = lines.get(cursorY);
                            lines.set(cursorY, currentLine.substring(0, cursorX - 1) + currentLine.substring(cursorX));
                            setCursorPosition(cursorX - 1, cursorY);
                            textChanged();
                        }
                        if (cursorX == horizonzalScroll && horizonzalScroll > 0)
                        {
                            horizonzalScroll--;
                        }
                    }
                    break;

                case Keyboard.KEY_DELETE:
                    if (selectionEndX != -1)
                    {
                        int selStartX = cursorX;
                        int selStarY = cursorY;
                        if (getOneDimPosition(cursorX, cursorY) > getOneDimPosition(selectionEndX, selectionEndY))
                        {
                            selStartX = selectionEndX;
                            selStarY = selectionEndY;
                        }

                        deleteSelection();
                        setCursorPosition(selStartX, selStarY);
                    } else
                    {
                        if (cursorX == lines.get(cursorY).length() && cursorY != lines.size() - 1)
                        {
                            String newLine = lines.get(cursorY) + lines.get(cursorY + 1);
                            lines.remove(cursorY);
                            lines.set(cursorY, newLine);
                            setCursorPosition(cursorX, cursorY);
                            textChanged();
                        } else if (cursorX < lines.get(cursorY).length())
                        {
                            String currentLine = lines.get(cursorY);
                            lines.set(cursorY, currentLine.substring(0, cursorX) + currentLine.substring(cursorX + 1));
                            textChanged();
                        }
                    }
                    break;

                case Keyboard.KEY_INSERT:
                    replaceMode = !replaceMode;
                    break;

                default:
                    if (key == Keyboard.KEY_A && GuiScreen.isCtrlKeyDown())
                    {
                        cursorX = 0;
                        cursorY = 0;
                        selectionEndY = lines.size() - 1;
                        selectionEndX = lines.get(selectionEndY).length();
                    } else if (key == Keyboard.KEY_V && GuiScreen.isCtrlKeyDown())
                    {
                        String text = GuiScreen.getClipboardString();
                        String[] split = text.split("\n");
                        for (int i = 0; i < split.length; i++)
                        {
                            writeText(split[i]);
                            if (i < split.length - 1)
                                writeText("\n");
                        }
                    } else if (key == Keyboard.KEY_C && GuiScreen.isCtrlKeyDown())
                    {
                        GuiScreen.setClipboardString(getSelectedText());
                    } else if (c == 167 || ChatAllowedCharacters.isAllowedCharacter(c))
                    {
                        writeText(String.valueOf(c));
                    }
            }
            scrollToCursor();
        }
    }

    private void scrollToCursor()
    {
        while (cursorX >= horizonzalScroll + colsToDisplay)
        {
            horizonzalScroll++;
        }

        while (cursorX < horizonzalScroll)
        {
            horizonzalScroll--;
        }

        while (cursorY >= verticalScroll + linesToDisplay)
        {
            verticalScroll++;
        }

        while (cursorY < verticalScroll)
        {
            verticalScroll--;
        }
    }

    private void setCursorPosition(int x, int y)
    {
        int prevX = cursorX;
        int prevY = cursorY;

        y = MathHelper.clamp_int(y, 0, lines.size() - 1);
        x = MathHelper.clamp_int(x, 0, lines.get(y).length());

        cursorX = x;
        cursorY = y;
        scrollToCursor();

        if (!GuiBase.isShiftKeyDown())
        {
            selectionEndX = -1;
            selectionEndY = -1;
        } else
        {
            if (selectionEndX == -1)
            {
                selectionEndX = prevX;
                selectionEndY = prevY;
            }
        }
    }

    @Override
    public void onUpdate()
    {
        counter++;
        if (GuiBase.dWheel != 0)
        {
            int maxVertScroll = Math.max(0, lines.size() - linesToDisplay);
            verticalScroll = MathHelper.clamp_int(verticalScroll - GuiBase.dWheel, 0, maxVertScroll);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GuiHelper.drawRect(bounds, Color.BLACK);

        int oneDimCursor = getOneDimPosition(cursorX, cursorY);
        int oneDimSelection = getOneDimPosition(selectionEndX, selectionEndY);

        int selStart = selectionEndX == -1 ? -1 : Math.min(oneDimCursor, oneDimSelection);
        int selEnd = selectionEndX == -1 ? -1 : Math.max(oneDimCursor, oneDimSelection);

        for (int i = 0; i < linesToDisplay; i++)
        {
            int row = i + verticalScroll;
            if (row >= lines.size())
                break;

            for (int j = 0; j < colsToDisplay; j++)
            {
                int col = j + horizonzalScroll;
                if (col >= lines.get(row).length())
                    break;

                char c = lines.get(row).charAt(col);
                int charWidth = fontRenderer.getCharWidth(c);
                int charOffset = (6 - charWidth) / 2;

                int pos = getOneDimPosition(col, row);
                int color = syntaxHighlighting ? colors.get(row).get(col) : Color.WHITE;
                if (pos >= selStart && pos < selEnd)
                    GuiHelper.drawRect(getX() + 2 + j * 6 - 1, getY() + 2 + i * 9, getX() + 2 + j * 6 + 6, getY() + 2 + i * 9 + 8, Color.BLUE);
                fontRenderer.drawString(String.valueOf(c), getX() + 2 + j * 6 + charOffset, getY() + 2 + i * 9, color);

            }
        }

        if (isFocused && counter % 20 < 10 && cursorY >= verticalScroll && cursorY < verticalScroll + linesToDisplay)
        {
            if (!replaceMode && cursorY < lines.size() && cursorX < lines.get(cursorY).length())
            {
                int x1 = getX() + 2 + (cursorX - horizonzalScroll) * 6;
                int y1 = getY() + 1 + (cursorY - verticalScroll) * 9;
                GuiHelper.drawRect(x1, y1, x1 + 1, y1 + 10, Color.WHITE);
            } else
            {
                int x1 = getX() + 2 + (cursorX - horizonzalScroll) * 6;
                int y1 = getY() + 2 + (cursorY - verticalScroll) * 9 + 8;
                GuiHelper.drawRect(x1, y1, x1 + 6, y1 + 1, Color.WHITE);
            }
        }
    }
}
