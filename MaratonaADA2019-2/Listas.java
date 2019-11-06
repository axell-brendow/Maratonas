import java.util.Scanner;

class Celula
{
    public int elemento;
    public Celula prox;
    public Celula ant;

    public Celula(int elemento, Celula prox, Celula ant)
    {
        this.elemento = elemento;
        this.prox = prox;
        this.ant = ant;
    }
}

class Lista
{
    public Celula cabeca;
    public Celula fim;
    public int tamanho;

    public Lista()
    {
        this.cabeca = new Celula(Integer.MIN_VALUE, null, null);
        this.fim = cabeca;
        this.tamanho = 0;
    }

    public Celula get(int indice)
    {
        Celula celula = cabeca;

        for (int i = -1; i < indice; i++) celula = celula.prox;

        return celula;
    }

    public void inserirInicio(int elemento)
    {
        Celula inicioAtual = cabeca.prox;
        Celula nova = new Celula(elemento, inicioAtual, cabeca);

        cabeca.prox = nova;
        if (tamanho > 0) inicioAtual.ant = nova;

        if (tamanho == 0) fim = nova;

        tamanho++;
    }

    public void inserirFim(int elemento)
    {
        Celula fimAtual = fim;
        Celula nova = new Celula(elemento, null, fimAtual);

        fimAtual.prox = nova;
        fim = nova;
        tamanho++;
    }

    public void inserir(int elemento, int indice)
    {
        if (indice >= 0 && indice <= tamanho)
        {
            if (indice == 0) inserirInicio(elemento);
            else if (indice == tamanho) inserirFim(elemento);
            else
            {
                Celula celulaNoIndice = get(indice);
                Celula celulaAnterior = celulaNoIndice.ant;
                Celula nova = new Celula(elemento, celulaNoIndice, celulaAnterior);

                celulaAnterior.prox = nova;
                celulaNoIndice.ant = nova;
                tamanho++;
            }
        }
    }

    public void removerInicio()
    {
        if (tamanho > 0)
        {
            if (tamanho == 1)
            {
                cabeca.prox = null;
                fim = cabeca;
            }

            else
            {
                Celula aposAPrimeira = cabeca.prox.prox;
                cabeca.prox = aposAPrimeira;
                aposAPrimeira.ant = cabeca;
            }

            tamanho--;
        }
    }

    public void removerFim()
    {
        if (tamanho > 0)
        {
            Celula anterior = fim.ant;
            anterior.prox = null;
            fim = anterior;
            tamanho--;
        }
    }

    public void remover(int indice)
    {
        if (indice >= 0 && indice < tamanho)
        {
            if (indice == 0) removerInicio();
            else if (indice == tamanho - 1) removerFim();
            else
            {
                Celula antesDaDoIndice = get(indice).ant;
                Celula aposADoIndice = antesDaDoIndice.prox.prox;

                antesDaDoIndice.prox = aposADoIndice;
                aposADoIndice.ant = antesDaDoIndice;
                tamanho--;
            }
        }
    }

    @Override
    public String toString()
    {
        String str = "";

        if (tamanho > 0)
        {
            Celula celula = cabeca.prox;
            str = "" + celula.elemento;

            for (int i = 1; i < tamanho; i++)
            {
                celula = celula.prox;
                str += " " + celula.elemento;
            }
        }

        return str;
    }
}

public class Listas
{
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        String comando = scan.next();
        Lista lista = new Lista();

        while (!comando.startsWith("0"))
        {
            switch (comando)
            {
                case "II":
                    lista.inserirInicio( scan.nextInt() );
                    break;

                case "RI":
                    lista.removerInicio();
                    break;

                case "IF":
                    lista.inserirFim( scan.nextInt() );
                    break;

                case "RF":
                    lista.removerFim();
                    break;

                case "I":
                    lista.inserir( scan.nextInt(), scan.nextInt() );
                    break;

                case "R":
                    lista.remover( scan.nextInt() );
                    break;

                case "M":
                    System.out.println(lista);
                    break;
            
                default:
                    break;
            }

            comando = scan.next();
        }
    }
}
