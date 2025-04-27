package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import projlab.fungorium.models.*;
import projlab.fungorium.models.MushroomThread.CutState;
import projlab.fungorium.models.MushroomThread.GrowState;
import projlab.fungorium.models.effects.EffectTypes;

public class Interpreter {
    private static Game game;

    public Interpreter() {
        game = Game.getInstance();
        inputinit();
        configinit();
    }

    /**
     * Beolvassa a standard inputra írt sorokat.
     * Szétválasztja a kulcsot(első és ha van, harmadik token) és az
     * argumentumokat(második, és ha van, negyedik token) és a HashMapban tárolt
     * parancsok közül meghívja a megfelelőt
     */
    public void input() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            System.out.print("Parancs: ");
            String line = scanner.nextLine().trim();

            if (line.startsWith("#")) {
                continue;
            }

            if (line.equalsIgnoreCase("EXIT")) {
                System.out.println("Kilépés...");
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            List<String> tokens = Arrays.asList(line.split("\\s+"));
            if (tokens.isEmpty()) {
                continue;
            }

            String key;
            if (tokens.size() >= 3 && (tokens.get(0).equalsIgnoreCase("MUSHROOMBODY") ||
                    tokens.get(0).equalsIgnoreCase("MUSHROOMTHREAD") ||
                    tokens.get(0).equalsIgnoreCase("INSECT"))) {
                key = tokens.get(0).toUpperCase() + " " + tokens.get(2).toUpperCase();
            } else {
                key = tokens.get(0).toUpperCase();
            }

            InterpreterCommand cmd = inputmap.get(key);
            if (cmd != null) {
                if (key.contains(" ")) {
                    List<String> args = new ArrayList<>();
                    args.add(tokens.get(1));
                    if (tokens.size() > 3) {
                        args.addAll(tokens.subList(3, tokens.size()));
                    }
                    cmd.execute(args);
                } else {
                    cmd.execute(tokens.subList(1, tokens.size()));
                }
            } else {
                System.err.println("Unknown command: " + line);
            }
        }

        scanner.close();
    }

    private Map<String, InterpreterCommand> configmap = new HashMap<>();
    private Map<String, InterpreterCommand> inputmap = new HashMap<>();

    /**
     * feltölti az input nyelv parancsait tartalmazó HashMapet a megfelelő
     * kulcsokkal és értékekkel
     */
    private void inputinit() {
        inputmap.put("LOAD", args -> {
            load(args);
        });
        inputmap.put("SAVE", args -> {
            save(args);
        });
        inputmap.put("LIST", args -> {
            list(args);
        });
        inputmap.put("SHOW", args -> {
            show(args);
        });
        inputmap.put("MUSHROOMBODY DISTRIBUTESPORES", args -> {
            distributespores(args);
        });
        inputmap.put("MUSHROOMTHREAD EATINSECT", args -> {
            eatinsect(args);
        });
        inputmap.put("MUSHROOMTHREAD GROWBODY", args -> {
            growbody(args);
        });
        inputmap.put("MUSHROOMTHREAD GROW", args -> {
            grow(args);
        });
        inputmap.put("INSECT MOVE", args -> {
            move(args);
        });
        inputmap.put("INSECT EATSPORE", args -> {
            eatspore(args);
        });
        inputmap.put("INSECT CUTTHREAD", args -> {
            cutthread(args);
        });
        inputmap.put("NEXTROUND", args -> {
            nextround();
        });
    }

    /**
     * feltölti a config nyelv parancsait tartalmazó HashMapet a megfelelő
     * kulcsokkal és értékekkel
     */
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
        configmap.put("SET EFFECTGENERATION", args -> {
            setEffectgeneration(args);
        });
        configmap.put("SET MUSHROOMTHREAD STATE", args -> {
            setMushroomthreadstate(args);
        });
    }

    /**
     * Hozzáad egy tektont a játékhoz
     * a konstruktorhívás elég, mert a GameObject a kontruktorában implicit
     * hozzáadja magát a game-hez
     */
    private void addTecton() {
        new Tecton();
        System.out.println("Tecton added");
    }

    /**
     * Hozzáad egy gombafonál-ölő tektont a játékhoz
     */
    private void addThreadKillingTecton() {
        new ThreadKillingTecton();
        System.out.println("ThreadKillingTecton added");
    }

    /**
     * Hozzáad egy csak egy fonalat elbíró tektont a játékhoz
     */
    private void addSingleThreadTecton() {
        new SingleThreadTecton();
        System.out.println("SingleThreadTecton added");
    }

    /**
     * Hozzáad egy terméketlen, gombatestet nem növesztő tektont a játékhoz
     */
    private void addInfertileTecton() {
        new InfertileTecton();
        System.out.println("InfertileTecton added");
    }

    /**
     * Hozzáad egy fonal nélküli gombatestet életben tartó tektont a játékhoz
     */
    private void addKeepAliveTecton() {
        new KeepAliveTecton();
        System.out.println("KeepAliveTecton added");
    }

    /**
     * hozzáad egy gombafonalat egy adott tektonhoz, egy adott játékos alá tartozva
     * 
     * @param args(0) a játékos ID-ja
     * 
     * @param args(1) a tekton ID-ja
     */
    private void addMushroomThread(List<String> args) {
        GameObject threadtecton = game.getObject(Integer.valueOf(args.get(1)));
        if (threadtecton instanceof Tecton) {
            int threadmycid = Integer.valueOf(args.get(0));
            new MushroomThread((Tecton) threadtecton, threadmycid);
            System.out.println(
                    "MushroomThread added to Mycologist " + threadmycid + " and Tecton " + threadtecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    /**
     * hozzáad egy gombatestet egy adott tektonhoz, egy adott játékos alá tartozva
     * 
     * @param args(0) a játékos ID-ja
     * 
     * @param args(1) a tekton ID-ja
     */
    private void addMushroomBody(List<String> args) {
        GameObject bodytecton = game.getObject(Integer.valueOf(args.get(1)));
        if (bodytecton instanceof Tecton) {
            int bodymycid = Integer.valueOf(args.get(0));
            new MushroomBody((Tecton) bodytecton, bodymycid);
            System.out.println("MushroomBody added to Mycologist " + bodymycid + " and Tecton " + bodytecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    /**
     * rak egy spórát egy adott tektonra
     * 
     * @param args(0) a játékos ID-ja
     */
    private void addMushroomSpore(List<String> args) {
        GameObject sporetecton = game.getObject(Integer.valueOf(args.get(0)));
        if (sporetecton instanceof Tecton) {
            new MushroomSpore((Tecton) sporetecton);
            System.out.println("MushroomSpore added to Tecton " + sporetecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    /**
     * rak egy rovart egy adott tektonra, egy adott játékos alá tartozva
     * 
     * @param args(0) a játékos ID-ja
     * 
     * @param args(1) a tekton ID-ja
     */
    private void addInsect(List<String> args) {
        GameObject insecttecton = game.getObject(Integer.valueOf(args.get(1)));
        if (insecttecton instanceof Tecton) {
            int insectologistid = Integer.valueOf(args.get(0));
            new Insect(insectologistid, (Tecton) insecttecton);
            System.out.println(
                    "Insect added to Insectologist " + insectologistid + " and Tecton " + insecttecton.getID());
        } else {
            System.err.println("The id does not belong to a Tecton.");
        }
    }

    /**
     * szomszédossá teszi a megadott tektonokat
     * 
     * @param args(0) az első tekton ID-ja
     * 
     * @param args(1) a második tekton ID-ja
     */
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

    /**
     * összeköti a megadott gombafonalakat
     * 
     * @param args(0) az első gombafonál ID-ja
     * 
     * @param args(1) a második gombafonál ID-ja
     */
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

    /**
     * beállítja a tektonok törési esélyét egy adott számra (százalékban megadva)
     * 
     * @param args(0) a törés esélye százalékban
     */
    private void setTectonsplitchance(List<String> args) {
        double chance = Double.valueOf(args.get(0)) / 100;
        Tecton.setSplitChance(chance);
        System.out.println("Global Tecton split chance set to: " + args.get(0) + "%");
    }

    /**
     * beállítja a gombafonál-ölő tektonok ölési esélyét egy adott számra
     * (százalékban megadva)
     * 
     * @param args(0) az ölés esélye százalékban
     */
    private void setTectonkillchance(List<String> args) {
        double chance = Double.valueOf(args.get(0)) / 100;
        ThreadKillingTecton.setKillChance(chance);
        System.out.println("ThreadKillingTecton kill chance set to: " + args.get(0) + "%");
    }

    /**
     * beállítja adott spórának az effektjét
     * 
     * @param args(0) a spóra id-je
     * @param args(1) az effekt
     */
    private void setEffectgeneration(List<String> args) {
        GameObject obj = game.getObject(Integer.valueOf(args.get(0)));
        if (obj instanceof MushroomSpore msp) {
            switch (args.get(1)) {
                case "BLOCK":
                    msp.setEffectGeneration(EffectTypes.BLOCK);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "SLOW":
                    msp.setEffectGeneration(EffectTypes.SLOW);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "SPEED":
                    msp.setEffectGeneration(EffectTypes.SPEED);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "STUN":
                    msp.setEffectGeneration(EffectTypes.STUN);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "NO":
                    msp.setEffectGeneration(EffectTypes.NO);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "DUPLICATE":
                    msp.setEffectGeneration(EffectTypes.DUPLICATE);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                case "RANDOM":
                    msp.setEffectGeneration(EffectTypes.RANDOM);
                    System.out.println("Effect of spore " + args.get(0) + " set to " + args.get(1));
                    break;
                default:
                    System.err.println("Effect not recognised.");
                    break;
            }
        } else {
            System.err.println("The id does not belong to a MushroomSpore.");
        }
    }

    /**
     * beállítja egy adott gombafonál növekedési és vágási állapotát
     * 
     * @param args(0) a gombafonál ID-ja
     * 
     * @param args(1) a vágási állapot
     * 
     * @param args(2) a növekedési állapot
     */
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
                case "grown":
                    mt.setGrowState(GrowState.GROWN);
                    break;
                default:
                    System.err.println("Grow state not recognized. Possible states: GROWN, SPROUT");
                    break;
            }
        } else {
            System.err.println("The id does not belong to a MushroomThread.");
        }
    }

    /**
     * Szétválasztja a paraméterként kapott listából (egy fájl sora szavakra bontva)
     * a kulcsot(első és második token) és az
     * argumentumokat(ha van, harmadik, és ha van, negyedik token) és a HashMapban
     * tárolt parancsok közül meghívja a megfelelőt
     * 
     * @param args(0) és
     * @param args(1) az elvégzendő parancs típusa, és az objektum amin
     *                el kell végezni
     * 
     * @param args(2) és
     * @param args(3) a parancsok paraméterei, nem feltétlenül vannak
     */
    public void processConfig(List<String> args) {
        String key;
        boolean state = false;
        /*
         * Egy három szavas parancs van, a SET MUSHROOMTHREAD STATE, a többihez mind 2
         * szavas parancs kell, így a SET MUSHROOMTHREAD STATE-t külön kell kezelni az
         * elején hogy a STATE-t ne argumentumként vegye át értelmetlen parancshoz
         */
        if (args.size() >= 2 && args.get(0).equalsIgnoreCase("SET") && args.get(1).equalsIgnoreCase("MUSHROOMTHREAD")) {
            key = "SET MUSHROOMTHREAD STATE";
            state = true;
        } else {
            key = args.get(0).toUpperCase() + " " + args.get(1).toUpperCase();
        }

        InterpreterCommand command = configmap.get(key);
        if (command != null) {
            if (state) {
                command.execute(args.subList(3, args.size()));
            } else {
                command.execute(args.subList(2, args.size()));
            }
        } else {
            System.err.println("Unknown command: " + key);
        }
    }

    /**
     * a paraméterként kapott fájlnévhez tartozó fájl sorait beolvassa, és a
     * processConfig() függvénnyel feldogoztatja
     * 
     * @param args(0) a betöltendő fájl neve
     */
    public void load(List<String> args) {
        String filename = args.get(0);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                List<String> configargs = Arrays.asList(line.split("\\s+"));
                processConfig(configargs);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * elmenti a paraméterként kapott néven egy fájlba a kiírhatóü játékbeli
     * objektumokat
     * 
     * @param args(0) a fájl neve, ahova a játék a mentést végzi
     */
    private void save(List<String> args) {
        String filename = args.get(0);
        File file = new File(filename);

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            Logger.printError("Could not prepare file: " + e.getMessage());
            return;
        }

        for (GameObject obj : game.getGameObjects()) {
            IOHandler.save(file, obj);
        }

        System.out.println("Save complete: " + filename);
    }

    /**
     * Kiírja a standard kimenetre az összes objektumot, ami a paraméterként kapott
     * típussal rendelkezik
     * 
     * @param args(0) a listázandó típus
     */
    public void list(List<String> args) {
        String typeName = args.get(0);
        System.out.println("Objects of type '" + typeName + "':");

        boolean found = false;
        for (GameObject obj : game.getGameObjects()) {
            if (obj.getClass().getSimpleName().equalsIgnoreCase(typeName)) {
                System.out.println(obj.getOutputString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No objects found for type: " + typeName);
        }
    }

    /**
     * kiírja standard kimenetre az adott id-vel rendelkező objektumot
     * 
     * @param args(0) az id
     */
    public void show(List<String> args) {
        int id = Integer.valueOf(args.get(0));
        System.out.println(game.getObject(id).getOutputString());
    }

    /**
     * adott gombatesttel spórát szórat
     * 
     * @param args(0) a gombatest id-ja
     */
    public void distributespores(List<String> args) {
        int id = Integer.valueOf(args.get(0));
        GameObject mushroombody = game.getObject(id);
        if (mushroombody instanceof MushroomBody mb) {
            mb.distributeSpores();
            System.out.println("Spore distribution successful");
        } else {
            System.err.println("The object with the specified ID isn't a MushroomBody");
        }
    }

    public void eatinsect(List<String> args) {
        GameObject mt = game.getObject(Integer.valueOf(args.get(0)));
        if (mt instanceof MushroomThread) {
            ((MushroomThread) mt).eat();
            System.out.println("Insect eaten successfully");
        } else {
            System.err.println("The object with the specified ID isn't a MushroomThread");
        }
    }

    /**
     * gombatestet növeszt az adott id-jű gombafonal tektonjára
     * 
     * @param args(0) a gombafonál id-je
     */
    public void growbody(List<String> args) {
        int threadid = Integer.valueOf(args.get(0));
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

    /**
     * adott gombafonál létrehoz egy új gombafonalat az adott tecton felé
     * 
     * @param args(0) a gombafonál id-je
     * @param args(1) a tekton id-je
     */
    public void grow(List<String> args) {
        int threadid = Integer.valueOf(args.get(0));
        int tectonid = Integer.valueOf(args.get(1));
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

    /**
     * adott rovart átmozget egy adott tektonra
     * 
     * @param args(0) a rovar id-je
     * @param args(1) a tekton id-je
     */
    public void move(List<String> args) {
        int insectid = Integer.valueOf(args.get(0));
        int tectonid = Integer.valueOf(args.get(1));
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

    /**
     * adott rovar megeszik egy spórát a tektonról, amin van
     * 
     * @param args(0) a rovar id-je
     */
    public void eatspore(List<String> args) {
        int insectid = Integer.valueOf(args.get(0));
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

    /**
     * adott rovar elvág egy adott id-jű gombafonalat
     * 
     * @param args(0) a rovar id-je
     * @param args(0) a gombafonál id-je
     */
    public void cutthread(List<String> args) {
        int insectid = Integer.valueOf(args.get(0));
        int threadid = Integer.valueOf(args.get(1));
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

    /**
     * az összes köröket figyelő objektumot átléptet a következő körbe
     */
    public void nextround() {
        game.nextRound();
    }
}
