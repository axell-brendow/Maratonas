import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;

class Quarteto<T0, T1, T2, T3>
{
	public T0 v0;
	public T1 v1;
	public T2 v2;
	public T3 v3;
	
	public Quarteto(T0 v0, T1 v1, T2 v2, T3 v3)
	{
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
}

public class A
{
	public static boolean animar = true;
	public static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
	public static int DELAY_PADRAO = 50;
	public static final String CLEAR = isWindows ? "cls" : "clear";
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
            	coluna, linha, sensores[i][0], sensores[i][1], sensores[i][2]);
        }
        
        return detecta;
    }
    
    public static void gerarMatrizDeDeteccoes(int[][] matriz, int numLinhas, int numColunas, int[][] sensores)
    {
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColunas; j++)
            {
            	matriz[i][j] = algumSensorDetecta(i, j, sensores) ? 0 : 1;
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
    	
    	int[] indicesColunas = new int[matriz[0].length];
    	
    	for (int i = 0; i < indicesColunas.length; i++) indicesColunas[i] = i;
    	
    	println("   " + Arrays.toString(indicesColunas));
    	
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
    
    public static void sleep(int milliseconds)
    {
    	if (animar)
    	{
        	try {
    			Thread.sleep(milliseconds);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public static void exec(String command)
    {
    	if (animar)
    	{
        	try
    		{
    			new ProcessBuilder(isWindows ? "cmd" : "bash", isWindows ? "/c" : "-c",
    				"\"" + command + "\"").inheritIO().start().waitFor();
    		}
    		catch (InterruptedException | IOException e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
    
    public static int[][] gerarMovimentos(int coluna, int linha)
    {
    	return new int[][] {
    		{coluna + 1, linha},
    		{coluna + 1, linha - 1},
    		{coluna + 1, linha + 1},
    		{coluna - 1, linha},
    		{coluna - 1, linha + 1},
    		{coluna - 1, linha - 1},
    		{coluna, linha - 1},
    		{coluna, linha + 1},
    	};
    }
    
    public static boolean ePossivelRoubar(int[][] matriz, int numLinhas, int numColunas)
    {
    	// Cada Quarteto guardará três valores:
    	// v0 -> coluna da posição
    	// v1 -> linha da posição
    	// v2 -> contador da posição (iterador sobre os nós filhos)
    	// v3 -> arranjo de movimentos (matriz onde cada linha tem dois elementos:
    	//								a coluna e a linha de um nó filho)
    	
    	ArrayList< Quarteto<Integer, Integer, Integer, int[][]> > pilha = new ArrayList<>();
    	
    	Quarteto<Integer, Integer, Integer, int[][]> posicao =
    		new Quarteto<Integer, Integer, Integer, int[][]>(0, 0, 0, null);
    		
    	pilha.add(posicao);

    	int coluna = posicao.v0;
    	int linha = posicao.v1;
//    	int contador = posicao.v2;
    	int[][] movimentos = posicao.v3 = movimentos = gerarMovimentos(coluna, linha);
    	int xMovimento;
    	int yMovimento;

    	boolean ePossivel = linha == matriz.length - 1 && coluna == matriz[0].length - 1;
    	
    	while (!ePossivel && !pilha.isEmpty())
    	{
    		matriz[linha][coluna] = 0; // Marca a posição como detectável
    		
    		for (int i = posicao.v2; i < movimentos.length; posicao.v2 = ++i)
    		{
    	    	xMovimento = movimentos[i][0];
    	    	yMovimento = movimentos[i][1];
    	    	
    			if (xMovimento < numColunas && yMovimento < numLinhas &&
					xMovimento > 0 && yMovimento > 0 &&
					matriz[yMovimento][xMovimento] != 0)
    			{
    	    		if (animar) printarMatriz(matriz, xMovimento, yMovimento);
    	    		sleep(DELAY_PADRAO);
    	    		exec(CLEAR);

    	    		posicao.v2 = ++i;
    	    		
    	    		posicao = new Quarteto<Integer, Integer, Integer, int[][]>(
    	    				xMovimento, yMovimento, 0, gerarMovimentos(xMovimento, yMovimento));
    	    		
    	    		pilha.add(posicao);
    	    		
    	    		break;
    			}
    		}
    		
    		if (posicao.v2 == movimentos.length) pilha.remove(pilha.size() - 1);

        	if (!pilha.isEmpty())
        	{
        		posicao = pilha.get(pilha.size() - 1);
            	coluna = posicao.v0;
            	linha = posicao.v1;
	    		movimentos = posicao.v3;
            	ePossivel = linha == matriz.length - 1 && coluna == matriz[0].length - 1;
        	}
    	}
    	
    	return ePossivel;
    }
    
    public static void printError(String msg)
    {
    	System.err.println(msg);
    	System.exit(0);
    }
    
    public static void readArgs(String[] args)
    {
    	if (args != null && args.length > 0)
    	{
    		for (int i = 0; i < args.length; i++)
    		{
    			if (args[i].equals("--help") || args[i].equals("-h"))
    			{
    				println(
    					"Options:" +
    					"\n\t-h\t\t--help\t\t\tShows this help message." +
    					"\n\t-d <int>\t--delay <int>\t\tSet a delay for each iteration of the algorithm. Default is 50ms." +
    					"\n\t-n\t\t--nodelay\t\tDisables animations.");
    				
    				System.exit(0);
    			}
    			
    			else if (args[i].equals("--delay") || args[i].equals("-d"))
    			{
    				if (i == args.length - 1) printError("Missing time");
    				
    				try
					{
						int delay = Integer.parseInt(args[++i]);
						if (delay < 0) throw new NumberFormatException();
						DELAY_PADRAO = delay;
					}
					catch (NumberFormatException e)
					{
						printError("Invalid delay (" + args[i] + ")");
					}
    			}
    			
    			else if (args[i].equals("--nodelay") || args[i].equals("-n"))
    			{
    				animar = false;
    			}
    			
    			else printError("Invalid argument (" + args[i] + ")");
    		}
    	}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	readArgs(args);
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
        
        println(ePossivelRoubar(matriz, numLinhas, numColunas) ? "É possível roubar" : "Não é possível roubar");
        printarMatriz(matriz, -1, -1);
    }
}
/*
Exemplos de entrada:
10 22 2
4 6 5
6 16 5

----------------
10 10 2
3 7 4
5 4 4

----------------
100 100 3
40 50 30
90 10 5

----------------
*/
