package com;

import com.data.Global;

public class G2CMessage {
    /*
     * 下标 0~3：第i路
     * kind = 0~5 reward , 6~x 当前阶段monster, -1 不生成
     * 如果kind = 0~5, 对应需要给num数值。
     */
    public int[] kind,num;
    G2CMessage()
    {
        kind = new int[Global.CHANNEL_COUNT];
        num = new int[Global.CHANNEL_COUNT];
        for(int i=0;i<4;++i)
        kind[i]=-1;
    }
}
