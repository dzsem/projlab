package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

import projlab.fungorium.interfaces.WritableGameObject;
import projlab.fungorium.models.*;

public class Interpreter {
    private static IOHandler iohandler;
    private static Game game;

    public void execute(List<Object> args) {
    }

    protected Interpreter() {
        init();
    }

    protected Interpreter(Game g) {
        game = g;
        init();
    }

    List<String> input = iohandler.getTokens();

    private Map<String, Command> commands = new HashMap<>();

    private void init() {
        commands.put("LOAD", args);
        commands.put("SAVE", args);
        commands.put("LIST", args);
        commands.put("SHOW", args);
        commands.put("INFERTILETECTON", args);
        commands.put("MUSHROOMBODY", args);
        commands.put("MUSHROOMTHREAD", args);
        commands.put("INSECT", args);
        commands.put("NEXTROUND", args);
        commands.put("ADD", args);
        commands.put("REGISTER", args);
        commands.put("SET", args);
    };

    private void add(List<String> args) {
        switch (args.get(0).toLowerCase()) {
            case "tecton":
                Tecton t = new Tecton();
                game.addObject(t);
                break;
            case "threadkillingtecton":
                ThreadKillingTecton tkt = new ThreadKillingTecton();
                game.addObject(tkt);
                break;
            case "singlethreadtecton":
                SingleThreadTecton stt = new SingleThreadTecton();
                game.addObject(stt);
                break;
            case "infertiletecton":
                InfertileTecton it = new InfertileTecton();
                game.addObject(it);
                break;
            /*
             * case "keepalivetecton":
             * KeepAliveTecton kat = new KeepAliveTecton();
             * game.addObject(kat);
             * break;
             */
            case "mushroomthread":
                Tecton threadtecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));

                break;
            case "mushroombody":
                break;
            case "mushroomspore":
                break;
            case "insect":
                break;
            default:
                break;
        }
    }

    private void load(File filename) {
        if (!filename.exists()) {
            System.err.println("Error: this file does not exist.");
        }

    }

    private void save(File filename) {
        for (GameObject obj : game.gameObjects) {
            if (obj instanceof WritableGameObject writable) {
                iohandler.save(filename, writable);
            }
        }
    }

    public void listByType(String typeName) {
        System.out.println("Objects of type '" + typeName + "':");

        boolean found = false;
        for (GameObject obj : game.gameObjects) {
            if (obj.getClass().getSimpleName().equalsIgnoreCase(typeName)) {
                System.out.println(" - ID: " + obj.getID());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No objects found for type: " + typeName);
        }
    }

    public void show(int id) {
        System.out.println(game.getObject(id).getOutputString());
    }

    public void distributespores(int id, String dist) {
        if (!dist.equalsIgnoreCase("distributespores")) {
            System.err.println("Command not understood. Did you mean to write \"DISTRIBUTESPORES?\"");
        }
        GameObject mushroom = game.getObject(id);

        if (mushroom instanceof MushroomBody mush) {
            mush.distributeSpores();
            System.out.println("Spore distribution successful");
        } else {
            System.err.println("The object with the specified ID isn't a MushroomBody");
        }
    }

}
