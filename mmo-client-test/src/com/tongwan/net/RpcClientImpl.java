package com.tongwan.net;

import com.tongwan.MainJFrame;
import com.tongwan.MainJPanel;
import com.tongwan.common.net.channel.BaseChannel;

import gen.client.RpcClient;
import gen.data.UserVO;

/**
 * @author zhangde
 *
 * @date 2014年1月24日
 */
public class RpcClientImpl extends RpcClient{
	public RpcClientImpl(BaseChannel channel){
		this.channel=channel;
	}
	/* (non-Javadoc)
	 * @see gen.client.RpcClient#closeUserCallback(int, gen.data.UserVO)
	 */
	@Override
	public void closeUserCallback(int state, UserVO result) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see gen.client.RpcClient#loadGameMapCallback(int, byte[][])
	 */
	@Override
	public void loadGameMapCallback(int state, byte[][] result)
			throws Exception {
		System.out.println(state);
		MainJPanel panel= MainJFrame.getPanel();
		panel.setMapData(result);
		panel.repaint();
	}

	/* (non-Javadoc)
	 * @see gen.client.RpcClient#loginCallback(int, gen.data.UserVO)
	 */
	@Override
	public void loginCallback(int state, UserVO result) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
