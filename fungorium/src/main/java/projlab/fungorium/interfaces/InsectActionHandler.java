package projlab.fungorium.interfaces;

import projlab.fungorium.models.Insect;

public interface InsectActionHandler {
	public void call(Insect insect) throws Exception;
}
