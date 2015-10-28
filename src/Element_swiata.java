import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author Rafa³ MAKOWSKI. Element swiata to kwadrat o wielkosci 20x20 wyswietlany na ekranie za pomoca wgranego obrazka, jednak obiekty te nie sa tworzone bezposrednio z tej klasy. 
 * Lecz jest to klasa macierzysta dla innych klas. Taki obiekt musi posiadac swoje wspolrzedne i wymiary, dzieki czemu wiem gdzie go narysowac i bede mogl
 * sprawdzic kolizje z nim. Posiada zmienna widoczy dzieki czemu nie musze go stale rysowac gdy znajduje sie poza granicami widoku ekranu. Dzieki id moge rozpoznac jaki to
 * konkretnie element swiata aby wiedziec jak zareagowac na kolizje.
 */
public class Element_swiata {

protected int id,x,y;
protected boolean widoczny;
private Image image;
protected Component r;
//konstruktor
public Element_swiata(int x,int y,String i,Component r,int id)
{

this.x=x;//wpolrzedne
this.y=y;
setImage(i);//obrazek elementu
this.r=r;
this.id=id;//numer spec. elementu
widoczny=true;
}

int getId(){
	return id;
}
void setX(int x){
	this.x=x;
}

int getX(){
	return x;
}

void setY(int y){
	this.y=y;
}

int getY(){
	return y;
}

void setId(int id){
	this.id=id;
}
boolean czyWidoczny(){
	return widoczny;
}
void setWidoczny(boolean wid){
	this.widoczny=wid;
}
void setImage(String sciezka)
{
	ImageIcon i = new ImageIcon(this.getClass().getResource(sciezka));
    image = i.getImage();
}
Image getImage(){
	return image;
}
//rysuje element jesli jest widoczny
/**Rysuje obiekt na ekranie, wazne ze robi to tylko wowczas gdy ten jest w polu ekranu.
 */
public void rysujElementSwiata(Graphics g)
{
	if(widoczny)
	g.drawImage(getImage(),x ,y,r);
	
}
//tj w Graczu, scrooluje pojedynczy bloczek w zaleznosci od strony(zmienna podana z + lub -,pozniej gora dol jak uda sie wielopoziom
/**
 * @param v wartosc o jaka chce przesunac obiekt. W zaleznosci od znaku przy nim wiem czy chce zrobic to w prawo czy w lewo.
 */
public void przesunElementSwiata(int v)
{
		x+=v;

	//spr. i ustawiam czy element miesci sie w ekranie,jak nie to ustaw widocznosc na false 
	if (x < S_swiata.lewaKrawedzWidoku || x+S_swiata.szerokoscObrazkaGracza > S_swiata.prawaKrawedzWidoku )
	{
	widoczny=false;	
	}
	else
	{
		widoczny=true;
	}
}

}
