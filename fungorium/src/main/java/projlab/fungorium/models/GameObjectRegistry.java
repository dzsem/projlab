package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;

public class GameObjectRegistry {
	private List<Tecton> tectons;
	private List<ThreadKillingTecton> threadKillingTectons;
	private List<KeepAliveTecton> keepAliveTectons;
	private List<SingleThreadTecton> singleThreadTectons;
	private List<InfertileTecton> infertileTectons;
	private List<MushroomBody> mushroomBodies;
	private List<MushroomThread> mushroomThreads;
	private List<Insect> insects;
	private List<MushroomSpore> mushroomSpores;

	public GameObjectRegistry() {
		tectons = new ArrayList<>();
		threadKillingTectons = new ArrayList<>();
		keepAliveTectons = new ArrayList<>();
		singleThreadTectons = new ArrayList<>();
		infertileTectons = new ArrayList<>();
		mushroomBodies = new ArrayList<>();
		mushroomThreads = new ArrayList<>();
		insects = new ArrayList<>();
		mushroomSpores = new ArrayList<>();
	}

	// @formatter:off
	public void registerTecton(Tecton t) { tectons.add(t); }
	public void registerThreadKillingTecton(ThreadKillingTecton t) { threadKillingTectons.add(t); }
	public void registerKeepAliveTecton(KeepAliveTecton t) { keepAliveTectons.add(t); }
	public void registerSingleThreadTectons(SingleThreadTecton t) { singleThreadTectons.add(t); }
	public void registerInfertileTecton(InfertileTecton t) { infertileTectons.add(t); }
	public void registerMushroomBody(MushroomBody b) { mushroomBodies.add(b); }
	public void registerMushroomThread(MushroomThread th) { mushroomThreads.add(th); }
	public void registerInsect(Insect i) { insects.add(i); }
	public void registerMushroomSpore(MushroomSpore s) { mushroomSpores.add(s); }

	public void unregisterInsect(Insect i) { insects.remove(i); }
	public void unregisterMushroomSpore(MushroomSpore s) { mushroomSpores.remove(s); }
	public void unregisterMushroomBody(MushroomBody b) { mushroomBodies.remove(b); }

	public List<Tecton> getTectons() { return tectons; }
	public List<ThreadKillingTecton> getThreadKillingTectons() { return threadKillingTectons; }
	public List<KeepAliveTecton> getKeepAliveTectons() { return keepAliveTectons; }
	public List<SingleThreadTecton> getSingleThreadTectons() { return singleThreadTectons; }
	public List<InfertileTecton> getInfertileTectons() { return infertileTectons; }
	public List<MushroomBody> getMushroomBodies() { return mushroomBodies; }
	public List<MushroomThread> getMushroomThreads() { return mushroomThreads; }
	public List<Insect> getInsects() { return insects; }
	public List<MushroomSpore> getMushroomSpores() { return mushroomSpores; }
	// @formatter:on
}
