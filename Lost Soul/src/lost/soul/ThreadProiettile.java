/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gatto
 */
public class ThreadProiettile extends Thread {

    private boolean attivo;
    private final int velocita = 10;
    private int altezza;
    private int larghezza;
    private int x, y;
    private BufferedImage image_proiettile;
    private LostSoul main;

    public ThreadProiettile() {
    }

    public ThreadProiettile(int altezza, int larghezza, int x, int y, BufferedImage image_proiettile, LostSoul main) {
        this.attivo = false;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.x = x;
        this.y = y;
        this.image_proiettile = image_proiettile;
        this.main = main;
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

    public void disegna(Graphics g) {
        g.drawImage(image_proiettile, x, y, larghezza, altezza, main);
    }

    private void aggiorna() {
        y -= velocita;
        if ((y + (altezza) < 0) || (y + (altezza) > 720) || (x + (larghezza) < 0) || (x + (larghezza) > 1200)) {
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
