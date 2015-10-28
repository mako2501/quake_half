

import javax.swing.JFrame;

/**
 * @author Rafa³ MAKOWSKI. Najwazniejsza klasa w grze, rozszerza ona bowiem klase JFrame->tzn za jej pomoca rysuje okno na ekrania. Do tego okna dodaje panel
 * ktorym jest Obsluga.
 *
 */
public class Quake_Half extends JFrame{

public Quake_Half(){
	
	add(new Obsluga());
	//Obsluga gra=new Obsluga();
	setTitle("Quake Half "); //ustawia tytul na pasku
	setDefaultCloseOperation(EXIT_ON_CLOSE);//zamyka aplikacje po kliknieciu zamknij
	setSize(S_swiata.szerokosc,S_swiata.wysokosc);//ustawia wysokosc okna
	
	//setLocationRelativeTo(null);//centruje okno
	setVisible(true);//pokazuje okno na ekranie
	setResizable(false);//nie mozna zmieniac rozmiaru okna
	//setUndecorated(true) ;//gdy fulll screen to nie ma pasak tytulu
	
	
}

	public static void main(String[] args) {
	new Quake_Half();
	
	}

}
