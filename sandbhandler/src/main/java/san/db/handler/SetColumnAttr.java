package san.db.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface SetColumnAttr {

    boolean setPrimary() default  false;
    boolean setNotNull() default  false;
    boolean setUnique() default  false;
    boolean setAutoIncrement() default false;
}
