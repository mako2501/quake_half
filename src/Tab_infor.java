
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Tab_infor
{
	Font fontDuzy = new Font ("Tahoma",Font.PLAIN,22);
	Font font = new Font ("Tahoma",Font.BOLD,21);
	Font fontMaly = new Font ("Tahoma",Font.BOLD,18);
	private boolean koniec;
	String wmg,tmg,kmg,wrl,trl,krl,wrg,trg,krg,kills,sw,st,sk,d;
	private Image image;//obrazek statusbara
	Component c; 
	
	private int i;
	
	public Tab_infor()
	{
		wgrajTablice();
	koniec=false;

	}
	private void wgrajTablice()
	{
		ImageIcon i = new ImageIcon(this.getClass().getResource("/tex/tablica.gif"));
	        image = i.getImage();
	     
	}
//dostaje odpowiednie zmienne i przerabiam je na ciagi znakow
	public void move(int wmg,int tmg,int kmg,int wrl,int trl,int krl,int wrg,int trg,int krg,int deaths)
	{
		this.wmg=Integer.toString(wmg);
		this.tmg=Integer.toString(tmg);
		this.kmg=Integer.toString(kmg);
		this.wrl=Integer.toString(wrl);
		this.trl=Integer.toString(trl);
		this.krl=Integer.toString(krl);
		this.wrg=Integer.toString(wrg);
		this.trg=Integer.toString(trg);
		this.krg=Integer.toString(krg);
		sw=Integer.toString(wmg+wrl+wrg);
		st=Integer.toString(tmg+trl+trg);
		sk=Integer.toString(kmg+krl+krg);
		d=Integer.toString(deaths);
	
	}
	public void setNieKoniec()
	{
		koniec=false;
	}
	public boolean czyKoniecTI()
	{
		return koniec;
	}
	public void rysuj(Graphics g)
	{
		g.drawImage(image,0,0,c);
		g.setColor(S_swiata.czerwony);g.setFont(font);
		
		g.drawString("Ukoñczono poziom", 4, 33);
		g.setColor(S_swiata.czerwony);g.setFont(fontDuzy);
		
		g.drawString("Wystrzelone", 58,110);g.drawString("Trafione", 75,182);
		
		g.drawString(wmg, 249+p(wmg),111);g.drawString(tmg, 249+p(tmg),183);g.drawString(kmg, 249+p(kmg),255);
		g.drawString(wrl, 316+p(wrl),111);g.drawString(trl, 316+p(trl),183);g.drawString(krl, 316+p(krl),255);
		g.drawString(wrg, 381+p(wrg),111);g.drawString(trg, 381+p(trg),183);g.drawString(krg, 381+p(krg),255);
		
		g.drawString(sw, 483+p(sw),111);g.drawString(st, 483+p(st),183);g.drawString(sk, 483+p(sk),255);
	
		g.drawString("Deaths", 85,345);g.drawString(d, 247+p(d),344);
	}
	private int p(String ciag)
	{//funkcja zwraca wartosc o jaka przesuwam w lewo ciagi aby wyswietlaly sie rowno w kratce
		int zwrot=0;
		if(ciag.length()==1)zwrot=0;
		if(ciag.length()==2)zwrot=-6;
		if(ciag.length()==3)zwrot=-14;
		return zwrot;
	}
	 public void klawiszWcisniety(KeyEvent e) {

	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_ESCAPE)
	        {
	        	koniec=true;
	        	
	        }
	       
	 }
	 public void klawiszPuszczony(KeyEvent e)
	 {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_ESCAPE)
	        {
	        	
	        }
	 }
}

