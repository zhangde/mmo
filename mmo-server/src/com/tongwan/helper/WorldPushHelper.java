package com.tongwan.helper;

import gen.data.SpriteMotionVO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.net.ResultObject;
import com.tongwan.common.path.Point;
import com.tongwan.common.thread.NameThreadFactory;
import com.tongwan.domain.sprite.Sprite;
import com.tongwan.manage.SessionManage;

/**
 * 世界地图推送助手
 * @author zhangde
 *
 * @date 2014年2月25日
 */
@Component
public class WorldPushHelper {
	@Autowired
	private SessionManage sessionManage;
	
	private static WorldPushHelper instatnce;
	private static final ExecutorService EXECUTOR=Executors.newFixedThreadPool(20, new NameThreadFactory("异步推送线程"));
	@PostConstruct
	public void init(){
		instatnce=this;
	}
	public static void pushMotion(Sprite sprite,List<Point> path){
		final ResultObject<SpriteMotionVO> result=ResultObject.valueOf(5);
		SpriteMotionVO vo = new SpriteMotionVO();
		vo.id=sprite.getId();
		vo.spriteType = sprite.getType().ordinal();
		vo.path=new int[path.size()*2];
		int i=0;
		for(Point p:path){
			vo.path[i]=p.x;
			i++;
			vo.path[i]=p.y;
			i++;
		}
		result.setValue(vo);
		EXECUTOR.submit(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				instatnce.sessionManage.writeToAllOnline(result);
			}
		});
	}
}
