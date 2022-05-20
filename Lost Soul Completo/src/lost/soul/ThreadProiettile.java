/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gatto
 */
public class ThreadProiettile extends Thread {

    private boolean attivo;
    private final int velocita = 30;
    private int altezza;
    private int larghezza;
    private int x, y;
    private BufferedImage image_proiettile;
    private IntelligenzaArtificiale ai;
    private LostSoul main;
    private double angle;

    public ThreadProiettile() {
    }

    public ThreadProiettile(int altezza, int larghezza, int x, int y, BufferedImage image_proiettile, double angle) {
        
        this.attivo = false;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.x = x;
        this.y = y;
        this.image_proiettile = image_proiettile;
        this.angle = angle;
    }

    public IntelligenzaArtificiale getAi()
    {
        return ai;
    }

    public void setAi(IntelligenzaArtificiale ai)
    {
        this.ai = ai;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAltezza() {
        return altezza;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public Rectangle getBordi() {
        return new Rectangle(x, y, larghezza, altezza);
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }
    
    

    public void disegna(Graphics2D g) {
        AffineTransform at = AffineTransform.getTranslateInstance(x - 75, y - 75);
        at.rotate(angle, altezza / 2, larghezza / 2);
        g.drawImage(image_proiettile, x, y, larghezza, altezza, main);
        //g.drawImage(image_proiettile, at, null);
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public void run() {
        attivo = true;
        while (attivo) {
            ai.aggiornaProiettile(this);

            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadProiettile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
