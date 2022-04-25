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
public class ThreadGiocatore{

    int x;
    int y;
    int width;
    int height;
    int velocita;
    BufferedImage img_giocatore;

    public ThreadGiocatore(int x, int y, int width, int height, BufferedImage img_giocatore) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocita = 20;
        this.img_giocatore = img_giocatore;
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
        this.x += velocita;
    }

    public void spostaSinistra() {
        this.x -= velocita;
    }

    public void spostaSu() {
        this.y -= velocita;
    }

    public void spostaGiu() {
        this.y += velocita;
    }

    public void disegna(Graphics g) {
        g.drawImage(img_giocatore, x, y, width, height, null);
    }
}
