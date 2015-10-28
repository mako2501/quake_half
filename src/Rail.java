import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Rafal MAKOWSKI
 *RG ->bron. Koncepcja strzalu jest troche inna niz rl czy mg, tutaj w momencie wystrzalu juz tzreba 
 *wiedziec gdzie bron trafi aby na ca³ej
 *dlugosci stzralu moc narysowac linie strzalu. Nie ma tu pocisku ktory sie przemieszcza. Dlatego inicjuje sie tylko x i y
 *a na wspolrzedne konca linii (bo tak rysuje ten strzal) czeka sie do nastepnego cyklu kiedy to
 *metoda z Poziomu policzy gdzie uderzyl rail.
 */
public class Rail {
private int x,y,x2,y2,przeladowanie,obrazenia,kierunek,id,predkoscZanikania,i,i2,i3;
private boolean widoczny,uderzenie,otrzymalXY2,koniec;
private Component r;
Color kolor;

public Rail(int x,int y,int kierunek)
{
	this.x=x;
	this.y=y;
	x2=0;//te wspolrzedne dopiero zostana ustalone na podstawie info z obiektu poziom gdzie trafil rail
	y2=0;
	this.przeladowanie=S_swiata.czasPrzeladowaniaRaila;
	this.obrazenia=S_swiata.obrazeniaRg;
	this.kierunek=kierunek;
	this.id=S_swiata.rg_id;
	widoczny=false;
	this.predkoscZanikania=S_swiata.predkoscZanikaniaRaila;
	uderzenie=false;
	koniec=false;//aby wiedziec kiedy usunac z listy->do info gdy skonczy sie zliczanie koloru
	otrzymalXY2=false;
	i=255;
	i2=255;
	
	kolor = new Color(255, 255, 0,i);
}
public boolean czyOtrzymalXY2()
{
	return otrzymalXY2;
}

public void setXY2(int x,int y)
{
	x2=x;
	y2=y;
	uderzenie=true;
	otrzymalXY2=true;
}
public boolean getUderzenie()
{
	return uderzenie;
}
public int getX()
{
	return x;
}
public int getX2()
{
	return x2;
}
public int getY2()
{
	return y2;
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
public boolean getKoniec()
{
	return koniec;
}
public void moveRail()
{
	if(otrzymalXY2)widoczny=true;
	i-=predkoscZanikania;
	i2-=10;//na poczatku rail jest bialy, aby szybko zejsc do czeronego 2 i 3 wartosc koloru musi
	//duzo szybciej spasc do 0 i sa to wartosci stale
	if(i>0)//sprawiam ze kolor staje sie coraz bardziej przezroczysty
		if (i2>0)
		{
	kolor=new Color(i2,i2,100,i);
		}
		else if (i2<0)
		{
			i2=0;
			kolor=new Color(i2,i2,255,i);
		}
	if(i<0)koniec=true;//info do usunieca z listy strzalu
}
public void przesunPromien(int v)
//tutaj est inaczej z przesunieciem anizeli z mg czy rl
//w zaleznosci od kierunku strzalu bo rysuje sie on albo od lewj do prawej lub od prawej do lewej
{
		x+=v;
		x2+=v;
}//
public void rysujPromien(Graphics g)
{
	g.setColor(kolor);
	if(widoczny)
	{	
	g.drawLine(x,y,x2,y2);
	g.drawLine(x,y+1,x2,y2+1);
	}
}
}
