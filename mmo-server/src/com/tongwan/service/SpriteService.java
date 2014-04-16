package com.tongwan.service;

import com.tongwan.domain.sprite.Sprite;

/**
 * 精灵业务操作接口
 * @author zhangde
 *
 * @date 2014年4月14日
 */
public interface SpriteService {
	public Object[] attributes(Sprite sprite,int[] keys);
}
