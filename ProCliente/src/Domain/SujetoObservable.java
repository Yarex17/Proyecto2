package Domain;

public interface SujetoObservable {
	
	public void agregarObservador(Observador observador);
	
	public void notificar();

}
