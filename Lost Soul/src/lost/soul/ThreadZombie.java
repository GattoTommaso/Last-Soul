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
    private boolean attivo;
    BufferedImage img_zombie;

    public ThreadZombie(int x, int y, int larghezza, int altezza, boolean attivo, BufferedImage img_zombie) {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.attivo = true;
        this.img_zombie = img_zombie;
    }

    @Override
    public void run() {
        attivo=true;
        while(attivo){
            aggiorna();
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadZombie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void aggiorna() {
        x++;
    }

    public void disegna(Graphics g) {
        g.drawImage(img_zombie, x, y, larghezza, altezza, null);
    }
}
