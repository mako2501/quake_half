/**
 * Rakieta to potomek klasy pocisk, nie posiada ¿adnych dodatkowych funkcji i zmiennych
 * wiêc jej konstruktor to wywolanie konstruktora pocisku z odpowiednimi parametrami.
 */
import java.awt.Component;


public class Rakieta extends Pocisk {
//pocisk(int x,int y,int v,int prz,int obr,Component r,dlugosc pocisku!!!!,String i)
	public Rakieta(int x, int y,int kierunek) {
	
		super(x+S_swiata.szerokoscObrazkaGracza, y+13, S_swiata.rl_id,S_swiata.predkoscRakiety, S_swiata.czasPrzeladowaniaRakiety, S_swiata.obrazeniaRakiety,7, "/tex/rl/rl.gif","/tex/rl/rl1.gif");
		setKierunek(kierunek);

		
	}

}
