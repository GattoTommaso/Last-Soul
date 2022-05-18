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
    private LostSoul main;
    private double angle;

    public ThreadProiettile() {
    }

    public ThreadProiettile(int altezza, int larghezza, int x, int y, BufferedImage image_proiettile, LostSoul main, double angle) {
        this.attivo = false;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.x = x;
        this.y = y;
        this.image_proiettile = image_proiettile;
        this.main = main;
        this.angle = angle;
        start();
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

    public void disegna(Graphics2D g) {
        AffineTransform at = AffineTransform.getTranslateInstance(x - 75, y - 75);
        at.rotate(angle, altezza / 2, larghezza / 2);
        g.drawImage(image_proiettile, x, y, larghezza, altezza, main);
        //g.drawImage(image_proiettile, at, null);
    }

    private void aggiorna() {
        x += Math.cos(angle) * velocita;
        y += Math.sin(angle) * velocita;

        if ((y + (altezza) < 0) || (y + (altezza) > LostSoul.altezzaMondo) || (x + (larghezza) < 0) || (x + (larghezza) > LostSoul.larghezzaMondo)) {
            this.setAttivo(false);
            LostSoul.proiettili.remove(this);
        }
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public void run() {
        attivo = true;
        while (attivo) {
            aggiorna();

            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadProiettile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
