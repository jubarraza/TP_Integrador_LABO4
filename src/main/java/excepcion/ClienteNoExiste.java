package excepcion;

public class ClienteNoExiste extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteNoExiste() {
		
	}

	@Override
	public String getMessage() {
		return "No se puede crear la cuenta, porque el cliente es inexistente.";
	}
	
}
