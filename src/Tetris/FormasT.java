
package Tetris;
public class FormasT {

    enum Tetrominos { ninguna, z, s, linea, t, cuadro, l, lreves };

    private Tetrominos tetroFig;
    private int coordenadas[][];
    private int[][][] coordFigura;


    public FormasT() {
        coordenadas = new int[4][2];
        setFigura(Tetrominos.ninguna);
    }

    public void setFigura(Tetrominos tetro) {

         coordFigura = new int[][][] {
            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },  //no
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } }, //Z
            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },  //S
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },  //I
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },  //T
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },  //▀
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },  //L
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }   //Kira 
        };

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 2; ++j) {
                coordenadas[i][j] = coordFigura[tetro.ordinal()][i][j];
            }
        }
        tetroFig = tetro;
    }

    private void setX(int recorre, int x){ 
        coordenadas[recorre][0] = x; 
    }
    private void setY(int recorre, int y){ 
        coordenadas[recorre][1] = y; 
    }
    public int x(int recorre){ 
        return coordenadas[recorre][0]; 
    }
    public int y(int recorre){ 
        return coordenadas[recorre][1]; 
    }
  
    public int minY() 
    {
      int m = coordenadas[0][1];
      for (int i=0; i < 4; i++) {
          if(m>coordenadas[i][1])
              m= coordenadas[i][1];
      }
      return m;
    }
 
    public void setFigRandom()
    {
        int x = (int)(Math.random()*(7)+1); 
        Tetrominos[] valor = Tetrominos.values(); 
        setFigura(valor[x]);
    }
    
    public Tetrominos getFigura(){ 
        return tetroFig; 
    }
    
    public FormasT giroIzquierda() 
    {
        if (tetroFig == Tetrominos.cuadro)
            return this;

        FormasT fGirada = new FormasT();
        fGirada.tetroFig = tetroFig;

        for (int i = 0; i < 4; ++i) {
            fGirada.setX(i, y(i));
            fGirada.setY(i, -x(i));
        }
        return fGirada;
    }

    public FormasT giroDerecha()
    {
        if (tetroFig == Tetrominos.cuadro)
            return this;

        FormasT fGirada = new FormasT();
        fGirada.tetroFig = tetroFig;

        for (int i = 0; i < 4; ++i) {
            fGirada.setX(i, -y(i));
            fGirada.setY(i, x(i));
        }
        return fGirada;
    }
}    

