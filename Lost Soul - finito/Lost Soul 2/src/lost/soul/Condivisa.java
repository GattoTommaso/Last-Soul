/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lost.soul;

/**
 *
 * @author dalessandro_davide
 */
public class Condivisa {
    
    static  int stato=0;    //stato = 0 -> gioco //stato = 1 -> morto //stato = 2 -> opzioni
    static int punteggioPartitaCorrente=0; //variabile da incrementare ogni volta che si ammazza uno zombie
    static int punteggiVecchiePartite[] = {0,0,0,0,0};
    
    //per ora faccio un metodo che randomicamente crea il punteggio a caso, solo per mostrarlo nella schermata finale
    public static void generaPunteggioPartitaCorrente()
    {
        punteggioPartitaCorrente = Utility.RandomRange(10, 100);
    }
    
}
