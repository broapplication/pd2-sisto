package it.polito.pd2.WF.sol4;

public interface Unmarshallable {
	public void beforeUnmarshal(Object parent);
	public void afterUnmarshal(Object parent);
}
