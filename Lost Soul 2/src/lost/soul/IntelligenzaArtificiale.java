
package lost.soul;

import java.awt.Graphics;


public class IntelligenzaArtificiale
{
    int xGiocatore;
    int yGiocatore;
    
    int posZombieX[] = {-1,-1,-1,-1,-1}; //-1 = libera
    int posZombieY[] = {-1,-1,-1,-1,-1}; //-1 = libera
    int velocita;
    
    public IntelligenzaArtificiale()
    {
        
    }
    
    public IntelligenzaArtificiale(int nrZombie, int xZombie, int yZombie, int xGiocatore, int yGiocatore)
    {
         System.out.println("numero zombie: "+nrZombie);
        
        this.posZombieX[nrZombie] = xZombie;
        this.posZombieY[nrZombie] = yZombie;
        this.xGiocatore = xGiocatore;
        this.yGiocatore = yGiocatore;
        velocita = 10;
        
        System.out.println("xZombie iniziale: "+xZombie);
        System.out.println("yZombie iniziale: "+yZombie);
        System.out.println("xGiocatore iniziale: "+xGiocatore);
        System.out.println("yGiocatore iniziale: "+yGiocatore);
        
    }
    
    public void disegna(Graphics g, ThreadZombie zombieDaDisegnare) {
        g.drawImage(zombieDaDisegnare.getImg_zombie(), posZombieX[zombieDaDisegnare.getNrZombie()], posZombieY[zombieDaDisegnare.getNrZombie()], zombieDaDisegnare.getLarghezza(), zombieDaDisegnare.getAltezza(), null);
    }
    
    void spostaZombie(int nrZombie)
    {
        while(posZombieX[nrZombie] > xGiocatore)
        {

            posZombieX[nrZombie]-=velocita;
            System.out.println("xZombie: "+ posZombieX[nrZombie]);
            System.out.println("yZombie: "+ posZombieY[nrZombie]);
        }
        while(posZombieX[nrZombie] < xGiocatore)
        {
            posZombieX[nrZombie]+=velocita;
            System.out.println("xZombie: "+ posZombieX[nrZombie]);
            System.out.println("yZombie: "+ posZombieY[nrZombie]);
        }
        
        
        if(posZombieX[nrZombie] == xGiocatore)
        {
            while(posZombieY[nrZombie] > yGiocatore)
            {
                posZombieY[nrZombie]-=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
            }
            while(posZombieY[nrZombie] < yGiocatore)
            {
                posZombieY[nrZombie]+=velocita;
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
            }
            
            if(posZombieY[nrZombie] == yGiocatore)
            {
                System.out.println("BECCATO");
                //da disattivare lo stato dello zombie
                System.out.println("xZombie: "+ posZombieX[nrZombie]);
                System.out.println("yZombie: "+ posZombieY[nrZombie]);
            }
        }
    }
    
    void disegnaGiocatore(int x, int y)
    {
    }
    
}
