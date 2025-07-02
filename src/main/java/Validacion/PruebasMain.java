package Validacion;

import dao.TransferenciaDao;
import daoImpl.TransferenciaImpl;
import entidad.Transferencia;

public class PruebasMain {

	public static void main(String[] args) {

			TransferenciaDao transferenciaDao = new TransferenciaImpl();
			
			Transferencia transferencia = new Transferencia("5024-192534/4", "5024-747657/1", 1000.00);
			
			if(transferenciaDao.Insert(transferencia, "Exito Pepe"))
			{
				System.out.println("Ok");
			}
			else
			{
				System.out.println("Fallamos");
			}
				
				

	}

}
