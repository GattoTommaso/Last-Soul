/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gatto
 */
public class ThreadZombie extends Thread {

    
    private int x;
    private int y;
    private int larghezza;
    private int altezza;
    private int nrZombie;
    private boolean attivo;
    BufferedImage img_zombie;
    private IntelligenzaArtificiale ai;

    public ThreadZombie(int x, int y, int nrZombie, int larghezza, int altezza, boolean attivo, BufferedImage img_zombie) {
        this.x = x;
        this.y = y;
        this.nrZombie = nrZombie;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.attivo = true;
        this.img_zombie = img_zombie;
    }
    
    public IntelligenzaArtificiale getAi()
    {
        return ai;
    }
    
    public void setAi(IntelligenzaArtificiale ai)
    {
        this.ai = ai;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getLarghezza()
    {
        return larghezza;
    }

    public void setLarghezza(int larghezza)
    {
        this.larghezza = larghezza;
    }

    public int getAltezza()
    {
        return altezza;
    }

    public void setAltezza(int altezza)
    {
        this.altezza = altezza;
    }

    public int getNrZombie()
    {
        return nrZombie;
    }

    public void setNrZombie(int nrZombie)
    {
        this.nrZombie = nrZombie;
    }

    public boolean isAttivo()
    {
        return attivo;
    }

    public void setAttivo(boolean attivo)
    {
        this.attivo = attivo;
    }

    public BufferedImage getImg_zombie()
    {
        return img_zombie;
    }

    public void setImg_zombie(BufferedImage img_zombie)
    {
        this.img_zombie = img_zombie;
    }

    
    

    @Override
    public void run() {
        attivo=true;
        
        
        while(attivo)
        {
            ai.spostaZombie(nrZombie);
            
            /*
            try {
                sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadZombie.class.getName()).log(Level.SEVERE, null, ex);
            }
           */
        }
    }
}
