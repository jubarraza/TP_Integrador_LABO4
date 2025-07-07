package negocioImpl;

import dao.TransferenciaDao;
import entidad.Transferencia;
import negocio.negocioTransferencia;

public class negocioTransferenciaImpl implements negocioTransferencia {
	
	private TransferenciaDao transferenciaDao;

	public negocioTransferenciaImpl(TransferenciaDao transferenciaDao) {
		this.transferenciaDao = transferenciaDao;
	}

	@Override
	public boolean Insert(Transferencia transferencia, String detalle) {
		return transferenciaDao.Insert(transferencia, detalle);
	}
	
	
}
