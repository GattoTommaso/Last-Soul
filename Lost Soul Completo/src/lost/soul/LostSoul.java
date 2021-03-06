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

import java.util.ArrayList;


/**
 *
 * @author Gatto
 */
public class LostSoul extends Canvas implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    public static final int larghezzaMondo = 1200;
    public static final int altezzaMondo = 720;
    private static final String nome_gioco = "Lost Soul";

    BufferedImage sfondo = null;
    BufferedImage gameover = null;
    BufferedImage record = null;
    BufferedImage zombie = null;
    BufferedImage soldato = null;
    BufferedImage proiettile = null;

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

    
    private ThreadMusica musica;

    private int xMouse;
    private int yMouse;
    private AffineTransform at;
    private double angle = 0;

    public LostSoul() {
        caricaRisorse();
        iniziaGioco();

    }

    public static void main(String[] args) {
        LostSoul gioco = new LostSoul();

        finestra_gioco = new JFrame(nome_gioco);
        Dimension dimensione_finestra = new Dimension(larghezzaMondo, altezzaMondo);
        finestra_gioco.setPreferredSize(dimensione_finestra);
        finestra_gioco.setMaximumSize(dimensione_finestra);
        finestra_gioco.setResizable(false);

        finestra_gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gioco.addMouseMotionListener(gioco);
        gioco.addMouseListener(gioco);
        
        

        finestra_gioco.addKeyListener(gioco);
        //gioco.addMouseMotionListener(gioco);

        finestra_gioco.addMouseListener(gioco);
        finestra_gioco.addMouseMotionListener(gioco);
        //gioco.addMouseListener(gioco);
        
        finestra_gioco.add(gioco);

        /*finestra_gioco.addMouseMotionListener(gioco);
        gioco.addMouseMotionListener(gioco);*/
        finestra_gioco.pack();
        finestra_gioco.setVisible(true);

        Thread ThreadGioco = new Thread(gioco);
        ThreadGioco.start();
    }

    private void iniziaGioco() {
        int x0 = 100;
        int y0 = 600;

        int x1 = 700;
        int y1 = 600;

        int x2 = 0;
        int y2 = 0;

        int x3 = 1000;
        int y3 = 700;

        int x4 = 60;
        int y4 = 350;

        zombie0 = new ThreadZombie(x0, y0, 0, 75, 100, giocoAttivo, zombie);
        zombie1 = new ThreadZombie(x1, y1, 1, 75, 100, giocoAttivo, zombie);
        zombie2 = new ThreadZombie(x2, y2, 2, 75, 100, giocoAttivo, zombie);
        zombie3 = new ThreadZombie(x3, y3, 3, 75, 100, giocoAttivo, zombie);
        zombie4 = new ThreadZombie(x4, y4, 4, 75, 100, giocoAttivo, zombie);
        arrayZombie = new ThreadZombie[5];
        
        //decido che il giocatore in tutte le partite nasce sempre al centro della finestra
        //VOLENDO si pu?? generare random la posizione del giocatore cos?? ogni volta che viene avviato il gioco il giocatore nasce in un punto diverso
        giocatore = new ThreadGiocatore(larghezzaMondo / 2, altezzaMondo / 2, 75, 100, soldato, ai, proiettile, this, true);

        //DA FARE CON UN CICLO in cui le posizioni iniziali degli zombie
        //vengono calcolate randomicamente  E NON A MANO
        arrayZombie[0] = zombie0;
        arrayZombie[1] = zombie1;
        arrayZombie[2] = zombie2;
        arrayZombie[3] = zombie3;
        arrayZombie[4] = zombie4;

        //costruttore con un array zombie
        ai = new IntelligenzaArtificiale(arrayZombie, giocatore);

        musica = new ThreadMusica(1);
        musica.start();
        for (int i = 0; i < arrayZombie.length; i++) {
            arrayZombie[i].setAi(ai);
        }
        
        giocatore.setAi(ai);
        
        
        

        giocatore.start();
        for (int i = 0; i < arrayZombie.length; i++) {
            arrayZombie[i].start();
        }

    }

    private void caricaRisorse() {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        sfondo = loader.caricaImmagine("/images/sfondo.jpg");
        gameover = loader.caricaImmagine("/images/gameover.jpg");
        record = loader.caricaImmagine("/images/record.jpg");
        soldato = loader.caricaImmagine("/images/soldier.png");
        zombie = loader.caricaImmagine("/images/GifZombie.gif");
        proiettile = loader.caricaImmagine("/images/Bullet.png");

        System.out.println("Risorse Caricate");
    }

    //DA CHIEDERE: come cambiare la schermata 
    private void caricaGameOver(Graphics g) {
        // System.out.println("GAME OVER");
        g.drawImage(gameover, 0, 0, larghezzaMondo, altezzaMondo, this);
        g.setColor(Color.red);
        g.setFont(new Font("calibri", Font.BOLD, 60));
        g.drawString("GAME OVER", larghezzaMondo / 3, altezzaMondo / 2);

        //stampo a video il contenuto del punteggio corrente
        g.setFont(new Font("calibri", Font.BOLD, 30));
        String daStampare = "Punteggio partita corrente: " + Condivisa.punteggioPartitaCorrente;
        g.drawString(daStampare, larghezzaMondo / 3, altezzaMondo / 2 + 50);
        giocoAttivo = false;
        
    }

    private void caricaRecord(Graphics g) {
        g.drawImage(record, 0, 0, larghezzaMondo, altezzaMondo, this);
        g.setColor(Color.red);
        g.setFont(new Font("calibri", Font.BOLD, 20));
        String daStampare = "Record partite vecchie: " + System.lineSeparator();
        //creo la stringa che contiene i vari record scorrendo l'array dei punteggi
        for (int i = 0; i < Condivisa.punteggiVecchiePartite.length; i++) {
            if (Condivisa.punteggiVecchiePartite[i] != 0) {
                daStampare = daStampare + "PARTITA " + (i + 1) + ": " + Condivisa.punteggiVecchiePartite[i] + "\n";
            }
        }
        g.drawString(daStampare, larghezzaMondo / 3, altezzaMondo / 2 + 50);

    }

    private void disegna() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        //disegna lo sfondo
        if (Condivisa.stato == 0) {
            //disegna lo sfondo
            g.drawImage(sfondo, 0, 0, larghezzaMondo, altezzaMondo, this);
            //CICLO che scorre l'array di zombie e li disegna tutti
            for (int i = 0; i < arrayZombie.length; i++) {
                ai.disegna(g, arrayZombie[i]);
            }
            //giocatore.disegna(g);

        } else if (Condivisa.stato == 1) {
            //scoreboard            
            g.fillRect(0, 0, larghezzaMondo, altezzaMondo);
            //la generazione del punteggio casuale va rimossa una volta che funziona quello vero
            Condivisa.generaPunteggioPartitaCorrente();
            for (int i = 0; i < Condivisa.punteggiVecchiePartite.length; i++) {
                if (Condivisa.punteggiVecchiePartite[i] == 0) {
                    Condivisa.punteggiVecchiePartite[i] = Condivisa.punteggioPartitaCorrente;
                    break;
                }
            }
            caricaGameOver(g);
            caricaRecord(g);
        } else if (Condivisa.stato == 2) {
            g.fillRect(0, 0, larghezzaMondo, altezzaMondo);
            caricaRecord(g);
        }

        //================================
        //giocatore.disegna(g);
        
        ai.disegnaProiettili(g2d);

        //ruota
        at = AffineTransform.getTranslateInstance(giocatore.getX() - 75, giocatore.getY() - 75);
        at.rotate(angle, giocatore.getImg_giocatore().getHeight() / 2, giocatore.getImg_giocatore().getWidth() / 2);

        //g2d.drawImage(giocatore.getImg_giocatore(), giocatore.getX(), giocatore.getY(), giocatore.getWidth(), giocatore.getAltezza(), this);
        g2d.drawImage(giocatore.getImg_giocatore(), at, null);
        g.dispose();
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
            case KeyEvent.VK_F:
                giocatore.spara(angle);
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

        int mX, mY, pX, pY, opp, adj;

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
        //System.out.println(angle);
    }

   

    //il main chiede all'AI se tutti gli stati degli zombie sono a false, se s?? comunica true come gameOver
    private boolean verificaGameOver() {

        for (int i = 0; i < ai.statoZombie.length; i++) {
            //se trova almeno uno zombie con stato=true non ?? gameover
            if (ai.statoZombie[i] == true) {
                return false; //gameover false
            }
        }

        //se sono arrivato qui senza entrare nell'if significa che tutti gli zombie sono a false -> GAMEOVER true
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
