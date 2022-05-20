package lost.soul;

import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IntelligenzaArtificiale
{
    /*int xGiocatore;
    int yGiocatore;*/
    
    int posZombieX[] = {-1,-1,-1,-1,-1}; //-1 = libera
    int posZombieY[] = {-1,-1,-1,-1,-1}; //-1 = libera
    boolean statoZombie[] = {false, false, false, false, false}; //inizialmente tutti gli zombie partono non attivi
    boolean statoGiocatore = false;
    int velocita;
    int velocitaProiettile;
    int rallentatore;
    
    ThreadGiocatore g;
    private static ArrayList<ThreadProiettile> proiettili;
    
    public static ArrayList<ThreadProiettile> getProiettili()
    {
        return proiettili;
    }
    
    public static void setProiettili(ArrayList<ThreadProiettile> proiettili)
    {
        IntelligenzaArtificiale.proiettili = proiettili;
    }
    
    
    
    public IntelligenzaArtificiale(ThreadZombie arrayZombie[], ThreadGiocatore giocatore)
    {
        g=giocatore;
        proiettili = new ArrayList<>();
        for(int i=0; i<arrayZombie.length; i++)
        {
            //int nrZombie, int xZombie, int yZombie, int xGiocatore, int yGiocatore
            System.out.println("numero zombie: "+arrayZombie[i].getNrZombie());
            
            this.posZombieX[arrayZombie[i].getNrZombie()] = arrayZombie[i].getX();
            this.posZombieY[arrayZombie[i].getNrZombie()] = arrayZombie[i].getY();
            this.statoZombie[arrayZombie[i].getNrZombie()] = arrayZombie[i].isAttivo();
            this.statoGiocatore = giocatore.isAttivo();
            velocita = 5;
            velocitaProiettile = 10;
            rallentatore = 1000;
            
            /*System.out.println("xZombie iniziale: "+arrayZombie[i].getX());
            System.out.println("yZombie iniziale: "+arrayZombie[i].getY());
            System.out.println("xGiocatore iniziale: "+xGiocatore);
            System.out.println("yGiocatore iniziale: "+yGiocatore);*/
        }
    }
    
    //nei costruttori dell'intelligenza artificale è più comodo passare direttamente gli oggetti Zombie e Giocatore piuttosto che i singoli dati "piatti"
    /*public IntelligenzaArtificiale(ThreadZombie z0, ThreadGiocatore giocatore)
    {
    
    //int nrZombie, int xZombie, int yZombie, int xGiocatore, int yGiocatore
    System.out.println("numero zombie: "+z0.getNrZombie());
    
    this.posZombieX[z0.getNrZombie()] = z0.getX();
    this.posZombieY[z0.getNrZombie()] = z0.getY();
    this.statoZombie[z0.getNrZombie()] = z0.isAttivo();
    this.xGiocatore = giocatore.x;
    this.yGiocatore = giocatore.y;
    this.statoGiocatore = giocatore.isAttivo();
    velocita = 10;
    rallentatore = 100;
    
    System.out.println("xZombie iniziale: "+z0.getX());
    System.out.println("yZombie iniziale: "+z0.getY());
    System.out.println("xGiocatore iniziale: "+xGiocatore);
    System.out.println("yGiocatore iniziale: "+yGiocatore);
    
    }*/
    
    public void disegna(Graphics g, ThreadZombie zombieDaDisegnare) {
        g.drawImage(zombieDaDisegnare.getImg_zombie(), posZombieX[zombieDaDisegnare.getNrZombie()], posZombieY[zombieDaDisegnare.getNrZombie()], zombieDaDisegnare.getLarghezza(), zombieDaDisegnare.getAltezza(), null);
    }
    
    public void disegnaProiettili(Graphics2D g2d)
    {
        if (proiettili != null) {
            for (ThreadProiettile p : proiettili) {
                p.disegna(g2d);
            }
        }
    }
    
    
    
    void spostaZombie(int nrZombie)
    {
        controllaProiettile(nrZombie);

        //per i movimenti in diagonale - volendo si può rallentare un po' di più rispetto a quando cammina dritto
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie]!= g.getX() && posZombieY[nrZombie]!=g.getY())
        {
            //muoviti in diagonale in basso a dx
            while(posZombieX[nrZombie] < g.getX() && posZombieY[nrZombie] < g.getY())
            {
                posZombieX[nrZombie]+=velocita;
                posZombieY[nrZombie]+=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            ////muoviti in diagonale in basso a sx
            while(posZombieX[nrZombie] > g.getX() && posZombieY[nrZombie] < g.getY())
            {
                posZombieX[nrZombie]-=velocita;
                posZombieY[nrZombie]+=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            //muoviti in diagonale in alto a dx
            while(posZombieX[nrZombie] < g.getX() && posZombieY[nrZombie] > g.getY())
            {
                posZombieX[nrZombie]+=velocita;
                posZombieY[nrZombie]-=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            //muoviti in diagonale in alto a sx
            while(posZombieX[nrZombie] > g.getX() && posZombieY[nrZombie] > g.getY())
            {
                posZombieX[nrZombie]-=velocita;
                posZombieY[nrZombie]-=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
        }
        
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie] > g.getX())
        {
            //muoviti in orizzontale verso sx
            posZombieX[nrZombie]-=velocita;
            //System.out.println("xZombie: "+ posZombieX[nrZombie]);
            //System.out.println("yZombie: "+ posZombieY[nrZombie]);
            rallentaZombie(rallentatore);
            
        }
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie] < g.getX())
        {
            //muoviti in orizzontale verso dx
            posZombieX[nrZombie]+=velocita;
            //System.out.println("xZombie: "+ posZombieX[nrZombie]);
            //System.out.println("yZombie: "+ posZombieY[nrZombie]);
            rallentaZombie(rallentatore);
            
        }
        
        
        if(statoZombie[nrZombie] == true && posZombieX[nrZombie] == g.getX())
        {
            while(statoZombie[nrZombie] == true && posZombieY[nrZombie] > g.getY())
            {
                //muoviti in verticale verso il basso
                posZombieY[nrZombie]-=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            while(statoZombie[nrZombie] == true && posZombieY[nrZombie] < g.getY())
            {
                //muoviti in verticale verso l'alto
                posZombieY[nrZombie]+=velocita;
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            if(statoZombie[nrZombie] == true && posZombieY[nrZombie] == g.getY() && posZombieX[nrZombie] == g.getX())
            {
                System.out.println("BECCATO");
                //da disattivare lo stato dello zombie
                this.statoZombie[nrZombie] = false;
                //disattiviamo anche il giocatore
                this.statoGiocatore = false;
                Condivisa.stato=1;
                
                //System.out.println("xZombie: "+ posZombieX[nrZombie]);
                //System.out.println("yZombie: "+ posZombieY[nrZombie]);
            }
        }
    }
    
    void rallentaZombie(int miss)
    {
        try
        {
            sleep(miss);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(IntelligenzaArtificiale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aggiornaProiettile(ThreadProiettile proiettile) {
      
        
            double x2 = proiettile.getX()+ Math.cos(proiettile.getAngle()) * velocitaProiettile;
            double y2 = proiettile.getY() + Math.sin(proiettile.getAngle()) * velocitaProiettile;
            
            proiettile.setX((int)x2);
            proiettile.setY((int)y2);
            
            //se il proiettile è uscito fuori dalla finestra rimuovilo
            if ((proiettile.getY() + (g.altezza) < 0) || (proiettile.getY() + (g.altezza) > LostSoul.altezzaMondo) || (proiettile.getX() + (g.larghezza) < 0) || (proiettile.getX() + (g.larghezza) > LostSoul.larghezzaMondo)) {
                proiettile.setAttivo(false);
                proiettili.remove(this);
            }  
       
        
    }
    
    void spara(ThreadProiettile proiettile)
    {
        
        System.out.println("ANGOLO: "+proiettile.getAngle());
        proiettile.start();
        this.proiettili.add(proiettile);
    }

    void controllaProiettile(int nrZombie)
    {
        System.out.println("proiettili.size(): "+proiettili.size());
        for(int i=0; i<proiettili.size();i++)
        {
            System.out.println("POS ZOMBIE "+nrZombie+" :"+posZombieX[nrZombie]+" "+posZombieY[nrZombie]);
            System.out.println("POS PROIETTILE "+i+" :"+proiettili.get(i).getX()+" "+proiettili.get(i).getY());
            
            //invece di cercare l'uguaglianza cerca posZombieX-20<posPrx && posPrx<posZombieX+20
            
            if(posZombieX[nrZombie]-200<proiettili.get(i).getX() && proiettili.get(i).getX()<posZombieX[nrZombie]+200 && posZombieY[nrZombie]-200<proiettili.get(i).getY() && proiettili.get(i).getY()<posZombieY[nrZombie]+200f)
            {
                //statoZombie[nrZombie] = false;
                posZombieX[nrZombie] = 20;
                posZombieY[nrZombie] = 20;
            }
        }
    }
}
