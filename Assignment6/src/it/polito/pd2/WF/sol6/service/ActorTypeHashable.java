package it.polito.pd2.WF.sol6.service;


import it.polito.pd2.WF.sol6.service.gen.ActorType;

public class ActorTypeHashable extends ActorType {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ActorTypeHashable))
			return false;
		ActorTypeHashable other = (ActorTypeHashable) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getRole() == null) {
			if (other.getRole() != null)
				return false;
		} else if (!getRole().equals(other.getRole()))
			return false;
		return true;
	}
}
