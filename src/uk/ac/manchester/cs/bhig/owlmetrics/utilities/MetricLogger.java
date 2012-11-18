package uk.ac.manchester.cs.bhig.owlmetrics.utilities;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MetricLogger {
	private static Logger logger;
	
	public static Logger create(){
		 logger = Logger.getLogger("MyLog");
		 try {
			 FileHandler fh = new FileHandler("MetricsLog.log", true);

			 logger.addHandler(fh);
			 logger.setLevel(Level.ALL);
			 SimpleFormatter formatter = new SimpleFormatter();
			 fh.setFormatter(formatter);
		     
			 logger.info("Setup of the logger is done");
			 
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return logger;
	}
	
	public static void log(String msg){
		logger.info(msg);
	}
	
	public static Logger getLogger(){
		return logger;
	}
	
}
