import java.awt.Graphics;
import java.awt.Rectangle;
public class Monster extends Thing {
    final static int INFdamage = 999999999;
    int skill;
    int catagory,hel,atk,def,money,exp,myrow;
    public Monster(int id,int _posx,int _posy){
        if ((skill & 1) > 0)
            haveHeight = true;
        hel = DataManager.monster_data[id][0];
        atk = DataManager.monster_data[id][1];
        def = DataManager.monster_data[id][2];
        money = DataManager.monster_data[id][3];
        exp = DataManager.monster_data[id][4];
        skill = DataManager.monster_data[id][5];
        myrow = DataManager.monster_data[id][6];
        myImage = DataManager.monster_img[id/4];
        posx=_posx;
        posy=_posy;
    }
    public int getExp(Player p)
    {
        return exp;
    }
    public int getMoney(Player p)
    {
        return money;
    }
    public int getDef(Player p)
    {
        //坚固处理
        if((skill & 16) > 0)return Math.max(def,p.attack - 1);
        //模仿处理
        if((skill & 64) > 0)return Math.max(def,p.defense);
        return def;
    }
    public int getAtk(Player p)
    {
        //模仿处理
        if((skill & 64) > 0)return Math.max(atk,p.attack);
        //怨念堆积处理
        if((skill & 256) > 0)return atk+Global.hateValue;
        return atk;
    }
    public int getDamage(Player p)
    {
        int atk=getAtk(p),def=getDef(p);
        if(p.attack <= def)return INFdamage;
        int turns = hel / (p.attack - def);
        if(hel % (p.attack - def) != 0)
            ++ turns;
        int damage = 0;
        //破甲处理
        if((skill & 32) > 0)damage += (int)(0.9*p.defense);
        //净化处理
        if((skill & 2048) > 0)damage += 2*p.magicDefense;
        //先攻处理
        if((skill & 2) > 0) ++turns;
        //贯杀炮处理
        if((skill & 128) > 0 && turns >= 10) damage += atk*10;
        //三连击处理
        if((skill & 4) > 0) turns*=3;
        int nowPlayerDefense=p.defense;
        //魔攻处理
        if((skill & 8) > 0)nowPlayerDefense = 0;
        //普攻伤害计算
        damage += turns * Math.max(0,(atk - nowPlayerDefense));
        //怨念发酵处理
        if((skill & 512) > 0)damage += Global.hateValue;
        //残虐处理
        if((skill & 1024) > 0 && p.health < hel)damage *= 2;

        return damage;
    }
    
    public T2PMessage interact(Player p)
    {
        int damage = getDamage(p);
        if(damage == INFdamage)return new T2PMessage(-1,0,0);
        //怨念处理
        if((skill & 512) > 0)Global.hateValue *= 2;
        else Global.hateValue ++;

        return new T2PMessage(damage,getMoney(p),getExp(p));
    }
    public T2PMessage died_from_skill()
    {
        if((skill & 512) > 0)Global.hateValue /= 2;
        return new T2PMessage(0,money,exp);
    }
    int receiveDamage(int damage,Animation animation)
    {
        Rectangle rec = transPos();
        animation.new_animation(1, rec.x-10, rec.y-10, rec.width+20, rec.height+20,5, DataManager.animationImg[3],0);
        if(damage<=def)return -1;
        hel -= damage - def;
        if(hel <= 0)return -hel;
        return -1;
    }
    public void repaint(Graphics g,Controller controller)
    {
        Rectangle rect = transPos();
        controller.dynamic_draw(g, myImage, myrow, rect.x, rect.y, rect.width, rect.height);
    }
}