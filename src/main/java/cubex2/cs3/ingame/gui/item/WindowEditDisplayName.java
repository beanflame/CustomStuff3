package cubex2.cs3.ingame.gui.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.util.ClientHelper;

import java.util.Map;
import java.util.Properties;

public class WindowEditDisplayName extends WindowEditItemAttribute implements IValidityProvider
{
    private TextBox textBox;

    public WindowEditDisplayName(WrappedItem item)
    {
        super(item, "displayName", EDIT | CANCEL, 150, 60);

        textBox = textBox().at(7, 7).height(16).fillWidth(7).add();
        textBox.setText(container.displayName);
        textBox.setValidityProvider(this);
    }

    @Override
    protected void applyChanges()
    {
        container.displayName = textBox.getText().trim();

        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        p.put("item." + wrappedItem.getName() + ".name", container.displayName);

        ClientHelper.refreshResources(mc);
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
            message = "Enter something";

        btnEdit.setEnabled(message == null);
        return message;
    }
}
