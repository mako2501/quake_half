
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;


/**
 * @author Rafa³ MAKOWSKI. Nazwa klasy moze byc troche mylaca, na poczatku miala sluzyc do czegos innego. Klasa dziedziczy wszystko po 
 * klasie Element_swiata, jednak tutaj inaczej jak w przypadku bloku, chce aby obiekty z niej mialy specjalna wlasciwosc.
 * Nie sa one rysowane na ekranie. W zasadzie maja byc tylko rozpoznawane po id i posiadac wspolrzedne. Beda to ograniczniki na mapie widzialne tylko dla wroga.
 * Na poczatku mialy byc to miejsca z ktorych wiedzialby ze moze wyskoczyc, a el finalnie skonczylo sie na informacji ze
 * jest to kraniec rampy (przeszkoda) i musi zawrocic.
 *
 */
public class AI_skok extends Element_swiata 
{

	
	public AI_skok(int x,int y,int id,int wys)
	{
		super(x,y,null,null,id);
		this.x=x;
		this.y=y;
		this.r=r;
		this.id=id;//id skoku to 2 w ten sposob rozruzniam go w tablicy 
		widoczny=false;
	
	}
	void setImage(String sciezka)
	{
		
	}
	public Image getImage(){
		return null;
	}

}
