package com.tongwan;

import org.jboss.netty.channel.ChannelFuture;

import com.tongwan.net.NetClient;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainJFrame frame=new MainJFrame();
		frame.setVisible(true);
		NetClient.init(frame);

		ChannelFuture cf=NetClient.conn();
	}

}
