package com.data;

import com.SaveData;
import com.Saver;

public class Global {
    public static final int TICK_PER_SEC = 50;
    public static final int CHANNEL_WIDTH = 4;
    public static final int CHANNEL_HEIGHT = 150;
    public static final int CHANNEL_COUNT = 4;
    public static final int WINDOW_WIDTH = 800;
    public static final int CHANNEL_PADDING=100;
    public static final int GENERATE_PADDING=50;
    public static final int TICKS_PER_PLAYER_MOVE = 4;
    public static final int MAX_PLAYER_JUMP = 10;
    public static final int iconnum = 6;
    public static final int backgroundnum = 9;
    public static final int animationnum = 4;
    public static final int bgmnum = 5;
    public static final int monsternum = 17;
    public static final int rewardnum = 5;
    public static final int senum = 4;
    public static final int skill_load_speed = 50;
    public static final int things_moving_speed = 4;
    public static int hateValue = 0;
    public static final int missionNum = 10;
    public static final int[][] monsterChosen = {{0,1,4,8,9,12,14,20},{2,5,13,60,16,65,6},{3,7,10,11,15,66,30},{30,18,21,23,19,22,28}};
    public static final int stage_num = 5;
    public static final int stage_tick[] = {0,1000,2000,3000,4000};

    public static SaveData saveData;
    static {
        saveData = Saver.load();
        if(saveData == null) saveData = new SaveData();
    }
}
