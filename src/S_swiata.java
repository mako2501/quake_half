import java.awt.Color;


public class S_swiata {

	//wielkosci blokow z ktorych sklada sie swiat
//public static final int wysokoscBloku=60;	
//public static final int szerokoscBloku=40;	
//wielkosc gracza
public static final int szerokoscObrazkaGracza=40;
public static final int wysokoscObrazkaGracza=60;
//kierunki strzalu
public static final int lewo=0;
public static final int prawo=180;

//rozdzielczosc
public static final int szerokosc=640;
public static final int wysokosc=480;

// gracza
public static final int predkoscX=4;
public static final int predkoscX2=2;
public static final int predkoscY=7;//predkosc skoku i spadania
public static final int predkoscY2=20;
public static final int maxIloscpancerza=200;
public static final int maxIloscHp=200;
public static final int minObnizki=100;
public static final int minWysokoscUpadku=141;
public static final int maxWysokoscUpadku=250;
public static final int maxAmunicji=999;

// wysokosc skoku
public static final int czescWysokosciSkoku=20;
public static final int wysokoscSkokumax=120;
//rozdzielczosc ekranu w blokach
public static final int maxIloscBlokowSzerokosc=32;
public static final int maxIloscBlokowWysokosc=20;
public static final int wielkoscBloku=20;

//marginesy planszy po bloku z kazdej strony
public static final int lewaKrawedzWidoku=-40;//-40
public static final int prawaKrawedzWidoku=680;//680

//id elementow swiata i Podów
public static final int brakElementu_id = -1;
public static final int blok_id = 1;
public static final int skok_id = 2;
public static final int kraniec_id = 3;
public static final int red_id = 0;
public static final int zolty_id = 1;
public static final int h_mala_id = 5;
public static final int h_srednia_id = 25;
public static final int h_duza_id = 50;
public static final int h_megas_id = 100;
public static final int a_rl_id=30;//od 30stki bedzie amunicja
public static final int a_mg_id=31;
public static final int a_rg_id=32;
public static final int b_rl_id=40;//od 40stki odpowiednio bron do podniesienia
public static final int b_rg_id=41;


//id broni ktora posiada
public static final int rl_id = 0;
public static final int mg_id = 1;
public static final int rg_id = 2;
//ilosc linii na ekranie//w tym przypadku tyle samo co rozdzielczosc Y ekranu
public static final int iloscLiniiEkranu=20;

//wartosci mowiace od kiedy mozna przesuwac ekran
public static final int odXPrzesunWLewo=320;//320
public static final int odXPrzesunWPrawo=320-szerokoscObrazkaGracza;
//stale broni
public static final int predkoscRakiety=8;
public static final int predkoscMg=18;
public static final int obrazeniaRakiety=80;
public static final int obrazeniaMg=6;
public static final int obrazeniaRg=100;
public static final int czasPrzeladowaniaRakiety=900;
public static final int czasPrzeladowaniaMg=100;
public static final int czasPrzeladowaniaRaila=1500;
public static final int predkoscZanikaniaRaila=6;

//stale PODow
public static final int wielkoscPodow=30;//wys i szer
public static final int czasOdnowyReda=20;//w sekundach
public static final int czasOdnowyZoltego=15;
public static final int czasOdnowyMalej=10;
public static final int czasOdnowySredniej=10;
public static final int czasOdnowyDuzej=10;
public static final int czasOdnowyMegasa=15;
public static final int czasOdnowyAmunicji=10;
public static final int czasOdnowyBroniRl=10;
public static final int czasOdnowyBroniRg=15;
public static final int wartoscReda=100;//ile pancerza to red
public static final int wartoscZoltego=50;
public static final int wartoscMalej=5;
public static final int wartoscSredniej=25;
public static final int wartoscDuzej=50;
public static final int wartoscMegasa=100;
public static final int pojemnosc_ammo_rl=10;
public static final int pojemnosc_ammo_rg=10;
public static final int pojemnosc_ammo_mg=40;

//stat board

public static final Color czerwony = new Color(255, 0, 0, 255);//koloek napisow na status boardzie z przezroczystoscia jako 4 kana³
public static final Color bialy = new Color(255, 250, 250, 255);

}
