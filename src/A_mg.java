

public class A_mg extends Pod {

	public A_mg(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
		//ZMIENIONY troche y aby pakiet nizej byl nad ziemia 
	super(x,y+12,S_swiata.a_mg_id,S_swiata.pojemnosc_ammo_mg,S_swiata.czasOdnowyAmunicji,"mg_amunicja",
			"/tex/ammo/mg/m3.gif","/tex/ammo/mg/m4.gif","/tex/ammo/mg/m1.gif","/tex/ammo/mg/m2.gif",
			"/tex/ammo/mg/m3.gif","/tex/ammo/mg/m4.gif","/tex/ammo/mg/m1.gif","/tex/ammo/mg/m2.gif");
	}
	
}


