package it.polito.pd2.WF.sol4;

import javax.xml.bind.Unmarshaller;

public class MyListener extends Unmarshaller.Listener {
	
	public MyListener() {
	}
	
	@Override
	public void beforeUnmarshal(Object target, Object parent) {
		if(target instanceof Unmarshallable)
			((Unmarshallable)target).beforeUnmarshal(parent);		
	}
	
	@Override
	public void afterUnmarshal(Object target, Object parent) {
		if(target instanceof Unmarshallable)
			((Unmarshallable)target).afterUnmarshal(parent);
	}
	
}
