package excepcion;

public class SaldoInsuficiente extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SaldoInsuficiente() {
		
	}

	@Override
	public String getMessage() {
		return "no se puede realizar la transferencia, saldo insuficiente";
	}
	
	
}
