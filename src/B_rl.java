
public class B_rl extends Pod {

	public B_rl(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
		//ZMIENIONY troche y aby pakiet nizej byl nad ziemia 
	super(x,y+8,S_swiata.b_rl_id,S_swiata.pojemnosc_ammo_rl,S_swiata.czasOdnowyBroniRl,"rl_bron",
			"/tex/rl/m5.gif","/tex/rl/m6.gif","/tex/rl/m7.gif","/tex/rl/m8.gif",
			"/tex/rl/m1.gif","/tex/rl/m2.gif","/tex/rl/m3.gif","/tex/rl/m4.gif" );
	}
	
}
