package com.hundsun.hq.modify.main;

import com.hundsun.hq.modify.factory.HqJobFactory;

/**
@author jingsir

**
*/
public class HqModifyMain {

	public static void main(String[] args){
		HqJobFactory hqJobFactory = new HqJobFactory() ;
		hqJobFactory.start();
	}
}
