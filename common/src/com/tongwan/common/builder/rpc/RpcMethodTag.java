/**
 * 
 */
package com.tongwan.common.builder.rpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangde
 * @date 2013-9-24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RpcMethodTag {
	int cmd();
	String[] params();
	String remark() default "";
}
