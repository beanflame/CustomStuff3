package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.control.listbox.ListBox;

public interface ToolTipProvider
{
    String getToolTip(Control c);

    class Enabled implements ToolTipProvider
    {
        private final String toolTip;

        public Enabled(String toolTip)
        {
            this.toolTip = toolTip;
        }

        @Override
        public String getToolTip(Control c)
        {
            if (c.isEnabled())
                return toolTip;
            return null;
        }
    }

    class SelectedNotEnabled<E> implements ToolTipProvider
    {
        private final ListBox<E> listBox;
        private final String toolTip;

        public SelectedNotEnabled(ListBox<E> listBox, String toolTip)
        {
            this.listBox = listBox;
            this.toolTip = toolTip;
        }

        @Override
        public String getToolTip(Control c)
        {
            if (listBox.getSelectedIndex() != -1 && !c.isEnabled())
                return toolTip;
            return null;
        }
    }
}
