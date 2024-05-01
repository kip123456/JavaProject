public class Controller {

    /**
     * 角色
     */
    Player player;

    /**
     * 存放滑块的集合
     */
    List<Thing> things;

    void work() {

    }

    /**
     * 产生新的滑块
     */
    void generateThings() {
        for(road ...) {
            things.add(new Thing(.......))
        }
    }

    /**
     * 移动滑块
     */
    void moveThing() {
        for(Thing thing : things) {
            thing.move();
        }
    }


    /**
     * 角色移动
     */
    void moveCharacter() {
        player.move();
    }


    /**
     * 角色与滑块交互
     */
    void intereact() {
        for(Thing thing : things) {
            if(player.interact(thing)) {
                // 角色与滑块交互
            }
        }
    }


    /**
     * 判断游戏是否结束（或者之类的？）
     */
    void judge() {

    }


    /**
     * 重绘ui
     */
    void repaint() {
        for(Thing thing : things) {
            thing.repaint();
        }
        player.repaint();
        。。。。。
    }
}
