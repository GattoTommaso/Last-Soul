/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gatto
 */
public class ThreadGiocatore extends Thread {

    int x;
    int y;
    int larghezza;
    int altezza;
    int velocita;
    private boolean attivo;
    BufferedImage img_giocatore;
    BufferedImage image_proiettile;
    private IntelligenzaArtificiale ai;
    LostSoul main;

    public ThreadGiocatore(int x, int y, int larghezza, int altezza, BufferedImage img_giocatore, IntelligenzaArtificiale ai, BufferedImage image_proiettile, LostSoul main, boolean attivo) {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.velocita = 20;
        this.img_giocatore = img_giocatore;
        this.ai = ai;
        this.image_proiettile = image_proiettile;
        this.main = main;
        this.attivo = attivo;

        LostSoul.proiettili = new ArrayList();
    }

    public void spara(double angle) {
        //int altezza, int larghezza, int x, int y, BufferedImage image_proiettile, LostSoul main, double angolo
        LostSoul.proiettili.add(new ThreadProiettile(20, 40, x + larghezza / 2, y + altezza / 2, image_proiettile, main, angle));
    }

    public IntelligenzaArtificiale getAi() {
        return ai;
    }

    public void setAi(IntelligenzaArtificiale ai) {
        this.ai = ai;
    }
    
    public boolean isAttivo()
    {
        return attivo;
    }

    public void setAttivo(boolean attivo)
    {
        this.attivo = attivo;
    }

    public int getWidth() {
        return larghezza;
    }

    public int getAltezza() {
        return altezza;
    }
    

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImg_giocatore() {
        return img_giocatore;
    }

    public void spostaDestra() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        if (x + (larghezza) < LostSoul.larghezzaMondo) {
            this.x += velocita;
        }
    }

    public void spostaSinistra() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        if (x + (larghezza) > 0) {
            this.x -= velocita;
        }

    }

    public void spostaSu() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        if (y + (altezza) > 0) {
            this.y -= velocita;
        }
    }

    public void spostaGiu() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        if (y + (altezza) < LostSoul.altezzaMondo) {
            this.y += velocita;
        }
    }

    public void disegna(Graphics g) {
        g.drawImage(img_giocatore, x, y, larghezza, altezza, null);
    }

    public Rectangle getBordi() {
        return new Rectangle(x, y, larghezza, altezza);
    }

    public void run() {
        attivo = true;
        System.out.println("stato giocatore " + attivo);

        while (attivo) {
            //sarebbe bello se fosse come lo zombie cioè controllato da un metodo dell'ai
            System.out.println("stato giocatore " + attivo);
            //ai.spostaGiocatore(); in questo metodo andrebbero messi i comandi che muovono il giocatore che ora sono ancora nella classe LOstSoul
            if (ai.statoGiocatore == false) {
                attivo = false;
            }
        }

        System.out.println("stato giocatore " + attivo);
    }
}
