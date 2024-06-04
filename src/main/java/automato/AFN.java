package automato;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AFN extends AutomatoFinito {

    public AFN(Set<String> estados, Set<Character> alfabeto, String estadoInicial, Set<String> estadosDeAceitacao) {
        super(estados, alfabeto, estadoInicial, estadosDeAceitacao);
    }

    @Override
    public boolean aceitarPalavra(String palavra) {
        return aceitarPalavraRecursivo(estadoInicial, palavra, 0);
    }

    private boolean aceitarPalavraRecursivo(String estadoAtual, String palavra, int posicao) {
        if (posicao == palavra.length()) {
            return estadosDeAceitacao.contains(estadoAtual);
        }
        char simbolo = palavra.charAt(posicao);
        if (transicoes.containsKey(estadoAtual) && transicoes.get(estadoAtual).containsKey(simbolo)) {
            for (String proximoEstado : transicoes.get(estadoAtual).get(simbolo)) {
                if (aceitarPalavraRecursivo(proximoEstado, palavra, posicao + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}