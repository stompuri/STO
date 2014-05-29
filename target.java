//****************
//***Kohde osio***
//****************

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.Random;

public class target
{		
	private Random sat;
	private double x, y;
	private double osanKoko;
	private int tarketti;
	private int paikka=1;
	private int randi=0, xx=0;
	
	public target(int ran)
	{
		sat = new Random();
		while(xx<ran)	//maaritellaan mista suunnasta suunnistaja tulee
		{
			xx++;
			paikka = java.lang.Math.abs(sat.nextInt() % 3);
			tarketti = java.lang.Math.abs(sat.nextInt() % 2);
		}

		if(paikka==1)
		{
			y=350; x=java.lang.Math.abs(sat.nextInt() % 450);
		}
		else if(paikka==2)
		{
			x=450; y=java.lang.Math.abs(sat.nextInt() % 180)+170;
		}
		else
		{
			x=0; y=java.lang.Math.abs(sat.nextInt() % 180)+170;
		}
		osanKoko=60;
	}
	
	public double annaX() { return x; }
	public double annaY() { return y; }
	public double annaOsanKoko() { return osanKoko; }
	public int annaTarketti() { return tarketti; }
	
	public void asetaX(double uusiX) { x = uusiX; }
	public void asetaY(double uusiY) { y = uusiY; }
	
	public void liiku(double kerroin)//yhtalo, jolla saadaan piste A (suunnistaja) menemaan pistettä B (rasti) kohti
	{
		double laskurix=2, laskuriy=2, laskuri, laskuri2;
		int target_x=225, target_y=189;//rastin koordinaatit

		laskurix = Math.abs(target_x - annaX());
		laskuriy = Math.abs(target_y - annaY());

		laskuri = (Math.atan(laskurix/laskuriy))/Math.PI*180;
		
		laskuri2 = laskuri/90*kerroin;
		if(annaX()<target_x)
		{ asetaX(annaX()+laskuri2); }
		if(annaX()>target_x)
		{ asetaX(annaX()-laskuri2); }
		
		laskuri2 = (90-laskuri)/90*kerroin;
		if(annaY()<target_y)
		{ asetaY(annaY()+laskuri2); }
		if(annaY()>target_y)
		{ asetaY(annaY()-laskuri2); }
	}
	
	public boolean onkoPerilla()	//tarkistetaan onko suunnistaja saavuttanut rastin
	{
		if((annaX() >=200) && (annaX() <= 250) && (annaY() >= 164) && (annaY() <= 214))
			return true;
		return false;
	}
}