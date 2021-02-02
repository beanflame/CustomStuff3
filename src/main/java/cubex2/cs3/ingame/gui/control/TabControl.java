package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabControl extends Control
{
    private static final int SCROLL_BUTTON_HEIGHT = 10;

    private List<Tab> tabs = Lists.newArrayList();
    private Tab activeTab = null;
    private int tabWidth;
    private int tabHeight;
    private int maxPossibleTabs;
    private int scroll = 0;
    private int maxScroll;
    public TabChangedListener listener;

    public TabControl(int tabWidth, int tabHeight, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.tabWidth = tabWidth;
        this.tabHeight = tabHeight;
        maxPossibleTabs = bounds.getHeight() / tabHeight;
        maxScroll = tabs.size() - maxPossibleTabs;
    }

    public Tab addTab(String title)
    {
        Anchor anchor = new Anchor(0, 0, 0, 0);
        anchor.controlLeft = this;
        anchor.controlRight = this;
        anchor.controlTop = this;
        anchor.controlBottom = this;
        anchor.sameSideLeft = true;
        anchor.sameSideRight = true;
        anchor.sameSideTop = true;
        anchor.sameSideBottom = true;
        Tab tab = new Tab(title, -1, -1, anchor, 0, 0, this);
        tabs.add(tab);
        if (activeTab == null)
            activeTab = tab;
        maxScroll = Math.max(tabs.size() - maxPossibleTabs, 0);
        return tab;
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        for (int i = 0; i < tabs.size(); i++)
        {
            tabs.get(i).onParentResized();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        if (mouseX >= getX() - tabWidth && mouseX < getX() && mouseY >= getY() && mouseY < getY() + getHeight())
        {
            int clickedTab = (mouseY - getY()) / tabHeight + scroll;
            if (clickedTab < tabs.size())
            {
                activeTab = tabs.get(clickedTab);
                if (listener != null)
                    listener.tabChanged(this, activeTab);
            }
        } else if (mouseX >= getX() - tabWidth / 2 - 4 && mouseX < getX() - tabWidth / 2 + 15 && mouseY >= getY() - SCROLL_BUTTON_HEIGHT && mouseY < getY() && scroll > 0)
        {
            scroll--;
        } else if (mouseX >= getX() - tabWidth / 2 - 4 && mouseX < getX() - tabWidth / 2 + 15 && mouseY >= getY() + getHeight() && mouseY < getY() + getHeight() + SCROLL_BUTTON_HEIGHT && scroll < maxScroll)
        {
            scroll++;
        } else if (activeTab != null)
        {
            activeTab.mouseClicked(mouseX, mouseY, button, intoControl);
        }
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (activeTab != null)
        {
            activeTab.mouseDown(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        if (activeTab != null)
        {
            activeTab.mouseUp(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (activeTab != null)
        {
            activeTab.keyTyped(c, key);
        }
    }

    @Override
    public void onUpdate()
    {
        if (activeTab != null)
        {
            activeTab.onUpdate();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && mouseX >= getX() - tabWidth && mouseX < getX() && mouseY >= getY() && mouseY < getY() + getHeight())
        {
            scroll = MathHelper.clamp_int(scroll - wheel, 0, maxScroll);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(Textures.CONTROLS2);

        // draw scroll buttons
        if (tabs.size() > maxPossibleTabs)
        {
            drawScrollButton(-SCROLL_BUTTON_HEIGHT, mouseX, mouseY, 180, scroll > 0);
            drawScrollButton(getHeight(), mouseX, mouseY, 191, scroll < maxScroll);
        }

        // draw tab backgrounds
        for (int i = 0; i < maxPossibleTabs; i++)
        {
            int idx = i + scroll;
            if (idx >= tabs.size())
                break;
            Tab tab = tabs.get(idx);
            int x = getX() - tabWidth + 4;
            int y = getY() + i * tabHeight;
            int u = 0;
            int v = (tab == activeTab ? 0 : 90) + (i == 0 ? 0 : i == maxPossibleTabs - 1 ? 60 : 30);

            // Top Left
            drawTexturedModalRect(x, y, u, v, tabWidth / 2, tabHeight / 2);
            // Top Right
            drawTexturedModalRect(x + tabWidth / 2, y, 100 - tabWidth / 2, v, tabWidth / 2, tabHeight / 2);
            // Bottom Left
            drawTexturedModalRect(x, y + tabHeight / 2, 0, 30 - tabHeight / 2 + v, tabWidth / 2, tabHeight / 2);
            // Bottom Right
            drawTexturedModalRect(x + tabWidth / 2, y + tabHeight / 2, 100 - tabWidth / 2, 30 - tabHeight / 2 + v, tabWidth / 2, tabHeight / 2);

        }

        // draw tab text
        for (int i = 0; i < maxPossibleTabs; i++)
        {
            int idx = i + scroll;
            if (idx >= tabs.size())
                break;
            Tab tab = tabs.get(idx);
            int x = getX() - tabWidth + 9;
            int y = getY() + i * tabHeight + (tabHeight - 9) / 2 + 1;
            mc.fontRenderer.drawString(tab.title, x, y, tab == activeTab ? Color.BLACK : 0x404040);
        }

        if (activeTab != null)
        {
            activeTab.draw(mouseX, mouseY, renderTick);
        }
    }

    private void drawScrollButton(int yOffset, int mouseX, int mouseY, int v, boolean enabled)
    {
        int x = getX() - tabWidth / 2 - 4;
        int y = getY() + yOffset;
        boolean hover = mouseX >= x && mouseX < x + 19 && mouseY >= y && mouseY < y + SCROLL_BUTTON_HEIGHT;
        int u = enabled ? hover ? 0 : 19 : 38;
        drawTexturedModalRect(x, y, u, v, 19, SCROLL_BUTTON_HEIGHT);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (activeTab != null)
        {
            activeTab.drawForeground(mouseX, mouseY);
        }
    }
}
