package automato;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
public class AFD extends AutomatoFinito {

    public AFD(Set<String> estados, Set<Character> alfabeto, String estadoInicial, Set<String> estadosDeAceitacao) {
        super(estados, alfabeto, estadoInicial, estadosDeAceitacao);
    }

    @Override
    public boolean aceitarPalavra(String palavra) {
        String estadoAtual = estadoInicial;
        for (char simbolo : palavra.toCharArray()) {
            if (transicoes.containsKey(estadoAtual) && transicoes.get(estadoAtual).containsKey(simbolo)) {
                estadoAtual = transicoes.get(estadoAtual).get(simbolo).iterator().next();
            } else {
                return false;
            }
        }
        return estadosDeAceitacao.contains(estadoAtual);
    }
}
