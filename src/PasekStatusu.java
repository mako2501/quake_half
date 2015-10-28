import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class PasekStatusu {

	Font font = new Font ("Tahoma",Font.BOLD,15);
	Font font_m = new Font ("Tahoma",Font.BOLD,11);
	Font font_hp1 = new Font ("Tahoma",Font.BOLD,30);
	
	private Image image,rl_p,rg_p,mg_p;//obrazek statusbara
	Component c;
	String hp,pancerz,a_rakiety,a_slugs,aktualnaBron,kills,a_mg,liczbaWrogow;
	private boolean posiadaRg,posiadaRl,posiadaMg;


	private int x,y;//tutaj bedzie inicjowany statusbar
	
	public PasekStatusu(Gracz gracz)
	{
		x=0;
		y=(S_swiata.iloscLiniiEkranu)*S_swiata.wielkoscBloku;//bedzie sie zaczynal tam gdzie konczy poziom + 1 bo od konca tej lini
		wgrajPasek();
		hp=Integer.toString(gracz.getHp());
		pancerz=Integer.toString(gracz.getPancerz());
		a_rakiety=Integer.toString(gracz.getAmunicje(S_swiata.rl_id));
		a_slugs=Integer.toString(gracz.getAmunicje(S_swiata.rg_id));
		a_mg=Integer.toString(gracz.getAmunicje(S_swiata.mg_id));
	kills=Integer.toString(gracz.getKills());
		posiadaRg=false;posiadaRl=false;posiadaMg=false;
		sprawdzBron(gracz);
		liczbaWrogow=null;
	}
	public void setLiczbaWrogow(int liczba)
	{
		liczbaWrogow=Integer.toString(liczba);
	}
	//spradzenie czy posiada bron, aby wiedziec czy wyswietlic ikonke na ekranie
	//poszczegolne wartosci sa przypisywane tylko w przypadku gdy sa one ustawione na false
	private void sprawdzBron(Gracz gracz)
	{
		if(!posiadaMg)
			if(gracz.getBron(S_swiata.mg_id)==true)posiadaMg=true;
		if(!posiadaRg)
			if(gracz.getBron(S_swiata.rg_id)==true)posiadaRg=true;
		if(!posiadaRl)
			if(gracz.getBron(S_swiata.rl_id)==true)posiadaRl=true;
	}
	private void wgrajPasek()
	{
		ImageIcon i = new ImageIcon(this.getClass().getResource("/tex/statusbar.gif"));
	        image = i.getImage();
	        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/tex/ammo/a_mg_p.gif"));
	        mg_p = i2.getImage();
	        ImageIcon i3 = new ImageIcon(this.getClass().getResource("/tex/ammo/a_rl_p.gif"));
	        rl_p = i3.getImage();
	        ImageIcon i4 = new ImageIcon(this.getClass().getResource("/tex/ammo/a_rg_p.gif"));
	        rg_p = i4.getImage();
	}
	
	public void uaktualnijPasek(Gracz gracz)
	{
		hp=Integer.toString(gracz.getHp());
		pancerz=Integer.toString(gracz.getPancerz());
		sprawdzBron(gracz);
		
		a_rakiety=Integer.toString(gracz.getAmunicje(S_swiata.rl_id));
		a_slugs=Integer.toString(gracz.getAmunicje(S_swiata.rg_id));
		a_mg=Integer.toString(gracz.getAmunicje(S_swiata.mg_id));
		kills=Integer.toString(gracz.getKills());
		
	}
	private void rysujHpIPancerz(Graphics g)
	{
		switch(hp.length())
		{
		case 1:g.drawString(hp, 100, 22*20);break;
		case 2:g.drawString(hp, 90, 22*20);break;
		case 3:g.drawString(hp, 80, 22*20);break;
		}
		switch(pancerz.length())
		{
		case 1:g.drawString(pancerz, 225, 22*20);break;
		case 2:g.drawString(pancerz, 215, 22*20);break;
		case 3:g.drawString(pancerz, 205, 22*20);break;
		}

	}
	private void rysujFragi(Graphics g)
	{
		switch(kills.length())
		{
		case 1:g.drawString(kills+"/"+liczbaWrogow, x+562, y+49);break;
		case 2:g.drawString(kills+"/"+liczbaWrogow, x+555, y+49);break;
		case 3:g.drawString(kills+"/"+liczbaWrogow, x+556, y+49);break;
		
		}
	}
	private void rysujIkonki(Graphics g)
	{/**
	W zaleznosci od dlugosci znakow do wyswietlenia ilosc amunicji wyswietla siê zawsze rowno pod
	ikonkami broni
	*/
		
		if (posiadaMg==true)
		{
			
			switch(a_mg.length())
			{
			case 1: g.drawString(a_mg, x+366, y+49);break;
			case 2: g.drawString(a_mg, x+362, y+49);break;
			case 3: g.drawString(a_mg, x+357, y+49);break;
			}			
			g.drawImage(mg_p,x+359 ,y+9,c);
		}
		else {
			
		}
		if (posiadaRg==true)
		{
			
			switch(a_slugs.length())
			{
			case 1: g.drawString(a_slugs,x+490 ,y+49);break;
			case 2: g.drawString(a_slugs,x+485 ,y+49);break;
			case 3: g.drawString(a_slugs,x+481 ,y+49);break;
			}
			g.drawImage(rg_p,x+482 ,y+9,c);
		}else
		{
			//nic nie rob
		}
		if (posiadaRl==true)
		{
			
			switch(a_rakiety.length())
			{
			case 1: g.drawString(a_rakiety,x+429 ,y+49);break;
			case 2: g.drawString(a_rakiety,x+424 ,y+49);break;
			case 3: g.drawString(a_rakiety,x+421 ,y+49);break;
			}
			g.drawImage(rl_p,x+421 ,y+9,c);
			
		}else{
			
		}
	}
	
	 public void rysujPasek(Graphics g)
		{
		
		 	g.setColor(S_swiata.czerwony);
		 
			g.drawImage(image,x ,y,c);
			g.setFont(font);
			g.setFont(font_m);
			rysujFragi(g);
			
			rysujIkonki(g);
			
			g.setFont(font_hp1);
			rysujHpIPancerz(g);

		}
}
