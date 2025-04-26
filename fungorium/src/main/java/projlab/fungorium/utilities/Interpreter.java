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
                System.out.println("Tecton added");
                break;
            case "threadkillingtecton":
                ThreadKillingTecton tkt = new ThreadKillingTecton();
                game.addObject(tkt);
                System.out.println("ThreadKillingTecton added");

                break;
            case "singlethreadtecton":
                SingleThreadTecton stt = new SingleThreadTecton();
                game.addObject(stt);
                System.out.println("SingleThreadTecton added");

                break;
            case "infertiletecton":
                InfertileTecton it = new InfertileTecton();
                game.addObject(it);
                System.out.println("InfertileTecton added");

                break;
            /*
             * case "keepalivetecton":
             * KeepAliveTecton kat = new KeepAliveTecton();
             * game.addObject(kat);
             * System.out.println("KeepAliveTecton added");
             * break;
             */
            case "mushroomthread":
                Tecton threadtecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
                int threadmycid = Integer.valueOf(args.get(1));
                MushroomThread mt = new MushroomThread(threadtecton, threadmycid);
                game.addObject(mt);
                System.out.println(
                        "MushroomThread added to Mycologist " + threadmycid + " and Tecton " + threadtecton.getID());
                break;
            case "mushroombody":
                Tecton bodytecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
                int bodymycid = Integer.valueOf(args.get(1));
                MushroomBody mb = new MushroomBody(bodytecton, bodymycid);
                game.addObject(mb);
                System.out.println(
                        "MushroomBody added to Mycologist " + bodymycid + " and Tecton " + bodytecton.getID());
                break;
            case "mushroomspore":
                Tecton sporetecton = (Tecton) game.getObject(Integer.valueOf(args.get(1)));
                MushroomSpore ms = new MushroomSpore(sporetecton);
                game.addObject(ms);
                System.out.println(
                        "MushroomSpore added to Tecton " + sporetecton.getID());
                break;
            case "insect":
                Tecton insecttecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
                int insectologistid = Integer.valueOf(args.get(1));
                Insect insect = new Insect(insectologistid, insecttecton);
                game.addObject(insect);
                System.out.println(
                        "Insect added to Insectologist " + insectologistid + " and Tecton " + insecttecton.getID());
                break;
            default:
                System.err.println("Command not recognized. Possible parameters:");
                System.err.println("ADD <tectontype>");
                System.err.println("ADD MUSHROOMTHREAD <mycologistID> <tectonID>");
                System.err.println("ADD MUSHROOMBODY <mycologistID> <tectonID>");
                System.err.println("ADD MUSHROOMSPORE <mycologistID> <tectonID>");
                System.err.println("ADD INSECT <insectologistID> <tectonID>");
                break;
        }
    }

    private void register(List<String> args) {
        switch (args.get(0).toLowerCase()) {
            case "neighbour":
                Tecton t1 = (Tecton) game.getObject(Integer.valueOf(args.get(1)));
                Tecton t2 = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
                t1.registerNeighbour(t2);
                break;
            case "connection":
                MushroomThread mt1 = (MushroomThread) game.getObject(Integer.valueOf(args.get(1)));
                MushroomThread mt2 = (MushroomThread) game.getObject(Integer.valueOf(args.get(2)));
                mt1.addConnection(mt2);
                mt2.addConnection(mt1);
                break;
            default:
                System.err.println("Command not recognized. Possible parameters:");
                System.err.println("REGISTER NEIGHBOUR <tectonID1> <tectonID2>");
                System.err.println("REGISTER CONNECTION <mushroomThreadID1> <mushroomThreadID2>");
                break;
        }
    }

    private void set(List<String> args) {
        switch (args.get(0).toLowerCase()) {
            /*
             * case "effectgeneration":
             * 
             * break;
             */
            case "tectonsplitchance":
                break;
            case "tectonkillchance":
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
