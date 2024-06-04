package automato;
public class SimuladorAutomato {

    public static void simular(AutomatoFinito automato, String palavra) {
        if (automato.aceitarPalavra(palavra)) {
            System.out.println("A palavra '" + palavra + "' foi aceita.");
        } else {
            System.out.println("A palavra '" + palavra + "' foi rejeitada.");
        }
    }
}
