import java.util.Arrays;
import java.util.Scanner;

public class Busca
{
    public static Scanner scan = new Scanner(System.in);
    public static final int ARESTA_INEXISTENTE = -1;
    public static final int NAO_VISITADO = 0;
    public static final int VISITADO = 1;

    public static int[][] criarGrafo(int numVertices)
    {
        int[][] grafo = new int[numVertices][numVertices];

        for (int i = 0; i < grafo.length; i++)
        {
            Arrays.fill(grafo[i], ARESTA_INEXISTENTE);
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

    public static void buscaEmProfundidade(int[][] grafo, int[] visitados, int vertice)
    {
        if (visitados[vertice] == NAO_VISITADO) // Não foi visitado
        {
            visitados[vertice] = VISITADO;
            System.out.print(vertice + " ");

            for (int sucessor = 0; sucessor < grafo.length; sucessor++)
            {
                if (grafo[vertice][sucessor] != ARESTA_INEXISTENTE)
                    buscaEmProfundidade(grafo, visitados, sucessor);
            }
        }
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

            int[] visitados = new int[grafo.length];

            // Começa a busca em profundidade
            for (int i = 0; i < grafo.length; i++)
            {
                buscaEmProfundidade(grafo, visitados, i);
            }

            System.out.println();
            numVertices = scan.nextInt();
        }
    }
}
