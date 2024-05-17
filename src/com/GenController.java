package com;

public class GenController {
    int stage,length,uphel,upatk,updef,upmdef,upexp,upmonsterNum,now_tick;
    /*
     * ps:限制，第i阶段最多出num=i+2的reward
     */
    Generator g;
    G2CMessage gen()
    {
        ++now_tick;
        if(g == null)
        {
            
        }
        return g.gen();
    }
    /*
     * _stage : 当前处于的阶段
     * _length : 当前阶段的tick数
     * _hel,_atk,_def,_mdef : 当前阶段所需生成的属性上线
     * (可能需要一个均值方差来调控生成特性)
     */
    void set(int _stage,int _length,int _hel,int _atk,int _def,int _mdef,int _exp,int _num)
    {
        stage = _stage;
        length = _length;
        uphel = _hel;
        upatk = _atk;
        updef = _def;
        upmdef = _mdef;
        upexp = _exp;
        upmonsterNum = _num;
        now_tick = 0;
    }
}
