import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Prova {
	private Object o;
	private ReentrantReadWriteLock l;
	public Prova() {
		l=new ReentrantReadWriteLock();
		l.writeLock().lock();
		l.readLock().lock();
		System.out.println("bloccato");
		l.writeLock().unlock();
		l.readLock().unlock();
		
		o=new Object();
		synchronized (o) {
			synchronized (o) {
				System.out.println("Ciao");
				
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Prova();		

	}

}