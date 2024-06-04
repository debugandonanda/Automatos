package automato;

import java.util.HashSet;
import java.util.Set;

public class EquivalenciaAutomatos {

    public static boolean saoEquivalentes(AFN afn, AFD afd) {
        Set<String> palavrasTeste = gerarPalavrasTeste(afn.alfabeto, 3);

        for (String palavra : palavrasTeste) {
            if (afn.aceitarPalavra(palavra) != afd.aceitarPalavra(palavra)) {
                return false;
            }
        }
        return true;
    }

    private static Set<String> gerarPalavrasTeste(Set<Character> alfabeto, int maxComprimento) {
        Set<String> palavras = new HashSet<>();
        gerarPalavrasRecursivo("", alfabeto, maxComprimento, palavras);
        return palavras;
    }

    private static void gerarPalavrasRecursivo(String prefixo, Set<Character> alfabeto, int maxComprimento, Set<String> palavras) {
        if (prefixo.length() > maxComprimento) {
            return;
        }
        palavras.add(prefixo);
        for (Character simbolo : alfabeto) {
            gerarPalavrasRecursivo(prefixo + simbolo, alfabeto, maxComprimento, palavras);
        }
    }
}
