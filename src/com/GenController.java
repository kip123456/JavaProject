package com;

import java.util.Random;

import com.data.Global;

public class GenController {
    int stage,length,uphel,upatk,updef,upmdef,upexp,upmonsterNum,now_tick;
    /*
     * ps:限制，第i阶段最多出num=i+2的reward
     */
    int ticklst,diflst;
    int myvar[] = new int[4],pw[];
    Random a = new Random(System.currentTimeMillis());
    void fill(G2CMessage ms,int id,int spemod)
    {
        int r = a.nextInt(0, 100);
        if(r<8&&uphel>0&&spemod==0)
        {
            r = a.nextInt(0,stage+2);
            while(r>0&&pw[r]*100>uphel)--r;
            ms.kind[id]=0;
            ms.num[id]=r;
            uphel -= pw[r]*100;
        }
        else if(r<12&&upatk>0&&spemod==0)
        {
            r = a.nextInt(0,stage+2);
            while(r>0&&pw[r]>upatk)--r;
            ms.kind[id]=1;
            ms.num[id]=r;
            upatk -= pw[r];
        }
        else if(r<16&&updef>0&&spemod==0)
        {
            r = a.nextInt(0,stage+2);
            while(r>0&&pw[r]>updef)--r;
            ms.kind[id]=2;
            ms.num[id]=r;
            updef -= pw[r];
        }
        else if(r<20&&upmdef>0&&spemod==0)
        {
            r = a.nextInt(0,stage+2);
            while(r>0&&pw[r]*5>upmdef)--r;
            ms.kind[id]=3;
            ms.num[id]=r;
            upmdef -= pw[r]*5;
        }
        else
        {
            double x = a.nextGaussian(now_tick*1.0/length, 2);
            if(x<=0)
            {
                ms.kind[id]=6;
            }
            else if(x>=upmonsterNum-1)
            {
                ms.kind[id]=6+upmonsterNum-1;
            }
            else
            {
                ms.kind[id]=(int)Math.floor(x)+6;
            }
        }
    }
    G2CMessage gen()
    {
        ++now_tick;
        --ticklst;
        if(ticklst <= 0)
        {
            ticklst = length / 10;
            diflst = a.nextInt(0, 4);
            for(int i=0;i<myvar.length;++i)myvar[i]=0;
            if(diflst==1)
            {
                myvar[2]=a.nextInt(0,4);
            }
            if(diflst==2||diflst == 3)
            {
                myvar[1]=1;
            }
        }
        if(diflst == 0)
        {
            if(myvar[0]==0)
            {
                myvar[0]=Global.GENERATE_PADDING/4;
                int channelid = a.nextInt(1, 16);
                G2CMessage ms = new G2CMessage();
                for(int i = 0,j=1;i < 4; ++ i,j<<=1)
                {
                    if((channelid & j)!=0)
                    {
                        fill(ms,i,0);
                    }
                }
                return ms;
            }
            --myvar[0];
            return null;
        }
        else if(diflst == 1)
        {
            if(myvar[0]!=0&&myvar[1]!=0)
            {
                --myvar[0];--myvar[1];
                return null;
            }
            G2CMessage ms = new G2CMessage();
            if(myvar[1]==0)
            {
                if(myvar[3]==0)
                {
                    myvar[2]=a.nextInt(0, 4);
                    myvar[3]=10;
                }
                --myvar[3];
                myvar[1]=Global.GENERATE_PADDING/12;
                if(myvar[3]<=5)
                fill(ms, myvar[2],1);
            }
            if(myvar[0]==0)
            {
                myvar[0]=Global.GENERATE_PADDING/5;
                int r = a.nextInt(0, 3);
                if(r>=myvar[2])++r;
                fill(ms, r,0);
            }
            --myvar[0];--myvar[1];
            return ms;
        }
        else if(diflst == 2)
        {
            if(myvar[0]==0)
            {
                myvar[0]=Global.GENERATE_PADDING/8;
                myvar[2]+=myvar[1];
                if(myvar[2]==0||myvar[2]==3)myvar[1]*=-1;
                G2CMessage ms = new G2CMessage();
                fill(ms, myvar[2], 0);
                return ms;
            }
            --myvar[0];
            return null;
        }
        else
        {
            if(myvar[0]==0)
            {
                myvar[0]=Global.GENERATE_PADDING/12;
                myvar[2]+=myvar[1];
                if(myvar[2]==0||myvar[2]==3)myvar[1]*=-1;
                G2CMessage ms = new G2CMessage();
                fill(ms, myvar[2], 0);
                return ms;
            }
            --myvar[0];
            return null;
        }
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
    GenController()
    {
        pw = new int[20];
        pw[0]=1;
        for(int i=1;i<pw.length;++i)
        pw[i]=pw[i-1]*2;
    }
}
