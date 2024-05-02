import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Player {
    int health, defense, attack, magicDefense;
    int posx, posy, posz;
    Global.MovingState state;
    int leftSteps;
    BufferedImage myImage;
    public Player() {
        health = 100;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        state = Global.MovingState.STOP;
        posx = 0;
        posy = 150;
        posz = 0;
        myImage = DataManager.player_img;
    }
    public void react(T2PMessage msg) {
        health += msg.hel;
        attack += msg.atk;
        defense += msg.def;
        magicDefense += msg.mdef;
    }
    public void move(Global.MovingState direc) {

        if(state == Global.MovingState.STOP && posz > 0) {
            leftSteps = 4;
            state = Global.MovingState.DOWN;           
        }

        if(state == Global.MovingState.STOP && direc != Global.MovingState.STOP) {
            if(direc == Global.MovingState.DOWN && posz > 0) {
                leftSteps = Global.TICKS_PER_PLAYER_MOVE;
                state = Global.MovingState.DOWN;
            } else if (direc == Global.MovingState.UP && posz == 0) {
                leftSteps = Global.TICKS_PER_PLAYER_MOVE;
                state = Global.MovingState.UP;
            } else if (direc == Global.MovingState.LEFT && posx > 0) {
                leftSteps = Global.TICKS_PER_PLAYER_MOVE;
                state = Global.MovingState.LEFT;
            } else if (direc == Global.MovingState.RIGHT && posx < Global.CHANNEL_WIDTH * Global.CHANNEL_COUNT) {
                leftSteps = Global.TICKS_PER_PLAYER_MOVE;
                state = Global.MovingState.RIGHT;
            }
        }

        if(state != Global.MovingState.STOP) {
            leftSteps--;
            switch (state) {
                case DOWN:
                    posz--;
                    break;
                case UP:
                    posz++;
                    break;
                case LEFT:
                    posx --;
                    break;
                case RIGHT:
                    posx ++;
                    break;
                default:
                    break;

            }
            if(leftSteps == 0) {
                state = Global.MovingState.STOP;
            }
        }
    }

    Rectangle transRectangle() {
        return new Rectangle(posx*25+300,posy*4+44,100,50);
    }

    void repaint(Graphics g)
    {
        Rectangle rect = transRectangle();
        g.drawImage(myImage, rect.x, rect.y, rect.width, rect.height, null);
    }

}
