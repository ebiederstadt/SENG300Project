import java.util.ArrayList;
import java.util.Set;
/**
 * Logger used for debugging purposes
 * @author Elvin
 *
 */
public class Logger {
	private boolean enabled;
	private ArrayList<String> logBook = new ArrayList<String>();
	
	/**
	 * Logger constructor defaulted to disabled
	 */
	public Logger(){
		this.enabled = false;
		toLogBook("Logger: initialized");
	}
	
	/**
	 * Logger constructor with option
	 * to enable logger
	 * @param caller -- function which initiated the logger
	 * @param initiator -- function which initiated the logger
	 * @param willBeEnabled
	 */
	public Logger(boolean willBeEnabled){
		this.enabled = willBeEnabled;
		toLogBook("Logger: initialized from ");
	}
	
	/**
	 * Logger constructor with option
	 * to alert that logger is enabled
	 * @param willEnable
	 * @param alert
	 */
	public Logger(boolean willEnable, boolean alert){
		this.enabled = willEnable;
		if(alert) this.log("Logger enabled");
		toLogBook("Logger: initialized");
	}

	/**
	 * Logs string if logger is enabled using
	 * System.out.println
	 * @param str
	 * @return this for chaining functions
	 */
	public Logger log(String str){
		if(enabled) log(str, true);
		toLogBook(str);
		return this;
	}
	
	/**
	 * Logs string if logger is enabled
	 * using System.out.println
	 * 
	 * If flag is true, logs string regardless
	 * of whether the logger is enabled
	 * If flag is false, an alternative
	 * print method is used: System.out.print
	 * 
	 * @param str
	 * @param flag
	 * @return this for chaining methods
	 */
	public Logger log(String str, boolean flag){
		if(flag) System.out.println(str);
		else if(enabled) log(str, true);
		toLogBook(str);
		return this;
	}	
	
	/**
	 * Enables logger
	 * @return this for chaining methods
	 */
	public Logger enable(){
		enabled = true;
		toLogBook("Logger: enable");
		return this;
	}
	
	/**
	 * Enables logger with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger enable(boolean alert){
		enabled = true;
		if(alert) log("Logger enabled", true);
		toLogBook("Logger: enable");
		return this;
	}

	/**
	 * Disables logger
	 * @return this for chaining methods
	 */
	public Logger disable(){
		enabled = false;
		toLogBook("Logger: disable");
		return this;
	}
	
	/**
	 * Disables logger with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger disable(boolean alert){
		enabled = false;
		if(alert) log("Logger disabled", true);
		toLogBook("Logger: disable");
		return this;
	}
	
	/**
	 * Toggles from disable to enable or vice versa
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger toggleEnable(){
		enabled = !enabled;
		toLogBook("Logger: toggled to "+ this.enabled);
		return this;
	}
	
	/**
	 * Toggles from disable to enable or vice versa
	 * with option to alert
	 * @param alert
	 * @return this for chaining methods
	 */
	public Logger toggleEnable(boolean alert){
		enabled = !enabled;
		if(alert){
			log("Logger toggled to ", true);
			if(enabled) log("enabled", true);
			else log("disabled", true);
		}
		toLogBook("Logger: toggled to "+ this.enabled);
		return this;
	}
	
	public Logger printAllLogs(){
		for(String log: logBook){
			log(log, true);
		}
		return this;
	}
	
	/**
	 * Checks and returns if logger is enabled
	 * @return whether function is enabled
	 */
	public boolean isEnabled(){
		return this.enabled;
	}
	
	/**
	 * Returns new instance of the log book
	 * @return
	 */
	public ArrayList<String> getLogBook(){
		ArrayList<String> result = new ArrayList<String>();
		for(String log: logBook){
			result.add(log);
		}
		return result;
	}
	
	/**
	 * Adds string to log book
	 * @param str
	 */
	private void toLogBook(String str){
		logBook.add(str);
	}

	public Logger log(ArrayList<String> allNodes, boolean flag) {
		if(flag){
			for(String node: allNodes){
				this.log(node, true);
			}
		}
		return this;
	}

	public Logger log(Set<String> allNodes, boolean flag) {
		if(flag){
			for(String node: allNodes){
				this.log(node, true);
			}
		}
		return this;
	}

	public Logger log(Set<String> allNodes) {
		if(enabled){
			for(String node: allNodes){
				this.log(node, true);
			}
		}
		return this;
	}
}
