package cubex2.cs3.lib;

import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.TextBox;
import net.minecraft.item.ItemArmor;

public class TextBoxValidators
{
    public static IValidityProvider FLOAT = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something.";
            } else if (!tb.getText().matches("(-?)([0-9]+)(\\.[0-9]+)*"))
            {
                message = "Enter a decimal number";
            }

            return message;
        }
    };

    public static IValidityProvider FLOAT_ZERO_ONE = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something.";
            } else
            {
                try
                {
                    float f = Float.parseFloat(tb.getText());
                    if (f < 0.0f || f > 1.0f)
                    {
                        message = "Number has to be between 0.0 and 1.0";
                    }
                } catch (NumberFormatException e)
                {
                    message = "Enter a decimal number.";
                }
            }

            return message;
        }
    };

    public static IValidityProvider NEW_ARMOR_MATERIAL_NAME = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something";
            } else
            {
                for (ItemArmor.ArmorMaterial material : ItemArmor.ArmorMaterial.values())
                {
                    if (material.name().equalsIgnoreCase(tb.getText()))
                    {
                        message = "Name is already used.";
                        break;
                    }
                }
            }

            return message;
        }
    };

    public static IValidityProvider NOT_EMPTY = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            String text = tb.getText().trim();
            if (text.length() == 0)
                message = "Enter something";

            return message;
        }
    };
}
