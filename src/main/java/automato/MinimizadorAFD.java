package automato;

import java.util.*;

public class MinimizadorAFD {

    public static AFD minimizar(AFD afd) {
        // Inicializar as partições
        Set<Set<String>> P = new HashSet<>();
        Set<String> F = new HashSet<>(afd.estadosDeAceitacao);
        Set<String> QF = new HashSet<>(afd.estados);
        QF.removeAll(F);

        P.add(F);
        P.add(QF);

        Queue<Set<String>> fila = new LinkedList<>(P);

        while (!fila.isEmpty()) {
            Set<String> C = fila.poll();

            for (char simbolo : afd.alfabeto) {
                Map<Set<String>, Set<String>> X = new HashMap<>();

                for (String q : afd.estados) {
                    if (afd.transicoes.containsKey(q) && afd.transicoes.get(q).containsKey(simbolo)) {
                        String qPrime = afd.transicoes.get(q).get(simbolo).iterator().next();

                        for (Set<String> Y : P) {
                            if (Y.contains(qPrime)) {
                                if (!X.containsKey(Y)) {
                                    X.put(Y, new HashSet<>());
                                }
                                X.get(Y).add(q);
                                break;
                            }
                        }
                    }
                }

                for (Set<String> Y : X.keySet()) {
                    if (X.get(Y).size() < Y.size()) {
                        P.remove(Y);
                        P.add(X.get(Y));
                        Set<String> diferenca = new HashSet<>(Y);
                        diferenca.removeAll(X.get(Y));
                        P.add(diferenca);

                        if (fila.contains(Y)) {
                            fila.remove(Y);
                            fila.add(X.get(Y));
                            fila.add(diferenca);
                        } else {
                            if (X.get(Y).size() <= diferenca.size()) {
                                fila.add(X.get(Y));
                            } else {
                                fila.add(diferenca);
                            }
                        }
                    }
                }
            }
        }

        // Construir o AFD minimizado
        Map<String, String> estadoParaClasse = new HashMap<>();
        int contador = 0;

        for (Set<String> Y : P) {
            String novoNome = "Q" + contador++;
            for (String estado : Y) {
                estadoParaClasse.put(estado, novoNome);
            }
        }

        Set<String> novosEstados = new HashSet<>(estadoParaClasse.values());
        String novoEstadoInicial = estadoParaClasse.get(afd.estadoInicial);
        Set<String> novosEstadosDeAceitacao = new HashSet<>();

        for (String estado : afd.estadosDeAceitacao) {
            novosEstadosDeAceitacao.add(estadoParaClasse.get(estado));
        }

        AFD afdMinimizado = new AFD(novosEstados, afd.alfabeto, novoEstadoInicial, novosEstadosDeAceitacao);
        for (String estado : afd.transicoes.keySet()) {
            for (char simbolo : afd.transicoes.get(estado).keySet()) {
                afdMinimizado.adicionarTransicao(estadoParaClasse.get(estado), simbolo, estadoParaClasse.get(afd.transicoes.get(estado).get(simbolo).iterator().next()));
            }
        }

        return afdMinimizado;
    }
}
