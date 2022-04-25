/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lost.soul;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Gatto
 */
public class CaricatoreImmagini {

    BufferedImage immagine;

    public BufferedImage caricaImmagine(String posizione) {
        try {
            immagine = ImageIO.read(getClass().getResource(posizione));
        } catch (IOException ex) {
            System.out.println("immagine alla poszione" + posizione + "caricata");
            Logger.getLogger(CaricatoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
        }
        return immagine;
    }

}
