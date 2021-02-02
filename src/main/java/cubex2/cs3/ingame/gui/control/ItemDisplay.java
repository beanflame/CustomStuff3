package cubex2.cs3.ingame.gui.control;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.WindowSelectBlock;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class ItemDisplay extends ValidityControl<ItemDisplay> implements ISelectElementCallback<ItemStack>
{
    private ItemStack originStack;
    private ItemStack currentRenderStack;
    private List<ItemStack> renderStacks;

    private boolean drawSlotBackground = false;
    private boolean showIdAndDamageValue = false;
    private boolean clearOnRightClick = false;
    private int tickCounter = 1;
    private int currentIndex = 0;

    private boolean usesSelectItemDialog = false;
    private boolean usesSelectBlockDialog = false;
    private boolean dialogWildCards = false;
    private Predicate<ItemStack> filter;

    private IToolTipModifier toolTipModifier;


    public ItemDisplay(Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(16, 16, anchor, offsetX, offsetY, parent);
    }

    public ItemDisplay useSelectItemDialog(boolean wildCardStacks)
    {
        usesSelectItemDialog = true;
        dialogWildCards = wildCardStacks;
        return this;
    }

    public ItemDisplay useSelectBlockDialog()
    {
        usesSelectBlockDialog = true;
        return this;
    }

    public ItemDisplay useSelectBlockDialog(Predicate<ItemStack> filter)
    {
        usesSelectBlockDialog = true;
        this.filter = filter;
        return this;
    }

    public ItemDisplay setDrawSlotBackground()
    {
        drawSlotBackground = true;
        return this;
    }

    public void setToolTipModifier(IToolTipModifier modifier)
    {
        toolTipModifier = modifier;
    }

    public void setShowIdAndDV()
    {
        showIdAndDamageValue = true;
    }

    public void setClearOnRightClick()
    {
        clearOnRightClick = true;
    }

    public void setStackSize(int stackSize)
    {
        if (originStack != null)
        {
            originStack.stackSize = stackSize;
            if (renderStacks != null)
            {
                for (ItemStack renderStack : renderStacks)
                {
                    renderStack.stackSize = originStack.stackSize;
                }
            } else
            {
                currentRenderStack.stackSize = originStack.stackSize;
            }
        }

        valueChanged();
    }

    public ItemStack getItemStack()
    {
        return originStack;
    }

    public void setItemStack(ItemStack stack)
    {
        if (stack == null)
        {
            originStack = null;
            currentRenderStack = null;
            renderStacks = null;
            valueChanged();
            return;
        }
        originStack = stack;
        currentRenderStack = stack;
        if (stack.getItem() != null)
        {
            if (stack.getHasSubtypes() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            {
                renderStacks = Lists.newArrayList();
                stack.getItem().getSubItems(stack.getItem(), null, renderStacks);
                if (renderStacks.size() == 0)
                {
                    renderStacks = null;
                } else
                {
                    for (ItemStack renderStack : renderStacks)
                    {
                        renderStack.stackSize = originStack.stackSize;
                    }
                    currentRenderStack = renderStacks.get(0);
                }
            } else if (stack.isItemStackDamageable() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            {
                renderStacks = Lists.newArrayList();
                for (int i = 0; i < 5; i++)
                {
                    renderStacks.add(new ItemStack(stack.getItem(), 1, (int) (stack.getMaxDamage() * 0.25 * i)));
                }
                currentRenderStack = renderStacks.get(0);
            } else
            {
                renderStacks = null;
            }

            if (currentRenderStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            {
                currentRenderStack.setItemDamage(0);
            }
        }

        valueChanged();
    }


    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 1 && clearOnRightClick)
        {
            setItemStack(null);
        } else if (button == 0)
        {
            if (usesSelectItemDialog)
            {
                GuiBase.openWindow(new WindowSelectItem(dialogWildCards, this));
            } else if (usesSelectBlockDialog)
            {
                GuiBase.openWindow(new WindowSelectBlock(false, false, this, filter));
            }
        }
    }

    @Override
    public void onUpdate()
    {
        if (renderStacks != null && tickCounter++ % 20 == 0)
        {
            currentIndex = (currentIndex + 1) % renderStacks.size();
            currentRenderStack = renderStacks.get(currentIndex);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (drawSlotBackground)
        {
            GL11.glColor3f(1f, 1f, 1f);
            mc.renderEngine.bindTexture(Textures.CONTROLS);
            drawTexturedModalRect(bounds.getX() - 1, bounds.getY() - 1, 200, 0, 18, 18);
        }

        if (currentRenderStack == null || currentRenderStack.getItem() == null)
            return;

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);

        GuiBase.itemRenderer.zLevel = 100.0F;
        GuiBase.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, currentRenderStack, bounds.getX(), bounds.getY());
        GuiBase.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, currentRenderStack, bounds.getX(), bounds.getY());
        GuiBase.itemRenderer.zLevel = 0.0F;

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();


        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (currentRenderStack == null || currentRenderStack.getItem() == null)
            return;

        if (isMouseOverControl(mouseX, mouseY))
        {
            List list = currentRenderStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

            for (int k = 0; k < list.size(); ++k)
            {
                if (k == 0)
                {
                    list.set(k, currentRenderStack.getRarity().rarityColor.toString() + list.get(k));
                } else
                {
                    list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
                }
            }

            if (showIdAndDamageValue)
            {
                list.add(EnumChatFormatting.GRAY + "Name: " + GameData.itemRegistry.getNameForObject(originStack.getItem()));
                list.add(EnumChatFormatting.GRAY + "DV: " + originStack.getItemDamage());
            }

            if (toolTipModifier != null)
            {
                toolTipModifier.modifyToolTip(list, originStack);
            }

            FontRenderer font = currentRenderStack.getItem().getFontRenderer(currentRenderStack);
            GuiHelper.drawHoveringText(list, mouseX, mouseY, font == null ? mc.fontRenderer : font);
        }
    }

    @Override
    public void itemSelected(ItemStack stack)
    {
        setItemStack(stack);
    }
}
