package cubex2.cs3.common.attribute;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeDescription
{
    String value();
}
