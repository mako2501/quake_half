import java.awt.Component;
import java.awt.Image;

//def. pancerza czerwonego
public class Red extends Pod 
{
	
	
	public Red(int x,int y)
	{
		//public Pod(int x,int y,int id,int wartosc,int czasOdnowy,String nazwa,String s1,String s2,String s3,String s4....)
	super(x,y,S_swiata.red_id,S_swiata.wartoscReda,S_swiata.czasOdnowyReda,"RA",
			"/tex/red/red5.gif","/tex/red/red6.gif","/tex/red/red7.gif","/tex/red/red8.gif",
			"/tex/red/red1.gif","/tex/red/red2.gif","/tex/red/red3.gif","/tex/red/red4.gif");
	}

}
