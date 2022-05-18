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

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


/**
 *
 * @author Gatto
 */
public class LostSoul extends Canvas implements Runnable, KeyListener, MouseMotionListener, MouseListener {
    
    private static final int larghezza = 1200;
    private static final int altezza = 720;
    private static final String nome_gioco = "Lost Soul";
    
    BufferedImage sfondo = null;
    BufferedImage gameover = null;
    BufferedImage record = null;
    BufferedImage zombie = null;
    BufferedImage soldato = null;
    BufferedImage soldato2 = null;
    
    static JFrame finestra_gioco;
    
    private boolean giocoAttivo = false;
    private ThreadZombie zombie0;
    private ThreadZombie zombie1;
    private ThreadZombie zombie2;
    private ThreadZombie zombie3;
    private ThreadZombie zombie4;
    
    private ThreadZombie arrayZombie[];
    private ThreadGiocatore giocatore;
    private IntelligenzaArtificiale ai;
    
    public LostSoul() {
        caricaRisorse();
        iniziaGioco();
        
        
        
    }
    
    public static void main(String[] args) {
        LostSoul gioco = new LostSoul();
        
        finestra_gioco = new JFrame(nome_gioco);
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
        int x0 = 100;
        int y0= 600;
        
        int x1 = 700;
        int y1= 600;
        
        int x2 = 0;
        int y2= 0;
        
        int x3 = 1000;
        int y3= 700;
        
        int x4 = 60;
        int y4= 350;
        
        
        zombie0 = new ThreadZombie(x0, y0, 0, 75, 100, giocoAttivo, zombie);
        zombie1 = new ThreadZombie(x1, y1, 1, 75, 100, giocoAttivo, zombie);
        zombie2 = new ThreadZombie(x2, y2, 2, 75, 100, giocoAttivo, zombie);
        zombie3 = new ThreadZombie(x3, y3, 3, 75, 100, giocoAttivo, zombie);
        zombie4 = new ThreadZombie(x4, y4, 4, 75, 100, giocoAttivo, zombie);
        arrayZombie = new ThreadZombie[5];
        
        
        
        
        
        //decido che il giocatore in tutte le partite nasce sempre al centro della finestra
        //VOLENDO si può generare random la posizione del giocatore così ogni volta che viene avviato il gioco il giocatore nasce in un punto diverso
        giocatore = new ThreadGiocatore(larghezza/2, altezza/2, 75, 100, soldato, true, ai);
        
        //costruttore con un solo zombie
        //ai = new IntelligenzaArtificiale(zombie0, giocatore);
        
        
        //DA FARE CON UN CICLO in cui le posizioni iniziali degli zombie
        //vengono calcolate randomicamente  E NON A MANO
        arrayZombie[0] = zombie0;
        arrayZombie[1] = zombie1;
        arrayZombie[2] = zombie2;
        arrayZombie[3] = zombie3;
        arrayZombie[4] = zombie4;
        
        //costruttore con un array zombie
        ai = new IntelligenzaArtificiale(arrayZombie, giocatore);
        
        giocatore.setAi(ai);
        //a tutti gli zombie setto l'intelligenza artificiale
        for(int i=0; i<arrayZombie.length; i++)
        {
            arrayZombie[i].setAi(ai);
        }
        
        
        giocatore.start();
        
        //faccio lo start dei thread degli zombie
        for(int i=0; i<arrayZombie.length; i++)
        {
            arrayZombie[i].start();
        }
        
        
    }
    
    private void caricaRisorse() {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        sfondo = loader.caricaImmagine("/images/sfondo.jpg");
        gameover = loader.caricaImmagine("/images/gameover.jpg");
        record = loader.caricaImmagine("/images/record.jpg");
        soldato = loader.caricaImmagine("/images/personaggio.jpg");
        zombie = loader.caricaImmagine("/images/GifZombie.gif");
        System.out.println("Risorse Caricate");
    }
    
    //carico una schermata particolare e spengo i thread che non mi servono
    private void caricaGameOver(Graphics g)
    {
        // System.out.println("GAME OVER");
        g.drawImage(gameover, 0, 0, larghezza, altezza, this);
        g.setColor(Color.red);
        g.setFont(new Font("calibri", Font.BOLD, 60));
        g.drawString("GAME OVER", larghezza/3, altezza/2);
        
        //stampo a video il contenuto del punteggio corrente
        g.setFont(new Font("calibri", Font.BOLD, 30));
        String daStampare = "Punteggio partita corrente: "+Condivisa.punteggioPartitaCorrente;
        g.drawString(daStampare, larghezza/3, altezza/2+50);
        giocoAttivo = false;
        
        
    }
    
    private void caricaRecord(Graphics g) {
        g.drawImage(record, 0, 0, larghezza, altezza, this);
        g.setColor(Color.red);
        g.setFont(new Font("calibri", Font.BOLD, 20));
        String daStampare = "Record partite vecchie: " + System.lineSeparator();
        //creo la stringa che contiene i vari record scorrendo l'array dei punteggi
        for (int i = 0; i < Condivisa.punteggiVecchiePartite.length; i++) {
            if (Condivisa.punteggiVecchiePartite[i] != 0) {
                daStampare = daStampare + "PARTITA " + (i + 1) + ": " + Condivisa.punteggiVecchiePartite[i] + "\n";
            }
        }
        g.drawString(daStampare, larghezza / 3, altezza / 2 + 50);

    }
    
    
    private void disegna() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        
        if( Condivisa.stato == 0)
        {
            //disegna lo sfondo
            g.drawImage(sfondo, 0, 0, larghezza, altezza, this);
            //CICLO che scorre l'array di zombie e li disegna tutti
            for(int i=0; i<arrayZombie.length; i++)
            {
                ai.disegna(g, arrayZombie[i]);
            }
            giocatore.disegna(g);
            
        }
        else if ( Condivisa.stato ==1)
        {
            //scoreboard
            
            g.fillRect(0, 0, larghezza, altezza);
            
            //la generazione del punteggio casuale va rimossa una volta che funziona quello vero
            Condivisa.generaPunteggioPartitaCorrente();
            
            for(int i=0; i<Condivisa.punteggiVecchiePartite.length; i++)
            {
                if(Condivisa.punteggiVecchiePartite[i]==0)
                {
                    Condivisa.punteggiVecchiePartite[i] = Condivisa.punteggioPartitaCorrente;
                    break;
                }
            }
            
            
            caricaGameOver(g);
            
            //DA RIMUOVERE
            g.fillRect(0, 0, larghezza, altezza);
            caricaRecord(g);
        }
        else if(Condivisa.stato == 2)
        {
            g.fillRect(0, 0, larghezza, altezza);
            caricaRecord(g);
        }
        
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
