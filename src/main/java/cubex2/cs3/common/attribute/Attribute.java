package cubex2.cs3.common.attribute;

import cubex2.cs3.ingame.gui.Window;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute
{
    Class<? extends AttributeBridge> bridgeClass() default NullAttributeBridge.class;

    Class<? extends Window> windowClass();

    /**
     * Set this to false if this attribute hasn't its own window, for example when it's edited with the same window as another attribute.
     *
     * @return True if it has its own window, false otherwise.
     */
    boolean hasOwnWindow() default true;

    /**
     * Additional information that is passed to the AttributeJsHandler
     *
     * @return The additional information
     */
    String additionalInfo() default "";

    /**
     * The name that is shown in the attributes list instead of the field name.
     *
     * @return The custom name
     */
    String customName() default "";

    /**
     * If set to true, the attribute will be loaded during the post init phase.
     *
     * @return True if loaded post init.
     */
    boolean loadOnPostInit() default false;
}
