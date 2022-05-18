/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Gatto
 */
public class ThreadMusica extends Thread {

    private int scelta;
    private boolean GAMEOVER;

    public ThreadMusica(int scelta) {
        this.scelta = scelta;
        GAMEOVER = false;
    }

    @Override
    public void run() {
        while (true) {
            //prende la directory corrente fino a prima del src
            String directoryName = SceltaMusica();

            //prende il file con la canzone
            File file = new File(directoryName);
            System.out.println(file);

            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(file);

            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ThreadMusica.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadMusica.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();

            } catch (LineUnavailableException ex) {
                Logger.getLogger(ThreadMusica.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                clip.open(audioStream);

            } catch (LineUnavailableException ex) {
                Logger.getLogger(ThreadMusica.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadMusica.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            //lo manda in loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            int CanzoneCorrente = scelta;
            while (GAMEOVER == false && CanzoneCorrente == scelta) {
                //solo lo start non lo fa andare in loop
                clip.start();
            }

            //ferma la canzone
            clip.stop();
            //se si ha perso fa partire la canzone del game over
            if (GAMEOVER == true) {
                //musica game over
                int temp=scelta;
                setScelta(1);
                directoryName = SceltaMusica();

                //prende il file con la canzone
                file = new File(directoryName);
                System.out.println(file);

                audioStream = null;
                try {
                    audioStream = AudioSystem.getAudioInputStream(file);

                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(ThreadMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                 clip = null;
                try {
                    clip = AudioSystem.getClip();

                } catch (LineUnavailableException ex) {
                    Logger.getLogger(ThreadMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    clip.open(audioStream);

                } catch (LineUnavailableException ex) {
                    Logger.getLogger(ThreadMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                clip.start();
                try {
                    sleep(6000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadMusica.class.getName()).log(Level.SEVERE, null, ex);
                }
                clip.stop();
                setScelta(temp);
                GAMEOVER = false;
            }
        }
    }

    //prende la directory della canzone giusta in base alla scelta
    public String SceltaMusica() {
        String directoryName = "";
        switch (scelta) {
            case (1):
                //doom
                //prende la directory corrente fino a prima del src
                directoryName = System.getProperty("user.dir") + "\\src\\musicaprova\\musica\\Doom.wav";
                break;
            case (2):
                //Kesha
                //prende la directory corrente fino a prima del src
                directoryName = System.getProperty("user.dir") + "\\src\\musicaprova\\musica\\Kesha.wav";
                break;
            default:
                System.out.println("Not a valid response");
        }
        return directoryName;
    }

    public void setGAMEOVER(boolean GAMEOVER) {
        this.GAMEOVER = GAMEOVER;
        System.out.println("cambiato");
    }

    public void setScelta(int scelta) {
        this.scelta = scelta;
        System.out.println("cambiato");
    }
}
