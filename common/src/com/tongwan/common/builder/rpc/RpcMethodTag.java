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
	/**指令编号 */
	int cmd();
	/** 是否需要推送 */
	RPCTMode mode();
	/** 参数名称 */
	String[] params();
	/** 注释*/
	String remark() default "";
}
