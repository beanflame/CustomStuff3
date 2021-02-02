package cubex2.cs3.gui;

import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.gui.attributes.GuiAttributes;
import cubex2.cs3.gui.data.ButtonData;
import cubex2.cs3.gui.data.ControlData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.JavaScriptHelper;

public class WindowNormal extends Window
{
    private final GuiAttributes container;
    private final WrappedGui gui;

    public WindowNormal(WrappedGui gui)
    {
        super(gui.container.width, gui.container.height);
        this.container = gui.container;
        this.gui = gui;
        drawBackground = false;

        for (ControlData data : container.guiData.controls)
        {
            data.addToWindow(this, null).controlTag = data;
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c.controlTag != null && c.controlTag instanceof ControlData)
        {
            if (c.controlTag instanceof ButtonData)
            {
                ButtonData data = (ButtonData) c.controlTag;
                if (data.onClicked != null && data.onClicked.script != null)
                {
                    ITriggerData trigger = new TriggerData("onClicked", TriggerType.GUI).setPlayer(ClientHelper.getPlayer());
                    JavaScriptHelper.executeTrigger(data.onClicked.script, trigger, gui.getPack());
                }
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (container.background != null)
        {
            mc.renderEngine.bindTexture(container.background);
            drawTexturedModalRect(bounds, container.bgU, container.bgV);
        }

        super.draw(mouseX, mouseY, renderTick);
    }

    @Override
    public void onGuiClosed()
    {
        GuiBase.openPrevWindow();
    }
}
