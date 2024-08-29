package Tetris;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Records {

    public Records() {
    }
    
    public void Poner(String nombre, int puntuacion) {
        try{ //Nombre		Puntuación
                
            File arch = new File("Records.txt"); 
            
            FileWriter escribir = new FileWriter(arch.getAbsoluteFile(), true);
            String text = "\n" + nombre + "\t\t" + puntuacion;
            escribir.write(text);
            escribir.close();
            
            
        } catch (IOException ioe){
            System.out.println("Ocurrio un error de E/S");
        } 
    }
}
