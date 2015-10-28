

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

//podstawowa definicja klasy ktora bedzie klasa macierzysta dla kazdego rodzaju broni
/**
 * @author Rafal MAKOWSKI
 * Klasa macierzysta dla klas Rakieta i MG definiuje zachowanie siê pociskow wystrzeliwanych
 * przez gracza(wystrzal w odpowiednia strone i ruch po osi OX). Sposob wyswietlania na ekranie i szereg
 * zmiennych okreslajacych pocisk tj. jego predkosc, obra¿enia czy czas przeladowania.
 * Bronie tego typu dzialaja na zasadzie poruszania sie co cykl o dana jednostke miary w jakims kierunku
 * nie znajac celu i miejsca uderzenia do mo mentu uderzenia.
 */
public class Pocisk {
	
	private int x,y,predkosc,przeladowanie,obrazenia,kierunek,xPrzodPocisku,dlugoscPocisku,id;
	Image image,image2;
	boolean widoczny,uderzenie;
	Component r;

	
	//po ciezkich probach zaimplementowania wybuchu w obsludze a potem poziomie, przyszla pora
	//na uzalerznienie rodzaju wybuchu od broni, dlatego tu dam wybuch
	//uderzenie mowi nam ze pocisk w cos trafil, nastanie wybuch a potem visible false
	
	
	
	public Pocisk(int x,int y,int id,int v,int prz,int obr,int dlugosc,String i,String i2)
	{
		this.x=x;
		this.y=y;
		this.predkosc=v;
		this.przeladowanie=prz;
		setImage(i,i2);
		
		widoczny=true;
		this.obrazenia=obr;
		uderzenie = false;
		this.dlugoscPocisku=dlugosc;
		this.id=id;
	}
	public void setWidoczny(boolean b)
	{
		widoczny=b;
	}
	
	public void setUderzenie(boolean u)
	{
		uderzenie=u;
	}
	public int getPrzodPocisku()
	{
		return xPrzodPocisku;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setKierunek(int k)
	{
		kierunek=k;
	}
	
	public int getKierunek()
	{
		return kierunek;
	}
	void setImage(String sciezka,String sciezka2)
	{
		ImageIcon i = new ImageIcon(this.getClass().getResource(sciezka));
	    image = i.getImage();
	    ImageIcon i2 = new ImageIcon(this.getClass().getResource(sciezka2));
	    image2 = i2.getImage();
	}
	Image getImage()
	{
		Image zwrot=null;
		if (kierunek==180)zwrot=image;
		if (kierunek==0)zwrot=image2;
		return zwrot;
	}
	public void rysujPocisk(Graphics g)
	{
		//kombinowanie na przyszlosc czy mozna obrocic pocisk aby mogl leciec pod innym katem
		//Graphics2D g2d;
		//g2d = (Graphics2D)g.create();
		if(widoczny){
			//g2d.rotate(Math.toRadians(5));
			//g=g2d;
		g.drawImage(getImage(),x ,y,r);
		}

	}
	//funkcja odpowiadajaca za ruch pocisku, pocisk porusza sie w zaleznosci od strony jaka wybiore->w jaka strzela gracz
	public void movePocisk()
	{
		if(!uderzenie)
		{
	//na razie stzrela tylko lewo prawo
		if (kierunek==0)//lewo
		{
			x-=predkosc;
			xPrzodPocisku=x;//przod pocisku przydaje sie aby okresli wspolrzedna x w zaleznosci w ktora strone leci aby ustalic dokladniej kiedy w cos uderzyl
		}
		else if (kierunek==180)//daje 180 wrazie jakbym wymyslil pozostale k¹ty;]
		{
			x+=predkosc;
			xPrzodPocisku=x+dlugoscPocisku;
		}
		//mozna tu juz spr czy ejst na ekranie, jak nie to niewidoczny
		if(x<S_swiata.lewaKrawedzWidoku || x>S_swiata.prawaKrawedzWidoku)
		{
			widoczny=false;
		}
		else
		{
		
		}
		
		}
		
	}
	public boolean czyWidoczny()
	{
		if (widoczny==true)
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	//gdy leca pociski, a tzreba przesunac ekran, aby nie bylo efektu zwolnienia pocisku gdy ekran sie przeuwa
	//w lewo, a przyspieszenia gdy przesuwa sie w prawo,, powinno byc odwrotnie
	public void przesunPocisk(int v)
	{
			x+=v;
	}//
	
	
}
