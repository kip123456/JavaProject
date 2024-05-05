import java.awt.Rectangle;

public class Monster extends Thing {
    int catagory,hel,atk,def,skill,money,exp;
    public Monster(int id,int _posx,int _posy){
        hel = DataManager.monster_data[id][0];
        atk = DataManager.monster_data[id][1];
        def = DataManager.monster_data[id][2];
        money = DataManager.monster_data[id][3];
        exp = DataManager.monster_data[id][4];
        skill = DataManager.monster_data[id][5];
        myImage = DataManager.monster_img[id];
        posx=_posx;
        posy=_posy;
    }
    public T2PMessage interact(Player p)
    {
        if(p.attack<=def)return new T2PMessage(-1,0,0);
        if(atk<=p.defense)return new T2PMessage(0,money,exp);
        int turns = hel / (p.attack - def);
        if(hel % (p.attack - def) != 0)
            ++ turns;
        int damage = Math.max(0,(turns - 1) * (atk - p.defense) - p.magicDefense);
        return new T2PMessage(damage,money,exp);
    }
    public T2PMessage died()
    {
        return new T2PMessage(0,money,exp);
    }
    int getDamage(int damage,Animation animation)
    {
        Rectangle rec = transPos();
        animation.new_animation(1, rec.x-10, rec.y-10, rec.width+20, rec.height+20,5, DataManager.animationImg[3],0);    
        
        if(damage<=def)return -1;
        hel -= damage - def;
        if(hel <= 0)return -hel;
        return -1;
    }
}