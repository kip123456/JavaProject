public class Rewards extends Thing{
    int catagory,hel,atk,def,mdef;
    public Rewards(int _catagory,int number,int posx,int posy)
    {
        catagory = _catagory;
        hel = atk = def = mdef = 0;
        if(catagory == 1){
            hel = number;
        }
        else if(catagory == 2){
            atk = number;
        }
        else if(catagory == 3){
            def = number;
        }
        else if(catagory == mdef){
            mdef = number;
        }
    }
    public T2PMessage interact(Player p)
    {
        return new T2PMessage(hel,atk,def,mdef);
    }
}
