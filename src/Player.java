import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Graphics;

public class Player {
    int health, defense, attack, magicDefense;
    int posx, posy, posz;
    int lrSteps;
    int udSteps;
    BufferedImage myImage;
    public Player() {
        health = 100;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        lrSteps = udSteps = 0;
        posx = 0;
        posy = 150;
        posz = 0;
        System.out.println("Player reach here\n");
        myImage = DataManager.player_img;
        System.out.println("player leave here\n");
    }
    public void react(T2PMessage msg) {
        health += msg.hel;
        attack += msg.atk;
        defense += msg.def;
        magicDefense += msg.mdef;
    }
    public void move(MovingState direc) {

        switch (direc) {
            case value:
                
                break;
        
            default:
                break;
        }

        if(state != MovingState.STOP) {
            System.out.println("moving");
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
                state = MovingState.STOP;
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
