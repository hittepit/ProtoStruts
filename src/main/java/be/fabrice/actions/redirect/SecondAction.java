package be.fabrice.actions.redirect;

public class SecondAction {
	public String execute(){
		//Ne devrait pas marcher sauf si c'est le même thread qui fait la deuxième requête...
		//Le piège à con? Si la requête d'un autre utilisateur atteint le même thread du threadpool
		//la variable ThreadLocal est initialisée...
		System.out.println(ThreadVariable.getInstance().get());
		return "success";
	}
}
