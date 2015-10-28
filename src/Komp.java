import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Komp {

	private int x,y,licznikAnimacji,licznikKroku,licznikCykli,hp,licznikCzekania,pozycjaXNaMapie,mozeSkoczycNaWysokosc;
	Component r;
	private Image l_rl_0,l_rl_1,l_rl_2,l_rl_3,l_rl_4,l_rl_5,l_rl_6,l_rl_7,l_rl_8,l_rl_9,l_rl_10,
	p_rl_0,p_rl_1,p_rl_2,p_rl_3,p_rl_4,p_rl_5,p_rl_6,p_rl_7,p_rl_8,p_rl_9,p_rl_10,l_s_0,l_s_1,l_s_2,l_s_3,l_s_4,p_s_0,p_s_1,p_s_2,p_s_3,p_s_4,
	sp_s_0,sp_s_1,sp_s_2,sp_s_3,sp_s_4,sl_s_0,sl_s_1,sl_s_2,sl_s_3,sl_s_4;
	private boolean stoi,patrzyWLewo,patrzyWPrawo,idzie,skacze,zyje,widoczny,czekanie,spada,przeszkodaZLewej,przeszkodaZPrawej,mozeSkoczyc,
	koniecRampyZLewej,koniecRampyZPrawej,niezyje,ginie,zaczalGinac,splash;
	public Komp(int x,int y,boolean l,boolean p)//tzreba uwazac aby nie zainicjowac 2x true
	{
		
		this.x=x;
		this.y=y;
		zaladujAnimacje();
		licznikAnimacji=1;
		licznikKroku=1;
		licznikCykli=1;
		stoi=true;
		patrzyWLewo=l;
		patrzyWPrawo=p;
		skacze=false;
		hp=100;
		zyje=true;
		widoczny=true;
		czekanie=false;
		licznikCzekania=0;
		spada=false;
		pozycjaXNaMapie=x+S_swiata.szerokoscObrazkaGracza/2;
		idzie=true;
		przeszkodaZLewej=false;
		przeszkodaZPrawej=false;
		koniecRampyZLewej=false;
		koniecRampyZPrawej=false;
		mozeSkoczyc=false;
		mozeSkoczycNaWysokosc=0;
		niezyje=false;
		ginie=false;
		zaczalGinac=false;
		splash=false;
	}

	
	public void setPrzeszkodaZLewej(boolean p)
	{
		przeszkodaZLewej=p;
	}
	public void setPrzeszkodaZPrawej(boolean p)
	{
		przeszkodaZPrawej=p;
	}
	public void setkoniecRampyZLewej(boolean p)
	{
		koniecRampyZLewej=p;
	}
	public void setkoniecRampyZPrawej(boolean p)
	{
		koniecRampyZPrawej=p;
	}
	public void zaladujAnimacje(){
		//splash lewy
		ImageIcon slsil_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p0.gif"));
		sl_s_0 = slsil_rl_0.getImage();
		ImageIcon slsil_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p1.gif"));
		sl_s_1 = slsil_rl_1.getImage();
		ImageIcon slsil_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p2.gif"));
		sl_s_2 = slsil_rl_2.getImage();
		ImageIcon slsil_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p3.gif"));
		sl_s_3 = slsil_rl_3.getImage();
		ImageIcon slsil_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p4.gif"));
		sl_s_4 = slsil_rl_4.getImage();
		//sprlash prawy
		ImageIcon ssil_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p0.gif"));
		sp_s_0 = ssil_rl_0.getImage();
		ImageIcon ssil_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p1.gif"));
		sp_s_1 = ssil_rl_1.getImage();
		ImageIcon ssil_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p2.gif"));
		sp_s_2 = ssil_rl_2.getImage();
		ImageIcon ssil_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p3.gif"));
		sp_s_3 = ssil_rl_3.getImage();
		ImageIcon ssil_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/splash_s_p4.gif"));
		sp_s_4 = ssil_rl_4.getImage();
		//smierci obroconego w lewo
		ImageIcon sil_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_l0.gif"));
		l_s_0 = sil_rl_0.getImage();
		ImageIcon sil_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_l1.gif"));
		l_s_1 = sil_rl_1.getImage();
		ImageIcon sil_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_l2.gif"));
		l_s_2 = sil_rl_2.getImage();
		ImageIcon sil_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_l3.gif"));
		l_s_3 = sil_rl_3.getImage();
		ImageIcon sil_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_l4.gif"));
		l_s_4 = sil_rl_4.getImage();
		//smierci obroconego w prawo
		ImageIcon sip_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_p0.gif"));
		p_s_0 = sip_rl_0.getImage();
		ImageIcon sip_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_p1.gif"));
		p_s_1 = sip_rl_1.getImage();
		ImageIcon sip_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_p2.gif"));
		p_s_2 = sip_rl_2.getImage();
		ImageIcon sip_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_p3.gif"));
		p_s_3 = sip_rl_3.getImage();
		ImageIcon sip_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/s_p4.gif"));
		p_s_4 = sip_rl_4.getImage();
		//gdy w lewo
		ImageIcon il_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l0.gif"));
		l_rl_0 = il_rl_0.getImage();
		ImageIcon il_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l1.gif"));
		l_rl_1 = il_rl_1.getImage();
		ImageIcon il_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l2.gif"));
		l_rl_2 = il_rl_2.getImage();
		ImageIcon il_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l3.gif"));
		l_rl_3 = il_rl_3.getImage();
		ImageIcon il_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l4.gif"));
		l_rl_4 = il_rl_4.getImage();
		ImageIcon il_rl_5 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l5.gif"));
		l_rl_5 = il_rl_5.getImage();
		ImageIcon il_rl_6 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l6.gif"));
		l_rl_6 = il_rl_6.getImage();
		ImageIcon il_rl_7 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l7.gif"));
		l_rl_7 = il_rl_7.getImage();
		ImageIcon il_rl_8 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l8.gif"));
		l_rl_8 = il_rl_8.getImage();
		ImageIcon il_rl_9 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l9.gif"));
		l_rl_9 = il_rl_9.getImage();
		ImageIcon il_rl_10 = new ImageIcon(this.getClass().getResource("/tex/e/rl/l10.gif"));
		l_rl_10 = il_rl_10.getImage();
		//w prawo
		ImageIcon ip_rl_0 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p0.gif"));
		p_rl_0 = ip_rl_0.getImage();
		ImageIcon ip_rl_1 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p1.gif"));
		p_rl_1 = ip_rl_1.getImage();
		ImageIcon ip_rl_2 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p2.gif"));
		p_rl_2 = ip_rl_2.getImage();
		ImageIcon ip_rl_3 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p3.gif"));
		p_rl_3 = ip_rl_3.getImage();
		ImageIcon ip_rl_4 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p4.gif"));
		p_rl_4 = ip_rl_4.getImage();
		ImageIcon ip_rl_5 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p5.gif"));
		p_rl_5 = ip_rl_5.getImage();
		ImageIcon ip_rl_6 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p6.gif"));
		p_rl_6 = ip_rl_6.getImage();
		ImageIcon ip_rl_7 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p7.gif"));
		p_rl_7 = ip_rl_7.getImage();
		ImageIcon ip_rl_8 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p8.gif"));
		p_rl_8 = ip_rl_8.getImage();
		ImageIcon ip_rl_9 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p9.gif"));
		p_rl_9 = ip_rl_9.getImage();
		ImageIcon ip_rl_10 = new ImageIcon(this.getClass().getResource("/tex/e/rl/p10.gif"));
		p_rl_10 = ip_rl_10.getImage();
	}
	public boolean getWidoczny()
	{
		return widoczny;
	}
	public void setWidoczny(boolean w)
	{
		widoczny=w;
	}
	public boolean getPatrzyWPrawo()
	{
		return patrzyWPrawo;
	}
	public boolean czyZyje()
	{
		return niezyje;
	}
	public boolean getPatrzyWLewo()
	{
		return patrzyWLewo;
	}
	public int getY()
	{
		return this.y;
	}
	public int getX()
	{
		return this.x;
	}
	public int getPozycjaXGry(){
		return pozycjaXNaMapie;
	}
	public void setPozycjaXGry(int x)
	{
		pozycjaXNaMapie=x;
	}
	private Image getImage()
	{
		Image zwrot=null;
		if(niezyje && !splash)
		{
			if(patrzyWPrawo)zwrot=p_s_4;
			if(patrzyWLewo)zwrot=l_s_4;
		}else if (niezyje && splash)
		{
			if(patrzyWPrawo)zwrot=sp_s_4;
			if(patrzyWLewo)zwrot=sl_s_4;
		}
		
		if(ginie==true && !splash)//jezeli ginie==true to licznik kroku jest zmieniany nie klawiszami ale czasowo z Obslugi
		{
		if(patrzyWPrawo)	
			switch(licznikAnimacji)
			{
			case 1: zwrot=p_s_0;break;
			case 2: zwrot=p_s_1;break;
			case 3: zwrot=p_s_2;break;
			case 4: zwrot=p_s_3;break;
			case 5: zwrot=p_s_4;break;
			case 6: niezyje=true;
			//gdy animacja dojdzie do 6 zabija gracza
			//default : zwrot=mg_l4;break;
			}
		if(patrzyWLewo)	
			switch(licznikAnimacji)
			{
			case 1: zwrot=l_s_0;break;
			case 2: zwrot=l_s_1;break;
			case 3: zwrot=l_s_2;break;
			case 4: zwrot=l_s_3;break;
			case 5: zwrot=l_s_4;break;
			case 6: niezyje=true;//gdy animacja dojdzie do 6 zabija gracza
			//default : zwrot=mg_l4;break;
			}
		}else if (ginie && splash)
		{
			if(patrzyWPrawo)	
				switch(licznikAnimacji)
				{
				case 1: zwrot=sp_s_0;break;
				case 2: zwrot=sp_s_1;break;
				case 3: zwrot=sp_s_2;break;
				case 4: zwrot=sp_s_3;break;
				case 5: zwrot=sp_s_4;break;
				case 6: niezyje=true;
				//gdy animacja dojdzie do 6 zabija gracza
				//default : zwrot=mg_l4;break;
				}
			if(patrzyWLewo)	
				switch(licznikAnimacji)
				{
				case 1: zwrot=sl_s_0;break;
				case 2: zwrot=sl_s_1;break;
				case 3: zwrot=sl_s_2;break;
				case 4: zwrot=sl_s_3;break;
				case 5: zwrot=sl_s_4;break;
				case 6: niezyje=true;//gdy animacja dojdzie do 6 zabija gracza
				//default : zwrot=mg_l4;break;
				}
		}

		if(!ginie)
		{
		//jezeli tylko stoi to taka animacja
		if(stoi && patrzyWLewo )zwrot=l_rl_0;
		if(stoi && patrzyWPrawo )zwrot=p_rl_0;
		//jezeli idzie to w zaleznosci od strony w ktora idzie i od licznika Animacji
		if(idzie && patrzyWLewo)
		{
			switch(licznikAnimacji)
			{
			case 1: zwrot=l_rl_1;break;
			case 2: zwrot=l_rl_2;break;
			case 3: zwrot=l_rl_3;break;
			case 4: zwrot=l_rl_4;break;
			case 5: zwrot=l_rl_5;break;
			case 6: zwrot=l_rl_6;break;
			case 7: zwrot=l_rl_7;break;
			case 8: zwrot=l_rl_8;break;
			case 9: zwrot=l_rl_9;break;
			}
		}
		if(skacze && patrzyWLewo)zwrot=l_rl_10;
		if(spada && patrzyWLewo)zwrot=l_rl_10;
		if(idzie && patrzyWPrawo)
		{
			switch(licznikAnimacji)
			{
			case 1: zwrot=p_rl_1;break;
			case 2: zwrot=p_rl_2;break;
			case 3: zwrot=p_rl_3;break;
			case 4: zwrot=p_rl_4;break;
			case 5: zwrot=p_rl_5;break;
			case 6: zwrot=p_rl_6;break;
			case 7: zwrot=p_rl_7;break;
			case 8: zwrot=p_rl_8;break;
			case 9: zwrot=p_rl_9;break;
			}
		}
		if(skacze && patrzyWPrawo)zwrot=p_rl_10;
		if (spada && patrzyWPrawo)zwrot=p_rl_10;
		}
		return zwrot;
	}
	//odejmuje od zycia obrazenia i zwraca prawde jesli postac zginela
	public boolean zadajObrazenia(int o)
	{
		boolean zwrot=false;
		
		hp-=o;
		if(hp<=0)
			{
			if(!ginie)//a no po to to jest aby zaliczal tylko 1 punkt, bo zdarza sie ze metody troche trwaja i postac tak naprawde
				//ginie kilka milisekund i za kazda nabijane sa punkty
			zwrot=true;
			ginie=true;
			if(hp<-30)splash=true;
			
			}
		return zwrot;
	}
	
	public void move()
	{
		if(niezyje)widoczny=true;
		
		if(!ginie)
		{
		//if (hp<=0)ginie=true;
		
		//aby rysowal zwloki
		
			//this.licznikCykli=l;//move pobiera c_licznikKroku z Obslugi i przypisuje go AI 
		
			if(idzie)
			{
				licznikKroku++;
				if(licznikKroku%10==0)
				{
					licznikAnimacji++;
					if (licznikAnimacji>9)licznikAnimacji=1;
					licznikKroku=1;
				}
			}else if(!idzie)
			{
				licznikAnimacji=1;
				licznikKroku=1;
			}
	
			if(przeszkodaZLewej )
			{
				idzWPrawo();
			}
			if(przeszkodaZPrawej )idzWLewo();
			if(koniecRampyZLewej )idzWPrawo();
			if(koniecRampyZPrawej )idzWLewo();
			
			if(idzie && patrzyWLewo  )
			{
				x-=S_swiata.predkoscX2;
				pozycjaXNaMapie-=S_swiata.predkoscX2;
			}
			if(idzie && patrzyWPrawo)
			{
				x+=S_swiata.predkoscX2;
				pozycjaXNaMapie+=S_swiata.predkoscX2;
			}
			
			if (spada)
			{
				y+=S_swiata.predkoscY;
			}
			if(x<S_swiata.lewaKrawedzWidoku || x>S_swiata.prawaKrawedzWidoku)
			{
				widoczny=false;
			}
			else
			{
			widoczny=true;
			}	
		}else if(ginie)
			
		{
			//tutaj jak ginie	
			if(!zaczalGinac)
			{//aby moc ustawic na pierwsza klatke
				licznikAnimacji=1;
				zaczalGinac=true;
			}
			licznikKroku++;
			if(licznikKroku%6 == 0)
				licznikAnimacji++;
		}
	}


	public void setSpada(boolean s)
	{
		if(s)skacze=false;
		spada=s;
	}
	
	public void idzWLewo()
	{
		idzie=true;
		patrzyWLewo=true;
		patrzyWPrawo=false;
	}
	public boolean czyPatrzyWPrawo()
	{
		return patrzyWPrawo;
	}
	public boolean czyPatrzyWLewo()
	{
		return patrzyWLewo;
	}
	public void idzWPrawo()
	{
		idzie=true;
		patrzyWLewo=false;
		patrzyWPrawo=true;
	}
	public void stoj()
	{
		idzie=false;
	}
	public void idz()
	{
		idzie=true;
	}
	public void obrocWLewo()
	{
		patrzyWLewo=true;
		patrzyWPrawo=false;
	}
	public void obrocWPrawo()
	{
		patrzyWLewo=false;
		patrzyWPrawo=true;
	}
	public void przesun(int v)
	{
		x+=v;
	}

	public void rysujKompa(Graphics g)
	{
		if(widoczny)
		g.drawImage(getImage(),x ,y,r);
	}
	
}
