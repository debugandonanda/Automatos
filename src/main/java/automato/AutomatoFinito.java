package automato;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public abstract class AutomatoFinito {
    protected Set<String> estados;
    protected Set<Character> alfabeto;
    protected Map<String, Map<Character, Set<String>>> transicoes;
    protected String estadoInicial;
    protected Set<String> estadosDeAceitacao;

    public AutomatoFinito(Set<String> estados, Set<Character> alfabeto, String estadoInicial, Set<String> estadosDeAceitacao) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosDeAceitacao = estadosDeAceitacao;
        this.transicoes = new HashMap<>();
    }

    public void adicionarTransicao(String estadoOrigem, Character simbolo, String estadoDestino) {
        transicoes.putIfAbsent(estadoOrigem, new HashMap<>());
        transicoes.get(estadoOrigem).putIfAbsent(simbolo, new HashSet<>());
        transicoes.get(estadoOrigem).get(simbolo).add(estadoDestino);
    }

    public abstract boolean aceitarPalavra(String palavra);
}
