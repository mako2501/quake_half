import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author Rafa³ MAKOWSKI. Klasa z ktorej beda wyprowadzane klasy okreslajace rzeczy do podniesienia, np:
 * pancerz, mh itp. Pody roznia sie miedzy soba wygladem, czasem ponowanego pojawienia sie na ekranie i rodzajem i iloscia zwracanych wartosci.
 */
public class Pod {

	int x,y,wartosc,czasOdnowy,id,licznikAnimacji,licznikKroku;
	private boolean widoczny, podniesiony;
	private Component com;//aby moc rysowac
	private Image i1,i2,i3,i4,i5,i6,i7,i8;
	private String nazwa;
	private long czasPodniesienia;

	//konstruktor Poda
	/**Aby kazdy obiekt tworzony z klasy macierzystej mogl miec wlasna animacje, w kostruktorze podaje scierzki dostepu do tych obrazow.
	 */
	public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8)
	{
		zaladujAnimacje(s1,s2,s3,s4,s5,s6,s7,s8);
		this.x=x;
		this.y=y;
		this.wartosc=wartosc;
		this.czasOdnowy=czasOdnowy;
		this.nazwa=nazwa;
		ustaw();//ustawiam go na mapce sprawiajac ze ejst widoczny i jeszcze nie podniesiony
		this.id=id;
		licznikAnimacji=1;
		licznikKroku=1;
		//podnies();
	}
	/**Metoda laduje z pliku obrazki i przypisuje je zmiennym. Jako ze calosc animacji dla wszystkich Podow bedzie sie skladac
	 * z 8 klatek to wgrywa 8 obrazkow.
	 */
	private void zaladujAnimacje(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8)
	{
		ImageIcon ii1 = new ImageIcon(this.getClass().getResource(s1));
        i1 = ii1.getImage();
        ImageIcon ii2 = new ImageIcon(this.getClass().getResource(s2));
        i2 = ii2.getImage();
        ImageIcon ii3 = new ImageIcon(this.getClass().getResource(s3));
        i3 = ii3.getImage();
        ImageIcon ii4 = new ImageIcon(this.getClass().getResource(s4));
        i4 = ii4.getImage();
        ImageIcon ii5 = new ImageIcon(this.getClass().getResource(s5));
        i5 = ii5.getImage();
        ImageIcon ii6 = new ImageIcon(this.getClass().getResource(s6));
        i6 = ii6.getImage();
        ImageIcon ii7 = new ImageIcon(this.getClass().getResource(s7));
        i7 = ii7.getImage();
        ImageIcon ii8 = new ImageIcon(this.getClass().getResource(s8));
        i8 = ii8.getImage();
	}
	
	/**
	 * @return obrazek ktory aktualnie bedzie wyswietlony na ekranie. To co bedziw wyswietlane jest zalezne od licznikaAnimacji.
	 */
	private Image getImage()
	{
		Image zwrot=null;		
			switch(licznikAnimacji){
			case 1: zwrot=i1;break;
			case 2: zwrot=i2;break;
			case 3:zwrot=i3;break;
			case 4:zwrot=i4;break;
			case 5:zwrot=i5;break;
			case 6:zwrot=i6;break;
			case 7:zwrot=i7;break;
			case 8:zwrot=i8;break;
			}
			return zwrot;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getId()
	{
		return id;
	}
	/**
	 * @return wartosc jaka posiada Pod. Wartosc ta bedzie przypisywana zyciu, pancerzowi lub ilosci amunicji gracza, w zaleznosci od rodzaju poda.
	 * Funkcja sprawdza czy Poda mozna podniesc jezeli tak, zapamietuje czas podniesienia i ustawia zwracana wartosc.
	 */
	public int podnies()//zwraca wartosc i na jakis czas usuwa go
	{
		int zwrot=0;
		if (!podniesiony)//mozna go podniesc tylko gdy jest jeszcze nie podniesiony
		{
		this.podniesiony=true;
		this.widoczny=false;
		czasPodniesienia=System.currentTimeMillis();
		zwrot=wartosc;
		}
		return zwrot;
	}
	/**Metoda powoduje ze Pod zacznie byc widoczny na ekranie, a zmienna podniesiony zmieni sie na false, dzieki czemu bedzie go mozna ponownie podniesc.
	 */
	public void ustaw()
	{
		this.podniesiony=false;
		this.widoczny=true;
	}
	/**Gdy Pod nie jest podniesiony tzn. jest wyswietlany na ekranie to nalezy obliczyc licznikAnimacji aby wiedziec jaki obrazek ma byc rysowany.
	 *  Licznik Animacji ustawia sie wg licznika kroku, ktory jest stale inkrementowany.
	 */
	private void animuj()
	{//f. zlicza licznik kroku i zlicza klatki animacji
		//wykonuj obliczenia tylko jak jest nie podniesiony
		if(!podniesiony)
		{
		if(licznikKroku%7 == 0)
		{
			
			licznikAnimacji++;
			
			if(licznikAnimacji>8) licznikAnimacji=1;
		
			licznikKroku=1;
		}else
		{
			licznikKroku++;
		}
		}
	}
	/**Funkcja wywolywana co cykl dzialania gry. Wywoluje metode animuj(). Sprawdza takze czy Pod jest podniesiony, jezeli jest to
	 * sprawdza czy po odjeciu od aktualnie pobranego czasu czasu podniesienia poda, czas ten jest wiekszy od czasu ktory podaje w kostruktorze jako parametr
	 * okreslajacy respawn Poda. Jezeli tak jest ustawia ponownie Poda na mapie. 
	 */
	public void podMove()
	{			
		animuj();//animacja i tak sie nie wykonaja jak jest podniesiony
		//jezeli jest podniesiony to trzeba liczyc czas do respawnu
		if(podniesiony)
		{
		long czas=System.currentTimeMillis();
		if(czas-czasPodniesienia>=czasOdnowy*1000)//*1000 aby wyszly sekundy
		{
			ustaw();//jezeli minie ustalony czas wstawiam pancerz na mapke
		}
		
		}
	}
	/**
	 * @param v wartosc o jaka przesune poda w przypadku gdy gracz minie granice od ktorej nalezy przesunac widok gry
	 * w lewo bedz w prawo.
	 */
	public void przesunPoda(int v)
	{//scroll danego elementu wzgledem podanej predkosci 
			x+=v;

	}//
	
	 public void rysuj(Graphics g)
		{
		 if(widoczny)
			g.drawImage(getImage(),x ,y,com);
		}

}
