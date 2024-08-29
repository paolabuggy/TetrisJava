package Tetris;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Tetris extends JFrame {
    public Tetris() {
        setSize(400, 600);
        setTitle("Tetris");
        setIconImage(new ImageIcon(getClass().getResource("IconTetris.png")).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Tablero tab = new Tablero(this);
        add(tab);
        tab.start();
        setVisible(true);
   }

    public static void main(String[] args) {
        Tetris empiezaJuego = new Tetris();
    } 
}    

