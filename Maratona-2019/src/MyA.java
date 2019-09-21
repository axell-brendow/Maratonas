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
        
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) < circRaio;
    }
    
    public static boolean algumSensorDetecta(int linha, int coluna, int[][] sensores)
    {
        boolean detecta = false;
        
        for (int i = 0; !detecta && i < sensores.length; i++)
        {
            detecta = estaDentroDaCircunferencia(
                    linha, coluna, sensores[i][1], sensores[i][0], sensores[i][2]);
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
        
        for (int[] sensor : sensores) matriz[sensor[1]][sensor[0]] = 9;
    }
    
    public static void printarMatriz(int[][] matriz, int colunaEspecial, int linhaEspecial)
    {
    	int copia = -1;
    	
    	if (colunaEspecial > 0 && linhaEspecial > 0)
    	{
        	copia = matriz[linhaEspecial][colunaEspecial];
        	matriz[linhaEspecial][colunaEspecial] = 'X';
    	}
    	
    	int[] l = new int[matriz[0].length];
    	
    	for (int i = 0; i < l.length; i++) l[i] = i;
    	println("   " + Arrays.toString(l));
    	int i = 0;
    	String coluna;
    	
        for (int[] linha : matriz)
        {
        	coluna = (i < 10) ? i + " " : i + "";
            println(coluna + " " + Arrays.toString(linha));
            i++;
        }

    	if (colunaEspecial > 0 && linhaEspecial > 0)
    	{
            matriz[linhaEspecial][colunaEspecial] = copia;
    	}
    }
    
    public static int[][] gerarMovimentos(int coluna, int linha)
    {
    	return new int[][] {
    		{coluna, linha - 1},
    		{coluna, linha + 1},
    		{coluna - 1, linha},
    		{coluna + 1, linha},
    		{coluna - 1, linha - 1},
    		{coluna + 1, linha + 1},
    		{coluna - 1, linha + 1},
    		{coluna + 1, linha - 1},
    	};
    }

    public static boolean ePossivelRoubar(int[][] matriz, int coluna, int linha, int numLinhas, int numColunas)
    {
    	boolean ePossivel = linha == matriz.length - 1 && coluna == matriz[0].length - 1;
    	
    	if (!ePossivel)
    	{
    		int[][] movimentos = gerarMovimentos(coluna, linha);
    		matriz[linha][coluna] = 0; // Marca a posição como detectável
    		
    		for (int i = 0; !ePossivel && i < movimentos.length; i++)
    		{
    			if (matriz[movimentos[i][1]][movimentos[i][0]] != 0 &&
        			movimentos[i][0] < numColunas && movimentos[i][1] < numLinhas &&
					movimentos[i][0] > 0 && movimentos[i][1] > 0)
    			{
    	    		println("###############################");
    	    		printarMatriz(matriz, movimentos[i][0], movimentos[i][1]);
    				ePossivel = ePossivelRoubar(matriz, movimentos[i][0], movimentos[i][1], numLinhas, numColunas);
    			}
    		}
    	}
    	
    	return ePossivel;
    }
    
    public static boolean ePossivelRoubar(int[][] matriz, int numLinhas, int numColunas)
    {
    	return ePossivelRoubar(matriz, 0, 0, numLinhas, numColunas);
    }
    
    public static void animar()
    {
        String str1 = "####################";
        String str2 = "@@@@@@@@@@@@@@@@@@@@";
        boolean alternar = false;
        
        print(str1);
        
        for (int i = 0; true; i = (i + 1) % str1.length())
        {
        	if (i == 0)
        	{
        		alternar = !alternar;
        		for (int j = 0; j < str1.length(); j++) print("\b");
        	}
        	
        	try {
				Thread.sleep(50);
				
				if (alternar) print(str2.charAt(i));
				else print(str1.charAt(i));
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
        
        printarMatriz(matriz, -1, -1);
        
        println(ePossivelRoubar(matriz, numLinhas, numColunas) ? "S" : "N");
        //animar();
    }
}
