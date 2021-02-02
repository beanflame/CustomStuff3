package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.control.builder.ControlBuilder;

public class ScrollContainer extends ControlContainer implements IValueListener<Slider>
{
    private static final int WHEEL_STEP = 9 + 4;
    public static final int SLIDER_WIDTH = 12;
    private int totalHeight;

    private Slider slider;
    ControlContainer scrollerWindow;
    private ControlContainer contentContainer;

    private int currentScroll;

    public ScrollContainer(int totalHeight, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);

        this.totalHeight = totalHeight;

        slider = verticalSlider(totalHeight).top(0).bottom(0).right(0).width(SLIDER_WIDTH).add();
        slider.setValueListener(this);
        slider.setWheelScrollEverywhere(true);
        slider.setWheelScrollStep(WHEEL_STEP);

        scrollerWindow = container().at(0, 0).bottom(slider, 0, true).right(slider, 3).add();
        scrollerWindow.enableScissor = true;
        contentContainer = new ContentContainerBuilder(this, scrollerWindow).fill().add();
    }

    public ControlContainer content()
    {
        return contentContainer;
    }

    /**
     * Makes wheel scroll and the use of pgDown... keys only work if mouse is over listbox.
     */
    public void disableGlobalScrolling()
    {
        slider.setWheelScrollEverywhere(false);
        slider.setWheelScrollParent(true);
    }

    private void setScroll(int value)
    {
        if (currentScroll != value)
        {
            currentScroll = value;
            contentContainer.offsetY = -currentScroll;
            onParentResized();
        }
    }

    public void automaticTotalHeight()
    {
        int newHeight = 0;
        for (Control c : content().controls)
        {
            if (c.getY() + c.getHeight() > newHeight)
                newHeight = c.getY() + c.getHeight();
        }
        totalHeight = newHeight - getY();
        onParentResized();
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        slider.setMaxValue(totalHeight - slider.getHeight());
    }

    @Override
    public void onValueChanged(Slider control)
    {
        setScroll(slider.getValue());
    }

    private static final class ContentContainer extends ControlContainer
    {
        private final ScrollContainer scroller;

        public ContentContainer(ScrollContainer scroller, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
        {
            super(width, height, anchor, offsetX, offsetY, parent);
            this.scroller = scroller;
        }

        /*@Override
        protected void controlClicked(Control c, int mouseX, int mouseY, int button)
        {
            scroller.controlClicked(c, mouseX, mouseY, button);
        }*/

        @Override
        public boolean isMouseOverControl(int mouseX, int mouseY)
        {
            return scroller.scrollerWindow.isMouseOverControl(mouseX, mouseY);
        }
    }

    private static final class ContentContainerBuilder extends ControlBuilder<ContentContainer>
    {
        private final ScrollContainer scroller;

        public ContentContainerBuilder(ScrollContainer scroller, ControlContainer c)
        {
            super(c);
            this.scroller = scroller;
        }

        @Override
        protected ContentContainer newInstance()
        {
            return new ContentContainer(scroller, width, height, anchor, offsetX, offsetY, container);
        }
    }
}
