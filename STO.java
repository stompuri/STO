import java.applet.*;
import java.awt.*;
import java.awt.event.*;
//***OHJELMAN MÄÄRITYS***
public class STO extends Applet implements Runnable, MouseListener, MouseMotionListener
{
	Thread Saie=null;
	Image puskuriKuva;
	Graphics puskuri;

	tahtain Tahtain;
	target Target;
	target Target2;
	target Target3;
	target Target4;
	private AudioClip lataus;
	private AudioClip laukaus;
	private AudioClip klik;
	private AudioClip huti;
	private int liekit=0;
	private int hutiPlay=0;
	private int maali=1;
	private int ind=0;
	private int ammusMaara=4;
	private double nopeus=1;
	private Image tausta;
	private Image gameover;
	private Image aloita;
	private Image target1;
	private Image target2;
	private Image ammus;
	private Image vasen_reuna;
	private Image oikea_reuna;
	private Image ala_reuna;
	private Image ala_reuna2;
	private Image yla_reuna;
	private Image sight;
	private boolean Ladattu;
	private boolean Painallus;
	private boolean Osuma;
	private boolean loppu;
	private int x_mouse, y_mouse;
	private boolean pysaytetty = true;
	private int Pisteet;
	private int Level;
	
	public int annaMX() { return x_mouse; }
	public void asetaMX(int uusiX) { x_mouse = uusiX; }
	public int annaMY() { return y_mouse; }
	public void asetaMY(int uusiY) { y_mouse = uusiY; }
	public boolean annaPain() { return Painallus; }
	public void asetaPain(boolean uusiPain) { Painallus = uusiPain; }
	
	public void init()
	{
		lataus = getAudioClip(getDocumentBase(), "clipin.au");
		laukaus = getAudioClip(getDocumentBase(), "fire.au");
		huti = getAudioClip(getDocumentBase(), "miss.au");
		klik = getAudioClip(getDocumentBase(), "klik.au");
		MediaTracker tracker = new MediaTracker(this);
		tausta = getImage(getDocumentBase(), "forest.gif");
		vasen_reuna = getImage(getDocumentBase(), "bg_vasen.gif");
		oikea_reuna = getImage(getDocumentBase(), "bg_oikea.gif");
		ala_reuna = getImage(getDocumentBase(), "bg_ala.gif");
		ala_reuna2 = getImage(getDocumentBase(), "bg_ala2.gif");
		yla_reuna = getImage(getDocumentBase(), "bg_yla.gif");
		ammus = getImage(getDocumentBase(), "ammus.gif");
		sight = getImage(getDocumentBase(), "tahtain.gif");
		target1 = getImage(getDocumentBase(), "target1.gif");
		target2 = getImage(getDocumentBase(), "target2.gif");
		aloita = getImage(getDocumentBase(), "aloita.gif");
		gameover = getImage(getDocumentBase(), "gameover.gif");
		tracker.addImage(tausta, 0);
		tracker.addImage(vasen_reuna, 4);
		tracker.addImage(oikea_reuna, 5);
		tracker.addImage(ala_reuna, 6);
		tracker.addImage(ala_reuna2, 9);
		tracker.addImage(yla_reuna, 7);
		tracker.addImage(sight, 1);
		tracker.addImage(ammus, 8);
		tracker.addImage(target1, 2);
		tracker.addImage(target1, 3);
		tracker.addImage(aloita, 6);
		tracker.addImage(gameover, 10);
		try { tracker.waitForID(10); } catch(Exception e) {};
		
		puskuriKuva = createImage(this.getSize().width, this.getSize().height);
		puskuri = puskuriKuva.getGraphics();

		Pisteet=0;
		Level=1;
		Ladattu = false;
		Osuma = false;
		loppu = false;
		asetaPain(false);
		addMouseListener(this);
		addMouseMotionListener(this);
		Tahtain = new tahtain(250,200);
		Target = new target(100);
		
	}
	//***OHJELMA ALKAA***
	public void start()
	{
		requestFocus();		//ei tarvitse napsauttaa hiirella Applet-ikkunaa, jotta applet käynnistyy
		Saie = new Thread(this);	//tehdään säie
		Saie.start();	//aloitetaan säie
	}
	
	public void run()
	{
		Thread tamaSaie = Thread.currentThread();
		while (Saie == tamaSaie && !loppu)	
		{
			loppu = Target.onkoPerilla();	//Jos joku suunnistaja pääsee rastille, loppu==true
			if(Pisteet>=3 && !loppu)
				loppu = Target2.onkoPerilla();
			if(Pisteet>=8 && !loppu)
				loppu = Target3.onkoPerilla();
			if(Pisteet>=14 && !loppu)
				loppu = Target4.onkoPerilla();
			
			if (!pysaytetty && !loppu)	//jos peli ei ole alkanut, pysaytetty=true
			{
				Tahtain.liiku(annaMX(),annaMY());	//tahtaimen liikutus
				
				if(annaPain())	//onko hiirennappia painettu
				{
					if(ammusMaara>=0)	//onko ammuksia
					{
						//riippuen pistemäärästä(pistemäärästä tiedetään suunnistaja saikeitten määrä), tarkistetaan osumat						 
						laukaus.play();
						Osuma = Tahtain.ammu(Target.annaX(), Target.annaY(), Target.annaOsanKoko(), annaPain());
						if(Osuma)
						{
							Pisteet++;
							Target = new target(100);
							hutiPlay=1;
						}
						
						if(Pisteet>=3)
						{
							if(maali==1)	{Target2 = new target(1); maali=2;}
							Osuma = Tahtain.ammu(Target2.annaX(), Target2.annaY(), Target2.annaOsanKoko(), annaPain());
							if(Osuma)
							{
								Pisteet++;
								Target2 = new target(1);
								hutiPlay=1;
							}
							
							if(Pisteet>=8)
							{
								if(maali==2)	{Target3 = new target(10); maali=3;}
								Osuma = Tahtain.ammu(Target3.annaX(), Target3.annaY(), Target3.annaOsanKoko(), annaPain());
								if(Osuma)
								{
									Pisteet++;
									Target3 = new target(10);
									hutiPlay=1;
								}
								
								if(Pisteet>=14)
								{
									if(maali==3)	{Target4 = new target(70);maali=4;}
									Osuma = Tahtain.ammu(Target4.annaX(), Target4.annaY(), Target4.annaOsanKoko(), annaPain());
									if(Osuma)
									{
										Pisteet++;
										Target4 = new target(70);
										hutiPlay=1;
									}
								}
							}
						}
						if(hutiPlay==0)
						{
							huti.play();
						}
						hutiPlay=0;
					}
					else	//ei ammuksia
					{
						klik.play();
					}
				}
				Target.liiku(nopeus);	//levelistä riippuva suunnistajien nopeus
				if(Pisteet>=3)
					Target2.liiku(nopeus);
				if(Pisteet>=8)
					Target3.liiku(nopeus);
				if(Pisteet>=14)
					Target4.liiku(nopeus);
				
				asetaPain(false);
				
			}
			if(Pisteet==3)	{ Level=2; nopeus=1.3; }
			else if(Pisteet==8)	{ Level=3; nopeus=1.6; }
			else if(Pisteet==14)	{ Level=4; nopeus=1.9; }
			else if(Pisteet==25)	{ Level=5; nopeus=2.3; }
			else if(Pisteet==35)	{ Level=6; nopeus=2.6; }
			else if(Pisteet==50)	{ Level=7; nopeus=3; }
			repaint();
			try	{ tamaSaie.sleep(40); 
			} catch (InterruptedException e) {} 
		}
	}
	
	public void update (Graphics kuvaruutu)
	{
		Font Fontti = new Font("TimesRoman", Font.BOLD, 15);
		puskuri.drawImage(tausta, 50, 50, this);
		if(!pysaytetty)
		{	
			if(Target.annaTarketti()==1)	//kumpi kahdesta kohteesta piirretään
				puskuri.drawImage(target1,(int)Target.annaX(),(int)Target.annaY(),this);
			else
				puskuri.drawImage(target2,(int)Target.annaX(),(int)Target.annaY(),this);
				
			if(loppu)	//onko peli ohi
				puskuri.drawImage(gameover,80,120,this);
			
			//levelin mukaan tulostetaan tietty määrä suunnistajia
			if(Level>=2)
			{
				if(Target2.annaTarketti()==1)
					puskuri.drawImage(target1,(int)Target2.annaX(),(int)Target2.annaY(),this);
				else
					puskuri.drawImage(target2,(int)Target2.annaX(),(int)Target2.annaY(),this);
			}
			
			if(Level>=3)
			{
				if(Target3.annaTarketti()==1)
					puskuri.drawImage(target1,(int)Target3.annaX(),(int)Target3.annaY(),this);
				else
					puskuri.drawImage(target2,(int)Target3.annaX(),(int)Target3.annaY(),this);
			}
			
			if(Level>=4)
			{
				if(Target4.annaTarketti()==1)
					puskuri.drawImage(target1,(int)Target4.annaX(),(int)Target4.annaY(),this);
				else
					puskuri.drawImage(target2,(int)Target4.annaX(),(int)Target4.annaY(),this);
			}
	
			if(!pysaytetty)	//jos peli käynnissä->piirretään tähtäin
				puskuri.drawImage(sight,(int)Tahtain.annaX(),(int)Tahtain.annaY(), this);
		}
		
		//kentän reunojen piirtäminen
		puskuri.drawImage(vasen_reuna, 0, 0, this);
		puskuri.drawImage(oikea_reuna, 400, 0, this);
		puskuri.drawImage(yla_reuna, 0, 0, this);
		puskuri.drawImage(ala_reuna, 0, 325, this);
		if(liekit>=0 && ammusMaara>=0)	//aseen suuliekit. pysyvät vähän aikaa, siksi silmukka
		{
			puskuri.drawImage(ala_reuna2, 0, 325, this);
			liekit--;
		}
		else	//jos ei ammuttu, ei suuliekkejä
			puskuri.drawImage(ala_reuna, 0, 325, this);
		
		
		ind=0;
		while(ind<ammusMaara)	//piirretään ammusten määrä
		{
			puskuri.drawImage(ammus, 300+(ind*20), 335, this);
			ind++;	
		}
		if(pysaytetty)
			puskuri.drawImage(aloita,150,150,this);
		if(!pysaytetty)
		{
			puskuri.setColor(new Color(255,0,0));
			puskuri.setFont(Fontti);
			puskuri.drawString("" + Pisteet, 160, 350);
			puskuri.drawString("" + Level, 160, 368);
		}
						
		paint(kuvaruutu);
	}
	
	public void paint(Graphics kuvaruutu)
	{
		if (puskuriKuva != null)
			kuvaruutu.drawImage(puskuriKuva, 0, 0, this);
	}
	
	public void mouseMoved(MouseEvent e)
	{
		asetaMX(e.getX());
		asetaMY(e.getY());
	}
	
	public void mouseClicked(MouseEvent e){}
	
	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		if(!pysaytetty)
		{
			if((x >=400) && (x <= 450) && (y >= 220) && (y <= 270))	//jos painettiin latausnapin päällä
			{
				lataus.play();
				ammusMaara=4;	//ammukset täyteen
			}
			else	//painettiin muualla, eli ammutaan
			{
				liekit=3;
				asetaPain(true);
				ammusMaara--;
			}
		}
		
		if(pysaytetty)	
		{
			if((x >=150) && (x <= 300) && (y >= 150) && (y <= 200))	//painettiin "Aloita" napin päällä
			{
				pysaytetty=false;
			}
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}

