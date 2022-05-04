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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gatto
 */
public class ThreadGiocatore extends Thread{

    int x;
    int y;
    int width;
    int height;
    int velocita;
    BufferedImage img_giocatore;
    private IntelligenzaArtificiale ai;

    public ThreadGiocatore(int x, int y, int width, int height, BufferedImage img_giocatore, IntelligenzaArtificiale ai) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocita = 20;
        this.img_giocatore = img_giocatore;
        this.ai = ai;
    }

    public IntelligenzaArtificiale getAi()
    {
        return ai;
    }

    public void setAi(IntelligenzaArtificiale ai)
    {
        this.ai = ai;
    }
    
    

    public int getWidth() {
        return width;
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
    
    
    
    public void spostaDestra() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        this.x += velocita;
        this.ai.xGiocatore=this.x;
    }

    public void spostaSinistra() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        this.x -= velocita;
        this.ai.xGiocatore=this.x;
    }

    public void spostaSu() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        this.y -= velocita;
        this.ai.yGiocatore=this.y;
    }

    public void spostaGiu() {
        //ogni volta che il giocatore modifica la propria posizione lo deve comunicare alla corrispondente variabile della AI
        this.y += velocita;
        this.ai.yGiocatore=this.y;
    }

    public void disegna(Graphics g) {
        g.drawImage(img_giocatore, x, y, width, height, null);
    }
    
    public void run()
    {
        //ai.disegnaGiocatore(x, y);
    }
}
