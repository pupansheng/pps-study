/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package mvc.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PpsRequestMapping {

    String url();

}
