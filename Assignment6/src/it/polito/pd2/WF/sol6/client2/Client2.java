package it.polito.pd2.WF.sol6.client2;
import java.net.MalformedURLException;
import java.net.URL;


public class Client2 {

	private static URL urlNextPort(URL u) throws MalformedURLException {
		int port = u.getPort();
		if(port == -1)
			port=80;
		return new URL(u.getProtocol() + "://" + u.getHost() + ":" +
				Integer.toString(port+1) + u.getFile());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		if(args.length!=2){
			System.err.println("usage: Client2 <ActorName> <ActorRole>");
			System.exit(1);
		}
		
		try {
			ClientCore client=new ClientCore(args[0], args[1]);
			String strURL=System.getProperty("it.polito.pd2.WF.sol6.URL");
			if(strURL!=null) {
				URL url = new URL(strURL);
				client.setWfEndpoint(url.toExternalForm());
				url=urlNextPort(url);
				client.setWfInfoEndpoint(url.toExternalForm());
				url=urlNextPort(url);
				client.setProcEndpoint(url.toExternalForm());
			}

			/*-----TEST *
			WorkflowMonitorFactoryImpl factory=new WorkflowMonitorFactoryImpl();
			WorkflowMonitor monitor;
			int i=0;
			while(true) {
				client.run();
				monitor = factory.newWorkflowMonitor();
				i++;
			}*--------*/
			
			client.run();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (ClientException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Bye!");
	}
}
