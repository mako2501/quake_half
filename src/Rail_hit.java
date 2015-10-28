
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Rail_hit {
	
	private int x,y,licznikAnimacji,licznikKroku;
	private Image w1,w2,w3,w4,w5,w6,w7,w8;
	boolean widoczny;
	
	Component com;

	public Rail_hit(int x,int y)
	{
		zaladujAnimacje();
		licznikAnimacji=1;
		licznikKroku=1;
		this.x=x;
		this.y=y;
		widoczny=true;
		moveRailHit();
		
	}
	
	public void zaladujAnimacje(){
		ImageIcon i = new ImageIcon(this.getClass().getResource("/tex/rg/boom_1.gif"));
        w1 = i.getImage();
        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_2.gif"));
        w2 = i2.getImage();
        ImageIcon i3 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_3.gif"));
        w3=i3.getImage();
        ImageIcon i4 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_4.gif"));
        w4=i4.getImage();
        ImageIcon i5 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_5.gif"));
        w5=i5.getImage();
        ImageIcon i6 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_6.gif"));
        w6=i6.getImage();
        ImageIcon i7 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_7.gif"));
        w7=i7.getImage();
        ImageIcon i8 = new ImageIcon(this.getClass().getResource("/tex/rg/boom_8.gif"));
        w8=i8.getImage();
	}
	public Image getImage() {
	    //w zaleznosci od roznych wartosci Gracza, zwraca odpowiednia animacje 
			Image zwrot=null;//musi byc zainicjowany zwrot bo inaczej sie nie skompiluje
			//jezeli postac patzry w lewo i idzie to mozlwe sa 2 klatki animacji
				switch(licznikAnimacji)
				{
				case 1: zwrot=w1;break;
				case 2: zwrot=w4;break;
				case 3: zwrot=w6;break;
				case 4: zwrot=w8;break;
				case 5: zwrot=w8;break;
				case 6: zwrot=w8;break;
				case 7: zwrot=w7;break;
				case 8: zwrot=w8;break;
				}
				return zwrot;
}
	public boolean czyWidoczny()
	{
		return widoczny;
	}
	
	//tzreba w momencie wybuchu policzyc klatki animacji
	public void moveRailHit()
	{
		if(licznikKroku%1 == 0)
		{
		
			licznikAnimacji++;
		
			if(licznikAnimacji>8){widoczny=false;}
		
			licznikKroku=1;
		}else {
			//tylko ustawic licznik kroku na wiekszy
			licznikKroku++;
		}
	}
	 public void rysujRailHita(Graphics g)
		{
		
			g.drawImage(getImage(),x ,y,com);
		}
	
		public void przesunRailHita(int v)
		{//aby wybuch nie ruszal sie wg x na ekranie wraz z postacia, tzreba go rpzeskrolowac
				x+=v;

		}//
	 
}

