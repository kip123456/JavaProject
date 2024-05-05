public class T2PMessage {
    int hel,atk,def,mdef,money,exp;
    T2PMessage(int _hel,int _atk,int _def,int _mdef,int _money,int _exp){
        hel = _hel;
        atk = _atk;
        def = _def;
        mdef = _mdef;
        money = _money;
        exp = _exp;
    }
    T2PMessage(int _hel,int _money,int _exp){
        this(_hel==-1?-0x3f3f3f3f:-_hel,0,0,0,_money,_exp);
    }
}
