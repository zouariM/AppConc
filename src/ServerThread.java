import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread implements Callable<Void> {
	
	private Socket socket;
	private Logger log;
	private Integer idClient; //juste pour le test, c'est le rang du client
	
	public ServerThread(Socket socket, Integer idClient){
		this.socket = socket;
		this.log = Logger.getLogger(this.getClass().getName());
		this.idClient = idClient;
	}
	
	@Override
	public Void call() throws Exception {
		this.log.log(Level.INFO, "UN client s'est connecté, on lit le contenu ...");
		Thread.sleep(10*1000);

		InputStream is = this.socket.getInputStream();
		byte bytes[] = new byte[1024];
		is.read(bytes);
		String request = new String(bytes);
			
		log.log(Level.INFO, "Le client demande : " + request);
		OutputStream os = socket.getOutputStream();
		os.write(this.getContent(request).getBytes());
			
		this.socket.close();
		return null;
	};
	
	private String getContent(String request){
		return "Ok, ca marhce ! Ton id est : " + this.idClient;
	}

}
