import java.awt.Component;
import java.awt.Image;

//BLOK nie ma spec zachowan, to tylko zwykly blok;]]
/**
 * @author Rafa³ MAKOWSKI. Mozna powiedziec ze obiekty tworzone z tej klasy sa podstawowymi elementami swiata rysowanymi w grze.
 * Nie ma on specjalnych wlasciowosci, tj. element_swiata. Takze nie rozrzezam jego wlasciowosci o dodatkowe metody, jego konstruktor wywoluje konstruktor klasy macierzystej.
 *
 */
public class Blok extends Element_swiata 
{
	//konstruktor Bloku wywoluje konstruktor Elementu_swiata
	//Element_swiata(int x,int y,Image i,Component r,int id)
	public Blok(int x,int y,String i,Component r,int id)
	{
	super(x,y,i,r,id);
	}

}
