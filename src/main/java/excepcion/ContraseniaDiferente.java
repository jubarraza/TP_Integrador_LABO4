package excepcion;

public class ContraseniaDiferente extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ContraseniaDiferente() {
		
	}

	@Override
	public String getMessage() {
		return "Las contraseñas no son iguales!";
	}
	
	
}
