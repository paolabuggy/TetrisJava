package Tetris;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import Tetris.FormasT.Tetrominos;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Tablero extends JPanel implements ActionListener {
    final int anchoT = 10;
    final int largoT = 20;
    NTimer timer;
    Records record;
    boolean maxLineas = false;
    boolean empieza = false;
    boolean pausa = false;
    int nLineasElimin = 0;
    int corx = 0;
    int cory = 0;
    FormasT tetroActual;
    Tetrominos[] vecTablero;
    public Tablero(Tetris parent) {
       setFocusable(true);
       tetroActual = new FormasT();
       timer = new NTimer(500, this);
       record = new Records();
       timer.start(); 
       vecTablero = new Tetrominos[anchoT * largoT];
       addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
       imprimeCuadri();  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (maxLineas) {
            maxLineas = false;
            sigFigura();
        } else {
            menosLinea();
        }
    }

    int anchoCFig(){ 
        return (int) getSize().getWidth() / anchoT; 
    }
    
    int largoCFig(){ 
        return (int) getSize().getHeight() / largoT; 
    }
    
    Tetrominos figuraActual(int x, int y){ 
        return vecTablero[(y * anchoT) + x]; 
        }

    public void start()
    {
        if (pausa)
            return;
        empieza = true;
        maxLineas = false;
        nLineasElimin = 0;
        imprimeCuadri();
        sigFigura();
        timer.start();
    }

    private void imprimeCuadri()
    {
        for (int i = 0; i < largoT * anchoT; ++i)
            vecTablero[i] = Tetrominos.ninguna;
    }
    
    private void dibujaCuadro(Graphics g, int x, int y, Tetrominos fig)
    {
        Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 204), 
            new Color(70, 193, 193), new Color(105, 210, 0), 
            new Color(255, 120, 190), new Color(251, 125, 0), 
            new Color(224, 12, 12), new Color(250, 213, 1)
        };


        Color color = colors[fig.ordinal()]; 
        g.setColor(color);

        //OPCIÓN 1 EXTRA RARA
        g.fillRoundRect(x-2, y-2, anchoCFig()+2, largoCFig()+2, 10, 10);
        g.setColor(color.darker());
        g.drawRect(x+1, y+1, anchoCFig()-5, largoCFig()-5);
        g.setColor(color.brighter());
        g.fillRect(x+2, y+2, anchoCFig()-6, largoCFig()-6);    //versión 1
        //g.fillRect(x+1, y+1, anchoCFig()-5, largoCFig()-5);  //versión 2
        
        /*
        //OPCIÓN 2 TIPO 3D GLASS
        g.fillRect(x , y, anchoCFig()-2, largoCFig()-2); 
        g.setColor(color.darker());
        g.drawRect(x, y, anchoCFig(), largoCFig());
        */
        
        /*
        //OPCIÓN 3 TIPO 3D GLASS PRO CENTRADO
        g.fillRect(x+3, y+3, anchoCFig()-5, largoCFig()-5); 
        g.setColor(color.darker());
        g.drawRect(x, y, anchoCFig(), largoCFig());
        */
        
        /*
        //OPCIÓN 4 SIN CONTORNO
        g.fillRect(x , y, anchoCFig()-2, largoCFig()-2); 
        */
        
        /*
        //OPCIÓN 5 ESQUINAS SUAVIZADAS
        g.fillRoundRect(x, y, anchoCFig(), largoCFig(), 25, 25);
        g.setColor(color.darker());
        g.drawRoundRect(x, y, anchoCFig(), largoCFig(),25,25);
        */
    }
    
    private void hacerPausa()
    {
        if (!empieza)
            return;
        pausa = !pausa;
        if (pausa) {
            timer.stop();
        } else {
            timer.start();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g)
    { 
        super.paint(g);
        Dimension tam = getSize();
        int tope = (int) tam.getHeight() - largoT * largoCFig();
        for (int i = 0; i < largoT; ++i) {
            for (int j = 0; j < anchoT; ++j) {
                Tetrominos fig = figuraActual(j, largoT - i - 1);
                if (fig != Tetrominos.ninguna)
                    dibujaCuadro(g, 0 + j * anchoCFig(),tope + i * largoCFig(), fig);
            }
        }

        if (tetroActual.getFigura() != Tetrominos.ninguna) {
            for (int i = 0; i < 4; ++i) {
                int x = corx + tetroActual.x(i);
                int y = cory - tetroActual.y(i);
                dibujaCuadro(g, 0 + x * anchoCFig(),
                           tope + (largoT - y - 1) * largoCFig(),tetroActual.getFigura());
            }
        }
    }

    private boolean mueve(FormasT sigFigura, int auxx, int auxy) 
    {  
        for (int i = 0; i < 4; ++i) {
            int x = auxx + sigFigura.x(i);
            int y = auxy - sigFigura.y(i);
            if (x < 0 || x >= anchoT || y < 0 || y >= largoT)
                return false;
            if (figuraActual(x, y) != Tetrominos.ninguna)
                return false;
        }

        tetroActual = sigFigura;
        corx = auxx;
        cory = auxy;
        repaint();
        return true;
    }
 
    private void mueveAbajo()
    {
        int auxy = cory;
        while (auxy > 0) {
            if (!mueve(tetroActual, corx, auxy - 1))
                break;
            --auxy;
        }
        fondoFigura();
    }

    private void menosLinea()
    {
        if (!mueve(tetroActual, corx, cory - 1))
            fondoFigura();
    }
 
    private void fondoFigura()
    {
        for (int i = 0; i < 4; ++i) {
            int x = corx + tetroActual.x(i);
            int y = cory - tetroActual.y(i);
            vecTablero[(y * anchoT) + x] = tetroActual.getFigura();
        }
        eliminaLineas();
        if (!maxLineas)
            sigFigura();
    }

    private void sigFigura()
    {
        tetroActual.setFigRandom();
        corx = anchoT / 2 + 1;
        cory = largoT - 1 + tetroActual.minY();

        if (!mueve(tetroActual, corx, cory)) {
            FinJuego();
        }
    }
    
    private void eliminaLineas()
    {
        int nLEliminadas = 0;

        for (int i = largoT - 1; i >= 0; --i) {
            boolean lineaCompleta = true;

            for (int j = 0; j < anchoT; ++j) {
                if (figuraActual(j, i) == Tetrominos.ninguna) {
                    lineaCompleta = false;
                    break;
                }
            }

            if (lineaCompleta) {
                ++nLEliminadas;
                for (int k = i; k < largoT - 1; ++k) {
                    for (int j = 0; j < anchoT; ++j)
                         vecTablero[(k * anchoT) + j] = figuraActual(j, k + 1);
                }
            }
        }

        if (nLEliminadas > 0) {
            nLineasElimin += nLEliminadas;
            maxLineas = true;
            tetroActual.setFigura(Tetrominos.ninguna);
            repaint();
        }
     }

    public void FinJuego(){
        tetroActual.setFigura(Tetrominos.ninguna);
        timer.stop();
        empieza = false;
            
        timer.GameOver();
            
        String nombre = JOptionPane.showInputDialog(null, "Ingresa tu nombre: ", "Game Over", JOptionPane.NO_OPTION);
        record.Poner(nombre, nLineasElimin);
        int opc = JOptionPane.showConfirmDialog(null, nombre + " obtuviste puntuacion de: " + nLineasElimin +"\nDeseas continuar?");
        if(opc == 0){
            String[] args = {""};
            Tetris.main(args);
        }
        if(opc == 1){
            System.exit(0);
        }
    }
  
    private void formKeyPressed(java.awt.event.KeyEvent evt) {                                
        // TODO add your handling code here:
        char tecla = evt.getKeyChar();
        int tec = evt.getKeyCode();
        if (!empieza || tetroActual.getFigura() == Tetrominos.ninguna) 
            return;
        if(tecla=='P' || tecla=='p'){
            hacerPausa();
            return;
        }
        if (pausa)
            return;
        if(tecla=='D' || tecla=='d')
            menosLinea();
        if(tecla == ' ')
            mueveAbajo();
        if(tec == 37) //izquierda
            mueve(tetroActual, corx - 1, cory);
        if(tec == 39) //derecha
            mueve(tetroActual, corx + 1, cory);
        if(tec == 38) //(arriba) rota izquierda
            mueve(tetroActual.giroIzquierda(), corx, cory);
        if(tec == 40) //(abajo) rota derecha
            mueve(tetroActual.giroDerecha(), corx, cory);
        if(tec == 27) //(abajo) rota derecha
            FinJuego();
    }    
}
