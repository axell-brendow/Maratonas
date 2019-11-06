import java.util.Arrays;
import java.util.Scanner;

public class Regular
{
    public static Scanner scan = new Scanner(System.in);

    public static int[][] criarGrafo(int numVertices)
    {
        int[][] grafo = new int[numVertices][numVertices];

        for (int i = 0; i < grafo.length; i++)
        {
            Arrays.fill(grafo[i], -1);
        }

        return grafo;
    }

    public static void printarGrafo(int[][] grafo)
    {
        for (int i = 0; i < grafo.length; i++)
        {
            System.out.println( i + " " + Arrays.toString(grafo[i]) );
        }
    }

    public static void regular(int[][] grafo)
    {
        boolean regular = true;
        int grauVerticeAtual;
        int grauPrimeiroVertice =
            Arrays.stream(grafo[0]).reduce(0,
                (grauAtual, aresta) -> grauAtual = aresta != -1 ? grauAtual + 1 : grauAtual);

        for (int i = 1; regular && i < grafo.length; i++)
        {
            grauVerticeAtual = 
                Arrays.stream(grafo[i]).reduce(0,
                    (grauAtual, aresta) -> grauAtual = aresta != -1 ? grauAtual + 1 : grauAtual);

            regular = grauVerticeAtual == grauPrimeiroVertice;
        }

        System.out.println(regular ? "SIM" : "NAO");
    }

    public static void main(String[] args)
    {
        int numVertices = scan.nextInt();

        while (numVertices > 0)
        {
            int[][] grafo = criarGrafo(numVertices);

            // Lê as arestas do grafo
            for (int i = 0; i < numVertices - 1; i++)
            {
                for (int j = i + 1; j < numVertices; j++)
                {
                    grafo[i][j] = grafo[j][i] = scan.nextInt();
                }
            }

            regular(grafo); // Checa se é regular
            numVertices = scan.nextInt();
        }
    }
}
