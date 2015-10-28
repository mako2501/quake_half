//machine gun
public class MG extends Pocisk{
	
	public MG(int x, int y,int kierunek) {
		
		super(x+S_swiata.szerokoscObrazkaGracza, y+13, S_swiata.mg_id,S_swiata.predkoscMg, S_swiata.czasPrzeladowaniaMg, S_swiata.obrazeniaMg,4, "/tex/mg.gif","/tex/mg.gif");
		setKierunek(kierunek);
	}

}
