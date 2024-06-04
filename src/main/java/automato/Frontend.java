package automato;
import java.util.*;

public class Frontend {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Defina os estados do AFN (separados por vírgula):");
        String[] estadosInput = scanner.nextLine().split(",");
        Set<String> estados = new HashSet<>(Arrays.asList(estadosInput));

        System.out.println("Defina o alfabeto (separado por vírgula):");
        String[] alfabetoInput = scanner.nextLine().split(",");
        Set<Character> alfabeto = new HashSet<>();
        for (String s : alfabetoInput) {
            alfabeto.add(s.charAt(0));
        }

        System.out.println("Defina o estado inicial:");
        String estadoInicial = scanner.nextLine();

        System.out.println("Defina os estados de aceitação (separados por vírgula):");
        String[] estadosDeAceitacaoInput = scanner.nextLine().split(",");
        Set<String> estadosDeAceitacao = new HashSet<>(Arrays.asList(estadosDeAceitacaoInput));

        AFN afn = new AFN(estados, alfabeto, estadoInicial, estadosDeAceitacao);

        System.out.println("Defina as transições (formato: estadoOrigem,simbolo,estadoDestino). Digite 'fim' para terminar:");
        while (true) {
            String transicao = scanner.nextLine();
            if (transicao.equals("fim")) {
                break;
            }
            String[] partes = transicao.split(",");
            afn.adicionarTransicao(partes[0], partes[1].charAt(0), partes[2]);
        }

        AFD afd = ConversorAFNtoAFD.converter(afn);

        System.out.println("Digite uma palavra para simulação:");
        String palavra = scanner.nextLine();
        SimuladorAutomato.simular(afn, palavra);
        SimuladorAutomato.simular(afd, palavra);

        System.out.println("Os autômatos são equivalentes? " + EquivalenciaAutomatos.saoEquivalentes(afn, afd));

        AFD afdMinimizado = MinimizadorAFD.minimizar(afd);

        System.out.println("Digite uma palavra para simulação no AFD minimizado:");
        palavra = scanner.nextLine();
        SimuladorAutomato.simular(afdMinimizado, palavra);
    }
}
