package lost.soul;

import java.awt.Graphics;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IntelligenzaArtificiale
{
    int xGiocatore;
    int yGiocatore;
    
    int posZombieX[] = {-1,-1,-1,-1,-1}; //-1 = libera
    int posZombieY[] = {-1,-1,-1,-1,-1}; //-1 = libera
    boolean statoZombie[] = {false, false, false, false, false}; //inizialmente tutti gli zombie partono non attivi
    boolean statoGiocatore = false;
    int velocita;
    int rallentatore;
    
    public IntelligenzaArtificiale(ThreadZombie z0, ThreadZombie z1)
    {
        
    }
    
    public IntelligenzaArtificiale(ThreadZombie arrayZombie[], ThreadGiocatore giocatore)
    {
        for(int i=0; i<arrayZombie.length; i++)
        {
            //int nrZombie, int xZombie, int yZombie, int xGiocatore, int yGiocatore
            System.out.println("numero zombie: "+arrayZombie[i].getNrZombie());

            this.posZombieX[arrayZombie[i].getNrZombie()] = arrayZombie[i].getX();
            this.posZombieY[arrayZombie[i].getNrZombie()] = arrayZombie[i].getY();
            this.statoZombie[arrayZombie[i].getNrZombie()] = arrayZombie[i].isAttivo();
            this.xGiocatore = giocatore.x;
            this.yGiocatore = giocatore.y;
            this.statoGiocatore = giocatore.isAttivo();
            velocita = 10;
            rallentatore = 100;

            System.out.println("xZombie iniziale: "+arrayZombie[i].getX());
            System.out.println("yZombie iniziale: "+arrayZombie[i].getY());
            System.out.println("xGiocatore iniziale: "+xGiocatore);
            System.out.println("yGiocatore iniziale: "+yGiocatore);
        }
    }
    
    //nei costruttori dell'intelligenza artificale è più comodo passare direttamente gli oggetti Zombie e Giocatore piuttosto che i singoli dati "piatti"
    public IntelligenzaArtificiale(ThreadZombie z0, ThreadGiocatore giocatore)
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
        
    }
    
    public void disegna(Graphics g, ThreadZombie zombieDaDisegnare) {
        g.drawImage(zombieDaDisegnare.getImg_zombie(), posZombieX[zombieDaDisegnare.getNrZombie()], posZombieY[zombieDaDisegnare.getNrZombie()], zombieDaDisegnare.getLarghezza(), zombieDaDisegnare.getAltezza(), null);
    }
    
    void spostaZombie(int nrZombie)
    {
        //per i movimenti in diagonale - volendo si può rallentare un po' di più rispetto a quando cammina dritto
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie]!= xGiocatore && posZombieY[nrZombie]!=yGiocatore)
        {
            //muoviti in diagonale in basso a dx
            while(posZombieX[nrZombie] < xGiocatore && posZombieY[nrZombie] < yGiocatore)
            {
                posZombieX[nrZombie]+=velocita;
                posZombieY[nrZombie]+=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            ////muoviti in diagonale in basso a sx
             while(posZombieX[nrZombie] > xGiocatore && posZombieY[nrZombie] < yGiocatore)
            {
                posZombieX[nrZombie]-=velocita;
                posZombieY[nrZombie]+=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            //muoviti in diagonale in alto a dx
             while(posZombieX[nrZombie] < xGiocatore && posZombieY[nrZombie] > yGiocatore)
            {
                posZombieX[nrZombie]+=velocita;
                posZombieY[nrZombie]-=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            //muoviti in diagonale in alto a sx
             while(posZombieX[nrZombie] > xGiocatore && posZombieY[nrZombie] > yGiocatore)
            {
                posZombieX[nrZombie]-=velocita;
                posZombieY[nrZombie]-=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
        }
        
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie] > xGiocatore)
        {
            //muoviti in orizzontale verso sx
            posZombieX[nrZombie]-=velocita;
            System.out.println("xZombie: "+ posZombieX[nrZombie]);
            System.out.println("yZombie: "+ posZombieY[nrZombie]);
            rallentaZombie(rallentatore);
            
        }
        while(statoZombie[nrZombie] == true && posZombieX[nrZombie] < xGiocatore)
        {
            //muoviti in orizzontale verso dx
            posZombieX[nrZombie]+=velocita;
            System.out.println("xZombie: "+ posZombieX[nrZombie]);
            System.out.println("yZombie: "+ posZombieY[nrZombie]);
            rallentaZombie(rallentatore);
            
        }
        
        
        if(statoZombie[nrZombie] == true && posZombieX[nrZombie] == xGiocatore)
        {
            while(statoZombie[nrZombie] == true && posZombieY[nrZombie] > yGiocatore)
            {
                //muoviti in verticale verso il basso
                posZombieY[nrZombie]-=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            while(statoZombie[nrZombie] == true && posZombieY[nrZombie] < yGiocatore)
            {
                //muoviti in verticale verso l'alto
                posZombieY[nrZombie]+=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
                rallentaZombie(rallentatore);
            }
            
            if(statoZombie[nrZombie] == true && posZombieY[nrZombie] == yGiocatore && posZombieX[nrZombie] == xGiocatore)
            {
                System.out.println("BECCATO");
                //da disattivare lo stato dello zombie
                this.statoZombie[nrZombie] = false;
                //disattiviamo anche il giocatore
                this.statoGiocatore = false;
                Condivisa.stato=1;
                
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
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
    
}
