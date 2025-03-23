package projlab.fungorium.interfaces;

/**
 * Az állapottal rendelkező osztályoktól le lehet kérni az állapotukat Stringként, ha megvalósítják az interfacet.
 */
public interface PrintableState {
    public String getStateString();
}
