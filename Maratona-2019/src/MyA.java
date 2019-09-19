import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MyA
{
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static void print(Object msg) { System.out.print(msg); }
    public static void println(Object msg) { print(msg + "\n"); }
    public static String readLine()
    {
        String line = null;
        
        try { line = reader.readLine(); }
        catch (IOException ex) { ex.printStackTrace(); }
        
        return line;
    }
    
    public static boolean estaDentroDaCircunferencia(int x, int y, int circX, int circY, int circRaio)
    {
        int deltaX = circX - x;
        int deltaY = circY - y;
        
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= circRaio;
    }
    
    public static boolean algumSensorDetecta(int x, int y, int[][] sensores)
    {
        boolean detecta = false;
        
        for (int i = 0; !detecta && i < sensores.length; i++)
        {
            detecta = estaDentroDaCircunferencia(
                    x, y, sensores[i][1], sensores[i][0], sensores[i][2]);
        }
        
        return detecta;
    }
    
    public static void gerarMatrizDeDeteccoes(int[][] matriz, int numLinhas, int numColunas, int[][] sensores)
    {
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColunas; j++)
            {
                if (algumSensorDetecta(i, j, sensores))
                {
                    matriz[i][j] = 0;
                }
                
                else
                {
                    matriz[i][j] = 1;
                }
            }
        }
        
        for (int[] sensor : sensores) matriz[sensor[0]][sensor[1]] = 9;
    }
    
    public static void printarMatriz(int[][] matriz)
    {
    	int[] l = new int[matriz[0].length];
    	
    	for (int i = 0; i < l.length; i++) l[i] = i;
    	println("  " + Arrays.toString(l));
    	int i = 0;
    	
        for (int[] linha : matriz)
        {
            println((i++ % 10) + " " + Arrays.toString(linha));
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String[] fields = readLine().split(" ");
        int numColunas = Integer.parseInt(fields[0]) + 1;
        int numLinhas = Integer.parseInt(fields[1]) + 1;
        int numSensores = Integer.parseInt(fields[2]);
        int[][] sensores = new int[numSensores][3];
        
        for (int i = 0; i < numSensores; i++)
        {
            String[] sensor = readLine().split(" ");
            int colunaSensor = Integer.parseInt(sensor[0]);
            int linhaSensor = Integer.parseInt(sensor[1]);
            int sensibilidade = Integer.parseInt(sensor[2]);
            sensores[i][0] = colunaSensor;
            sensores[i][1] = linhaSensor;
            sensores[i][2] = sensibilidade;
        }
        
        int[][] matriz = new int[numLinhas][numColunas];
        gerarMatrizDeDeteccoes(matriz, numLinhas, numColunas, sensores);
        
        printarMatriz(matriz);
    }
}