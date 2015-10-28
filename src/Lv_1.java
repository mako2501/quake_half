import java.awt.Component;


public class Lv_1 extends Poziom {
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
	
	
public static final String w1 = "q-------------------------------------------------Z---------------------------------------------------------------------------------------------------q";
public static final String w2 = "q---------------------------------------------------------A-------------------------------------------------------------------------------------------q";
public static final String w3 = "q---R----A-----Z-----Z-----A------------------+-----+---Œ---------A------Z--A----Z-A------------------------A---------Z-----+---+-awd-----------------q";
public static final String w4 = "q----------S-S--------------------------------awwwwwd---------+--Q----------------------------------------------------------awwwd-zxc-----------------q";
public static final String w5 = "q------+-----------+------------+---A---Z-----zxxxxrswwwwwwwwwd------+----------------+-----------------+----------------+--zxxxc------------------M--q";
public static final String w6 = "e------awwwwwwwwwwwwwwwwwwwwwwwwd------------------vbxxxxxxxrrswwwwwwwwwwwwwwwwwwwwwwwd-------------W---awwwwwwwwwwwwwwwwd-----------------A----------q";
public static final String w7 = "q------zxxxxxxxxxxxxrrrrrrrrrrrrs---------+-----------------vbxxxxxxxerrrrerxxxxxxxxxxc-----------------zxxxxxxxxxxxxxxxxc-----------------------awwwws";
public static final String w8 = "q-------------------vbbbbbqqqeqqqwwwwwwwwwd--------------------------vbbbbbn-----------------------awd------------------Z---------A------+-------zxxxxq";
public static final String w9 = "q-------------------------vbbbbbbxxxxxxxxxc--------------------------------------------------------sqs-----------------------Q-----------awwwwwwd-----q";
public static final String w10= "q--------------------------------------------------------------------------------------------------sqs---------------+---------------+---zxxxxxxc-----e";
public static final String w11= "q--------------------------------------------awd--------------------------------------S--S------awwsqsd--------------awwwwwwwwwwwwwwwd----------------e";
public static final String w12= "q--------------------------------------------zxc--A-------A-Z-----------------------------------vbrrqqs--------------zxxxxxxxxxxxxxxxc----------------s";
public static final String w13= "q--------------------------------------------------------------P--------------------awwwwwwd------vqqqs-----------------------------------------------s";
public static final String w14= "e------------------------------------------------------+--------+-------------------vxxxxxxc-------qqqswwwwd-----------------------------------Z---A--s";
public static final String w15= "e--------------W-----------Z-----A--------awwwwwwwwwwwwwwwwwwwwwd----------------------------------qqqrrrrrs------------------------------------------s";
public static final String w16= "s--0---------+------+---------------------sqqqqxxxxxxxxxxxxxxxxxc----------------------------------vqqqqqqqs------------------------------+-----------s";
public static final String w17= "s------------awwwwwwd---------------------sqqqq--A---------Z------A----------Z------AZ---------Z------qqqqqswd--A------A---Z--------Z-----awwwwwwwwwwws";
public static final String w18= "s----------awseqqqqqswwwwwwwwwwwwwwwwwwwwwsqqqe----------------------------------------------------Y--qqqqqqrc----------S-S-S-------------sqqqeqqqqqqqs";
public static final String w19= "s----------seqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqe---------+-----------------------------------+---------qqeqqqq------+------------+---------sqqqqqeqqqqqs";
public static final String w20= "wwwwwwwwwwwsqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqeswwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwqqwwwwwwwwwwwwwwwwwwwwqqqqqqqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwsqqqqqqqqqqqw";




//konstruktor
public Lv_1(Component r)
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
