package Tetris;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class NTimer extends Timer{
    AudioInputStream audio, fin;
    Clip son, finJ;
    FloatControl fondo;
    
    public NTimer(int i, ActionListener al) {
        super(i, al);
        try{
            audio = AudioSystem.getAudioInputStream(new File("src/Tetris.wav").getAbsoluteFile());
            son = AudioSystem.getClip();
            son.open(audio);
            fin = AudioSystem.getAudioInputStream(new File("src/GameOver.wav").getAbsoluteFile());
            finJ = AudioSystem.getClip();
            finJ.open(fin);
            fondo = (FloatControl) son.getControl(FloatControl.Type.MASTER_GAIN);
            fondo.setValue(-40.0f);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Juego sin sonido");
       }
    }

    @Override
    public void stop() {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        son.stop();
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        son.start();
    }
    
    public void GameOver(){
        finJ.start();
    }
}
