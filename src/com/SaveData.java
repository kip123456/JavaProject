package com;

import java.io.Serializable;
// 只需更改Global.saveData即可保存所需数据
public class SaveData implements Serializable{
    public int coin;
    public boolean[] sp,level;
    public int buyedTimes;
    public SaveData()
    {
        coin = 0;
        buyedTimes = 0;
        sp = new boolean[105];
        level = new boolean[105];
    }
}
