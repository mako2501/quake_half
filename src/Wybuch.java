import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Wybuch {
	
	private int x,y,licznikAnimacji,licznikKroku;
	private Image w1,w2,w3;
	boolean widoczny;
	
	Component com;

	public Wybuch(int x,int y)
	{
		zaladujAnimacje();
		licznikAnimacji=1;
		licznikKroku=1;
		this.x=x;
		this.y=y;
		widoczny=true;
		wybuch();
		
	}
	
	public void zaladujAnimacje(){
		ImageIcon i = new ImageIcon(this.getClass().getResource("/tex/boom_1.gif"));
        w1 = i.getImage();
        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/tex/boom_2.gif"));
        w2 = i2.getImage();
        ImageIcon i3 = new ImageIcon(this.getClass().getResource("/tex/boom_3.gif"));
        w3=i3.getImage();
	}
	public Image getImage() {
	    //w zaleznosci od roznych wartosci Gracza, zwraca odpowiednia animacje 
			Image zwrot=null;//musi byc zainicjowany zwrot bo inaczej sie nie skompiluje
			//jezeli postac patzry w lewo i idzie to mozlwe sa 2 klatki animacji
				switch(licznikAnimacji)
				{
				case 1: zwrot=w1;break;
				case 2: zwrot=w2;break;
				case 3: zwrot=w3;break;
				}
				return zwrot;
}
	public boolean czyWidoczny()
	{
		return widoczny;
	}
	
	//tzreba w momencie wybuchu policzyc klatki animacji
	public void wybuch()
	{
		if(licznikKroku%9 == 0)
		{
			//zmiana licznika animacji na nastepny
			licznikAnimacji++;
			//gdy = 2 to ustaw na 0, bo sa tylko 2 animacje do ruchu
			if(licznikAnimacji>3){widoczny=false;}
			//gdy licznikkroku ma 5 mozna go zresetowac,aby nie rosl w nieskonczonosc
			licznikKroku=1;
		}else {
			//tylko ustawic licznik kroku na wiekszy
			licznikKroku++;
		}
	}
	 public void rysujWybuch(Graphics g)
		{
		 //if(widoczny)
			g.drawImage(getImage(),x ,y,com);
		}
	
		public void przesunWybuch(int v)
		{//aby wybuch nie ruszal sie wg x na ekranie wraz z postacia, tzreba go rpzeskrolowac
				x+=v;

		}//
	 
}
