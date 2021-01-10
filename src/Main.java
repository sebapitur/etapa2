import io.Input;
import io.Writer;
import simulator.Simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        Input input = new Input(args[0]);
        Simulation sim = new Simulation(input);
        Writer writer = new Writer(args[1]);

        List<List<Map<String, Object>>> producersHistory = sim.simulate(input, writer);
        List<Map<String, Object>> consumers = new LinkedList<>();
        List<Map<String, Object>> distributors = new LinkedList<>();
        List<Map<String, Object>> producers = new LinkedList<>();
        writer.finalLists(input, producersHistory, consumers, distributors, producers);

        if(args[0].equals("/home/sebastian/etapa2/checker/resources/in/complex_5.json")) {
            new Writer("cacas.out").closeJSON(consumers, distributors, producers);
        }
        writer.closeJSON(consumers, distributors, producers);
    }






}
