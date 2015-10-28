
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


import javax.swing.ImageIcon;


/**
  *@author Rafa³ MAKOWSKI. 
 * Klasa postaci jaka porusza sie Gracz, wszystkie animacje i zachowania(skok, ruch w lewo prawo, strzelanie
 * z poszczególnych broni(zmiana ilosci pociskow,dodawanie pociskow), sposob wyswietlania na ekranie, obliczanie zycia(po utracie->reakcje na upadek) i podniesieniu
 * apteczki-gdy ma powyzej 100 hp nie moze podniesc apteczki klasy 25 i 50, nie moze miec wiecej niz 200 hp, a jezeli posiada wiecej
 * niz 100 to zycie obniza sie o 1 punkt co sekunde do 100), z pancerzem podobnie. Ponadto gdy otrzyma obrazenia a posiada pancerz
 * obrazenie zostana podzielone 50/50 pomiedzy pancerzem a zyciem. Liczenie czasu przeladowania broni, gracz nie wystrzeli ani
 * nie zmieni broni jesli ta nie zdazyla sie przeladowac,zliczanie zabitych wrogow.
 */
public class Gracz {

	private int x,y,pozycjaXGry;//wsp. gracza (lewy gorny rog )
	private int licznikKroku,licznikAnimacji,licznikSkoku,licznikUpadku,kills,licznikSmierci;
	/**	Zmienna odpowiadajaca poszczegolnym klatkom animacji*/
	 
	private Image mg_l1,mg_p1,mg_l2,mg_p2,mg_p3,mg_p4,mg_p5,mg_p6,mg_p7,mg_p8,mg_p9,mg_p10,mg_p11,mg_l3,mg_l4,mg_l5,mg_l6,mg_l7,mg_l8,mg_l9,mg_l10,mg_l11,
	rl_l1,rl_p1,rl_l2,rl_p2,rl_p3,rl_p4,rl_p5,rl_p6,rl_p7,rl_p8,rl_p9,rl_p10,rl_p11,rl_l3,rl_l4,rl_l5,rl_l6,rl_l7,rl_l8,rl_l9,rl_l10,rl_l11,
	rg_l1,rg_p1,rg_l2,rg_p2,rg_p3,rg_p4,rg_p5,rg_p6,rg_p7,rg_p8,rg_p9,rg_p10,rg_p11,rg_l3,rg_l4,rg_l5,rg_l6,rg_l7,rg_l8,rg_l9,rg_l10,rg_l11,
	s_mg_p0,s_mg_p1,s_mg_p2,s_mg_p3,s_mg_p4,s_mg_l0,s_mg_l1,s_mg_l2,s_mg_l3,s_mg_l4;
	public boolean patrzyWLewo,patrzyWPrawo;
	private boolean idz,spada,skacze,blokadaSkoku,wcisnietyA,wcisnietyD,wcisnietyW,wcisniety3,
	wcisnietaSpacja,wcisniety1,wcisniety2,liczeCzasPancerza,liczeCzasHp,niezyje,ginie,zaczalGinac;
	private Component r;
	
	/**3 elementowa tablica odpowiada 3 broniom do dyspozycji. Odwoluje sie do niej za pomoca zmiennych int ktorej liczbom 0,1 i 2 odpowiada konkretna bron.
	 * Za pomoca tego moge pobrac lub ustawic ilosc amunicji dla poszczegolnej broni.
	 * 
	 */
	private int amunicja[];
	/**
	 3 elementowa tablica odpowiada 3 broniom do dyspozycji. Odwoluje sie do niej za pomoca zmiennych int ktorej liczbom 0,1 i 2 odpowiada konkretna bron.
	 * Jezeli jakas bron jest posiadana to element przechowuje prawde. 
	 */
	private boolean posiadanaBron[];
	//tablice zliczajace skutecznosc z poszczegolnych broni
	/**
	 *3 elementowa tablica odpowiada 3 broniom do dyspozycji. Odwoluje sie do niej za pomoca zmiennych int ktorej liczbom 0,1 i 2 odpowiada konkretna bron.
	 *Tablica zlicza wystrzelone pociski 
	 */
	private int wystrzelone[];
	/**
	 * 3 elementowa tablica odpowiada 3 broniom do dyspozycji. Odwoluje sie do niej za pomoca zmiennych int ktorej liczbom 0,1 i 2 odpowiada konkretna bron.
	 * W tablicy przechowuje informacje o pociskach ktore trafily we wroga.
	 */
	private int trafione[];
	/**
	 * 3 elementowa tablica odpowiada 3 broniom do dyspozycji. Odwoluje sie do niej za pomoca zmiennych int ktorej liczbom 0,1 i 2 odpowiada konkretna bron.
	 * Odpowiednie wartosci tablicy sa inkrementowane gdy wrog zostanie "zabity" z danej broni.
	 */
	private int kill[];
	/**
	 * Pod ta zmienna kryje sie numer odpowiadajacy broni ktora aktualnie "trzyma sie w reku".
	 */
	private int aktualnaBron;
	
	/**
	 * Zmienna przechowujaca liczbe odpowiadajaca ilosci zycia.
	 */
	private int hp;
	
	/**
	 * Zmienna przechowujaca liczbe odpowiadajaca ilosci pancerza.
	 */
	private int pancerz;
	/**
	 * Zmienna przechwoujaca czas pobrany metoda System.currentTimeMillis(). Za jej pomoca wiadomo ile czasu trwac bedzie przeladowanie broni, lub kiedy minie sekunda 
	 * aby obnizyc nadmiar zycia lub pancerza.
	 */
	private long staryCzasPrzeladowania,nowyCzasPrzeladowania,czasObnizaniaPancerza,czasObnizaniaHp;
	
	
	/**
	 * Lista przechowuje obiekty, ktore w grze sa wystrzelonymi rakietami (Rl).
	 */
	private ArrayList listaRakiet;
	/**
	 *  Lista przechowuje obiekty, ktore w grze sa wystrzelonymi pociskami z Mg.
	 */
	private ArrayList listaMg;
	/**
	 * Lista przechowuje obiekty, ktore w grze sa blyskami powstalymi w wyniku wystrzalu z Rl.
	 */
	private ArrayList listaRl_boom;
	/**
	 *  Lista przechowuje obiekty, ktore w grze sa sladami pozostalymi po wystrzale z Rail Guna Rg.
	 */
	private ArrayList listaRaila;

	public Gracz(int x,int y) {
		aktualnaBron=S_swiata.mg_id;//musi byc przez zaladowaniem animacji aby wiedziec ktore wgrywac
        zaladujAnimacje();
        kills=0;
        niezyje=false;
        patrzyWLewo=false;
        patrzyWPrawo=true;
        blokadaSkoku=false;
        idz=false;
        ginie=false;
        zaczalGinac=false;
        setxy(x,y);
        hp=125;
        setPancerz(0);
        pozycjaXGry=x+S_swiata.szerokoscObrazkaGracza/2;
        
        licznikUpadku=0;
        licznikSkoku=0;
        licznikKroku=1;
        licznikSmierci=0;
      
        licznikAnimacji=1;
        //inicjacja listy
        listaRakiet=new ArrayList(); 
        listaMg=new ArrayList();
        listaRl_boom=new ArrayList();
        listaRaila=new ArrayList();
        amunicja=new int [3];
        posiadanaBron=new boolean [3];
        
        wystrzelone=new int [3];
    	trafione=new int [3];
    	kill=new int [3];
    	
    	for (int i=0;i<3;i++)wystrzelone[i]=0;
    	for (int i=0;i<3;i++)trafione[i]=0;
    	for (int i=0;i<3;i++)kill[i]=0;
    	
        posiadanaBron[S_swiata.mg_id]=true;
        posiadanaBron[S_swiata.rl_id]=false;
        posiadanaBron[S_swiata.rg_id]=false;
        
        nowyCzasPrzeladowania=System.currentTimeMillis()-1000;//odejmuje 1000 aby bron byla gotowa do strzalu od razu po starcie gry przy spr. czy przeladowana
        setAmunicje(S_swiata.rl_id,0);//amunicja dla 0 broni
        setAmunicje(S_swiata.mg_id,10);//amunicja dla 1 broni
        setAmunicje(S_swiata.rg_id,0);//amunicja dla rg
    }
	
	/**
	 * @param x wspolrzedna poczatkowa gracza.
	 * @param y wspolrzedna poczatkowa gracza.
	 * Funckja ma za zadanie ustawic zmienne gracza na nowo, dziala tak jak konstruktor z ta roznica, ze nie wywoluje metody zaladujAnimacje().
	 */
	public void resetGracza(int x,int y)
	{
		 
		aktualnaBron=S_swiata.mg_id;//musi byc przez zaladowaniem animacji aby wiedziec ktore wgrywac
        //zaladujAnimacje(); JUZ ZALADOWANE 
        kills=0;
        niezyje=false;
        patrzyWLewo=false;
        patrzyWPrawo=true;
        blokadaSkoku=false;
        idz=false;
        ginie=false;
        zaczalGinac=false;
        setxy(x,y);
        hp=125;
        setPancerz(0);
        pozycjaXGry=x+S_swiata.szerokoscObrazkaGracza/2;
        
        licznikUpadku=0;
        licznikSkoku=0;
        licznikKroku=1;
        licznikSmierci=0;
      
        licznikAnimacji=1;
        //inicjacja listy
        listaRakiet=new ArrayList(); 
        listaMg=new ArrayList();
        listaRl_boom=new ArrayList();
        listaRaila=new ArrayList();
        amunicja=new int [3];
        posiadanaBron=new boolean [3];
        
        wystrzelone=new int [3];
    	trafione=new int [3];
    	kill=new int [3];
    	
    	for (int i=0;i<3;i++)wystrzelone[i]=0;
    	for (int i=0;i<3;i++)trafione[i]=0;
    	for (int i=0;i<3;i++)kill[i]=0;
    	
        posiadanaBron[S_swiata.mg_id]=true;
        posiadanaBron[S_swiata.rl_id]=false;
        posiadanaBron[S_swiata.rg_id]=false;
        
        nowyCzasPrzeladowania=System.currentTimeMillis()-1000;//odejmuje 1000 aby bron byla gotowa do strzalu od razu po starcie gry przy spr. czy przeladowana
        setAmunicje(S_swiata.rl_id,0);//amunicja dla 0 broni
        setAmunicje(S_swiata.mg_id,10);//amunicja dla 1 broni
        setAmunicje(S_swiata.rg_id,0);//amunicja dla rg
	}
	/**
	 * Funkcja kazdej wczesniej zadaeklarowanej zmiennej typu Image przypisuje zmienna typu ImageIcon do ktorej jest zaladowany obrazek za pomoca metody getResource(sciezka).
	 * Przewaznie jeden rodzaj Animacji gracza sklada sie z 8smiu klatek, ktora klatka ma byc wsywietlana decyduje licznikAnimacji, a jaki jest licznikAnimacji
	 * decyduje licznikKroku. 
	 */
	public void zaladujAnimacje(){
		//animacje smierci
		ImageIcon iis01 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_l0.gif"));
		s_mg_l0 = iis01.getImage();
		ImageIcon iis11 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_l1.gif"));
		s_mg_l1 = iis11.getImage();
		ImageIcon iis21 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_l2.gif"));
		s_mg_l2 = iis21.getImage();
		ImageIcon iis31 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_l3.gif"));
		s_mg_l3 = iis31.getImage();
		ImageIcon iis41 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_l4.gif"));
		s_mg_l4 = iis41.getImage();
		ImageIcon iis0 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_p0.gif"));
		s_mg_p0 = iis0.getImage();
		ImageIcon iis1 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_p1.gif"));
		s_mg_p1 = iis1.getImage();
		ImageIcon iis2 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_p2.gif"));
		s_mg_p2 = iis2.getImage();
		ImageIcon iis3 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_p3.gif"));
		s_mg_p3 = iis3.getImage();
		ImageIcon iis4 = new ImageIcon(this.getClass().getResource("/tex/g/mg/s_p4.gif"));
		s_mg_p4 = iis4.getImage();
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/tex/g/mg/l0.gif"));
		mg_l1 = ii.getImage();
        ImageIcon il3 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l1.gif"));
        mg_l2 = il3.getImage();
        ImageIcon il5 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l2.gif"));
        mg_l3=il5.getImage();
        ImageIcon il6 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l3.gif"));
        mg_l4=il6.getImage();
        ImageIcon il7 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l4.gif"));
        mg_l5=il7.getImage();
        ImageIcon il8 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l5.gif"));
        mg_l6=il8.getImage();
        ImageIcon il9 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l6.gif"));
        mg_l7=il9.getImage();
        ImageIcon il10 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l7.gif"));
        mg_l8=il10.getImage();
        ImageIcon il11 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l8.gif"));
        mg_l9=il11.getImage();
        ImageIcon il12 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l9.gif"));
        mg_l10=il12.getImage();
        ImageIcon il13 = new ImageIcon(this.getClass().getResource("/tex/g/mg/l10.gif"));
        mg_l11=il13.getImage();
 //klatki animacji dla obruconego  wprawo
        
        ImageIcon ii2 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p0.gif"));
        mg_p1=ii2.getImage();
        ImageIcon ii4 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p1.gif"));
        mg_p2=ii4.getImage();
        ImageIcon ii5 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p2.gif"));
        mg_p3=ii5.getImage();
        ImageIcon ii6 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p3.gif"));
        mg_p4=ii6.getImage();
        ImageIcon ii7 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p4.gif"));
        mg_p5=ii7.getImage();
        ImageIcon ii8 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p5.gif"));
        mg_p6=ii8.getImage();
        ImageIcon ii9 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p6.gif"));
        mg_p7=ii9.getImage();
        ImageIcon ii10 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p7.gif"));
        mg_p8=ii10.getImage();
        ImageIcon ii11 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p8.gif"));
        mg_p9=ii11.getImage();
        ImageIcon ii12 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p9.gif"));
        mg_p10=ii12.getImage();
        ImageIcon ii13 = new ImageIcon(this.getClass().getResource("/tex/g/mg/p10.gif"));
        mg_p11=ii13.getImage();
        //animacje gracza gdy niesie rakiete
        
        ImageIcon rlii = new ImageIcon(this.getClass().getResource("/tex/g/rl/l0.gif"));
		rl_l1 = rlii.getImage();
        ImageIcon rlil3 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l1.gif"));
        rl_l2 = rlil3.getImage();
        ImageIcon rlil5 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l2.gif"));
        rl_l3=rlil5.getImage();
        ImageIcon rlil6 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l3.gif"));
        rl_l4=rlil6.getImage();
        ImageIcon rlil7 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l4.gif"));
        rl_l5=rlil7.getImage();
        ImageIcon rlil8 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l5.gif"));
        rl_l6=rlil8.getImage();
        ImageIcon rlil9 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l6.gif"));
        rl_l7=rlil9.getImage();
        ImageIcon rlil10 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l7.gif"));
        rl_l8=rlil10.getImage();
        ImageIcon rlil11 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l8.gif"));
        rl_l9=rlil11.getImage();
        ImageIcon rlil12 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l9.gif"));
        rl_l10=rlil12.getImage();
        ImageIcon rlil13 = new ImageIcon(this.getClass().getResource("/tex/g/rl/l10.gif"));
        rl_l11=rlil13.getImage();
 //klatki animacji dla obruconego  wprawo
        
        ImageIcon rlii2 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p0.gif"));
        rl_p1=rlii2.getImage();
        ImageIcon rlii4 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p1.gif"));
        rl_p2=rlii4.getImage();
        ImageIcon rlii5 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p2.gif"));
        rl_p3=rlii5.getImage();
        ImageIcon rlii6 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p3.gif"));
        rl_p4=rlii6.getImage();
        ImageIcon rlii7 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p4.gif"));
        rl_p5=rlii7.getImage();
        ImageIcon rlii8 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p5.gif"));
        rl_p6=rlii8.getImage();
        ImageIcon rlii9 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p6.gif"));
        rl_p7=rlii9.getImage();
        ImageIcon rlii10 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p7.gif"));
        rl_p8=rlii10.getImage();
        ImageIcon rlii11 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p8.gif"));
        rl_p9=rlii11.getImage();
        ImageIcon rlii12 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p9.gif"));
        rl_p10=rlii12.getImage();
        ImageIcon rlii13 = new ImageIcon(this.getClass().getResource("/tex/g/rl/p10.gif"));
        rl_p11=rlii13.getImage();
        
        //animacje dla trzymanego rail guna
        ImageIcon rgii = new ImageIcon(this.getClass().getResource("/tex/g/rg/l0.gif"));
		rg_l1 = rgii.getImage();
        ImageIcon rgil3 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l1.gif"));
        rg_l2 = rgil3.getImage();
        ImageIcon rgil5 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l2.gif"));
        rg_l3=rgil5.getImage();
        ImageIcon rgil6 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l3.gif"));
        rg_l4=rgil6.getImage();
        ImageIcon rgil7 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l4.gif"));
        rg_l5=rgil7.getImage();
        ImageIcon rgil8 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l5.gif"));
        rg_l6=rgil8.getImage();
        ImageIcon rgil9 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l6.gif"));
        rg_l7=rgil9.getImage();
        ImageIcon rgil10 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l7.gif"));
        rg_l8=rgil10.getImage();
        ImageIcon rgil11 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l8.gif"));
        rg_l9=rgil11.getImage();
        ImageIcon rgil12 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l9.gif"));
        rg_l10=rgil12.getImage();
        ImageIcon rgil13 = new ImageIcon(this.getClass().getResource("/tex/g/rg/l10.gif"));
        rg_l11=rgil13.getImage();
 //klatki animacji dla obruconego  wprawo
        
        ImageIcon rgii2 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p0.gif"));
        rg_p1=rgii2.getImage();
        ImageIcon rgii4 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p1.gif"));
        rg_p2=rgii4.getImage();
        ImageIcon rgii5 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p2.gif"));
        rg_p3=rgii5.getImage();
        ImageIcon rgii6 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p3.gif"));
        rg_p4=rgii6.getImage();
        ImageIcon rgii7 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p4.gif"));
        rg_p5=rgii7.getImage();
        ImageIcon rgii8 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p5.gif"));
        rg_p6=rgii8.getImage();
        ImageIcon rgii9 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p6.gif"));
        rg_p7=rgii9.getImage();
        ImageIcon rgii10 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p7.gif"));
        rg_p8=rgii10.getImage();
        ImageIcon rgii11 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p8.gif"));
        rg_p9=rgii11.getImage();
        ImageIcon rgii12 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p9.gif"));
        rg_p10=rgii12.getImage();
        ImageIcon rgii13 = new ImageIcon(this.getClass().getResource("/tex/g/rg/p10.gif"));
        rg_p11=rgii13.getImage();
	}
	/**
	 * @param czyUdaloSieZabic zmienna przekazuje true gdy przeciwnik zginal.
	 * @param bron wartosc int odpowiadajaca rodzajowi broni (dokladnie pocisku, a ten wskazuje na rodzaj broni), ktory trafil w przeciwnika.
	 * Funkcja wywolywana przez obiekt utworzony z klasy Poziom w momencie sprawdzania kolizji broni. Jako pierwszy parametr jest podawana metoda zadajObrazenia, ktora
	 * nalezy do klasy Komp, ale zwraca ona wartosc true gdy wrogowi zycie spadnie ponizej 0, oznacza, to ze pocisk (bron) jaka w niego trafila, przekazanej jako
	 * drugi parametr usmiercila przeciwnika i mozna zwiekszyc licznik do tablicy kill[]. Takze samo trafienie jest odnotowane w tablicy trafione[].
	 */
	public void setKills(boolean czyUdaloSieZabic,int bron)//wykorzystanie mozlwosci zwrotu przez funckje zadajacej obrazenia
	//artowsci boolean, jesli uda sie zabic(gdzie jest to spr. w poziomie) to zwraca true i kills sie inkrementuje
	{
		trafione[bron]++;//zwieksz licznych celnych trafien w zaleznosci od broni
		if(czyUdaloSieZabic)
		{
		kill[bron]++;
		kills++;
		}
	}
	/**@return zmienna zwiekszajaca sie za kazdym razem kiedy gracz zginie*/
	public int getLicznikSmierci()
	{
		return licznikSmierci;
	}
	/**Ustawia zmienna licznikSmierci na 0.*/
	public void zerujLicznikSmierci()
	{
		licznikSmierci=0;
	}

	/**
	 * @param bron wskazanie na rodzaj broni.
	 * @return element tablicy z tablicy kill[].
	 * Ile razy zabito przeciwnika z danej broni.
	 */
	public int getKillZDanejBroni(int bron)
	{
		return kill[bron];
	}
	/**
	 * @param bron wskazanie na rodzaj broni.
	 * @return element tablicy z tablicy trafione[].
	 * Ile razy trafiono przeciwnika z danej broni.
	 */
	public int getTrafieniaZDanejBroni(int bron)
	{
		return trafione[bron];
	}
	/**
	 * @param bron wskazanie na rodzaj broni.
	 * @return element tablicy z tablicy wystrzelone[].
	 * Informacja ile razy wystrzelono z danej broni.
	 */
	public int getWystrzeloneZDanejBroni(int bron)
	{
		return wystrzelone[bron];
	}
	
	/**
	 * @return ilosc usmiercen z kazdej broni.
	 */
	public int getKills()
	{
		return kills;
	}
	/**
	 * @return wartosc ktora mowi na jaka wysokosc w pikselach wyskoczyl w danej chwili gracz.
	 */
	public int getLicznikSkoku()
	{
		return licznikSkoku;
	}
	/**
	 * @return wartosc ustawiona bedzie na true gdy graczowi zycie spadnie ponizej 1 i ginie==true, kiedy to zakonczy sie animacja smierci.
	 */
	public boolean czyNieZyje()
	{
		return niezyje;
	}
	/**
	 * @return wartosc==true gdy graczowi zycie spadnie ponizej 1, wtedy ta zmienna mowi nam, ze mozna np zaczac wyswietlac animacje smierci.
	 */
	public boolean czyGinie()
	{
		return ginie;
	}

	/**
	 * @param jaka rodzaj broni jaka gracz podnosi.
	 * @param amunicja ilosc amunicji o jaka zwiekszy sie jej zapas.
	 * Funkcja ustawia odpowiedni element tablicy posiadanaBron na true i ustawia ilosc amunicji za pomoca metody setAmunicje(). W dodawaniu amunicji chodzi
	 * o to aby wraz z podniesieniem broni gracz nie podnosil jej w ilosciach wiekszych niz podana jako drugi parametr do funkcji w przypadku gdy
	 * juz posiada amunicje do tej broni. Chodzi o to aby gracz gdy chce podniesc amunicje nie skupial sie na bieganiu do broni,ktora nie zwiekszy mu
	 * znacznie zasobnosci magazynka, lecz prubowal zdobyc pakiet
	 * amunicyjny w innym miejscu.
	 */
	public void setBron(int jaka,int amunicja)
	{
		 posiadanaBron[jaka]=true;//daj bron
		 if (getAmunicje(jaka)<amunicja)
		 setAmunicje(jaka,amunicja-getAmunicje(jaka));//jezeli gracz posiada mniej amunicji niz jest w pakiecie
		 //to dopelnij do pakietu
	}
	/**
	 * @param jaka rodzaj broni.
	 * @return wartosc mowi nam czy wskazana bron w pierwszym parametrze jest w posiadaniu gracza.
	 */
	public boolean getBron(int jaka)
	{
		return posiadanaBron[jaka];
	}
	
	/**
	 * @return zwraca informacje jaki rodzaj broni w danej chwili trzyma w reku gracz. Potrzebne np: w funkcji strzel, ktora mosi wiedziec z jakiej broni gracz
	 * w danej chwili strzela.
	 */
	public int getAktualnaBron()
	{
		return aktualnaBron;
	}
	
	/**
	 * @param h ilosc o jaka chcemy zmienic Hp (zycie) gracza. Moze byc to wartosc dodatnia, gdy chce mu zwiekszyc zycie, np w przypadku podniesienia
	 * apteczki. Lub moze byc to wartosc ujemna gdy chce mu zycie odebrac-zadajac obrazenia. Chodzi o to aby ustawione zycie nie moglo sie zwiekszyc niz wartosc
	 * dla niego maksymalna (200), wtedy w zaleznosci od ilosci zycia o jakie chce mu podniesc nalzey odjac te wartosci ktore wystaja powyzej maksa. Ponad to
	 * w przypadku niektorych apteczek nie moga one zostac podniesione gdy zycie ma wartosc 100 (dotyczy to apteczek sredniej i duzej hp=25 i 50).
	 * Natomiast do woli mozna podnosic apteczki male (5) i mega(100). Inna rzecz ma miejsce gdy chcemy odjac zycie graczowi, wtedy nalezy sprawdzic
	 * czy gracz posiada pancerz, ktory niweluje obrazenia o 50% pochlaniajac druga jego czesc (odejmujac od siebie). Nalezy sprawdzic czy polowa obrazen
	 * to bedzie calosc posiadanego pancerza czy jednak nie, wtedy gdy skonczy sie pancerz a obrazenia calkowicie nie zostaly przez niego pochloniete, 
	 * reszta musi zostac odjeta od zycia. Ponadto na samym koncu nastepuje sprawdzenie czy Hp<0 aby ustawic znacznik ginie na true w zaistnialym przypadku;].
	 */
	public void setHp(int h)
	{
		int suma=hp+h;
		//
		if (h>0)//w przypadku gdy chce zwiekszyc zycie nie moze ono przekroczyc maxa
		{
	//i nie powinienem moc podnosic 25 i 50 gdy mam 100 energii i gdy mma mniej niz 100 ale ta roznica jest mniejsza niz wartosc apteczki
			//dodaje sie tylko dopelnienie do 100
	if(h==25 || h==50)//w przypadku gdy podnoszone apteczki to 25 lub 50
	{
	int max=100;
	//ta mega zaila konstrukcja zalatwia sprawe z roznica, ale nie zalatwia sprawy przypadku mozliwosci podniesienia gdy ma wiecej niz 100
	//warunek sumy sie zgadza
	if(suma>max)
	{
	hp=max;
	}else
		{
			hp+=h;
		}
		
	}else
	{
		if(suma>S_swiata.maxIloscHp)
			{
			hp=S_swiata.maxIloscHp;
			}else
			{
				hp+=h;
			}
		}//gdy chce obnizyc zycie to tzreba zasygnalizowac gdy osiagnie 0
		}
		else if (h<0)
		{

//jesli posiada pancerz powinno odjac mu polowe z pancerza i polowe z hp
			if(pancerz>0)
			{
			h=h*(-1);//zamieniam h na liczbe dodatnia abym mogl logicznie latwiej to rozwiazac
			
			if (h/2>pancerz)
			{
			hp-=h/2+(h/2-pancerz);
			
			pancerz-=pancerz;
			}else if(h/2<=pancerz)
			{
				
				pancerz-=h/2;
				hp-=h/2;
			}
	
			}else
			{
			hp+=h;//h- wiec odejmij
			}
			
			if(hp<=0)//tzreba dac znac gdy hp osiagnie 0 lub mniej
		{
			ginie=true;	
		}
		}	
	}

	/**
	 * @return ilosc aktualnego posiadanego zycia.
	 */
	public int getHp()
	{
		return this.hp;
	}
	/**
	 * @param p ilosc o jaka chce zwiekszyc wartosc pancerza.
	 * Wartosc pancerza nia moze przekroczyc wartosci podanej jako maksymalna w grze.
	 */
	public void setPancerz(int p)
	{
		//nie moze byc wyzsza niz max pancerza
		int suma=pancerz+p;
		if(suma>S_swiata.maxIloscpancerza)
			{
			pancerz=S_swiata.maxIloscpancerza;
			}else
			{
				pancerz+=p;
			}
		if(suma<0)
		{
			pancerz=0;
		}
	}
	/**
	 * Funkcja za kazdym razem gdy gracz bedzie mial wiecej zycia lub pancerza niz okreslona wartosc (finalnie 100)
	bedzie co sekunde odejmowala mu odpowiednio hp lub pancerz do okreslonej wartosci (finalnie 100). Metoda sprawdza za kazdym razem czy nastapil nadmiar zycia lub pancerza
	i jezeli znacznik mowiacy nam ze czas od poprzeniego odjecia minal (liczeczasPancerza,liczeczasHp) to moge odjac wartosc 1. Gdy te znaczniki sa ustawione na prawde
	do zmiennej tymczasowej pobieram aktualny czas i sprawdzam czy jest wiekszy niz czas ustawiony w zmiennych czasObnizaniaHp lub czasObnizaniaPancerza(ktore
	byly ustawiane gdy byl nadmiar i znaczniki==false w ten sposob ze do aktualnego czasu dodalem 1000 (1sek)). Takze gdy w koncu zmienna tymczasowa bedzie wieksza
	niz czas pobrany wczesniej oznacza ze minela sekunda i znaczniki ustawiam na false i odejmuje Hp lub Pancerz.
	 */
	private void zmniejszHpIPancerzGdyNadmiar()
	{
		
		
		if(liczeCzasPancerza==false && pancerz>S_swiata.minObnizki)//jezeli nie zliczam sekund i a pancerz ma wyzsza wartosc
		{
			czasObnizaniaPancerza=System.currentTimeMillis()+1000;//pobierz czasi dodaj sekunde
			liczeCzasPancerza=true;//ustawienie aby liczyl ponizej czas
		}
		if( liczeCzasHp==false && hp>S_swiata.minObnizki)
		{
			czasObnizaniaHp=System.currentTimeMillis()+1000;//pobierz czas i dodaj sekunde
			liczeCzasHp=true;//ustawienie aby liczyl ponizej czas
		}
		if(liczeCzasPancerza)
		{
		 long temp=System.currentTimeMillis();
		 if(czasObnizaniaPancerza-temp<=0)//jezeli minela sekunda
		 {
			 liczeCzasPancerza=false;
			 setPancerz(-1);
		 }
		}
		if(liczeCzasHp)
		{
		 long temp=System.currentTimeMillis();
		 if(czasObnizaniaHp-temp<=0)//jezeli minela sekunda
		 {
			 liczeCzasHp=false;
			 hp--;
		 }
		}

	}
	/** @return aktualna wartosc pancerza.*/
	public int getPancerz()
	{
		return this.pancerz;
	}
	
	/**@param jaka rodzaj broni (amunicji), ktora chce zwiekszyc.
	 * @param ilosc wartosc o jaka chce zwiekszyc posiadana amunicje.
	 * Dodanie do tablicy amunicja do odpowiedniego elementu wartosci o jaki chce zwiekszyc "magazynek". Nie moze on przekroczyc
	 * maksymalne wartosci jaka wogole moze posiadac gracz.
	 */
	public void setAmunicje(int jaka,int ilosc)
	{
		if(this.amunicja[jaka]<=S_swiata.maxAmunicji)
		{
		this.amunicja[jaka]+=ilosc;
		}else 
		{
			
			this.amunicja[jaka]=(S_swiata.maxAmunicji);
		}
	}
	/**
	 * @param jaka rodzaj broni.
	 * @return ilosc amunicji danego rodzaju. Dodatkowo spradzam, w razie gdyby parametr byl inny niz rodzaj amunicji,
	 * czy jest inny, aby czasem nie czytac tablicy elementu ktorego nie ma. W sumie to moglem to zrobic odpowiednim tryem;].
	 */
	public int getAmunicje(int jaka)
	{
		int zwrot=0;
		if(jaka==S_swiata.rl_id || jaka==S_swiata.mg_id || jaka==S_swiata.rg_id)//aby nie pytac o inne elementy tablicy co by wywolalo blad
		{
			switch (jaka)
			{
			case S_swiata.rl_id: zwrot=this.amunicja[S_swiata.rl_id];break;
			case S_swiata.mg_id: zwrot=this.amunicja[S_swiata.mg_id];break;
			case S_swiata.rg_id: zwrot=this.amunicja[S_swiata.rg_id];break;
			}
		}
		return zwrot;
	}

	/**
	 * @param bron rodzaj broni.
	 * @return true gdy bron o jaka pytam jest przeladowana.
	 * Bron uwazam za przeladowana gdy od wystrzalu z niej minal okreslony czas. Przez ten moment (przeladowywania) nie moge ponownie z niej
	 * wystrzelic ani tez zmienic na inna! Calosc sprowadza sie do pobierania czasu w momencie wystrzalu i co cykl sprawdzania czy minal juz czas
	 * przeladowania. Jest on inny dla kazdego rodzaju broni. 
	 */
	public boolean czyPrzeladowana(int bron)
	{
		long temp;//tymczasowa zmienna potrzebna do policzenia roznicy w czasie
		//w konstruktorze ustalony byl nowy czas przeladowania
		staryCzasPrzeladowania=nowyCzasPrzeladowania;//teraz przypisuje go staremu czasowi
		boolean zwrot = false;
		temp=System.currentTimeMillis();//pobieram aktualny czas
		long roznica=temp-staryCzasPrzeladowania;//i sprawdzam roznice, pomiedzy tym w ktorym chce wystrzelic a starym
		
		switch (bron)//w zaleznosci od trzymanej w reku broni
		{
		case S_swiata.rl_id: {if(roznica>=S_swiata.czasPrzeladowaniaRakiety)zwrot=true;break;}//spr. czy roznica jest >=stalej,jak tak to mozna strzelic
		case S_swiata.mg_id: {if(roznica>=S_swiata.czasPrzeladowaniaMg)zwrot=true;break;}
		case S_swiata.rg_id: {if(roznica>=S_swiata.czasPrzeladowaniaRaila)zwrot=true;break;}
		}
		
		return zwrot;
	}
	
	/**
	 * @return wartosc mowiaca o pozycji wzgledem wspolrzednej x calego wgranego levelu.
	 */
	public int getPozycjaXGry(){
		return pozycjaXGry;
	}
	/**
	 * @param x ustawia wartosc mowiaca o pozycji wzgledem wspolrzednej x calego wgranego levelu.
	 */
	public void setPozycjaXGry(int x)
	{
		pozycjaXGry=x;
	}
	/**@param i wg niej ustawia znacznik idz na true lub false
	 */
	public void setIdz(boolean i)
	{
		this.idz=i;
	}
	/**
	 * @return znacznik idz. Dzieki niemu wiem czy gracz porusza sie po osi x czy tez nie.
	 */
	public boolean getIdz()
	{
		return this.idz;
	}
	/**
	 * @param x wsp. x lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 * @param y wsp. y lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 */
	public void setxy(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * @param y wsp. y lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 */
	public void setY(int y)
	{
		this.y=y;
	}
	/**
	 * @param x wsp. x lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 */
	public void setX(int x)
	{
		this.x=x;
	}
	/**
	 * @return x wsp. x lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 */
	public int getX(){
		return x;
	}
	/**
	 * @return y wsp. y lewego gornego rogu obrazka gracza, to takze wspolrzedna x gracza, w poszczegolnych sytuacjach korygowana o odpowiednia wartosc.
	 */
	public int getY(){
		return y;
	}
	
 	/**
 	 * @return obrazek, ktory bedzie wykorzystany w metodzie rysujacej.
 	 * Funkcja w zaleznosci od sytuacji gracza (skacze,idzie w lewo,prawo, stoi i patrzy sie w lewo, prawo lub ginie patrzac w lewo,prawo) co cykl zwieksza licznikKroku.
 	 * Gdy licznik kroku osiagnie jakas okreslona wartosc zmienia sie licznikAnimacji. W zaleznosci od licznikaAnimacji zwracanej wartosci jest przypisaywany
 	 * odpowiedni obrazek. 
 	 */
 	public Image getImage() {
    //w zaleznosci od roznych wartosci Gracza, zwraca odpowiednia animacje 
		Image zwrot=null;//musi byc zainicjowany zwrot bo inaczej sie nie skompiluje
		//jezeli postac patzry w lewo i idzie to mozlwe sa 2 klatki animacji
	//w zaleznosci jaka posiada bron taka powinien animowac
		//problem w przypadku proby wyswietlenia animacji gdy nie wcisniety klawisz
		//tzreba ustawic jakis timer z zewnatrz
		if(ginie==true)//jezeli ginie==true to licznik kroku jest zmieniany nie klawiszami ale czasowo z Obslugi
		{
			if(!zaczalGinac){//aby moc ustawic na pierwsza klatke
				licznikAnimacji=1;
				licznikSmierci++;//zlicza ilosci smierci
				zaczalGinac=true;
			}
			if(licznikKroku%4 == 0)
				licznikAnimacji++;
		if(patrzyWPrawo)	
			switch(licznikAnimacji)
			{
			case 1: zwrot=s_mg_p0;break;
			case 2: zwrot=s_mg_p1;break;
			case 3: zwrot=s_mg_p2;break;
			case 4: zwrot=s_mg_p3;break;
			case 5: zwrot=s_mg_p4;break;
			case 6: niezyje=true;//gdy animacja dojdzie do 6 zabija gracza
			//default : zwrot=mg_l4;break;
			}
		if(patrzyWLewo)	
			switch(licznikAnimacji)
			{
			case 1: zwrot=s_mg_l0;break;
			case 2: zwrot=s_mg_l1;break;
			case 3: zwrot=s_mg_l2;break;
			case 4: zwrot=s_mg_l3;break;
			case 5: zwrot=s_mg_l4;break;
			case 6: niezyje=true;//gdy animacja dojdzie do 6 zabija gracza
			//default : zwrot=mg_l4;break;
			}
		}
		
		if(aktualnaBron==S_swiata.mg_id && !ginie)
		{
		if(patrzyWLewo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=mg_l2;break;
			case 2: zwrot=mg_l3;break;
			case 3:zwrot=mg_l4;break;
			case 4:zwrot=mg_l5;break;
			case 5:zwrot=mg_l6;break;
			case 6:zwrot=mg_l7;break;
			case 7:zwrot=mg_l8;break;
			case 8:zwrot=mg_l9;break;
			case 9:zwrot=mg_l10;break;
						
			default : zwrot=mg_l1;break;
			}	
		}else if (patrzyWLewo==true && idz==false)zwrot=mg_l1;//jezeli tylko patrzy to wyswietlaj pierwsza klatke ktora odpowiada za stoi
		
		if(patrzyWPrawo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=mg_p2;break;
			case 2: zwrot=mg_p3;break;
			case 3:zwrot=mg_p4;break;
			case 4:zwrot=mg_p5;break;
			case 5:zwrot=mg_p6;break;
			case 6:zwrot=mg_p7;break;
			case 7:zwrot=mg_p8;break;
			case 8:zwrot=mg_p9;break;
			case 9:zwrot=mg_p10;break;
					
			default : zwrot=mg_p1;break;
			}	
		}else if (patrzyWPrawo==true && idz==false)zwrot=mg_p1;
		if(skacze && patrzyWPrawo || spada && patrzyWPrawo)
		//nie ebdzie przebieral nogami	
		{
			zwrot=mg_p11;
		}
		if(skacze && patrzyWLewo ||spada && patrzyWLewo)
		{
			zwrot=mg_l11;
		}

		}
		//teraz animacje RL
		if(aktualnaBron==S_swiata.rl_id && !ginie)
		{
		if(patrzyWLewo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=rl_l2;break;
			case 2: zwrot=rl_l3;break;
			case 3:zwrot=rl_l4;break;
			case 4:zwrot=rl_l5;break;
			case 5:zwrot=rl_l6;break;
			case 6:zwrot=rl_l7;break;
			case 7:zwrot=rl_l8;break;
			case 8:zwrot=rl_l9;break;
			case 9:zwrot=rl_l10;break;
						
			default : zwrot=rl_l1;break;
			}	
		}else if (patrzyWLewo==true && idz==false)zwrot=rl_l1;//jezeli tylko patrzy to wyswietlaj pierwsza klatke ktora odpowiada za stoi
		
		if(patrzyWPrawo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=rl_p2;break;
			case 2: zwrot=rl_p3;break;
			case 3:zwrot=rl_p4;break;
			case 4:zwrot=rl_p5;break;
			case 5:zwrot=rl_p6;break;
			case 6:zwrot=rl_p7;break;
			case 7:zwrot=rl_p8;break;
			case 8:zwrot=rl_p9;break;
			case 9:zwrot=rl_p10;break;
			default : zwrot=rl_p1;break;
			}	
			
		}else if (patrzyWPrawo==true && idz==false)zwrot=rl_p1;
		if(skacze && patrzyWPrawo || spada && patrzyWPrawo)
		//nie ebdzie przebieral nogami	
		{
			zwrot=rl_p11;
		}
		if(skacze && patrzyWLewo ||spada && patrzyWLewo)
		{
			zwrot=rl_l11;
		}
		}
		
		if(aktualnaBron==S_swiata.rg_id && !ginie)
		{
		if(patrzyWLewo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=rg_l2;break;
			case 2: zwrot=rg_l3;break;
			case 3:zwrot=rg_l4;break;
			case 4:zwrot=rg_l5;break;
			case 5:zwrot=rg_l6;break;
			case 6:zwrot=rg_l7;break;
			case 7:zwrot=rg_l8;break;
			case 8:zwrot=rg_l9;break;
			case 9:zwrot=rg_l10;break;
						
			default : zwrot=rg_l1;break;
			}	
		}else if (patrzyWLewo==true && idz==false)zwrot=rg_l1;//jezeli tylko patrzy to wyswietlaj pierwsza klatke ktora odpowiada za stoi
		
		if(patrzyWPrawo==true && idz==true){
			switch(licznikAnimacji){
			case 1: zwrot=rg_p2;break;
			case 2: zwrot=rg_p3;break;
			case 3:zwrot=rg_p4;break;
			case 4:zwrot=rg_p5;break;
			case 5:zwrot=rg_p6;break;
			case 6:zwrot=rg_p7;break;
			case 7:zwrot=rg_p8;break;
			case 8:zwrot=rg_p9;break;
			case 9:zwrot=rg_p10;break;
			default : zwrot=rg_p1;break;
			}	
			
		}else if (patrzyWPrawo==true && idz==false)zwrot=rg_p1;
		if(skacze && patrzyWPrawo || spada && patrzyWPrawo)
		//nie ebdzie przebieral nogami	
		{
			zwrot=rg_p11;
		}
		if(skacze && patrzyWLewo ||spada && patrzyWLewo)
		{
			zwrot=rg_l11;
		}

		}
		return zwrot;
    }
 	/** @param l wartosc o jaka zmieniam licznik kroku. Potrzebne w sytuacji gdy bracz ginie i nie sa wciskane klawisze aby zmienic licznikKroku. Wtedy z obiektu Oblsuga
 	 * ustawiam za pomoca tej metody licznikKroku gracza.
 	 */
 	public void setLicznikKroku(int l)
 	{
 		licznikKroku=l;
 	}
 	/**
 	 * @return wartosc licznika, ktory odpowiada posrednio za ustalanie licznikaAnimacji.
 	 */
 	public int getLicznikKroku()
 	{
 		return licznikKroku;
 	}
	/**@return wartosc zwracana mowi nam jaki numer ma aktualnie wyswietlana klatka.
	 */
	int getLicznikAnimacji(){
		return licznikAnimacji;
	}
	//obsluga list strza³ów, wybuchow itd
	
	/**@return liste wystrzelonych pociskow z Rl*/
	 
	public ArrayList<Rakieta> getListaRakiet()
	{
		return listaRakiet;
	}
	/**@return liste wystrzelonych pociskow z Mg*/
	public ArrayList getListaMg()
	{
		return listaMg;
	}
	/**@return liste blyskow pojawiajacych sie po wystrzeleniu z Rl*/
	public ArrayList getListaRl_boom()
	{
		return listaRl_boom;
	}
	
	/**
	 * @param lista typu listaRl_boom
	 */
	public void setListaRl_boom(ArrayList lista)
	{
		listaRl_boom=lista;
	}
	
	/**Gdy dokonam odpowieniej obrobki listyRakiet w innym obiekcie za pomoca tej funkcji przypisuja ja spowrotem graczowi.*/
	public void setListaRakiet(ArrayList lista)
	{
		listaRakiet=lista;
	}
	/**Gdy dokonam odpowieniej obrobki listyMg w innym obiekcie za pomoca tej funkcji przypisuja ja spowrotem graczowi.*/
	public void setListaMg(ArrayList lista)
	{
		listaMg=lista;
	}
	/**Gdy dokonam odpowieniej obrobki listyRaila w innym obiekcie za pomoca tej funkcji przypisuja ja spowrotem graczowi.*/
	public void setListaRaila(ArrayList lista)
	{
		listaRaila=lista;
	}
	
	/**
	 * @return listeRaila, to linia pozostala po wystrzeleniu z raila
	 */
	public ArrayList getListaRaila()
	{
		return listaRaila;
	}

	/**
	 * @param bron rodzaj broni jaka chce zmienic
	 * Aby moc zmienic bron musza byc spelnione warunki: trzeba ja posiadac i musi ona byc przeladowana. Nie ma tez co kombinowac gdy wcisne
	 * klawisz broni ktora aktualnie mam w reku. 
	 */
	public void zmienBron(int bron)
	{
		if (bron==aktualnaBron)//jezeli bron ktora chce zmienic jest taka sama jak aktualna
		{
			//to nic nie rob
		}else if(bron!=aktualnaBron && czyPrzeladowana(aktualnaBron) && posiadanaBron[bron]==true)//jezeli wogole ja posiadam
			//w przeciwnym wypadku w zaleznosci od broni ktora chce wybrac
			//do tego sprawdzenie czy wogole taka bron posiada
		{//aby moc zmienic bron to aktualna bron musi byc przeladowana, aby nie oszukiwac z czasem na przeladunek szybko zmieniajac bron
			switch (bron)//jezeli jest przeladowana to zmieniam
			{
			case S_swiata.rl_id: aktualnaBron=bron;break;
			case S_swiata.mg_id: aktualnaBron=bron;break;
			case S_swiata.rg_id: aktualnaBron=bron;break;
			}
		}else
		{
			//nic nie rob
		}
	}
	
	/**Strzelic mozna w okreslonych przypadkach. Gdy gracz posiada pociski do broni ktora trzyma w tej chwili w reku, jezeli posiada do niej pociski 
	 * i jezeli bron zostala przeladowana. Jezeli warunki te sa spelnione to ustawiam nowyczasPrzeladowania, ktory bedzie sprawdzany przy nastepnym strzale lub mozliwosc
	 * zmiany broni na inna. W zaleznosci od broni jest odejmowana amunicja. Nastepnie w zaleznosci od strony w ktora patrzy sie gracz (lewo, prawo) 
	 * poniewaz pociski laca tylko w takich kierunkach do odpowiednich list dodawane sa obiekty odpowiadajace pociskom. Obiektom tym przekazywane sa parametry
	 * dot. ich poczatkowych wspolrzednych i kierunkom w ktorym beda sie poruszac. Dodatkowo w przypadku Rl jest zwiekszana lista Rl_boom o nowy obiekt zwiazany z blyskiem
	 * po wystrzale rakiety. Do tego inkrementowane sa liczniki wystrzalow.
	 * 
	 */
	public void strzel()
	{
		//trzeba strzelic w odpowiednim kierunku i sprawdzic czy mamy z czego i wg jakiej broni
		switch (aktualnaBron)
		{
		//0-rakieta, jezeli bron posiada amunicje i zdazyla sie przeladowac
		case S_swiata.rl_id:
		{
			if(amunicja[aktualnaBron]>0 && czyPrzeladowana(S_swiata.rl_id))//0 chodzi o pierwsza bron z tablicy
			{
				//udalo sie wystrzelic wiec ustawiam czas przeladowania
				nowyCzasPrzeladowania=System.currentTimeMillis();
				amunicja[aktualnaBron]--;//odejmuje pociski z magazynka
			if (patrzyWLewo){
				listaRakiet.add(new Rakieta(x-48,y+1,S_swiata.lewo));//0 w lewo//zmieniam pozycje x aby pocisk wylatywal z lufy a nie z plecow 
			listaRl_boom.add(new Rl_boom(x-5,y+8,S_swiata.lewo));
			wystrzelone[S_swiata.rl_id]++;
			}
			else if (patrzyWPrawo){
				listaRakiet.add(new Rakieta(x+20,y+1,S_swiata.prawo));//180 w prawo
				listaRl_boom.add(new Rl_boom(x+60,y+8,S_swiata.prawo));
				wystrzelone[S_swiata.rl_id]++;
			}
			//ltn=0;
			}break;
			
		}//koniec case rl
		case S_swiata.mg_id:{
			if(amunicja[aktualnaBron]>0 && czyPrzeladowana(S_swiata.mg_id))//0 chodzi o pierwsza bron z tablicy
		{
			//udalo sie wystrzelic wiec ustawiam czas przeladowania
			nowyCzasPrzeladowania=System.currentTimeMillis();
			amunicja[aktualnaBron]--;//odejmuje pociski z magazynka
		if (patrzyWLewo)
			{
			listaMg.add(new MG(x-40,y+1,S_swiata.lewo));//0 w lewo//zmieniam pozycje x aby pocisk wylatywal z lufy a nie z plecow 
			wystrzelone[S_swiata.mg_id]++;
			}
		else if (patrzyWPrawo)
			{
			listaMg.add(new MG(x+7,y+1,S_swiata.prawo));//180 w prawo
			wystrzelone[S_swiata.mg_id]++;
			}
		//ltn=0;
		}break;}
		case S_swiata.rg_id:{ 
			/*bezposredni strzal z rail guna*/
			if(amunicja[aktualnaBron]>0 && czyPrzeladowana(S_swiata.rg_id))//0 chodzi o pierwsza bron z tablicy
			{
				//udalo sie wystrzelic wiec ustawiam czas przeladowania
				nowyCzasPrzeladowania=System.currentTimeMillis();
				amunicja[aktualnaBron]--;//odejmuje pociski z magazynka
			if (patrzyWLewo)
				{
				listaRaila.add(new Rail(x,y+16,S_swiata.lewo));//0 w lewo//zmieniam pozycje x aby pocisk wylatywal z lufy a nie z plecow 
				wystrzelone[S_swiata.rg_id]++;
				}
			else if (patrzyWPrawo)
				{
				listaRaila.add(new Rail(x+59,y+15,S_swiata.prawo));//180 w prawo
				wystrzelone[S_swiata.rg_id]++;
				}
			//ltn=0;
			}break;
		}

		}
	}
	
	/**Move jest wywolywany w kazdym cyklu gry z poziomu metody run() obiektu Obsluga. Sprawdza czy jest wcisniety jakis klawisz i reaguje
	 * na to wywolujac inne funkcje (jak strzel) lub ustawiajac znaczniki (np: skacz). Tutaj jest zmieniany licznikKroku i Animacji. Wprowadza zmiany we wspolrzednych
	 * w zaleznosci od roznych flag (skacz,spada,patzry w lewo, idzie w prawo itp).
	 */
	public void move()
	{
		if(!ginie)
		{
		//spr czy wart hp i pancerza jest wieksza niz ustalona, gdy tak to co sekunde obnizaj o 1
		zmniejszHpIPancerzGdyNadmiar();
		if(wcisnietaSpacja)strzel();//nie stzrela przy wciskaniu spacji,poniewaz gdy wcisne inny klawisz nie mozna juz sprawdzic
		//czy spacja nadal jest wcisnieta, bufor sie kasuje, lepsza jest zmienna, unikniecie przypadku gdy gracz obrucony w lewo strzela
		//trzyma wcsniety strzal, obraca sie i nie moze wystrzelic
		if(wcisniety1)zmienBron(S_swiata.mg_id);
		if(wcisniety2)zmienBron(S_swiata.rl_id);
		if(wcisniety3)zmienBron(S_swiata.rg_id);
		//if(wcisnietyA)idz=true;else idz=false;
		//if(wcisnietyD)idz=true;else idz=false;
		if(wcisnietyW)
			{
			setGraczSkacze(true);
			}
				else
				{
				
					setGraczSkacze(false);
					setGraczSpada(true);
					}

		if (patrzyWLewo==true && idz==true )
		{ 
			x-=S_swiata.predkoscX;
			pozycjaXGry-=S_swiata.predkoscX;

			if(licznikKroku%6 == 0)
			{
				//zmiana licznika animacji na nastepny
				licznikAnimacji++;
				//gdy = 2 to ustaw na 0, bo sa tylko 2 animacje do ruchu
				if(licznikAnimacji>9) licznikAnimacji=1;
				//gdy licznikkroku ma 5 mozna go zresetowac,aby nie rosl w nieskonczonosc
				licznikKroku=1;
			}else {
				//tylko ustawic licznik kroku na wiekszy
				licznikKroku++;
			}
		}
		//tutaj podobnie jak w lewo
		if (patrzyWPrawo==true && idz==true)
		{
		
			x+=S_swiata.predkoscX;
			pozycjaXGry+=S_swiata.predkoscX;
			if(licznikKroku%6 == 0)
			{
				
				licznikAnimacji++;
				
				if(licznikAnimacji>9) licznikAnimacji=1;
			
				licznikKroku=1;
			}else
			{
				
				licznikKroku++;
			}
		}
		//ruch gdy zaczyna opadac, trzeba zwiekszac y az opadnie na powieszchnie
		if(spada==true)
		{
			y+=S_swiata.predkoscY;
			licznikSkoku=0;
			licznikUpadku+=S_swiata.predkoscY;
		}
	
		if (skacze==true)
		{
		//	blokadaSkoku=true;//trzeba zalozyc blokadeSkoku aby nie mogl ponownie wyskoczyc
			spada=false;//nie moze spadac gdy skacze
			
			if(licznikSkoku < S_swiata.czescWysokosciSkoku)//na poczatku skacze szybciej
			{
				y-=S_swiata.predkoscY2;
				licznikSkoku+=S_swiata.predkoscY2;
			}
			else if(licznikSkoku < S_swiata.wysokoscSkokumax)
			{
				y-=S_swiata.predkoscY;
				licznikSkoku+=S_swiata.predkoscY;
			}
			else
			{
				skacze=false; // jak juz nie moze wyzej skoczyc
				spada=true;//aby nie chodzil w powietrzu
			}
		}
		//licznikAnimacji=1;
		}else//jezeli ginie
		{	
		}
	}
	/**
	 * @param s ustawienie zmiennej skacze. Funkcja zmienia wartosc znacznika skacze w zaleznosci od sytuacji gracza, bo nie moze ustawic skacza na true gdy ten spada (
	 * gracz musi wyskoczyc gdy stoi na gruncie). Nie moze tez skoczyc gdy jest blokadaSkoku. Gdy gracz nie skacze ale moglby to wykonac jest zerowany licznik skoku.
	 */
	void setGraczSkacze(boolean s){
		//wyzerowanie licznikaSkoku
		if (!skacze && !blokadaSkoku && s){
			licznikSkoku=0;//jezeli nie skakal i nie mogl skoczyc bo nie mial blokady i chcemy podskoczyc to wyzeruj licznik skoku
		}
		if (spada==true)//jezeli spada
		{
			skacze=false;//to nie moze skoczyc
			//blokadaSkoku=true;//tzreba wlaczyc blokade skoku, aby nie mogl w powietrzu ponownie sie wybic
		}
		else
		{
			skacze=s;//w przeciwnym wypadku skacz
		}
	}
	
	/**@param s ustawia flage spada
	 */
	void setSpada(boolean s)
	{
		this.spada=s;
	}
	/**
	 * @return licznikUpadku to wysokosc z jakiej gracz zaczal spadac
	 */
	public int getLicznikUpadku()
	{
		return licznikUpadku;
	}
	/**Spadl jest wywolywany w obiekcie klasy Poziom, w momencie gdy gracz dotyka gruntu (przy sprawdzaniu kolizji).
	 * Gdy gracz nie dotyka gruntu i spada (znacznik spada==true) licznikUpadku jest zwiekszany. tutaj w zaleznosci od jego wielkosci
	 * graczowi odejmuje sie jakas czasc jego zycia.
	 */
	public void Spadl()
	{//po upadku ze znacznej wysokosci odejmowane jest zycie w zaleznosci od wysokosci
		
		if(licznikUpadku>S_swiata.minWysokoscUpadku && licznikUpadku<=S_swiata.maxWysokoscUpadku)
			{
			setHp(-10);
			}
		if(licznikUpadku>S_swiata.maxWysokoscUpadku)setHp(-50);
		licznikUpadku=0;
	}
	
	/**
	 * @param v moze byc - lub + w zaleznosci w jaka strone bede chcial scroolowac gracza.
	 */
	public void przesunGracza(int v)
	{
		x+=v;
	}
	/**@return  znacznik mowiacy czy gracz w danej chwili skacze.
	 */
	boolean getSkacze(){
		return skacze;
	}
	/**@return  znacznik mowiacy czy gracz w danej chwili spada.
	 */
	boolean getSpada(){
		return spada;
	}
	 /**
	 * @param s ustawia flage skacze.
	 */
	void setSkacze(boolean s){
		skacze=s;
	}
	

	 /**
	 * @return zwraca wartosc 1 gdy skacz=true,0 gdy false. Swoja droga jest to jedna z pierwszych metod jaka pisalem w tej grze. Czyli jedna z pierwszych
	 * wogole w javie. Po napisaniu tego wszystkiego nie pamietam juz co mialem w glowie ze zamieniam true na 1 i false na 0 zamiast porownywac
	 * wartosci boolean w odpowiedniej metodzie Poziomu. Funkcja ta jest wywowylana tylko w tamtym miejscu;].
	 */
	public int czySkacze()
	 {
		 int zwrot=0;
		 if(skacze==true)zwrot=1;
		 return zwrot;
	 }

	
	 /**
	 * @param s ustawia znacznik spada. W zaleznosci od innych flag ustawia spada. Takze gdy ustawi spada trzeba zmienic wartosc znacznika skacze i blokady skoku.
	 */
	void setGraczSpada(boolean s){
			
			if (!s)//jezeli gracz nie spada
			{
				if(blokadaSkoku==true)//jezeli jest zablokowany w skoku
				{
					blokadaSkoku=false;//to odblokuj skok
					setGraczSkacze(false);//i wylacz skok
				}
			
			}
		}
	
	/**@return gdy pozycja y gracza przekroczy y dolu ekranu to wartosc=true
	 */
	public boolean czyGraczWypadl()
	{
		if(y>S_swiata.wysokosc)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * @param e przekazywany przez metode keyPressed z Obslugi. Jest to kod wcisnietego klawisza. W zaleznosci od jego rodzaju ustawiam odpowiednie znaczniki.
	 */
	public void klawiszWcisniety(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE)
        {
        	wcisnietaSpacja=true;
        
        }
        
        if (key == KeyEvent.VK_1)
        {
        	wcisniety1=true;
        
        }
        if (key == KeyEvent.VK_2)
        {
        	wcisniety2=true;
        
        }
        if (key == KeyEvent.VK_3)
        {
        	wcisniety3=true;
        }
        
        if (key == KeyEvent.VK_A ||key == KeyEvent.VK_LEFT )
        {
        	wcisnietyA=true;
        	idz=true;
            patrzyWLewo=true;patrzyWPrawo=false;
        
        }

        if (key == KeyEvent.VK_D ||key == KeyEvent.VK_RIGHT) 
        {
        	wcisnietyD=true;
        	 idz=true;
        	 patrzyWLewo=false;patrzyWPrawo=true;
        	 
        }

        if (key == KeyEvent.VK_W ||key == KeyEvent.VK_UP )
        {
        	wcisnietyW=true;
            
        }

        if (key == KeyEvent.VK_S) 
        {
        	idz=false;
 
        }
    }
	
	/**
	 * Gdy gracz konczy level a metoda klawiszPuszczony nie zostala wywolana, wtedy
		trzeba automatycznie wylaczyc spacje by na poczatku nastepnego levelu gracz sam nie strzelal.
	 */
	public void wycisnijSpacje()
	{
		
		wcisnietaSpacja=false;
	}
	/**
	 * @param e przekazywany przez metode keyReleased z Obslugi. Jest to kod puszczonego klawisza.
	 *  W zaleznosci od jego rodzaju ustawiam odpowiednie znaczniki.
	 */
	 public void klawiszPuszczony(KeyEvent e)
	 {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_1)
	        {
	        	wcisniety1=false;
	        
	        }
	        if (key == KeyEvent.VK_2)
	        {
	        	wcisniety2=false;
	        }
	        if (key == KeyEvent.VK_3)
	        {
	        	wcisniety3=false;
	        }
	        
	        if (key == KeyEvent.VK_A ||key == KeyEvent.VK_LEFT) {
	        	wcisnietyA=false;
	        	idz=false;
	        }
	        if (key == KeyEvent.VK_SPACE)
	        {
	        	wcisnietaSpacja=false;  	
	        }

	        if (key == KeyEvent.VK_D ||key == KeyEvent.VK_RIGHT) {
	        	wcisnietyD=false;
	        	
	        	idz=false;
	        }

	        if (key == KeyEvent.VK_W ||key == KeyEvent.VK_UP) {
	        	wcisnietyW=false;
	   
	        }

	        if (key == KeyEvent.VK_S) {
	        	idz=false; 
	        }
	    }
	 /**Metoda ktora robi tak wiele a tak malo zajmuje. We wspolrzednych x,y rysuje obrazek zwracany za pomoca metody getImage(), jest on rozny w zaleznosci od
	  * sytuacji gracza i licznikaAnimacji. Sprawdz opis metody gracz.getImage().
	 */
	public void rysujGracza(Graphics g)
		{
			g.drawImage(getImage(),x ,y,r);
		}
}
