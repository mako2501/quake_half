
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


/**@author Rafa³ MAKOWSKI.  
 *  Klasa rozszerzajaca Panel z zaimplementowanym interfejsem Runnable potrzebnym aby uruchomic gre jako watek, aby moc nim sterowac,
 * potrzebne aby uspic go w g³ownej petli while(true). Obiekt tej klasy ³aczy pozosta³e obiekty w grze i sprawia, ¿e wspoldzialaja miedzy soba
 * Posiada g³owna funkcje paint ktora wyrysowuje Poziom(Bloki i obiekty POD) i Gracza(Postac gracza i jego strza³y), wybuchy i pasek statusu.
 * Aby je wyrysowac ca³y czas kontroluje pozycje Gracza w calej grze aby w odpowiednim momencie przewinac wszystkie obiekty w odpowiednia strone.
 * Klasa obsluguje zdarzenia zwiazane z nacisnieciem i puszczeniem klawiszy a potem przekazuje te informacje do odpowiednich obiektow
 * (dla Gracza aby wiedzia³ np czy sie ruszyc czy strzelic, lub do menu aby sie w nim poruszac).
 */
public class Obsluga extends JPanel implements Runnable {

	private Gracz gracz;//zmienna gracz
	
	private Lv_1 P1;
	private Lv_2 P2;
	private Menu menu1;
	private Tab_infor tInf;
	private int c_licznikKroku;
	
	/**Zmienna ktora posluguje sie w oblsudze, wskazuje ona na aktualny poziom ktory chce obslugiwac,
	 *  gdy zaistnieja wrunki tworze inny poziom i tej zmiennej przypisuje referencje do tego poziomu*/
	private Poziom aktualnyPoziom;
	
	private boolean koniecGry,koniecPoziomu,tablicaInformacyjna;
	
	/**Tyle ms oczekuje, jezeli czas wykonania obliczen i wgrania obrazkow byl krotszy, na nowy cykl gry.
	 */
	private static final int FPS=19;
	
	/**Lista zawierajaca obiekty, które powstaja w momencie Uderzenia Pocisku mg	 */
	ArrayList mg_wybuchy;
	/**Lista zawierajaca obiekty, które powstaj¹ w momencie Uderzenia Pocisku rl
	 */
	ArrayList rl_wybuchy;
	/**Lista zawierajaca obiekty, które powstaj¹ w momencie Uderzenia RG
	 */
	ArrayList rg_hity;
	
	/**Informuje czy aktualnie gramy w grê czy znajdujemy siê w menu g³ównym
	 * 
	 */
	private boolean gra,menu;
	
	/**Pasek na dole ekranu, wyœwietlaj¹cy informacje tj. HP,Pancerz, posiadan¹ broñ i odpowiednio do niej iloœæ pocisków,
	 * ilosc zniszczonych przeciwnikó i iloœæ przeciwników których nale¿y pokonaæ aby  ukoñczyæ poziom
	 */
	private PasekStatusu sb;
	

	//konstruktor Obslugi gry
	public Obsluga()
	{
		addKeyListener(new ObsKlawisza());
		setFocusable(true);
		//setDoubleBuffered(true);
		setBackground(Color.BLACK);
		
		tablicaInformacyjna=koniecGry=koniecPoziomu=false;
		gra=false;
		menu=true;
		tablicaInformacyjna=false;
		//tymczasowy licznik zliczajacy wypadanie za ekran 
		
		//obiekt poziomu 1
		menu1= new Menu();
		tInf= new Tab_infor();
		P1= new Lv_1(this);
		P1.przesunEkranPoziomu(0);
		c_licznikKroku=1;//zmienna inkrementuje sie przez cala gre, przydatna gdy mam gdzies niezaleznie wg cyklu gry
					//wiedziec kiedy on minal
		
		
		
		aktualnyPoziom=P1;
		gracz = new Gracz(P1.getStartX(),P1.getStartY());//tworzenie nowej instancji gracza w x y brana z wczesniej utworzoego poziomu a wnim ustawiony respawn
		//spr. animacji wybuchu, przyda sie gdy wykombinuje jak zrobic aby pociski uderzaly w przeszkody i tworzyly eksplozje
		mg_wybuchy=new ArrayList();//lista w ktorej trzymam animacje wybuchow pociskow z mg
		rl_wybuchy=new ArrayList();//lista wiekszych wybuchow z rl
		rg_hity=new ArrayList();
		sb=new PasekStatusu(gracz);//status bar z informacjami na dole ekranu
		sb.setLiczbaWrogow(aktualnyPoziom.getlicznikWrogow());
	
	//tworze nowy watek do tego panelu
	     new Thread(this).start();
	}
	

	public void paint(Graphics g) {
		 
	        super.paint(g);
	        if(gra)
	        {
	        aktualnyPoziom.rysujPoziom(g);
	        gracz.rysujGracza(g);
	     
	        rysujWybuchy(mg_wybuchy,g,S_swiata.mg_id);
	        rysujWybuchy(rl_wybuchy,g,S_swiata.rl_id);
	        rysujWybuchy(rg_hity,g,S_swiata.rg_id);
	        pobierzIRysujPociski(gracz,g);
	        sb.rysujPasek(g);
	        }
	        if(menu)
	        {
	        	menu1.rysujMenu(g);
	        }
	        if(tablicaInformacyjna)
	        {
	         
	        	tInf.rysuj(g);
	        }
    
	    }
	
	 /** Zwieksza licznik kroku przez cala gre, gdy osiagnie 6 ustawia go na 1. Przydatne gdy chce
	  * wyswietlic animacje gracza gdy zginal, wtedy animacja postaci jest zalezna od czasu a nie od ruchu 
	 */
	private void inkr_c_licznikKroku()
	 {
		 c_licznikKroku++;
		 if(c_licznikKroku>6)c_licznikKroku=1;
	 }


	
	/**
	 * @param g potrzebny aby okreslic jego polozenie
	 * @param p potrzebne aby moc przewinac poziom
	 * Funkcja przesuwa gracza, poziom i wszystkie obiekty ktore sa wyswietlane w lewo badz w prawo
	 * w zaleznosci od tego w jakiej pozycji znajduje sie gracz, tzn gdy znajdzie
	 * sie w granicach okreslonych w klasie S_swiata (miejsca ekranu od ktorego chce przesunac poziom)
	 */
	public void przewinObraz(Gracz g,Poziom p)
	 {
		 //gdy zbliza sie do lewj krawedzi
		 if(g.getX()<=S_swiata.odXPrzesunWLewo)
		 {
			 if(p.czyMoznaPrzesunacWLewo()==true)
			 {
				 p.przesunEkranPoziomu(4);
				 g.przesunGracza(4);
				 przesunWybuchy(mg_wybuchy,4,S_swiata.mg_id);
				 przesunWybuchy(rl_wybuchy,4,S_swiata.rl_id);
				 przesunWybuchy(rg_hity,4,S_swiata.rg_id);
				 przesunPociski(gracz,4);
				
			 }
		 }
		 
		 if(g.getX()>=S_swiata.odXPrzesunWPrawo)
		 {
			 if(p.czyMoznaPrzesunacWPrawo()==true)
			 {
				 p.przesunEkranPoziomu(-4);
				 g.przesunGracza(-4); 
				 przesunWybuchy(mg_wybuchy,-4,S_swiata.mg_id);
				 przesunWybuchy(rl_wybuchy,-4,S_swiata.rl_id);
				 przesunWybuchy(rg_hity,-4,S_swiata.rg_id);
				 przesunPociski(gracz,-4);
				
			 }
		 }	 
	 }
	
	 /**
	 * @param lista poszczegolne listy wybuchow
	 * @param v wartosc o jaka chce przesunac dany obiekt - lewo + prawo
	 * @param rodzaj rodzaj wybuchu, rozpoznaje go w metodzie po numerze ktory odpowiada konkretnej broni
	 * ktora generuje odpowiedni wybuch
	 * Opis zobacz PrzewinObraz()
	 */
	private void przesunWybuchy(ArrayList lista,int v,int rodzaj)
	 {
		 if(rodzaj==S_swiata.mg_id)
		 for (int i=0;i<lista.size();i++)
		 {
			 Wybuch w=(Wybuch)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
			 w.przesunWybuch(v);//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
			 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
		 }
		 if(rodzaj==S_swiata.rl_id)
			 for (int i=0;i<lista.size();i++)
			 {
				 Rl_wybuch w=(Rl_wybuch)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
				 w.przesunWybuch(v);//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
				 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
			 }
		 if(rodzaj==S_swiata.rg_id)
			 for (int i=0;i<lista.size();i++)
			 {
				 Rail_hit w=(Rail_hit)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
				 w.przesunRailHita(v);//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
				 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
			 }
	 }
	 //f. przesuwa rakiety i mg i rg
	 /**
	 * @param g potrzebny aby pobrac liste wystzrelonych pociskow
	 * @param v wartosc o jaka przesune pociski
	 * Zobacz opis funkcji przewinObraz()
	 */
	private void przesunPociski(Gracz g,int v)
	 {
		 		 
			 ArrayList rl = g.getListaRakiet();//podobnie jak w wybuchach tylko tutaj lista jest z obiektu ktory powstal z klasy gracz
			 for(int i=0;i<rl.size();i++)
			 {
				 
				Rakieta r=(Rakieta)rl.get(i);
				r.przesunPocisk(v);
				rl.set(i,r);
		 }
			 g.setListaRakiet(rl);//tutaj oddaje cala liste graczowi ze zmienionymi wspolrzednymi
			 //to byly rakiety, teraz machinegun
			 ArrayList mgLista = g.getListaMg();//podobnie jak w wybuchach tylko tutaj lista jest z obiektu ktory powstal z klasy gracz
			 for(int i=0;i<mgLista.size();i++)
			 {
				 
				MG m=(MG)mgLista.get(i);
				m.przesunPocisk(v);
				mgLista.set(i,m);
		 }
			 g.setListaMg(mgLista);//tutaj oddaje cala liste graczowi ze zmienionymi wspolrzednymi
			 
			 ArrayList railLista = g.getListaRaila();//podobnie jak w wybuchach tylko tutaj lista jest z obiektu ktory powstal z klasy gracz
			 for(int i=0;i<railLista.size();i++)
			 {
				 
				Rail rg=(Rail)railLista.get(i);
				rg.przesunPromien(v);
				railLista.set(i,rg);
		 }
			 g.setListaRaila(railLista);
	 }

	 /**
	 * @param lista wybuchy wyswietlane na ekranie
	 * @param rodzaj rodzaj broni ktora wygenerowana wybuchy
	 * Funkcja ktora zmienia wartosci licznikaAnimacji dla wszystkich rodzajow wybuchow
	 */
	private void akcjeWybuchowe(ArrayList lista,int rodzaj)//move dla obiekotw Wybuch
	 {
		 if(rodzaj==S_swiata.mg_id)
		 for (int i=0;i<lista.size();i++)
		 {
			 Wybuch w=(Wybuch)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
			 w.wybuch();//uruchamiam mechanizm ktory zwiaksza kroki i animacje
			 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
		 }
		 
		 if(rodzaj==S_swiata.rl_id)
			 for (int i=0;i<lista.size();i++)
			 {
				 Rl_wybuch w=(Rl_wybuch)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
				 w.wybuch();//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
				 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
			 }
		 
		 if(rodzaj==S_swiata.rg_id)
		 
			 for (int i=0;i<lista.size();i++)
			 {
				 Rail_hit w=(Rail_hit)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
				 w.moveRailHit();//uruchamiam mechanizm ktory zwiaksza kroki i animacje
				 lista.set(i, w);//wrzucam zmieniony obiet spowrotem do listy aby mogl byc wyswietlony
			 }
		 
		
	 }
	
	/**
	 * @param lista wybuchy wyswietlane na ekranie
	 * @param g 
	 * @param rodzaj rodzaj broni ktora wygenerowana wybuchy
	 * Funkcja wyswietla poszczegolne klatki wybuchow kazdego rodzaju broni 
	 */
	private void rysujWybuchy(ArrayList lista,Graphics g,int rodzaj)
	 {
		 if(rodzaj==S_swiata.mg_id)
		 for (int i=0;i<lista.size();i++)
		 {
			 Wybuch w=(Wybuch)lista.get(i);
			 w.rysujWybuch(g);
		 }
		 if(rodzaj==S_swiata.rl_id)
		for (int i=0;i<lista.size();i++)
			 {
				 Rl_wybuch w=(Rl_wybuch)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
				 w.rysujWybuch(g);//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
			 }
		 if(rodzaj==S_swiata.rg_id)
				for (int i=0;i<lista.size();i++)
					 {
						 Rail_hit w=(Rail_hit)lista.get(i);//za kazdym razem tworzac nowa zmienna wybuchowa
						 w.rysujRailHita(g);//uruchamiam mechanizm ktory bedzie zmienial wartosc przesuniecia wzgledem akranu gdy caly swiat bedzie sie scroolowal
					 }
		 
	 }
	
	 /**
	 * @param g potrzebny aby pobrac liste kazdego rodzaju pociskow
	 * obsluguje rakiety i mg, do tego promien raila dodatkowo takze klatkuje wystrzal z rakiety rl_boom
		dodatkowo czysci listy z pociskow ktore sa niewidoczne i kopiuje je do listy gracza
			 
	 */
	public void pobierzIRuszRakiety(Gracz g)
	 {
		 ArrayList rl = g.getListaRakiet();
		 for(int i=0;i<rl.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete
			Rakieta r=(Rakieta)rl.get(i) ;
			 if(r.uderzenie==true)//jezeli rakieta uderzyla to dodaj uderzenie do listy w miejscu gdzie uderzyla
			 {
				 
				 if(r.getKierunek()==0)
					 rl_wybuchy.add(new Rl_wybuch(r.getPrzodPocisku()-3,r.getY()-10));	
				 if(r.getKierunek()==180)
					 rl_wybuchy.add(new Rl_wybuch(r.getX()-20,r.getY()-10));//tutaj wg tulu pocisku patrzac wg kierunku lotu
				//teraz Mg 
			 }
		 //jezeli rakieta jest widoczna ruszam ja
		 if(r.czyWidoczny()==true)
		 {
			 r.movePocisk();
		 }else if(r.uderzenie)rl.remove(i);//jezeli pocisk poza zasiegiem to go usun z listy i jak uderzyl
		
		 }
		 g.setListaRakiet(rl);//ustawiam liste rakiet dla gracza, tutaj nastepuje czyszczenie listy
		 //wiec u gracza nie musze jej juz usuwac
		 //MGun
		 ArrayList mgLista = g.getListaMg();
		 for(int i=0;i<mgLista.size();i++)
		 {
			MG mg=(MG)mgLista.get(i) ;
			 if(mg.uderzenie==true)
			 {
				
				 if(mg.getKierunek()==0)
					 mg_wybuchy.add(new Wybuch(mg.getPrzodPocisku()-2,mg.getY()-10));	
				 if(mg.getKierunek()==180)
					 mg_wybuchy.add(new Wybuch(mg.getX()-10,mg.getY()-10));
			 }
		
		 if(mg.czyWidoczny()==true)
		 {
			 mg.movePocisk();
		 }else if(mg.uderzenie)mgLista.remove(i);
		 }
		 g.setListaMg(mgLista);
		 //RL_BOOMY
		 ArrayList boomLista = g.getListaRl_boom();
		 for(int i=0;i<boomLista.size();i++)
		 {
			Rl_boom rlboom=(Rl_boom)boomLista.get(i) ;
			
		
		 if(rlboom.czyWidoczny()==true)
		 {
			 rlboom.boom();
			 
		 }else boomLista.remove(i);
		 }
		 g.setListaRl_boom(boomLista);
		 
		//pobranie wystrzalow z raila od gracza i zaktualizowanie ich danych
		 ArrayList listaRaila=g.getListaRaila();
		 for(int i=0;i<listaRaila.size();i++)
		 {
			 Rail rg=(Rail)listaRaila.get(i);
			 if(rg.getUderzenie()==true)
			 {
			
				 if(rg.getKierunek()==S_swiata.prawo)
					 rg_hity.add(new Rail_hit(rg.getX2()-3,rg.getY()-9));	
				 if(rg.getKierunek()==S_swiata.lewo)
					 rg_hity.add(new Rail_hit(rg.getX2()-6,rg.getY()-10));
			 }
			 if(!rg.getKoniec())//jezeli rail sie jeszcze nie wygasil
			 {
				 rg.moveRail();//to ustaw kolejny kolor itp
				 listaRaila.set(i,rg);
			 }else //a jezeli sie wygasil
				 {
				 listaRaila.remove(i);//usuwam go z tej listy
				 }
				 }
		 g.setListaRaila(listaRaila);
		 
		 
	 }
	 
	 /**
	 * @param g aby moc pobrac liste Pociskow od gracz
	 * @param graf aby wyswietlic na ekranie dany pocisk, za pomoca funkcji rysuj dla odpowiednich obiektow
	 * 
	 */
	public void pobierzIRysujPociski(Gracz g,Graphics graf )
	 {
		 ArrayList rl = g.getListaRakiet();
		 for(int i=0;i<rl.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete
			Rakieta r=(Rakieta)rl.get(i) ;
			r.rysujPocisk(graf);
		 }
		 ArrayList mgLista = g.getListaMg();
		 for(int i=0;i<mgLista.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete
			MG mg=(MG)mgLista.get(i) ;
			mg.rysujPocisk(graf);
		 }
		 ArrayList boomLista = g.getListaRl_boom();
		 for(int i=0;i<boomLista.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete
			 Rl_boom rlboom=(Rl_boom)boomLista.get(i) ;
			rlboom.rysujWybuch(graf);
		 }
		 ArrayList listaRaila = g.getListaRaila();
		 for(int i=0;i<listaRaila.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete
			 Rail rg=(Rail)listaRaila.get(i) ;
			rg.rysujPromien(graf);
		 }
	 }
	 /**
	 * @param g aby sprawdzic pozycje Y gracza
	 * Y garcza okresla jego polozenie wzgledem poziomu w pionie. Sprawdzenie za pomoca funkcji gracza
	 * czyGraczWypadl, jezli tak ustawiam jego hp na -1000 co automatycznie powinno go zabic;] 
	 */
	public void jesliGraczWypadlZaPoziom(Gracz g)
	 {
		if(g.czyGraczWypadl()==true)
		{
			g.setHp(-1000);//taka dawka powinna zabic gracza;]]
		}
	 }
	 /**
	 * Jezli gracz zginal nalezy ustawic jego licznik kroku (aby moc wyswietlic animacje)wg zmiennej obslugowej ktora inkrementuje sie
	 * przez caly czas trwania gry co 1 cykl. Funkcja resetujePoziom i gracza jezeli oczywiscie nie zyje.
	 */
	private void jesliGraczZginal(Gracz gracz,Poziom p)
	 {
		 if(gracz.czyGinie()==true)//start timera ktory bedzie zmienial licznikAnimacji
		 {
		gracz.setLicznikKroku(c_licznikKroku);
		 }
		 if(gracz.czyNieZyje()==true)
		 {
			 
			 p.resetujPoziom();
			 gracz.resetGracza(aktualnyPoziom.getStartX(),aktualnyPoziom.getStartY());//na razie reset gracza	
		 }
	 }
	/**
	 *Poziom mozna ukonczyc tylko w jeden sposob. Zabijajac wszystkich wrogow. Takze gdy licznik zabitych wrogow pobierany od gracza
	 *zrowna sie z licznikiem wrogow zliczonym w czasie ladowania poziomu zmienna koniecPoziomu ustawia sie na true.
	 *Jezeli tak sie stanie to nalzey wyswietlic tablice informacyjna, wyzerowac licznikSmierci gracza, aby od poczatku
	 *nastepnego poziomu gracz nie zaczal strzelac (pozniewaz po zabiciu ostatniego wroga zmienna wcisnietaSpacja jest ustawiona na true)
	 *ustawia sie ze spacja jest jzu nie wcisnieta.
	 */
	private void czyUkonczylPoziom(Gracz gracz,Poziom p)
	{
		
		if(aktualnyPoziom.getlicznikWrogow()==gracz.getKills() )
		{
			koniecPoziomu=true;
			
		}
		if (koniecPoziomu)
		{
			tablicaInformacyjna=true;
			if(tInf.czyKoniecTI())
			{	
		   	nastepnyPoziom();
			 gracz.resetGracza(aktualnyPoziom.getStartX(),aktualnyPoziom.getStartY());//na razie reset gracza
			 gracz.zerujLicznikSmierci();
			 gracz.wycisnijSpacje();
			 koniecPoziomu=false;
			 tablicaInformacyjna=false;
			 tInf.setNieKoniec();
			
			}
		}
	}
	/**Funkcja wywolywana gdy koniecPoziomu==true i gdy tablicaInformacyjna byla wyswietlona.
	 * Tworzy ona instancje klasy Poziom i przypisuje ja zmiennej aktualnyPoziom. Resetuje Poziom. Ponad to sprawdza czy czasem poziom ktory dopiero
	 * co byl ukoczony nie jest ostatnim poziomem i nalezy ukonczyc gre 
	 * 
	 */
	private void nastepnyPoziom()
	{

		if(aktualnyPoziom==P2)
			{
			koniecGry=true;//jezeli jest to drugi ostatnio poziom zakoncz gre
			}
		
		P2= new Lv_2(this);
		aktualnyPoziom=P2;
		aktualnyPoziom.resetujPoziom();
		
	}
	/**Funkcja kozysta z metody System.exit(0); aby zakonczyc dzialanie aplikacji jesli koniecGry==true	 */
	public void jesliKoniecGry()
	{
		if (koniecGry)System.exit(0);
		
	}

public void run()
{
while(true)
{
	//zapamietanie czasu
	long czas=System.currentTimeMillis();
	inkr_c_licznikKroku();
	if(gra)
	{
	//enemy.setLicznikKroku(c_licznikKroku);
	aktualnyPoziom.testKolizjiGracza(gracz);
	aktualnyPoziom.ruszListeWrogow();
	gracz.move();

	sb.uaktualnijPasek(gracz);
	sb.setLiczbaWrogow(aktualnyPoziom.getlicznikWrogow());
	//przekazanie do paska statusu informacji o ilosci wrogow na mapie
	
	pobierzIRuszRakiety(gracz);
	aktualnyPoziom.testKolizjiBroni(gracz);
	akcjeWybuchowe(mg_wybuchy,S_swiata.mg_id);//wlaczaja nastepne klatki animacji wybuchow
	akcjeWybuchowe(rl_wybuchy,S_swiata.rl_id);
	akcjeWybuchowe(rg_hity,S_swiata.rg_id);

	czyUkonczylPoziom(gracz,aktualnyPoziom);
	jesliGraczWypadlZaPoziom(gracz);
	jesliGraczZginal(gracz,aktualnyPoziom);
	jesliKoniecGry();
	przewinObraz(gracz,aktualnyPoziom);	
	}
	if (menu)
	{
		menu1.move();
	}
	if (menu1.czyKoniecMenu())
		{
		menu=false;
		gra=true;
		}
	if(tablicaInformacyjna)
	{	
	gra=false;
	
	tInf.move(gracz.getWystrzeloneZDanejBroni(S_swiata.mg_id),gracz.getTrafieniaZDanejBroni(S_swiata.mg_id),gracz.getKillZDanejBroni(S_swiata.mg_id),
			gracz.getWystrzeloneZDanejBroni(S_swiata.rl_id),gracz.getTrafieniaZDanejBroni(S_swiata.rl_id),gracz.getKillZDanejBroni(S_swiata.rl_id),
			gracz.getWystrzeloneZDanejBroni(S_swiata.rg_id),gracz.getTrafieniaZDanejBroni(S_swiata.rg_id),gracz.getKillZDanejBroni(S_swiata.rg_id),
			gracz.getLicznikSmierci());
	if(tInf.czyKoniecTI())gra=true;
	}
	repaint();
	
try {
	czas+=FPS;
	Thread.sleep(Math.max(0,czas-System.currentTimeMillis()));//max zwraca wartoœæ wieksza od 2 podanych
	//Thread.sleep(20);
}
catch (InterruptedException ex)
{

}
}

}
	private class ObsKlawisza extends KeyAdapter {

	
	public void keyReleased(KeyEvent e)
	{
		if(gra)
        gracz.klawiszPuszczony(e);
		if(menu)
		{
		menu1.klawiszPuszczony(e);
		}
		if(tablicaInformacyjna)
		{
    	tInf.klawiszPuszczony(e);
		}
    }
	
    public void keyPressed(KeyEvent e)
    {
    	if(gra)
        gracz.klawiszWcisniety(e);
    	if(menu)
		{
    	menu1.klawiszWcisniety(e);
		}
    	if(tablicaInformacyjna)
		{
    	tInf.klawiszWcisniety(e);
		}
    	
    }
}	
}


