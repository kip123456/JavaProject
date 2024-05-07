import java.awt.Graphics;
import java.awt.Rectangle;

public class Rewards extends Thing{
    int catagory,hel,atk,def,mdef,money,exp;
    /*
     * _catagory : 0~5依次为血攻防金经
     * number : 数值大小（传入后会变为2^x）
     * posx,posy : 当前坐标
     */
    public Rewards(int _catagory,int number,int posx,int posy)
    {
        catagory = _catagory;
        hel = atk = def = mdef = money = exp = 0;
        super.posx = posx;
        super.posy = posy;
        myImage = DataManager.reward_img[catagory][number];
        number = (int)Math.pow(2,number);
        if(catagory == 0){
            hel = number*100;
        }
        else if(catagory == 1){
            atk = number;
        }
        else if(catagory == 2){
            def = number;
        }
        else if(catagory == 3){
            mdef = number*5;
        }
        else if(catagory == 4){
            money = number*10;
        }
        else if(catagory == 5){
            exp = number*10;
        }
    }
    public T2PMessage interact(Player p)
    {
        return new T2PMessage(hel,atk,def,mdef,money,exp);
    }
    public void repaint(Graphics g,Controller controller)
    {
        Rectangle rect = transPos();
        g.drawImage(myImage, rect.x, rect.y, rect.width, rect.height, null);
    }
}
