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

    //DA FARE: mi raccomando gli attributi interni di una classe sempre private
    //quindi di conseguenza vanno creati i metodi get e set
    int x;
    int y;
    int width;
    int height;
    int velocita;
    private boolean attivo;
    BufferedImage img_giocatore;
    private IntelligenzaArtificiale ai;

    public ThreadGiocatore(int x, int y, int width, int height, BufferedImage img_giocatore, boolean attivo, IntelligenzaArtificiale ai) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocita = 20;
        this.img_giocatore = img_giocatore;
        this.ai = ai;
        this.attivo = attivo;
    }

    public IntelligenzaArtificiale getAi()
    {
        return ai;
    }

    public void setAi(IntelligenzaArtificiale ai)
    {
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
        attivo = true;
        System.out.println("stato giocatore "+attivo);
        
        while(attivo)
        {
            //sarebbe bello se fosse come lo zombie cioè controllato da un metodo dell'ai
             System.out.println("stato giocatore "+attivo);
            //ai.spostaGiocatore(); in questo metodo andrebbero messi i comandi che muovono il giocatore che ora sono ancora nella classe LOstSoul
            if(ai.statoGiocatore == false)
            {
                attivo = false;
            }
        }
        
       System.out.println("stato giocatore "+attivo);
        
        
    }
}
