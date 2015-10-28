import java.awt.Component;


public class Lv_2 extends Poziom {

	/*LEGENDA 	 '-' NIC(null)
	 * 0-startpoint gracza <uwaga aby byl tylko 1>
	 * --------------
	 * PODY:
	 * 
	 * 	pancerze:
	 * R-czerwony,	Y-zolty,
	 * 
	 * apteczki:
	 * 	S-mala apteczka,	Œ-srednia apteczka,	D-DUZA APT, M-Megas,
	 * 
	 * amunicja:
	 * Q- do rl  W- do mg E-rg
	 * 
	 * bronie do podniesienia:
	 * P- rl  O-(duze o)-rg
	 * ---------------
	 * ELEMENTY SWIATA:
	*	a-lewy gorny rog	,w-gora bloku, d-prawy gorny rog,	q-wypelnienie srodek 1,r-srodek 2	s-pion bloku
	*		e- wypelnienie z dziura
	* z-lewydol1 x-dol1 c-prawydol1 v-lewydol2 b-srodekdol2 n-prawydol2
	*	pody najlepiej umieszczac z 2 pola nad blikiem
	*	poszczegolne wiersze ktore beda "wzorami" dla funkcji
	*-------------------
	*  WROGOWIE:
	*  A - Komp patrzy w prawo, Z - Komp patrzy w lewo
	*  + - koniec rampy
	*	
	*	*/ 
public static final String w1 = "----------------------------------------------------------------";
public static final String w2 = "----------------------------------------------------------------";
public static final String w3 = "----------------------------------------------------------------";
public static final String w4 = "----------------------------------------------------------------";
public static final String w5 = "----------------------------------------------------------------";
public static final String w6 = "----------------------------------------------------------------";
public static final String w7 = "b--------------------------------------------------------------b";
public static final String w8 = "b--------------------------------------------------------------b";
public static final String w9 = "b--------------------------------------------------------------b";
public static final String w10= "b--------------------------------------------------------------b";
public static final String w11= "b-0----------------------------------------A---Z--ZA----M------b";
public static final String w12= "b--------------------------------------------S--Œ--D-----------b";
public static final String w13= "b----------------------------A-Z-A----Z--+-----------+---------b";
public static final String w14= "b----------------------------R-Y-R-Y-----awwwwwwwwwwwd---------b";
public static final String w15= "b-------------A---Z--------+------------+zxxxxxxxxxxxd---------b";
public static final String w16= "b------------E-W-W-W-E-O---awwwwwwwwwwwwd----------------------b";
public static final String w17= "b----------+---------------zxxxxxxxxxxxxc-----Z----------------b";
public static final String w18= "b---P-Q-Q--awwwwwwwwwwwwwwwd-----------------------------------b";
public static final String w19= "b----------srrrrrrrrrrrrrrrs-----------------------------------b";
public static final String w20= "bbbbbbbbbbbsqqqqqqqqqqqqqqqsbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";


public static final String nazwa="L-> 2";


public String getNazwa()
{
	return nazwa;
}

//konstruktor
public Lv_2(Component r)
{
	super(r);
	//tablica ktora przechowuje wiersze definiujace elementySwiata
	String [] wzor = {w1,w2,w3,w4,w5,w6,w7,w8,w9,
						w10,w11,w12,w13,w14,w15,w16,
						w17,w18,w19,w20};
	//inicjacja poziomu
	super.inicjujPoziom(wzor);
	
	
}
//funkcja ktora byla abstrakcyjna w klasie poziomu, teraz trzeba ja
//zdefiniowac, powoduje ona poznowne wywolanie funkcji inicjujPoziom

public void resetujPoziom()
{
	String [] wzor = {w1,w2,w3,w4,w5,w6,w7,w8,w9,
			w10,w11,w12,w13,w14,w15,w16,
			w17,w18,w19,w20};
	super.inicjujPoziom(wzor);
}

}

