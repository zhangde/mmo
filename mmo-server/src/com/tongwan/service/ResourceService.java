package com.tongwan.service;

import java.util.List;

/**
 * 静态资源接口
 * @author zhangde
 *
 * @date 2014年1月17日
 */
public interface ResourceService {
	
	public <T> List<T> list(Class<T> clazz);
	
	public <T> T get(Class<T> clazz,int id);

}
