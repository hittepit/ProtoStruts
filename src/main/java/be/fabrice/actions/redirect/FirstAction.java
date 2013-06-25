package be.fabrice.actions.redirect;

/**
 * <p>Initialement définie dans un exercice de redirection, cette action ainsi que {@link SecondAction}
 * montrent un problème potentiel lors de l'utilisation d'un ThreadLocal.</p>
 * <p>La redirection est une nouvelle requête, à laquelle un Thread est assigné. Il n'y a aucune raison
 * que ce Thread soit le même que celui utilisé avant le redirect. Néanmoins, avec Tomcat, le ThreadPool
 * utilise généralement le même Thread et, surtout, les variables threadlocal de ce thread restent initialisées...</p>
 * @author fabrice.claes
 *
 */
public class FirstAction {
	
	public String input(){
		return "input";
	}
	
	public String submit(){
		//Ceci est un piège à con...
		ThreadVariable.getInstance().set("hello");
		return "next";
	}
}
