package com;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Controller controller = new Controller();
        controller.work();
    }
}
/*bug to fix : UI.java 69行 , 原文为
 *(DataManager.monster_img[i/4].getSubimage(0, (i%4)*_height, _width, _height),
 *
 * mention : 在进入新的一局游戏时，记得Global.hateValue = 0
 *          ：可能的优化思路：状态栏可以单拿出来，只有在更新时重画
 *          ：              setDoubleBuffered(true)
 */