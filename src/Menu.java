import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;


public class Menu{
	Font fontDuzy = new Font ("Tahoma",Font.BOLD,34);
	Font font = new Font ("Tahoma",Font.BOLD,30);
	Font fontMaly = new Font ("Tahoma",Font.BOLD,26);
	private boolean koniecMenu,wcisnietaSpacja,wybrany1,wybrany2,wybrany3,menuGlowne,sterowanie,infoOGrze,zmniejszamI,zwiekszamI;
	
	 
	private int i;
	public Menu()
	{
		infoOGrze=sterowanie=wybrany3=wybrany2=wcisnietaSpacja=koniecMenu=zwiekszamI=false;
		menuGlowne=wybrany1=zmniejszamI=true;
		i=100;
	}
	public void move()
	{
		if(zmniejszamI)
			{
			i--;
			if(i<1)
			{
				zmniejszamI=false;zwiekszamI=true;
			}
			}
		if(zwiekszamI)
			{
			i++;
			if(i>100)
			{
				zwiekszamI=false;zmniejszamI=true;
			}
			}
		
		
		if (wcisnietaSpacja && wybrany1){
			koniecMenu=true;
			menuGlowne=false;
			sterowanie=false;
			infoOGrze=false;
			wybrany2=false;
			wybrany3=false;
			wybrany1=false;
		
		}
		if (wcisnietaSpacja && wybrany2){
			menuGlowne=false;
			sterowanie=true;
			infoOGrze=false;
			wybrany2=false;
			koniecMenu=false;
			
		}
		if (wcisnietaSpacja && wybrany3){
			menuGlowne=false;
			sterowanie=false;
			infoOGrze=true;
			wybrany3=false;
			koniecMenu=false;
		}
		
	}
	public boolean czyKoniecMenu()
	{
		return koniecMenu;
	}
public void wybrany1(Graphics g)
{
	g.setColor(S_swiata.bialy);g.setFont(font);
	g.drawString("Graj", 275,240);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Sterowanie", 235,280);	
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Info o grze", 239,320);
}
public void wybrany2(Graphics g)
{
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Graj", 280,240);
	g.setColor(S_swiata.bialy);g.setFont(font);
	g.drawString("Sterowanie", 230,280);	
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Info o grze", 239,320);
}

public void wybrany3(Graphics g)
{
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Graj", 280,240);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Sterowanie", 235,280);	
	g.setColor(S_swiata.bialy);g.setFont(font);
	g.drawString("Info o grze", 228,320);
}

public void infoOGrze(Graphics g)
{
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);
	g.drawString("Praca zaliczeniowa z POJ ", 161,120);
	g.drawString("wykonal", 250,165);
	g.setColor(S_swiata.bialy);g.setFont(font);
	g.drawString("Rafal Makowski nr indeksu 8113 ", 80,220);
	g.setColor(new Color(255, 0, 0, i));g.setFont(fontMaly);
	g.drawString("ESC-wyjscie    SPACJA-wybór ", 111,430);
}

public void sterowanie(Graphics g)
{
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("W", 200,100);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  skok ", 250,100);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("A", 205,140);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  lewo ", 250,140);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("D", 205,180);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  prawo ", 250,180);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("SPACJA", 120,220);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  strzal ", 250,220);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("1", 205,260);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  bron 1 ", 250,260);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("2", 205,300);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  bron 2 ", 250,300);
	
	g.setColor(S_swiata.bialy);g.setFont(font);g.drawString("3", 205,340);
	g.setColor(S_swiata.czerwony);g.setFont(fontMaly);g.drawString("-  bron 3 ", 250,340);
	g.setColor(new Color(255, 0, 0, i));g.setFont(fontMaly);
	g.drawString("ESC-wyjscie    SPACJA-wybór ", 111,430);
}
	public void rysujMenu(Graphics g)
	{
		if(menuGlowne)
		{
		g.setFont(fontDuzy);
		g.setColor(S_swiata.czerwony);
		g.drawString("QH", 280,160);
		if(wybrany1)wybrany1(g);
		if(wybrany2)wybrany2(g);
		if(wybrany3)wybrany3(g);
		g.setColor(new Color(255, 0, 0, i));g.setFont(fontMaly);
		g.drawString("ESC-wyjscie    SPACJA-wybór ", 111,430);
		}
		
		if(sterowanie)sterowanie(g);
		if(infoOGrze)infoOGrze(g);
	}
	 public void klawiszWcisniety(KeyEvent e) {

	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_SPACE)
	        {
	        	wcisnietaSpacja=true;
	        }
	        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP )
	        {
	        	if(wybrany1)
	        	{
	        		wybrany1=false;
	        		wybrany3=true;
	        	}
	        	else if(wybrany2)
	        	{
	        		wybrany1=true;
	        		wybrany2=false;
	        	}
	        	else if(wybrany3)
	        	{
	        		wybrany3=false;
	        		wybrany2=true;
	        	}
	        }
	        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
	        {
	        	if(wybrany1)
	        	{
	        		wybrany1=false;
	        		wybrany2=true;
	        	}
	        	else if(wybrany2)
	        	{
	        		wybrany3=true;
	        		wybrany2=false;
	        	}
	        	else if(wybrany3)
	        	{
	        		wybrany3=false;
	        		wybrany1=true;
	        	}
	        }  
	        if (key == KeyEvent.VK_ESCAPE )
	        {
	        	if(sterowanie)
	        	{
	        	sterowanie=false;
	        	menuGlowne=true;
	        	wybrany2=true;
	        	}else
	        	if(infoOGrze)
	        	{
		        	infoOGrze=false;
		        	menuGlowne=true;
		        	wybrany3=true;
		        }else
	        	if(menuGlowne && !infoOGrze && !sterowanie)
	        	{
	        		System.exit(0);
	        	}
	        }
	 }
	 public void klawiszPuszczony(KeyEvent e)
	 {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_SPACE)
	        {
	        	wcisnietaSpacja=false;
	        }
	 }
}
