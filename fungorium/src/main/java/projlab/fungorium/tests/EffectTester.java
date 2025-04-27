package projlab.fungorium.tests;

import projlab.fungorium.models.Insect;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.effects.*;
import projlab.fungorium.utilities.Logger;


public class EffectTester {
    /**
     * ez a teszt insect
     */
    private static Insect insect;

    /**
     * ezek a teszt effectek
     */
    private static BlockEffect blockEffect;
    private static StunEffect stunEffect;
    private static SpeedEffect speedEffect;
    private static SlowEffect slowEffect;

    /**
     * ez a függvény mindig inicializálja a változókat, és vissza teszi alap állapotba
     */
    public static void init(){
        insect=new Insect(0, new Tecton());
        blockEffect=new BlockEffect();
        stunEffect=new StunEffect();
        speedEffect=new SpeedEffect();
        slowEffect=new SlowEffect();
    }

    /**
     * Ez a teszt a speed effectet teszteli
     */

    public static void SpeedTest(){
        init();
        speedEffect.applyEffect(insect);
        Logger.printState(speedEffect);
        Logger.printState(insect);
    }

    /**
     * Ez a teszt a slow effectet teszteli (majd a controllerben végez el változtatásokat, jelenleg még nincs ellenőrízhető része)
     */

    public static void SlowEffectTest(){
        init();
        slowEffect.applyEffect(insect);
        Logger.printState(slowEffect);
        Logger.printState(insect);

    }
    /**
     * Ez a teszt a stun effectet teszteli, ami beállítja a canMove paramétert (ezzel meggátolja a következő körben a mozgását)
     */
    public static void StunEffectTest(){
        init();
        stunEffect.applyEffect(insect);
        Logger.printState(stunEffect);
        Logger.printState(insect);
    }
    /**
     * Ez a teszt a block effectet teszteli, ami az insecten beállítka a canCutot és elindítja a számlálót(nem 0 lesz)
     */
    public static void BlockEffectTest(){
        init();
        blockEffect.applyEffect(insect);
        Logger.printState(blockEffect);
        Logger.printState(insect);
    }
}
