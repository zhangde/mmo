using UnityEngine;
using System.Collections;
using System;
public abstract class UserInterfaceClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInput input){
		int cmd=input.readInt();
		switch(cmd){
		}
	}
}
