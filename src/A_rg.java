
public class A_rg extends Pod {

	public A_rg(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
		//ZMIENIONY troche y aby pakiet nizej byl nad ziemia 
	super(x,y+12,S_swiata.a_rg_id,S_swiata.pojemnosc_ammo_rg,S_swiata.czasOdnowyAmunicji,"rg_amunicja",
			"/tex/ammo/rg/m3.gif","/tex/ammo/rg/m4.gif","/tex/ammo/rg/m1.gif","/tex/ammo/rg/m2.gif",
			"/tex/ammo/rg/m3.gif","/tex/ammo/rg/m4.gif","/tex/ammo/rg/m1.gif","/tex/ammo/rg/m2.gif");
	}
	
}
