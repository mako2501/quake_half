
public class B_rg extends Pod {

	public B_rg(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
		//ZMIENIONY troche y aby pakiet nizej byl nad ziemia 
	super(x,y+8,S_swiata.b_rg_id,S_swiata.pojemnosc_ammo_rg,S_swiata.czasOdnowyBroniRg,"rg_bron",
			"/tex/rg/m1.gif","/tex/rg/m2.gif","/tex/rg/m3.gif","/tex/rg/m4.gif",
			"/tex/rg/m5.gif","/tex/rg/m6.gif","/tex/rg/m7.gif","/tex/rg/m8.gif");
	}
	
}
