import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
	
	private Integer portNum;
	private Logger log;
	private Integer nbClient; // nombre totale du client
	private ExecutorService executor;
	
	//maxClient est la taille du pool de thread qui traitent la communication avec le client
	public Server(Integer portNum, Integer maxClient){
		this.portNum = portNum;
		this.log = Logger.getLogger(this.getClass().getName());
		this.nbClient = 0;
		this.executor = Executors.newFixedThreadPool(maxClient);
	}
	
	public void start(){
		try {
			ServerSocket serverSocket = new ServerSocket(this.portNum);
			this.log.log(Level.INFO, "Attend une nouvelle connexion ...");
			
			while(true){
				Socket socket = serverSocket.accept();
				nbClient++;
				ServerThread serverThread = new ServerThread(socket, nbClient);
				
				this.executor.submit(serverThread);
			}
			
		} catch (IOException e) {
			this.log.log(Level.WARNING, "Erreur de connexion !!!");
			e.printStackTrace();
		}
		
	}

}
