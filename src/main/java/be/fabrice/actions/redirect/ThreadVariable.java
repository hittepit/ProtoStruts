package be.fabrice.actions.redirect;

public class ThreadVariable {
	private ThreadLocal<String> value = new ThreadLocal<String>();

	private static ThreadVariable instance = new ThreadVariable();
	
	private ThreadVariable(){
		
	}
	
	public static ThreadVariable getInstance(){
		return instance;
	}
	
	public void set(String value){
		this.value.set(value);
	}
	
	public String get(){
		return this.value.get();
	}
}
