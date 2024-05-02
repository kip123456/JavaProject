public class Rewards extends Thing{
    int catagory,hel,atk,def,mdef;
    public Rewards(int _catagory,int number,int posx,int posy)
    {
        catagory = _catagory;
        hel = atk = def = mdef = 0;
        super.posx = posx;
        super.posy = posy;
        myImage = DataManager.reward_img[catagory][number];
        if(catagory == 0){
            hel = number*50;
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
    }
    public T2PMessage interact(Player p)
    {
        return new T2PMessage(hel,atk,def,mdef);
    }
}
