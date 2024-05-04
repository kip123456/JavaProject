public class T2PMessage {
    int hel,atk,def,mdef;
    T2PMessage(int _hel,int _atk,int _def,int _mdef){
        hel = _hel;
        atk = _atk;
        def = _def;
        mdef = _mdef;
    }
    T2PMessage(int _hel){
        this(_hel==-1?-0x3f3f3f3f:-_hel,0,0,0);
    }
}
