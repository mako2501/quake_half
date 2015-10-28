
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * @author Rafal MAKOWSKI. 
 * 
 *Obiekt tworzy tablice w ktorych przechowuje obiekty elementow swiata (tj bloki) i Pod (np pancerze) 
	*, posiada metody ktore sprawdzaja kolizje gracza z tymi obiektami
	*obiekty te definiuje z klasy potomnej gdzie dokladnie zdefiniowane w tablicy znakowej sa znaki odpowiadajace poszczegolnym obiektom.
	*Sprawdza tez czy obiekty gracza (pociski) uderzaja w obiekty poziomu, robi to jawnie ( nalezy w Obsludze umiescic odpowiedni kod
	* natomiast cala obsluge Podow wykonuje bez "wiedzy Obslugi" wewnatrz swoich metod obslugi poziomu.
 */
public abstract class Poziom {
	
	private Component r;
	
	private Element_swiata elementyPoziomu[];
	private Element_swiata tablicaKolizji[][];//wskazuje na dany element ekranu
	private ArrayList<Pod> listaKolizjiPodow;//bedzie przechowywala armoury,healthy itd
	private ArrayList<Komp> listaWrogow;
	Component rodzic;
	private int dlugoscPoziomu,lewaGranicaPoziomu,prawaGranicaPoziomu,licznikWrogow;
	
	private int startx,starty,trafioneMg,trafioneRg,trafioneRl;//wspolrzedne startu gracza zdefiniowane pozniej w tablicy wzoru
	
	public Poziom(Component r)
	{
	this.r=r;
	}
	
	private void setStartXY(int x,int y)
	{
		startx=x;
		starty=y;
	}
	public int getStartX()
	{
		return startx;
		}
	public int getStartY()
	{
		return starty;
	}

	/**
	 * @param wzor tablica podana jako tablica elementow klasy String, takze jest to taka tablica tablic. InicjujPoziom to jedna z najbardziej rozbudowanych metod
	 * w grze. Tworze tutaj tablice 2 wymiarowa, pierwszy to wysokosc ekranu(liczony wg elementow swiata, mozna powiedziec ze to 
	 * wiersze badz linie ekranu), drugi to dlugosc wiersza, jego wielkosc okresla dlugosc pierwszego elementu czyli pierwszego wiersza podanego we wzorze. 
	 * Kazdy element tej tablicy wzor to jakis znak. Funkcja odpowiednim forem sprawdza kazdy ten element czy nie jest on zdefiniowany jako znak(char) ktory 
	 * reprezentuje jakis obiekt typu element_poziomu, Pod lub wrog. Gdyjest to element poziomu tworzy nowa instancje tego obiektu w tablicy kolizji pod nr elementu
	 * takim jaki byl wiersz i kolumna znaku reprezentujacego go. wspolrzedne x i y takiego elementu to takze wspolrzedne wiersza i kolumny tylko tutaj pomnozone przez
	 * 20, gdyz wielkosc takiego elementu to 20. Gdy jest to znak reprezentujacy Poda sprawdzam jaki to Pod i odpowiednio dorzucam go do listy Podow, gdy jest to wrog odpowiednio do 
	 * listyWrogow. Wspolrzedne podobnie jak elementySwiata. Na koniec gdy sprawdzanie sie skonczy i listy i tablica kolizji sa juz ustalone jeszcze raz sprawdzam ta tablice, 
	 * (aby w trakcie rysowania nie robic tego za kazdym razem), natrafiajac na niepusty element kopiuje go do nowej tablicy elementyPoziomu ktora bedzie zawierala tylko i wylacznie elementy swiata.
	 * 
	 */
	public void inicjujPoziom(String [] wzor)
	{
	//zainicjowanie tablicy kolizji, tab. 2 wymiary 1szy to ilosc linii druga ktory element w linii
		//a ilosc elementow w linni zostanie podane w tablicy wzor->f length()pobiera dlugosc tej tablicy
		tablicaKolizji=new Element_swiata[S_swiata.iloscLiniiEkranu][wzor[0].length()];
	//dane poziomu, dlugosc poziomu w pikselach to ilosc elementow w linni*wielkosc poj. elementu
		dlugoscPoziomu=wzor[0].length()*S_swiata.wielkoscBloku;
		trafioneMg=trafioneRg=trafioneRl =lewaGranicaPoziomu=0;
		prawaGranicaPoziomu=S_swiata.szerokosc;
	int licznikElementow=0;
	licznikWrogow=0;//bedzie zliczal ile jest przeciwnikow na mapce, aby wiedziec kiedy ukonczylo sie poziom
	listaKolizjiPodow=new ArrayList<Pod>(); //zainicjowanie listy do przechowywania Podow (pancerzy, apteczek)
	listaWrogow=new ArrayList<Komp>();//lista ktora bedzie przechowywala obiekty Komp czyli wrogow;]
	//spr. kazdy element taplicy podanej we wzorze
	for (int i=0;i<wzor.length;i++)
	{
		//definiuje tablice typu char, ktora przechowa cala linijke podana
		//jako wzor, zamiana tablicy String na char funkcja toCharArrey
		char [] liniaWzoru = wzor[i].toCharArray();
		
		//wypelniam tablice kolizji odpowiednimi elementami w zaleznosci od wzoru
		//- to nic,b-blok
		for(int j=0;j<liniaWzoru.length;j++)
		{
			 if (liniaWzoru[j]=='A')// worg
			{
				listaWrogow.add(new Komp(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku,true,false));
				licznikWrogow++;
			}
			 else if(liniaWzoru[j]=='Z')
				{
				 listaWrogow.add(new Komp(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku,false,true));
				 licznikWrogow++;//wrog
				}
			//wypelnienie tablicy nullem gdy ma byc pusta
			 else if(liniaWzoru[j]=='-')
			{
				tablicaKolizji[i][j]=null;
			}
			 else if (liniaWzoru[j]=='4')
				{
					AI_skok as=new AI_skok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,S_swiata.skok_id,4);
					
					tablicaKolizji[i][j]=as;
					licznikElementow++;
				}
			 else if (liniaWzoru[j]=='+')
				{
				 //do skoku przekazuje wartosc 0 tzn ze nie bedzie to miejsce do skoku 
				 //ale informacja o koncu rampy
					AI_skok as=new AI_skok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,S_swiata.kraniec_id,0);
					
					tablicaKolizji[i][j]=as;
					licznikElementow++;
				}
			//gdy w lini znajdzie b wypelnij tablice kolizji blokiem
			else if (liniaWzoru[j]=='a')
			{//inicjuje blok Blok(int x,int y,String i,Component r,int id)
			//wzp x=j*wielkoscbloku,y=i*wielkoscbloku
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/lewybok.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='d')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,//koryguje y o -1 aby sie nachodzily bloki o 1 piksel na siebie, wtedy
						//miesci sie Poziom na ekranie
						"/tex/prawybok.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='s')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='w')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/gora.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='q')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek_metal.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='e')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek_metal_dziura.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='z')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/lewy_dol_1.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			 
			else if (liniaWzoru[j]=='x')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek_dol_1.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='c')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/prawy_dol_1.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='v')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/lewy_dol_2.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='b')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek_dol_2.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='n')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/prawy_dol_2.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			else if (liniaWzoru[j]=='r')
			{
				Blok blok=new Blok(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1,
						"/tex/srodek_metal_2.gif",r,S_swiata.blok_id);
				//umieszczam nowy blok w odpowiednim elemencie tablicy
				tablicaKolizji[i][j]=blok;
				licznikElementow++;
			}
			//tworzenie obiektow typu Pod i wrzucanie ich do listy
			else if (liniaWzoru[j]=='R')//czerwony pancerz ra
			{
				listaKolizjiPodow.add(new Red(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));

			}
			else if (liniaWzoru[j]=='Y')// zolty ya
			{
				listaKolizjiPodow.add(new Zolty(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));

			}
			else if (liniaWzoru[j]=='S')// zolty ya
			{
				listaKolizjiPodow.add(new H_mala(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));

			}
			else if (liniaWzoru[j]=='Œ')// zolty ya
			{
				listaKolizjiPodow.add(new H_srednia(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));

			}
			else if (liniaWzoru[j]=='D')// zolty ya
			{
				listaKolizjiPodow.add(new H_duza(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='M')// zolty ya
			{
				listaKolizjiPodow.add(new Megas(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='Q')// ammo do RL
			{
				listaKolizjiPodow.add(new A_rl(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='W')// ammo do MG
			{
				listaKolizjiPodow.add(new A_mg(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='E')// ammo do rg
			{
				listaKolizjiPodow.add(new A_rg(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='P')// bron r.launcher
			{
				listaKolizjiPodow.add(new B_rl(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='O')// bron railgun
			{
				listaKolizjiPodow.add(new B_rg(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku-1));
			}
			else if (liniaWzoru[j]=='0')// miejsce startu gracza
			{
				setStartXY(j*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku);
			}
		}	
	}
	//inicjuje tablice elentowpoziomu, 1wymiarowa,wypelniona tylko elementami
	elementyPoziomu=new Element_swiata [licznikElementow];
	int l=0;//licznik
//skopiowanie elementow z tablicy kolizji do tablicy elementypoziomu
	for (int i=0;i<tablicaKolizji.length;i++)
	{
		for (int j=0;j<tablicaKolizji[i].length;j++)
		{//gdy w elemencie tablicy cos jest kopiuj to do tablicyelementowswiata
			if(tablicaKolizji[i][j]!=null)
			{
				elementyPoziomu[l]=tablicaKolizji[i][j];
				l++;
			}
		}
	}
	}//koniecInicjujPoziom
	public int getlicznikWrogow()
	{
		return licznikWrogow;
	}
	public ArrayList<Pod> getKolizjiPodow()
	{
		return listaKolizjiPodow;
	}
	public ArrayList<Komp> getListaWrogow()
	{
		return listaWrogow;
	}
	public void setListaWrogow(ArrayList<Komp> lista)
	{
		listaWrogow=lista;
	}
	
	/**
	 * @param gracz aby moc pobrac od gracz liste pociskow
	 * Funkcja pobiera od gracza liste wystrzelonych pociskow(Rl,Mg,Rg)odpowiednimi metodami. Nastepnie tworzy obiekty
	 * typu Rectangle ktorych wspolrzedne sa wspolrzednymi x i y elementow_swiata pobranymi z tablicy elementyPoziomu i sprawdza 
	 * za pomoca contains() lub intersects() czy te wspolrzedne sie zawieraja co oznacza ze pocisk w cos uderzyl i jest wywolywana funkcja Uderzenie dla 
	 * odpowiedniego pocisku. Dodatkowo sprawdzane jest
	 * czy ten obiekt nie ma wartosci id odowiedniej dla kranca to wtedy pocisk nie uderza, kraniec jest widzialny tylko dla wroga.
	 * Funkcja takze w podobny sposob sprawdza czy pocisk uderzyl w przeciwnika, gdy tak sie stanie nastepuje odpwiednia reakcja w postaci
	 * ustalenia obrazen i ustawienia licznikaKillsow, trafien itp  
	 */
	@SuppressWarnings("deprecation")
	public void testKolizjiBroni(Gracz gracz)
	{
		//w zaleznosci od broni gracza, zakladam ze uda sie zrobic 2 rodzaje
		//a gracz moze miec zmieniona juz bron, a pociski stare leca, wiec nie ma co sprawdzac co ma dloni
		//tylko pobrac wszystkie pociski
		ArrayList rakiety = gracz.getListaRakiet();
		 for(int i=0;i<rakiety.size();i++)
		 {//tworze obiakt typu rakieta i wyciagam element z listy
			 //przeciazajac go na obiekt rakiete i teraz moge pobrac jego wspolrzedne
			Rakieta r=(Rakieta)rakiety.get(i) ;
		 
			for(int j=0;j<elementyPoziomu.length;j++)
			{
		 //najlepiej by bylo spr kazdy element swiata czy w nim znajduja sie wsp pocisku
					
						//kwadrat o wsp
						//Rectangle kwadrat=new Rectangle(l*S_swiata.wielkoscBloku,i*S_swiata.wielkoscBloku);
				//super funkcja rectangle.contains - spr. czy wspolrzedne podane zawieraja sie wewnatrz rectangle
						Rectangle kwadrat=new Rectangle(elementyPoziomu[j].getX(),elementyPoziomu[j].getY(),20,20);
						//spr czy w nim znajduje sie wsp rakiety
						if(kwadrat.contains(r.getPrzodPocisku(), r.getY())==true && elementyPoziomu[j].getId()!=S_swiata.kraniec_id)//przod pocisku to jego x w zaleznosci w ktora strone leci
						
						{
							r.setWidoczny(false);//ustawiam jego widocznosc na zadna;]
							//rakiety.remove(i);-->nie musze usuwac bo ona sie i tak usunie w funkcji pobierziruszrakiety,gdzie spr czy widoczna
							//teraz przydalby sie wybuch
							//koncepcja jest taka ze uderzenie informuje obsluge ze nastapilo uderzenie w cos i tzreba pobrac xy aby narysowac wybuch
							r.setUderzenie(true);
						}
			}
			for (int j=0;j<listaWrogow.size();j++)//sprawdzam cala liste wrogow
			{
				 Komp wrog=(Komp)listaWrogow.get(j);
				 if(!wrog.czyZyje())//tylko jesli zyje aby moc chodzic po zwlokach
				 {
				 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
				 if(kwadrWroga.contains(r.getPrzodPocisku(),r.getY())==true)
				 {
					 r.setWidoczny(false);
					 r.setUderzenie(true);
					 
					gracz.setKills( wrog.zadajObrazenia(S_swiata.obrazeniaRakiety),S_swiata.rl_id);
				 }
				 
				 }
				 listaWrogow.set(j, wrog);
			}
			
			rakiety.set(i,r);
		 }
		 gracz.setListaRakiet(rakiety);
		 
		 ArrayList mgLista = gracz.getListaMg();
		 for(int i=0;i<mgLista.size();i++)
		 {//tworze obiakt typu mg i wyciagam element z listy
			 //przeciazajac go na obiekt MG i teraz moge pobrac jego wspolrzedne
			MG mg=(MG)mgLista.get(i) ;
		 
			for(int j=0;j<elementyPoziomu.length;j++)
			{
						Rectangle kwadrat=new Rectangle(elementyPoziomu[j].getX(),elementyPoziomu[j].getY(),20,20);
						if(kwadrat.contains(mg.getPrzodPocisku(), mg.getY())==true && elementyPoziomu[j].getId()!=S_swiata.kraniec_id)
						{
							mg.setWidoczny(false);
							mg.setUderzenie(true);
						}
						
			}
			for (int j=0;j<listaWrogow.size();j++)//sprawdzam cala liste wrogow
			{
				 Komp wrog=(Komp)listaWrogow.get(j);
				 if(!wrog.czyZyje())//tylko jesli zyje aby moc chodzic po zwlokach
				 {
				 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
				 if(kwadrWroga.contains(mg.getPrzodPocisku(),mg.getY())==true)
				 {
					 mg.setWidoczny(false);
					 mg.setUderzenie(true);
					 gracz.setKills(wrog.zadajObrazenia(S_swiata.obrazeniaMg),S_swiata.mg_id);
				 }
				 
				 }
				 listaWrogow.set(j, wrog);
			}
			mgLista.set(i,mg);//zmieniona wartosc mg wrzucam spowrotem do listy
			
		 }
		 gracz.setListaMg(mgLista);//oddaje liste graczowi
		 
		 //wylicznie drugich wspolrzednych dla RG, znam tylko wystrzalu x i y
		 
		 ArrayList rgLista = gracz.getListaRaila();
		 
		 for(int i=0;i<rgLista.size();i++)
		 {//tworze obiakt typu mg i wyciagam element z listy
			 //przeciazajac go na obiekt MG i teraz moge pobrac jego wspolrzedne
			Rail rg=(Rail)rgLista.get(i) ;
			//mam dany rail teraz jego wspolrzedne
			boolean znalezionyX=false;
			Rectangle prostRaila=new Rectangle(1,1,1,1);//tworze jakis prost raila, potem przypisze odpowiednia wielkosc mu w zaleznosci od strzalu,chodzi o to aby pon byl zmienn
			//globalna dla tej funkcji
			if(rg.getKierunek()==S_swiata.prawo && rg.czyOtrzymalXY2()==false )
			{
				for(int j=0;j<elementyPoziomu.length;j++)
				{
							Rectangle kwadrat=new Rectangle(elementyPoziomu[j].getX(),elementyPoziomu[j].getY(),20,20);
			//if zaweza liczbe poszukiwanych elementow poziomu do elementow ktorych wspolrzedne x sa na prawo od szukanej odleglosci
			//gdzie w skrajnych przypadkach to polowa elemento a Y sa zawezane do szukania w jednej badz dwoch liniach elementow
							//aby nastepna petla for  nie spowalniala programu
if(elementyPoziomu[j].getX()*20>rg.getX() && (elementyPoziomu[j].getY()*20>rg.getY()))
							for (int odlX=0;odlX<320;odlX+=3)
							{
							if(kwadrat.contains(rg.getX()+odlX, rg.getY())==true && elementyPoziomu[j].getId()!=S_swiata.kraniec_id)
							{
								rg.setXY2(rg.getX()+odlX,rg.getY());
								znalezionyX=true;
								//break;
							}
							//
						/*	for (int k=0;k<listaWrogow.size();k++)//sprawdzam cala liste wrogow
							{
								 Komp wrog=(Komp)listaWrogow.get(k);
								 if(!wrog.czyNieZyje())//tylko jesli zyje aby moc chodzic po zwlokach
								 {
								 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
								 if(kwadrWroga.contains(rg.getX()+odlX,rg.getY())==true)
								 {
									
									 gracz.setKills(wrog.zadajObrazenia(S_swiata.obrazeniaRg));
									
									 rg.setXY2(rg.getX()+odlX,rg.getY());
									 znalezionyX=true;
								 }
								 
								 }
								
								 listaWrogow.set(k, wrog);
							}*///przetestuje funkcje ktora rysuje raila na calej dlugosci i razi wszystko po drodze
							
							
							if (znalezionyX)break;
							}
							//if (znalezionyX)continue;
				if (znalezionyX==false)rg.setXY2(dlugoscPoziomu, rg.getY());
				prostRaila.setBounds(rg.getX(), rg.getY(),rg.getX2()-rg.getX(), 2);
				}
				
			}else // jezeli strzal padl  w lewa strone
			if(rg.getKierunek()==S_swiata.lewo && rg.czyOtrzymalXY2()==false)
			{	
				int punkt_x=0;// szukany najmniejszy punkt x
				for(int j=elementyPoziomu.length-1;j>0;j--)
				{
							Rectangle kwadrat=new Rectangle(elementyPoziomu[j].getX(),elementyPoziomu[j].getY(),20,20);
			//szukam tylko w sasiedujacych elementach o rozniacych sie wysokoscia o 1
if (elementyPoziomu[j-1].getY()*20>rg.getY() || elementyPoziomu[j+1].getY()*20<rg.getY()) 
{
							for (int odlX=0;odlX<320;odlX+=3)
							{
							if(kwadrat.contains(rg.getX()-odlX, rg.getY())==true && elementyPoziomu[j].getId()!=S_swiata.kraniec_id)
							{

								if(punkt_x<rg.getX()-odlX)
								punkt_x=rg.getX()-odlX;//jezeli element zawiera ten punkt to zapamietaj jego wspolrzenda x
								break;//opusc fora bo nie ma sensu iterowac dalej
							}
						/*	for (int k=0;k<listaWrogow.size();k++)//sprawdzam cala liste wrogow
							{
								 Komp wrog=(Komp)listaWrogow.get(k);
								 if(!wrog.czyNieZyje())//tylko jesli zyje aby moc chodzic po zwlokach
								 {
								 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
								 if(kwadrWroga.contains(rg.getX()-odlX,rg.getY())==true)
								 {
									
									 gracz.setKills(wrog.zadajObrazenia(S_swiata.obrazeniaRg));
									
									 if(punkt_x<rg.getX()-odlX)
									 punkt_x=rg.getX()-odlX;
									 break;
									
								 }
								 }
								
								 listaWrogow.set(k, wrog);
							}*/	
							}
				}
				}
				rg.setXY2(punkt_x,rg.getY());
				if (punkt_x==0)rg.setXY2(rg.getX()-dlugoscPoziomu,rg.getY());//jezeli nie znalaz zadnego pasujacego elementu to 0 przypisz -40
				prostRaila.setBounds(rg.getX2(), rg.getY2(),rg.getX()-rg.getX2(), 2);
			}
			
			
			//Rectangle prostRaila=new Rectangle(rg.getX(),rg.getY(),200,2);
			for (int k=0;k<listaWrogow.size();k++)//sprawdzam cala liste wrogow
			{
				 Komp wrog=(Komp)listaWrogow.get(k);
				 if(!wrog.czyZyje())//tylko jesli zyje aby moc chodzic po zwlokach
				 {
				 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
				 
				 if(kwadrWroga.intersects(prostRaila)==true && wrog.getWidoczny())//jezeli jest widoczny na ekranie,aby nie razil kogogs za sciana np
				 {
					
					 gracz.setKills(wrog.zadajObrazenia(S_swiata.obrazeniaRg),S_swiata.rg_id);

				 }
				 
				 }
				
				 listaWrogow.set(k, wrog);
			}
			rgLista.set(i,rg);
		 }
		
		 
		 gracz.setListaRaila(rgLista);//oddaje spowrotem liste graczowi
	}

//funkcja spr. kolizje gracza z blokami a TAKZE kolizje dla wszystkich wrogow(obiektow Komp)
	public void testKolizjiGracza(Gracz gracz)
	{
		//obsluga podow,ta funkcja jest z kazdym razem wywaolywana, takze przy okazji bedzie uruchamiala podMove
		ruszListePodow();
		
		//zdobycie wspolrzednych gracza
		//srodek postaci
		int pGraczaWGrzeX = gracz.getPozycjaXGry();
		int pGraczaGora=gracz.getY();
		int pGraczaDol=gracz.getY()+(S_swiata.wysokoscObrazkaGracza)-6;//skorygowanie aby postac bardziej stala na blokach,nie w powietrzu
		//tzreba skorygowac troszke jego x aby  nie stawal zbyt daleko od scian,korekta o wielkosc obrazka
		int pGraczaLewo=pGraczaWGrzeX-(S_swiata.szerokoscObrazkaGracza/2)+15;
		int pGraczaPrawo=pGraczaWGrzeX+(S_swiata.szerokoscObrazkaGracza/2)+10;
		Rectangle prostGracza=new Rectangle(gracz.getX()+15,gracz.getY()+10,10,40);
		
		//takze sprawdzenie kolizji gracza z wrogami
		for (int i=0;i<listaWrogow.size();i++)//sprawdzam cala liste wrogow
		{
			 Komp wrog=(Komp)listaWrogow.get(i);
			 if(!wrog.czyZyje())//tylko jesli zyje aby moc chodzic po zwlokach
			 {
			 Rectangle kwadrWroga=new Rectangle(wrog.getX()+3,wrog.getY()+5,30,60);
			 if(kwadrWroga.intersects(prostGracza)==true )
			 {
				 gracz.setIdz(false);
				 //wrog.stoj();
				 if(wrog.czyPatrzyWLewo())//jezeli patzry w lewo
					 //i jest po lewej stronie gracza
				 {
					 if(kwadrWroga.getX()<prostGracza.getX())
					 {
					 gracz.setX(gracz.getX()+S_swiata.predkoscX);
					 gracz.setPozycjaXGry(gracz.getPozycjaXGry()+S_swiata.predkoscX);	
					 }
					 else if(kwadrWroga.getX()>prostGracza.getX())
					 {
						 gracz.setX(gracz.getX()-S_swiata.predkoscX);
						 gracz.setPozycjaXGry(gracz.getPozycjaXGry()-S_swiata.predkoscX); 
					 }
				 }
				 
				 if(wrog.czyPatrzyWPrawo())
				 {
					 if(kwadrWroga.getX()<prostGracza.getX())
					 {
					 gracz.setX(gracz.getX()+S_swiata.predkoscX);
					 gracz.setPozycjaXGry(gracz.getPozycjaXGry()+S_swiata.predkoscX);	
					 }
					 else if(kwadrWroga.getX()>prostGracza.getX())
					 {
						 gracz.setX(gracz.getX()-S_swiata.predkoscX);
						 gracz.setPozycjaXGry(gracz.getPozycjaXGry()-S_swiata.predkoscX); 
					 }
				 }
				 if (!wrog.czyZyje())gracz.setHp(-25);//jezeli dotknie wroga to otrzymuje automatycznie duze obrazenia
			 }
			 }
		}
		
		
		//spr do³u
		Element_swiata dolnyElement=testKolizjiDol(pGraczaWGrzeX,pGraczaDol);
		//jezeli taki element istnieje to gracz musi spasc
		if (dolnyElement!=null && dolnyElement.id==S_swiata.blok_id)//sprawdzenie czy to blok
		{
			gracz.setSpada(false);
			gracz.Spadl();
		}
		else
		{
			if(gracz.czySkacze()!=1)//jezeli wykonuje skok to nie zmuszaj go do spadania
			gracz.setSpada(true);
		}
		//korzystaj¹c z testukolizji dol ze pobiera tylko x i y a nie obiekty udalo sie przerobic tak aby sprawdzal
		//takze wroga
		for (int i=0;i<listaWrogow.size();i++)//sprawdzam cala liste wrogow
		{
			 Komp wrog=(Komp)listaWrogow.get(i);

			 Element_swiata dElement=testKolizjiDol(wrog.getPozycjaXGry(),wrog.getY()+(S_swiata.wysokoscObrazkaGracza-6));
			 if (dElement!=null && dElement.id==S_swiata.blok_id)
				{
					wrog.setSpada(false);
				}
				else
				{
					wrog.setSpada(true);		
				}
			 
			 listaWrogow.set(i, wrog);
		}
		
		
		//spr. granicy od gory, wazne przy skoku
		Element_swiata gornyElement=testKolizjiGora(pGraczaWGrzeX,pGraczaGora);
		//jezeli taki element istnieje to gracz nie moze podskakiwac
		if (gornyElement!=null && gornyElement.id==S_swiata.blok_id)
		{
			gracz.setSkacze(false);
			gracz.setSpada(true);
		}
	
		
		//kolizja z lewej strony
		Element_swiata lewyElement=testKolizjiLewo(pGraczaLewo,pGraczaDol);
		//jezeli taki element istnieje to gracz nie moze isc w lewo
		//dlatego jezeli patzry w lewo i jest obok elementu to nie moze isc
		if (lewyElement!=null && gracz.patrzyWLewo && lewyElement.id==S_swiata.blok_id)
		{
			gracz.setIdz(false);
		}
		//sprawdzenie kolizji wrogow z lewej strony
		for (int i=0;i<listaWrogow.size();i++)//sprawdzam cala liste wrogow
		{
			 Komp wrog=(Komp)listaWrogow.get(i);
			 //wrog.getPozycjaXGry()-(S_swiata.szerokoscObrazkaGracza/2)+15-> pozycja x lewej strony modelu wroga
			 Element_swiata lElement=testKolizjiLewo(wrog.getPozycjaXGry()-(S_swiata.szerokoscObrazkaGracza/2)+15,wrog.getY()+(S_swiata.wysokoscObrazkaGracza-6));
			 if (lElement!=null && wrog.getPatrzyWLewo() && lElement.id==S_swiata.blok_id)
				{
				 wrog.setPrzeszkodaZLewej(true);	
				}else if(lElement==null )wrog.setPrzeszkodaZLewej(false);
			 if (lElement!=null && wrog.getPatrzyWLewo() && lElement.id==S_swiata.kraniec_id)
				{
				 wrog.setkoniecRampyZLewej(true);	
				}else if(lElement==null )wrog.setkoniecRampyZLewej(false);
			 
			/* if (lElement!=null && wrog.getPatrzyWPrawo()  && lElement.id==S_swiata.skok_id)//jezeli element swiata est miejscem z ktorego mozna skoczycw gore
			 {
				wrog.mozeSkoczyc(((AI_skok) lElement).getWysokosc());
				 
			 }*/
			 
			 listaWrogow.set(i, wrog);
		}
		
		//kolizja z prawej
		Element_swiata prawyElement=testKolizjiPrawo(pGraczaPrawo,pGraczaDol);
		//jezeli taki element istnieje to gracz nie moze isc w prawo
		if (prawyElement!=null && gracz.patrzyWPrawo && prawyElement.id==S_swiata.blok_id)
		{
			gracz.setIdz(false);
		}
		//if (lewyElement!=null && gracz.patrzyWLewo)
		//{
			//gracz.setIdz(false);
		//}
		//sprawdzenie kolizji wrogow z prawej strony
		for (int i=0;i<listaWrogow.size();i++)//sprawdzam cala liste wrogow
		{
			 Komp wrog=(Komp)listaWrogow.get(i);
			 
			 Element_swiata pElement=testKolizjiPrawo(wrog.getPozycjaXGry()+(S_swiata.szerokoscObrazkaGracza/2)+10,wrog.getY()+(S_swiata.wysokoscObrazkaGracza-6));
			 if (pElement!=null && wrog.getPatrzyWPrawo()&& pElement.id==S_swiata.blok_id)
				{
				 wrog.setPrzeszkodaZPrawej(true);
				}else if(pElement==null )wrog.setPrzeszkodaZPrawej(false);
			
			 if (pElement!=null && wrog.getPatrzyWPrawo()&& pElement.id==S_swiata.kraniec_id)
				{
				 wrog.setkoniecRampyZPrawej(true);
				}else if(pElement==null )wrog.setkoniecRampyZPrawej(false);
			/* if (pElement!=null && wrog.getPatrzyWLewo() && pElement.id==S_swiata.skok_id)//jezeli element swiata est miejscem z ktorego mozna skoczycw gore
			 {
				wrog.mozeSkoczyc(((AI_skok) pElement).getWysokosc()) ;
				 
			 }*/
			 
			 listaWrogow.set(i, wrog);
		}
		
		//sprawdzenie czy gracz jest w zasiegu Poda
		testKolizjiPodow(gracz);
		
	}

	/**
	 * @param gracz aby moc pobrac wspolrzedne gracza, a pozniej gdy test interakcji sie powiedzie moc graczowi przypisac odpowienie wartosci zmiennych.
	 * Funckja sprawdza liste listaKOlizjiPodow czy prostakat utworzony na podstawie wspolrzednych konkretnego Poda zawiera sie w prostokacie gracza utworzonym
	 * przed wyszukiwaniem. Jezeli tak oznacza to ze gracz znalazl sie w polu dzialania Poda i jezeli zachodza odpowiednie warunki Pod zwraca wartosc graczowi
	 * i jest on podnoszony. Pancerze sa podnoszone zawsze (aby przeciwnik nie mgl go podniesc) nawet w przypadku gdy gracz ma go max ilosc,tzn 200. Pancerz wykona 
	 * metode podnies, ale graczowi nie przypisze sie wartosci ponad max. Natomiast nie zawsze podnosi apteczke o wart. 25 i 50 (srednia duza), robi to tylko gdy ma mniej
	 * niz 100 zycia (aby zostawic jakas forme healingu przeciwnikowi). Bron takze podnosi zawsze, pamietajac ze gdy posiada pelny magazynek i tak nie zwiekszy mu sie
	 * ilosc amunicji.
	 */
	private void testKolizjiPodow(Gracz gracz)
	{
		//robie prostokat troche mniejszy niz prostokat jakim jest figura gracza
		//aby potem funkcja intersects sprawdzic czy jest on czasia prostokatu Poda
		Rectangle prostGracza=new Rectangle(gracz.getX()+15,gracz.getY()+10,10,40);
		
		//dla wszystkich podow
			for (int i=0;i<listaKolizjiPodow.size();i++)
			{
			//pobierz pody po kolei i przypisz do nowego obiektu tymczasowego aby wyciagnac z niego wspolrzedne
				Pod pod=(Pod)listaKolizjiPodow.get(i);
			//utworz kwdrat odpowiadajacy wspo i wielkosci Poda	
				Rectangle kwadrPoda=new Rectangle(pod.getX()+3,pod.getY()+3,30,30);
				
				//jezli gracz znalazl sie wewnatrz Poda

				if(kwadrPoda.intersects(prostGracza)==true)
				{
					switch(pod.getId())
					{	//podnies poda w zaleznosci od tego czym jest
					case S_swiata.red_id: gracz.setPancerz(pod.podnies());break; //0 to czerwony pancerz wiec przypisz go pancerzowi gracza
					case S_swiata.zolty_id: gracz.setPancerz(pod.podnies());break;//1 to zolty pancerz
					case S_swiata.h_mala_id: gracz.setHp(pod.podnies());break;//5 helath moze podnosic zawsze
					case S_swiata.h_srednia_id:{
						//jesli gracz ma >=100 energii to nie powienien tego podnosic
						if(gracz.getHp()<100)gracz.setHp(pod.podnies()); break;}
			
					case S_swiata.h_duza_id:{
					//jesli gracz ma >=100 energii to nie powienien tego podnosic
					if(gracz.getHp()<100)gracz.setHp(pod.podnies()); break;
					}
					case S_swiata.h_megas_id: gracz.setHp(pod.podnies());break;//megasa oczywiscie zawsze
					case S_swiata.a_rl_id: gracz.setAmunicje(S_swiata.rl_id, pod.podnies());break;
					
					case S_swiata.a_mg_id:
							gracz.setAmunicje(S_swiata.mg_id, pod.podnies());
						break;
					
					
					case S_swiata.a_rg_id: gracz.setAmunicje(S_swiata.rg_id, pod.podnies());break;
					
					case S_swiata.b_rl_id:{
						gracz.setBron(S_swiata.rl_id,pod.podnies());
	
						break;
						}
					case S_swiata.b_rg_id:{
						gracz.setBron(S_swiata.rg_id,pod.podnies());
	
						break;
						}
				}//nawias konca casea
				}
				 //przypisz spowrotem wartosc poda do listy
				 listaKolizjiPodow.set(i, pod);
			}
	}
	
	//spr kolizji od dolu
	/**
	 * @param pGry wpolrzedna x gracz wzgeledem gry.
	 * @param pGraczaD wspolrzedna y dolu gracz (jego nog).
	 * Na podstawie parametrow obliczam (dzielac przez wielkosc elementuSwiata (czyli przez 20)) wpolrzedne
	 * ekranowe (wg wiersza i kolumny elementowSwiata). Pamietam ze inicjowalem ich wpolrzedne x i y w odwrotny sposob. Czyli teraz chcac znalesc taki element moge
	 * go po prostu odczytac z tablicy. Jezeli on tam jest, tzn gracz ma cos pod nogami (bo te wspolrzedne obliczone beda takie same jak wspolrzedne
	 * elementu swiata)to wyczytam je z tablicy i cos zwroce, gdy nie powinien wyskoczyc blad IndexArrayBound..., dlatego robie to w bloku try().
	 */
	private Element_swiata testKolizjiDol(int pGry,int pGraczaD)
	{
		//zamiana pozycji gracza w pikselach na pozycje z tablicyKolizji
		//czyli dziele pozycje przez wielkosc elementu
		int kolumna=pGry/S_swiata.wielkoscBloku;
		int wiersz=pGraczaD/S_swiata.wielkoscBloku;
		
		try
		{
			//zwraca elementswiata ktory jest pod graczem
			if (tablicaKolizji[wiersz][kolumna]!=null )
			{
				return tablicaKolizji[wiersz][kolumna];
			}
			else
			{
				return null;
			}
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	//spr kolizji od gory
	/**
	 * @param pGry wpolrzedna x gracz wzgeledem gry.
	 * @param pGraczaG wspolrzedna y gory gracza (mozna powiedziec ze czubek jego glowy;]).
	 * Na podstawie parametrow obliczam (dzielac przez wielkosc elementuSwiata (czyli przez 20)) wpolrzedne
	 * ekranowe(wg wiersza i kolumny elementowSwiata). Pamietam ze inicjowalem ich wpolrzedne x i y w odwrotny sposob . Czyli teraz chcac znalesc taki element moge
	 * go po prostu odczytac z tablicy. Jezeli on tam jest, tzn gracz ma cos nad glowa (bo te wspolrzedne obliczone beda takie same jak wspolrzedne
	 * elementu swiata)to wyczytam je z tablicy i cos zwroce, gdy nie powinien wyskoczyc blad IndexArrayBound..., dlatego robie to w bloku try().
	 */
	private Element_swiata testKolizjiGora(int pGry,int pGraczaG)
	{
		//zamiana pozycji gracza w pikselach na pozycje z tablicyKolizji
		//czyli dziele pozycje przez wielkosc elementu
		int kolumna=pGry/S_swiata.wielkoscBloku;
		int wiersz=pGraczaG/S_swiata.wielkoscBloku;
		
		try
		{
			//zwraca elementswiata ktory jest pod graczem
			if (tablicaKolizji[wiersz][kolumna]!=null)
			{
				return tablicaKolizji[wiersz][kolumna];
			}
			else
			{
				return null;
			}
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	//spr kolizji od lewej strony
	/**
	 * @param pGry wpolrzedna x gracz wzgeledem gry.
	 * @param pGraczaD wspolrzedna y dolu gracz (jego nog).
	 * Na podstawie parametrow obliczam (dzielac przez wielkosc elementuSwiata (czyli przez 20)) wpolrzedne
	 * ekranowe(wg wiersza i kolumny elementowSwiata). Pamietam ze inicjowalem ich wpolrzedne x i y w odwrotny sposob . Czyli teraz chcac znalesc taki element moge
	 * go po prostu odczytac z tablicy. Jezeli on tam jest, tzn gracz ma cos po lewej stronie (bo te wspolrzedne obliczone beda takie same jak wspolrzedne
	 * elementu swiata)to wyczytam je z tablicy i cos zwroce, gdy nie powinien wyskoczyc blad IndexArrayBound..., dlatego robie to w bloku try().
	 */
	private Element_swiata testKolizjiLewo(int pGry,int pGraczaD)
	{
		//zamiana pozycji gracza w pikselach na pozycje z tablicyKolizji
		//czyli dziele pozycje przez wielkosc elementu
		int kolumna=pGry/S_swiata.wielkoscBloku;
		int wiersz1=(pGraczaD/S_swiata.wielkoscBloku)-1;
		//po to aby nie mogl przejsc przez sciane zawieszona w powietrzu o jego wysokosci
		int wiersz2=wiersz1-1;
		int wiersz3=wiersz2-1;
		
		try
		{
			//zwraca elementswiata ktory jest obok gracza jezeli taki bedzie sie zgadzal
			if (tablicaKolizji[wiersz1][kolumna]!=null)
			{
				return tablicaKolizji[wiersz1][kolumna];
			}
			else if (tablicaKolizji[wiersz2][kolumna]!=null)
			{
				return tablicaKolizji[wiersz2][kolumna];
			}
			else if (tablicaKolizji[wiersz3][kolumna]!=null)
			{
				return tablicaKolizji[wiersz3][kolumna];
			}
			else
			{
				return null;
			}
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	//spr kolizji od prawej strony
	/**
	 * @param pGry wpolrzedna x gracz wzgeledem gry.
	 * @param pGraczaD wspolrzedna y dolu gracz (jego nog).
	 * Na podstawie parametrow obliczam (dzielac przez wielkosc elementuSwiata (czyli przez 20)) wpolrzedne
	 * ekranowe(wg wiersza i kolumny elementowSwiata). Pamietam ze inicjowalem ich wpolrzedne x i y w odwrotny sposob . Czyli teraz chcac znalesc taki element moge
	 * go po prostu odczytac z tablicy. Jezeli on tam jest, tzn gracz ma cos po prawej stronie (bo te wspolrzedne obliczone beda takie same jak wspolrzedne
	 * elementu swiata)to wyczytam je z tablicy i cos zwroce, gdy nie powinien wyskoczyc blad IndexArrayBound..., dlatego robie to w bloku try().
	 */
	private Element_swiata testKolizjiPrawo(int pGry,int pGraczaD)
	{
		//zamiana pozycji gracza w pikselach na pozycje z tablicyKolizji
		//czyli dziele pozycje przez wielkosc elementu
		int kolumna=pGry/S_swiata.wielkoscBloku;
		int wiersz1=(pGraczaD/S_swiata.wielkoscBloku)-1;
		int wiersz2=wiersz1-1;
		int wiersz3=wiersz2-1;
		
		try
		{
			//zwraca elementswiata ktory jest obok gracza
			if (tablicaKolizji[wiersz1][kolumna]!=null)
			{
				return tablicaKolizji[wiersz1][kolumna];
			}
			else if (tablicaKolizji[wiersz2][kolumna]!=null)
			{
				return tablicaKolizji[wiersz2][kolumna];
			}
			else if (tablicaKolizji[wiersz3][kolumna]!=null)
			{
				return tablicaKolizji[wiersz3][kolumna];
			}
			else
			{
				return null;
			}
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return null;
		}
	}
//funkcja przesuwa ekran poziomu, przesuwane sa wszystkie elementy
	//w tablicyelementow,
	/**
	 * @param v wartosc o jaka chce przesunac wyswietlane elementy
	 * Gdy przekroczy granice przesuniecia nalezy w odpowiednia strone przesunac wszystkie elementySwiata. Razem z nimi
	 * przesuwam takze te granice.
	 */
	public void przesunEkranPoziomu(int v)
	{//dlugoscPoziomu,lewaGranicaPoziomu,prawaGranicaPoziomu

			lewaGranicaPoziomu-=v;
			prawaGranicaPoziomu-=v;	
		
		//przesun kazdy element poziomu
		for (int i=0;i<elementyPoziomu.length;i++)
		{
			elementyPoziomu[i].przesunElementSwiata(v);
		
		}
		//pody takze przesuwam
		przesunListePodow(v);
		przesunListeWrogow(v);
		
	}
	
	/**Kolejno sprawdzam elemnty listy listaPodow i wykonuje metode move(). Na koniec zwracam poda do listy.*/
	private void ruszListePodow()
	{
		for (int i=0;i<listaKolizjiPodow.size();i++)
		{
		
			Pod pod=(Pod)listaKolizjiPodow.get(i);
			
			 pod.podMove();//uruchamiam mechanizm ktory zwiaksza kroki i animacje
			 listaKolizjiPodow.set(i, pod);
		}
	}
	/**Kolejno sprawdzam elemnty listy listaWrogow i wykonuje metode move(). Na koniec zwracam poda do listy.*/
	public void ruszListeWrogow()
	{
		for (int i=0;i<listaWrogow.size();i++)
		{
			 Komp wrog=(Komp)listaWrogow.get(i);
			 wrog.move();
			 listaWrogow.set(i, wrog);
		}
	}
	/**Kolejno sprawdzam elemnty listy listaWrogow i wykonuje metode przesun(). Na koniec zwracam wroga do listy.*/
	private void przesunListeWrogow(int v)
	{
		for (int i=0;i<listaWrogow.size();i++)
		{
			 Komp wrog=(Komp)listaWrogow.get(i);
			 wrog.przesun(v);
			 listaWrogow.set(i, wrog);
		}
	}
	/**Kolejno sprawdzam elemnty listy listapodow i wykonuje metode przesunPoda(). Na koniec zwracam poda do listy.*/
	private void przesunListePodow(int v)
	{
		for (int i=0;i<listaKolizjiPodow.size();i++)
		{
		
			Pod pod=(Pod)listaKolizjiPodow.get(i);
			
			 pod.przesunPoda(v);//uruchamiam mechanizm ktory zwiaksza kroki i animacje
			 listaKolizjiPodow.set(i, pod);
		}
	}

	/**
	 * @return wartosc mowi nam czy ekran jest przesuniety na maksa w lewo bedz prawo.
	 * Aby w nieskoczonosc nie scrollowac ekranu sprawdzane jest czy granice lewe i prawa sa juz w swoich maksymalnych wartosciach.
	 * Jako ze poziom rysuje sie od lewej jego lewa granica to 0 i bardziej w ta strone nie bedzie przesuwany, bo tam nic nie ma. Prawa granice
	 * okresla dlugosc poziomu, jest ona wyliczana w funkcji inicjuj poziom gdzie mnoze dlugosz pierwszego wiersza wzoru podanego w parametrze przez 20
	 * (jest to szerokosc elementu swiata w pikselach).
	 */
	public boolean czyMoznaPrzesunacWPrawo()
	{
		if(prawaGranicaPoziomu>=dlugoscPoziomu)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean czyMoznaPrzesunacWLewo()
	{
		if(lewaGranicaPoziomu<=0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	//funkcja rysujaca poziom
	/**Zdarzylo mi sie raz ze wyskoczyl blad wiec jako ze czytam z tablicy zrobilem to w bloku try catch.
	 * Sprawdzam wszystkie elementy tablicy elementyPoziomu, list listaKolizjiPodow i listaWrogow i odpowiednio wykonuje dla nich metody rysujace je.
	 * 
	 */
	public void rysujPoziom(Graphics g)
	{
		//aby sie nie wykszaczylo wrazie wyjatkow
		try
		{
		
			//rysuje wszyctkie elementy z tablicy elementow
			for (int i=0;i<elementyPoziomu.length;i++)
			{
				if(elementyPoziomu[i].getId()==1)//jezeli to blok
				elementyPoziomu[i].rysujElementSwiata(g);
			}
			
		}
		catch(Exception ex)
		{
			//nic nie rob
		}
		for (int i=0;i<listaKolizjiPodow.size();i++)
		{
		
			Pod pod=(Pod)listaKolizjiPodow.get(i);
			pod.rysuj(g);
		}
		for (int i=0;i<listaWrogow.size();i++)
		{
			Komp wrog=(Komp)listaWrogow.get(i);
			wrog.rysujKompa(g);
		}
	}

	/**Abstrakcyjna funkcja ktora resetuje poziom, musi ona byc zdefiniowana
	 * w klasie dziedziczacej , skoro jest tu funkcja abstrakcyjna tocala ta klasa jest abstrakcyjna
	 */
	public abstract void resetujPoziom();
	
}
