package excepcion;

public class ClienteExcedeCuentas extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ClienteExcedeCuentas() {
		
	}

	@Override
	public String getMessage() {
		return "no se puede crear mas cuentas, excede el total de 3 cuentas";
	}
	
	
}	
