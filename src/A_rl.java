

public class A_rl extends Pod {

	public A_rl(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
		//ZMIENIONY troche y aby pakiet nizej byl nad ziemia 
	super(x,y+12,S_swiata.a_rl_id,S_swiata.pojemnosc_ammo_rl,S_swiata.czasOdnowyAmunicji,"rl_amunicja",
			"/tex/ammo/rl/m2.gif","/tex/ammo/rl/m3.gif","/tex/ammo/rl/m4.gif","/tex/ammo/rl/m1.gif",
			"/tex/ammo/rl/m2.gif","/tex/ammo/rl/m3.gif","/tex/ammo/rl/m4.gif","/tex/ammo/rl/m1.gif");
	}
	
}

