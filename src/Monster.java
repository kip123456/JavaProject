public class Monster extends Thing {
    int catagory,hel,atk,def,skill,posx,posy;
    public Monster(int id,int _posx,int _posy){
        hel = DataManager.monster_data[id][0];
        atk = DataManager.monster_data[id][1];
        def = DataManager.monster_data[id][2];
        skill = DataManager.monster_data[id][3];
        myImage = DataManager.monster_img[id];
        posx=_posx;
        posy=_posy;
    }
    public T2PMessage interact(Player p)
    {
        if(p.attack<=def)return new T2PMessage(-1);
        if(atk<=p.defense)return new T2PMessage(0);
        int turns = hel / (p.attack - def);
        if(hel % (p.attack - def) != 0)
            ++ turns;
        int damage = (turns - 1) * (atk - p.defense) - p.magicDefense;
        return new T2PMessage(damage);
    }
}