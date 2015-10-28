/**
 * Blysk pojawiajacy sie przy wystrzale z rakiety 
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Rl_boom {

		private int x,y,licznikKroku,kierunek;
		private Image image,image2;
		boolean widoczny;
		
		Component com;

		public Rl_boom(int x,int y,int kierunek)
		{
			setImage();
			licznikKroku=1;
			this.x=x;
			this.y=y;
			widoczny=true;
			this.kierunek=kierunek;
			boom();
		}
		
		public void setImage(){
			ImageIcon i = new ImageIcon(this.getClass().getResource("/tex/rl/rl_boom_r.gif"));
	        image = i.getImage();
	        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/tex/rl/rl_boom_l.gif"));
	        image2 = i2.getImage();
		}
		public Image getImage() {
		   Image z=null;
			switch(kierunek)
			{
			case 180: z=image;break;
			case 0: z=image2;break;
			}
			
					return z;
	}
		public boolean czyWidoczny()
		{
			return widoczny;
		}
		
		public void boom()
		{
			if(licznikKroku%10 == 0)
			{
				widoczny=false;
			}else {
			
				licznikKroku++;
			}
		}
		 public void rysujWybuch(Graphics g)
			{
			 if(widoczny)
				g.drawImage(getImage(),x ,y,com);
			}
 
	}
	
	

