package com;

import java.io.Serializable;

public class SaveData implements Serializable{
    public int coin;
    public boolean[] sp,level;
    SaveData()
    {
        coin = 0;
        sp = new boolean[105];
        level = new boolean[105];
    }
}
