package Validacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daoImpl.Conexion;

public class Validaciones {
	
	private static final String select = "select * from cuentas where num_de_cuenta like ?;";
	private static final String buscarDni = "select dni from clientes where dni = ?;";
	private static final String buscarNumCuenta = "select num_de_cuenta from cuentas where num_de_cuenta = ?;";
	private static final String buscarCbu = "select cbu from cuentas where cbu = ?;";
    
    public static boolean Verificarfecha(String fecha) {    
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate fechaValidar;
        if (fecha != null && !fecha.trim().isEmpty()) {
            try {
                fechaValidar = LocalDate.parse(fecha, formato);
            } catch (DateTimeParseException e) {
                return false;
            }
            LocalDate fechaMinima = LocalDate.of(1900, 1, 1);
            LocalDate fechaMaxima = LocalDate.of(2099, 12, 31);

            if (!fechaValidar.isBefore(fechaMinima) && !fechaValidar.isAfter(fechaMaxima)) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean existeCuenta(String cbu) {
        
    	PreparedStatement statement = null;
    	Connection conexion = (Connection) Conexion.getConexion().getSQLConexion();
        ResultSet resultSet = null;       
        boolean cuentaExiste = false;

        try {
                      
            statement = (PreparedStatement) conexion.prepareStatement(select);
            statement.setString(1, cbu); 
            resultSet = statement.executeQuery(); 

            if (resultSet.next()) { 
                cuentaExiste = true; 
            }
            

        } catch (SQLException e) {
            System.err.println("Error SQL al buscar la cuenta" + e.getMessage());

        } catch (Exception e) {
           
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                e.printStackTrace();
            }           
        }
        return cuentaExiste;
    }
    
    public static boolean MismaCuenta(String Cuenta1, String Cuenta2)
    {	
    	if(Cuenta1.equals(Cuenta2))
    	{
    		return true;
    	}
    	return false;
    }

    public boolean buscarDni(String dni) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		
		try {
			statement = conexion.prepareStatement(buscarDni);
			statement.setString(1, dni);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	existe = true;
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return existe;
	}
    
    public boolean buscarNumCuenta(String numCuenta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		
		try {
			statement = conexion.prepareStatement(buscarNumCuenta);
			statement.setString(1, numCuenta);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	existe = true;
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return existe;
	}
    
    public boolean buscarCbu(String cbu) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		
		try {
			statement = conexion.prepareStatement(buscarCbu);
			statement.setString(1, cbu);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	existe = true;
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return existe;
	}
    
    
    
    
    
}
