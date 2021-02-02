package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class RecipeInputDisplay extends Control
{
    private RecipeInput recipeInput;
    private ItemStack currentRenderStack;
    private List<ItemStack> renderStacks;

    private boolean drawSlotBackground = false;
    private boolean clearOnRightClick = false;
    private boolean showItemData = true;
    private int tickCounter = 1;
    private int currentIndex = 0;

    private IRecipeInputToolTipModifier toolTipModifier;

    public RecipeInputDisplay(Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(16, 16, anchor, offsetX, offsetY, parent);

        if (rootControl instanceof IRecipeInputToolTipModifier)
        {
            toolTipModifier = (IRecipeInputToolTipModifier) rootControl;
        }
    }

    public RecipeInputDisplay setDrawSlotBackground()
    {
        drawSlotBackground = true;
        return this;
    }

    public void setClearOnRightClick()
    {
        clearOnRightClick = true;
    }

    public RecipeInput getRecipeInput()
    {
        return recipeInput;
    }

    public void setRecipeInput(RecipeInput recipeInput)
    {
        if (recipeInput == null)
        {
            this.recipeInput = null;
            currentRenderStack = null;
            renderStacks = null;
            return;
        }
        if (recipeInput instanceof IRecipeInputToolTipModifier)
        {
            toolTipModifier = (IRecipeInputToolTipModifier) recipeInput;
        }
        this.recipeInput = recipeInput;

        renderStacks = Lists.newArrayList();

        for (ItemStack stack : recipeInput.getStacks())
        {
            if (stack.getItem() != null)
            {
                if (stack.getHasSubtypes() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    List<ItemStack> subItems = Lists.newArrayList();
                    stack.getItem().getSubItems(stack.getItem(), null, subItems);
                    renderStacks.addAll(subItems);
                } else if (stack.isItemStackDamageable() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    for (int i = 0; i < 5; i++)
                    {
                        renderStacks.add(new ItemStack(stack.getItem(), 1, (int) (stack.getMaxDamage() * 0.25 * i)));
                    }
                } else
                {
                    renderStacks.add(stack);
                }
            }
        }

        if (renderStacks.size() == 0)
        {
            renderStacks = null;
        } else
        {
            currentRenderStack = renderStacks.get(0);
        }

        if (currentRenderStack != null && currentRenderStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
        {
            currentRenderStack.setItemDamage(0);
        }
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 1 && clearOnRightClick)
        {
            setRecipeInput(null);
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

        if (currentRenderStack == null)
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
        if (currentRenderStack == null)
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

            if (showItemData)
            {
                if (recipeInput.isOreClass())
                {
                    list.add(EnumChatFormatting.GRAY + "Ore class: " + recipeInput.getInput());
                } else
                {
                    ItemStack stack = (ItemStack) recipeInput.getInput();
                    list.add(EnumChatFormatting.GRAY + "Name: " + GameData.itemRegistry.getNameForObject(stack.getItem()));
                    list.add(EnumChatFormatting.GRAY + "DV: " + stack.getItemDamage());
                }
            }

            if (toolTipModifier != null)
            {
                toolTipModifier.modifyToolTip(list, recipeInput);
            }

            FontRenderer font = currentRenderStack.getItem().getFontRenderer(currentRenderStack);
            GuiHelper.drawHoveringText(list, mouseX, mouseY, font == null ? mc.fontRenderer : font);
        }
    }
}
