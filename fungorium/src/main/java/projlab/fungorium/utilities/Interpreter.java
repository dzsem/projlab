package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import projlab.fungorium.interfaces.WritableGameObject;
import projlab.fungorium.models.*;
import projlab.fungorium.models.MushroomThread.CutState;
import projlab.fungorium.models.MushroomThread.GrowState;

public class Interpreter {
    private static Game game;

    protected Interpreter() {
        init();
    }

    protected Interpreter(Game g) {
        game = g;
        init();
    }

    private Map<String, InterpreterCommand> configmap = new HashMap<>();
    private Map<String, InterpreterCommand> inputmap = new HashMap<>();

    private void init() {
        inputmap.put("LOAD", args -> {
            load(args.get(0));
        });
        inputmap.put("SAVE", args -> {
            save(args.get(0));
        });
        inputmap.put("LIST", args -> {
            list(args.get(0));
        });
        inputmap.put("SHOW", args -> {
            show(Integer.valueOf(args.get(0)));
        });
        inputmap.put("DISTRIBUTESPORES", args -> {
            distributespores(Integer.valueOf(args.get(0)));
        });
        /*
         * inputmap.put("EATINSECT", args -> {
         * load(args);
         * });
         */
        inputmap.put("GROWBODY", args -> {
            growbody(Integer.valueOf(args.get(0)));
        });
        inputmap.put("GROW", args -> {
            grow(Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)));
        });
        inputmap.put("MOVE", args -> {
            move(Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)));
        });
        inputmap.put("EATSPORE", args -> {
            eatspore(Integer.valueOf(args.get(0)));
        });
        inputmap.put("CUTTHREAD", args -> {
            cutthread(Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)));
        });
        inputmap.put("NEXTROUND", args -> {
            nextround();
        });
    };

    private void addTecton() {
        Tecton t = new Tecton();
        game.addObject(t);
        System.out.println("Tecton added");
    }

    private void addThreadkillingtecton() {
        ThreadKillingTecton tkt = new ThreadKillingTecton();
        game.addObject(tkt);
        System.out.println("ThreadKillingTecton added");
    }

    private void addSinglethreadtecton() {
        SingleThreadTecton stt = new SingleThreadTecton();
        game.addObject(stt);
        System.out.println("SingleThreadTecton added");
    }

    private void addInfertiletecton() {
        InfertileTecton it = new InfertileTecton();
        game.addObject(it);
        System.out.println("InfertileTecton added");
    }

    private void addKeepalivetecton() {
        KeepAliveTecton kat = new KeepAliveTecton();
        game.addObject(kat);
        System.out.println("KeepAliveTecton added");
    }

    private void addMushroomthread(List<String> args) {
        Tecton threadtecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
        int threadmycid = Integer.valueOf(args.get(1));
        MushroomThread mt = new MushroomThread(threadtecton, threadmycid);
        game.addObject(mt);
        System.out.println("MushroomThread added to Mycologist " + threadmycid + " and Tecton " + threadtecton.getID());
    }

    private void addMushroombody(List<String> args) {
        Tecton bodytecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
        int bodymycid = Integer.valueOf(args.get(1));
        MushroomBody mb = new MushroomBody(bodytecton, bodymycid);
        game.addObject(mb);
        System.out.println("MushroomBody added to Mycologist " + bodymycid + " and Tecton " + bodytecton.getID());
    }

    private void addMushroomspore(List<String> args) {
        Tecton sporetecton = (Tecton) game.getObject(Integer.valueOf(args.get(1)));
        MushroomSpore ms = new MushroomSpore(sporetecton);
        game.addObject(ms);
        System.out.println("MushroomSpore added to Tecton " + sporetecton.getID());
    }

    private void addInsect(List<String> args) {
        Tecton insecttecton = (Tecton) game.getObject(Integer.valueOf(args.get(2)));
        int insectologistid = Integer.valueOf(args.get(1));
        Insect insect = new Insect(insectologistid, insecttecton);
        game.addObject(insect);
        System.out.println("Insect added to Insectologist " + insectologistid + " and Tecton " + insecttecton.getID());
    }

    private void registerNeighbour(List<String> args) {
        GameObject t1 = game.getObject(Integer.valueOf(args.get(1)));
        GameObject t2 = game.getObject(Integer.valueOf(args.get(2)));
        if (t1 instanceof Tecton) {
            if (t2 instanceof Tecton) {
                ((Tecton) t1).registerNeighbour((Tecton) t2);
            } else {
                System.err.println("The second id does not belong to a Tecton.");
            }
        } else {
            System.err.println("The first id does not belong to a Tecton.");
        }
    }

    private void registerConnection(List<String> args) {
        GameObject mt1 = game.getObject(Integer.valueOf(args.get(1)));
        GameObject mt2 = game.getObject(Integer.valueOf(args.get(2)));
        if (mt1 instanceof MushroomThread) {
            if (mt2 instanceof MushroomThread) {
                ((MushroomThread) mt1).addConnection((MushroomThread) mt2);
                ((MushroomThread) mt2).addConnection((MushroomThread) mt1);
            } else {
                System.err.println("The second id does not belong to a MushroomThread.");
            }
        } else {
            System.err.println("The first id does not belong to a MushroomThread.");
        }
    }

    private void setTectonsplitchance(List<String> args) {
        List<GameObject> gos = game.getGameObjects();
        for (GameObject g : gos) {
            if (g instanceof Tecton) {
                ((Tecton) g).setSplitChance(Double.valueOf(args.get(1)) / 100);
            }
        }
        System.out.println("Global Tecton split chance set to: " + args.get(1) + "%");
    }

    private void setTectonkillchance(List<String> args) {
        List<GameObject> gos = game.getGameObjects();
        for (GameObject g : gos) {
            if (g instanceof ThreadKillingTecton) {
                ((ThreadKillingTecton) g).setKillChance(Double.valueOf(args.get(1)) / 100);
            }
        }
        System.out.println("ThreadKillingTecton kill chance set to: " + args.get(1) + "%");
    }

    private void setMushroomthread(List<String> args) {
        MushroomThread mt = (MushroomThread) game.getObject(Integer.valueOf(args.get(2)));
        if (!args.get(1).toLowerCase().equals("state")) {
            System.err.println("Command not recognized.");
            System.err.println("SET MUSHROOMTHREAD STATE <mushroomThreadID> <cutState> <growState>");
            return;
        }

        switch (args.get(3).toLowerCase()) {
            case "cut":
                mt.setCutState(CutState.CUT);
                break;
            case "uncut":
                mt.setCutState(CutState.UNCUT);
                break;
            default:
                System.err.println("Cut state not recognized. Possible states: CUT, UNCUT");
                break;
        }

        switch (args.get(4).toLowerCase()) {
            case "sprout":
                mt.setGrowState(GrowState.SPROUT);
                break;
            case "grow":
                mt.setGrowState(GrowState.GROWN);
                break;
            default:
                System.err.println("Grow state not recognized. Possible states: GROW, SPROUT");
                break;
        }
    }

    private void load(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String fileline;
            while ((fileline = br.readLine()) != null) {
                List<String> fl = Arrays.asList(fileline.split(" "));
                String commandName = fl.get(0);
                if (commandName.startsWith("#")) {
                    continue;
                }
                List<String> loadargs = fl.subList(1, fl.size());
                InterpreterCommand command = inputmap.get(commandName);
                if (command != null) {
                    command.execute(loadargs);
                } else {
                    System.out.println("Unknown command: " + commandName);
                }
            }
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }
    }

    // TODO
    private void save(String filename) {

    }

    public void list(String typeName) {
        System.out.println("Objects of type '" + typeName + "':");

        boolean found = false;
        for (GameObject obj : game.getGameObjects()) {
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

    public void distributespores(int id) {
        GameObject mushroombody = game.getObject(id);
        if (mushroombody instanceof MushroomBody mb) {
            mb.distributeSpores();
            System.out.println("Spore distribution successful");
        } else {
            System.err.println("The object with the specified ID isn't a MushroomBody");
        }
    }

    /*
     * TODO: finish after insect eating mushroomthread implemented
     * public void eatinsect(int id) {
     * GameObject mushroom = game.getObject(id);
     * if (mushroom instanceof MushroomThread mush) {
     * 
     * System.out.println("Insect eaten successfully");
     * } else {
     * System.err.println("");
     * }
     * }
     */

    public void growbody(int threadid) {
        GameObject mushroomthread = game.getObject(threadid);
        if (mushroomthread instanceof MushroomThread mt) {
            try {
                mt.getTecton().growBody(mt.getMushroomID());
            } catch (Exception e) {
                System.err.println(e);
            }
            System.out.println("MushroomBody successfully grown on Tecton " + mt.getTecton().getID());
        } else {
            System.err.println("The object with the specified ID isn't a MushroomThread");
        }
    }

    public void grow(int threadid, int tectonid) {
        GameObject mushroomthread = game.getObject(threadid);
        if (mushroomthread instanceof MushroomThread mt) {
            try {
                GameObject tecton = game.getObject(tectonid);
                if (tecton instanceof Tecton tec) {
                    mt.createConnection(tec);
                } else {
                    System.err.println("The object with the specified ID isn't a Tecton");
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            System.err.println("The object with the specified ID isn't a MushroomThread");
        }
    }

    public void move(int insectid, int tectonid) {
        GameObject insect = game.getObject(insectid);
        GameObject tecton = game.getObject(tectonid);
        if (insect instanceof Insect) {
            if (tecton instanceof Tecton) {
                try {
                    ((Insect) insect).moveToTecton((Tecton) tecton);
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                System.err.println("The second id does not belong to a Tecton.");
            }
        } else {
            System.err.println("The first id does not belong to an Insect.");
        }
    }

    public void eatspore(int insectid) {
        GameObject insect = game.getObject(insectid);
        if (insect instanceof Insect) {
            try {
                ((Insect) insect).eatMushroomSpore();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.err.println("The id does not belong to an Insect.");
        }
    }

    public void cutthread(int insectid, int threadid) {
        GameObject insect = game.getObject(insectid);
        GameObject thread = game.getObject(threadid);
        if (insect instanceof Insect) {
            if (thread instanceof MushroomThread) {
                ((Insect) insect).cutMushroomThread((MushroomThread) thread);
            }
            System.err.println("The second id does not belong to a MushroomThread.");

        } else {
            System.err.println("The first id does not belong to an Insect.");
        }
    }

    public void nextround() {
        for (TurnAware ta : game.getTurnAwares()) {
            ta.onEndOfTheRound();
        }
    }
}
