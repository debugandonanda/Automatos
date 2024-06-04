package automato;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class ConversorAFNtoAFD {

    public static AFD converter(AFN afn) {
        Set<String> novosEstados = new HashSet<>();
        Set<Character> alfabeto = afn.alfabeto;
        String novoEstadoInicial = afn.estadoInicial;
        Set<String> novosEstadosDeAceitacao = new HashSet<>();
        Map<String, Map<Character, String>> novasTransicoes = new HashMap<>();

        Queue<Set<String>> fila = new LinkedList<>();
        Map<Set<String>, String> estadoParaNome = new HashMap<>();
        int contador = 0;

        Set<String> estadoInicialConjunto = new HashSet<>();
        estadoInicialConjunto.add(afn.estadoInicial);
        fila.add(estadoInicialConjunto);
        estadoParaNome.put(estadoInicialConjunto, "Q" + contador++);
        novosEstados.add("Q0");

        while (!fila.isEmpty()) {
            Set<String> estadoAtualConjunto = fila.poll();
            String estadoAtualNome = estadoParaNome.get(estadoAtualConjunto);

            for (char simbolo : alfabeto) {
                Set<String> novoEstadoConjunto = new HashSet<>();
                for (String estado : estadoAtualConjunto) {
                    if (afn.transicoes.containsKey(estado) && afn.transicoes.get(estado).containsKey(simbolo)) {
                        novoEstadoConjunto.addAll(afn.transicoes.get(estado).get(simbolo));
                    }
                }
                if (!estadoParaNome.containsKey(novoEstadoConjunto)) {
                    estadoParaNome.put(novoEstadoConjunto, "Q" + contador++);
                    novosEstados.add(estadoParaNome.get(novoEstadoConjunto));
                    fila.add(novoEstadoConjunto);
                }
                novasTransicoes.putIfAbsent(estadoAtualNome, new HashMap<>());
                novasTransicoes.get(estadoAtualNome).put(simbolo, estadoParaNome.get(novoEstadoConjunto));
            }
        }

        for (Set<String> conjuntoEstado : estadoParaNome.keySet()) {
            for (String estado : conjuntoEstado) {
                if (afn.estadosDeAceitacao.contains(estado)) {
                    novosEstadosDeAceitacao.add(estadoParaNome.get(conjuntoEstado));
                    break;
                }
            }
        }

        AFD afd = new AFD(novosEstados, alfabeto, novoEstadoInicial, novosEstadosDeAceitacao);
        for (String estado : novasTransicoes.keySet()) {
            for (char simbolo : novasTransicoes.get(estado).keySet()) {
                afd.adicionarTransicao(estado, simbolo, novasTransicoes.get(estado).get(simbolo));
            }
        }

        return afd;
    }
}
