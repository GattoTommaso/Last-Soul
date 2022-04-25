/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lost.soul;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author Gatto
 */
public class LostSoul extends Canvas implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    private static final int larghezza = 1200;
    private static final int altezza = 720;
    private static final String nome_gioco = "Lost Soul";

    BufferedImage sfondo = null;
    BufferedImage zombie = null;
    BufferedImage soldato = null;
    BufferedImage soldato2 = null;

    private boolean giocoAttivo = false;
    private ThreadZombie zombie1;
    private ThreadGiocatore giocatore;

    public LostSoul() {
        caricaRisorse();
        iniziaGioco();
    }

    public static void main(String[] args) {
        LostSoul gioco = new LostSoul();

        JFrame finestra_gioco = new JFrame(nome_gioco);
        Dimension dimensione_finestra = new Dimension(larghezza, altezza);
        finestra_gioco.setPreferredSize(dimensione_finestra);
        finestra_gioco.setMaximumSize(dimensione_finestra);
        finestra_gioco.setResizable(false);

        finestra_gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        finestra_gioco.add(gioco);
        finestra_gioco.addKeyListener(gioco);
        gioco.addMouseMotionListener(gioco);
        
        finestra_gioco.pack();
        finestra_gioco.setVisible(true);

        Thread ThreadGioco = new Thread(gioco);
        ThreadGioco.start();
    }

    private void iniziaGioco() {
        zombie1 = new ThreadZombie(150, 75, 75, 100, giocoAttivo, zombie);
        zombie1.start();
        giocatore = new ThreadGiocatore(150, 75, 75, 100, soldato);
    }

    private void caricaRisorse() {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        sfondo = loader.caricaImmagine("/images/sfondo.jpg");
        soldato = loader.caricaImmagine("/images/personaggio.jpg");
        zombie = loader.caricaImmagine("/images/GifZombie.gif");
        System.out.println("Risorse Caricate");
    }

    private void disegna() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        g.drawImage(sfondo, 0, 0, larghezza, altezza, this);

        zombie1.disegna(g);
        giocatore.disegna(g);
        /*AffineTransform at = AffineTransform.getTranslateInstance(100, 100);
        at.rotate(Math.toRadians(45), soldato.getHeight() / 2, soldato.getWidth() / 2);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(soldato, at, null);*/

        //g.dispose();
        buffer.show();
    }

    @Override
    public void run() {
        giocoAttivo = true;
        while (giocoAttivo) {
            disegna();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
                giocatore.spostaSu();
                break;
            case KeyEvent.VK_S:
                giocatore.spostaGiu();
                break;
            case KeyEvent.VK_A:
                giocatore.spostaSinistra();
                break;
            case KeyEvent.VK_D:
                giocatore.spostaDestra();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // ruotare e dare la x della rotazione del mouse 
        /*CaricatoreImmagini loader = new CaricatoreImmagini();
        soldato = loader.caricaImmagine("/images/GifZombie.gif");
        

        int posizione = (e.getPoint().x) - (giocatore.getWidth() / 2);
        giocatore.setX(posizione);*/
        /*int mX, mY, pX, pY, opp, adj;
        double angle = 0;
        mX = e.getPoint().x;
        mY = e.getPoint().y;
        pX = giocatore.getX() + 75;
        pY = giocatore.getY() + 75;
        opp = mX - pX;
        adj = mY - pY;

        double value = Math.atan((double) opp / (double) adj);
        angle = -(value + 2.2355708216122423);

        if (adj < 0) {
            angle = -Math.PI + angle;
        }
        System.out.println(angle);*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
