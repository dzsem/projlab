package projlab.fungorium.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import projlab.fungorium.models.*;
import projlab.fungorium.models.MushroomThread.CutState;
import projlab.fungorium.models.MushroomThread.GrowState;

public class Interpreter {
    private static Game game;

    protected Interpreter() {
        inputinit();
        configinit();
    }

    protected Interpreter(Game g) {
        game = g;
        inputinit();
        configinit();
    }

    public void processConfig(List<String> args) {
        String key;
        if (args.size() >= 2 && args.get(0).equalsIgnoreCase("SET") && args.get(1).equalsIgnoreCase("MUSHROOMTHREAD")) {
            key = "SET MUSHROOMTHREAD STATE"; // Speciális SET MUSHROOMTHREAD eset
        } else {
            key = args.get(0).toUpperCase() + " " + args.get(1).toUpperCase();
        }

        InterpreterCommand command = configmap.get(key);
        if (command != null) {
            command.execute(args.subList(2, args.size())); // Csak a paramétereket adja tovább
        } else {
            System.err.println("Unknown command: " + key);
        }
    }

    private Map<String, InterpreterCommand> configmap = new HashMap<>();
    private Map<String, InterpreterCommand> inputmap = new HashMap<>();

    private void inputinit() {
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
    }

    private void configinit() {
        configmap.put("ADD TECTON", args -> {
            addTecton();
        });
        configmap.put("ADD THREADKILLINGTECTON", args -> {
            addThreadKillingTecton();
        });
        configmap.put("ADD SINGLETHREADTECTON", args -> {
            addSingleThreadTecton();
        });
        configmap.put("ADD INFERTILETECTON", args -> {
            addInfertileTecton();
        });
        configmap.put("ADD KEEPALIVETECTON", args -> {
            addKeepAliveTecton();
        });
        configmap.put("ADD MUSHROOMTHREAD", args -> {
            addMushroomThread(args);
        });
        configmap.put("ADD MUSHROOMBODY", args -> {
            addMushroomBody(args);
        });
        configmap.put("ADD MUSHROOMSPORE", args -> {
            addMushroomSpore(args);
        });
        configmap.put("ADD INSECT", args -> {
            addInsect(args);
        });
        configmap.put("REGISTER NEIGHBOUR", args -> {
            registerNeighbour(args);
        });
        configmap.put("REGISTER CONNECTION", args -> {
            registerConnection(args);
        });
        configmap.put("SET TECTONSPLITCHANCE", args -> {
            setTectonsplitchance(args);
        });
        configmap.put("SET TECTONKILLCHANCE", args -> {
            setTectonkillchance(args);
        });
        /*
         * configmap.put("SET EFFECTGENERATION", args -> {
         * 
         * });
         */
        configmap.put("SET MUSHROOMTHREAD STATE", args -> {
            setMushroomthreadstate(args);
        });
    }

    private void addTecton() {
        Tecton t = new Tecton();
        game.addObject(t);
        System.out.println("Tecton added");
    }

    private void addThreadKillingTecton() {
        ThreadKillingTecton tkt = new ThreadKillingTecton();
        game.addObject(tkt);
        System.out.println("ThreadKillingTecton added");
    }

    private void addSingleThreadTecton() {
        SingleThreadTecton stt = new SingleThreadTecton();
        game.addObject(stt);
        System.out.println("SingleThreadTecton added");
    }

    private void addInfertileTecton() {
        InfertileTecton it = new InfertileTecton();
        game.addObject(it);
        System.out.println("InfertileTecton added");
    }

    private void addKeepAliveTecton() {
        KeepAliveTecton kat = new KeepAliveTecton();
        game.addObject(kat);
        System.out.println("KeepAliveTecton added");
    }

    private void addMushroomThread(List<String> args) {
        GameObject threadtecton = game.getObject(Integer.valueOf(args.get(1)));
        if (threadtecton instanceof Tecton) {
            int threadmycid = Integer.valueOf(args.get(0));
            MushroomThread mt = new MushroomThread((Tecton) threadtecton, threadmycid);
            game.addObject(mt);
            System.out.println(
                    "MushroomThread added to Mycologist " + threadmycid + " and Tecton " + threadtecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    private void addMushroomBody(List<String> args) {
        GameObject bodytecton = game.getObject(Integer.valueOf(args.get(1)));
        if (bodytecton instanceof Tecton) {
            int bodymycid = Integer.valueOf(args.get(0));
            MushroomBody mb = new MushroomBody((Tecton) bodytecton, bodymycid);
            game.addObject(mb);
            System.out.println("MushroomBody added to Mycologist " + bodymycid + " and Tecton " + bodytecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    private void addMushroomSpore(List<String> args) {
        GameObject sporetecton = game.getObject(Integer.valueOf(args.get(0)));
        if (sporetecton instanceof Tecton) {
            MushroomSpore ms = new MushroomSpore((Tecton) sporetecton);
            game.addObject(ms);
            System.out.println("MushroomSpore added to Tecton " + sporetecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    private void addInsect(List<String> args) {
        GameObject insecttecton = game.getObject(Integer.valueOf(args.get(1)));
        if (insecttecton instanceof Tecton) {
            int insectologistid = Integer.valueOf(args.get(0));
            Insect insect = new Insect(insectologistid, (Tecton) insecttecton);
            game.addObject(insect);
            System.out.println(
                    "Insect added to Insectologist " + insectologistid + " and Tecton " + insecttecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    private void registerNeighbour(List<String> args) {
        GameObject t1 = game.getObject(Integer.valueOf(args.get(0)));
        GameObject t2 = game.getObject(Integer.valueOf(args.get(1)));
        if (t1 instanceof Tecton) {
            if (t2 instanceof Tecton) {
                ((Tecton) t1).registerNeighbour((Tecton) t2);
                System.out.println("Registered neighbours: " + t1.getID() + " <-> " + t2.getID());
            } else {
                System.err.println("The second id does not belong to a Tecton.");
            }
        } else {
            System.err.println("The first id does not belong to a Tecton.");
        }
    }

    private void registerConnection(List<String> args) {
        GameObject mt1 = game.getObject(Integer.valueOf(args.get(0)));
        GameObject mt2 = game.getObject(Integer.valueOf(args.get(1)));
        if (mt1 instanceof MushroomThread) {
            if (mt2 instanceof MushroomThread) {
                ((MushroomThread) mt1).addConnection((MushroomThread) mt2);
                ((MushroomThread) mt2).addConnection((MushroomThread) mt1);
                System.out.println(
                        "Registered connection between MushroomThreads: " + mt1.getID() + " <-> " + mt2.getID());
            } else {
                System.err.println("The second id does not belong to a MushroomThread.");
            }
        } else {
            System.err.println("The first id does not belong to a MushroomThread.");
        }
    }

    private void setTectonsplitchance(List<String> args) {
        List<GameObject> gos = game.getGameObjects();
        double chance = Double.valueOf(args.get(0)) / 100;
        for (GameObject g : gos) {
            if (g instanceof Tecton) {
                ((Tecton) g).setSplitChance(chance);
            }
        }
        System.out.println("Global Tecton split chance set to: " + args.get(0) + "%");
    }

    private void setTectonkillchance(List<String> args) {
        List<GameObject> gos = game.getGameObjects();
        double chance = Double.valueOf(args.get(0)) / 100;
        for (GameObject g : gos) {
            if (g instanceof ThreadKillingTecton) {
                ((ThreadKillingTecton) g).setKillChance(chance);
            }
        }
        System.out.println("ThreadKillingTecton kill chance set to: " + args.get(0) + "%");
    }

    private void setMushroomthreadstate(List<String> args) {
        GameObject obj = game.getObject(Integer.valueOf(args.get(0)));
        if (obj instanceof MushroomThread) {
            MushroomThread mt = (MushroomThread) obj;
            switch (args.get(1).toLowerCase()) {
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

            switch (args.get(2).toLowerCase()) {
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
        } else {
            System.err.println("The id does not belong to a MushroomThread.");
        }
    }

    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                List<String> args = Arrays.asList(line.split("\\s+"));
                processConfig(args);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
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
