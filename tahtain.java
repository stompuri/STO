import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class tahtain
{
	private final int TahtainKoko = 5;
	private double x, y;
			
	public tahtain(double nx, double ny)
	{
		x=nx; y=ny;		//tahtaimen x, ja y koordinaatit
	}
	
	public double annaX() { return x; }
	public double annaY() { return y; }
	
	public void asetaX(double uusiX) { x = uusiX; }
	public void asetaY(double uusiY) { y = uusiY; }
	
	public void liiku(int mouse_x, int mouse_y)
	{
		double laskurix=2, laskuriy=2, laskuri, laskuri2;
		double kerroin;

		laskurix = Math.abs(mouse_x - annaX());
		laskuriy = Math.abs(mouse_y - annaY());

		laskuri = (Math.atan(laskurix/laskuriy))/Math.PI*180;
		kerroin = (Math.sqrt(Math.pow(laskuriy,2) + Math.pow(laskurix,2)))/20;
		
		laskuri2 = laskuri/90*kerroin;
		if(annaX()<mouse_x)
		{ asetaX(annaX()+laskuri2); }
		if(annaX()>mouse_x)
		{ asetaX(annaX()-laskuri2); }
		
		laskuri2 = (90-laskuri)/90*kerroin;
		if(annaY()<mouse_y)
		{ asetaY(annaY()+laskuri2); }
		if(annaY()>mouse_y)
		{ asetaY(annaY()-laskuri2); }
	}
	//osuuko ampuminen	
	public boolean ammu(double targetX, double targetY, double OsanKoko, boolean laukaus)
	{
		int pisteet=0;
		if ((targetX+OsanKoko >= annaX()) && (targetX <= annaX()+TahtainKoko/2) && (targetY+OsanKoko >= annaY()) && (targetY <= annaY()+TahtainKoko/2) && (laukaus))
		{
			return true;
		}
		return false;
	}


}