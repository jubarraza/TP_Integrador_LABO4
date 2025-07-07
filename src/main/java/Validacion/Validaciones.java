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
	private static final String selectEmail = "Select 1 from clientes where email like ?;";
	private static final String selectDNI ="Select 1 from clientes where dni like ?;";
    
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

public static boolean existeEmail(String email) {
        
    	PreparedStatement statement = null;
    	Connection conexion = (Connection) Conexion.getConexion().getSQLConexion();
        ResultSet resultSet = null;       
        boolean existeEmail = false;

        try {
                      
            statement = (PreparedStatement) conexion.prepareStatement(selectEmail);
            statement.setString(1, email); 
            resultSet = statement.executeQuery(); 

            if (resultSet.next()) { 
                existeEmail = true; 
            }
            

        } catch (SQLException e) {
            System.err.println("Error SQL al buscar el email " + e.getMessage());

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
        return existeEmail;
    }
    

public static boolean existeDNI(String DNI) {
    
	PreparedStatement statement = null;
	Connection conexion = (Connection) Conexion.getConexion().getSQLConexion();
    ResultSet resultSet = null;       
    boolean existeDNI = false;

    try {
                  
        statement = (PreparedStatement) conexion.prepareStatement(selectDNI);
        statement.setString(1, DNI); 
        resultSet = statement.executeQuery(); 

        if (resultSet.next()) { 
            existeDNI = true; 
        }
        

    } catch (SQLException e) {
        System.err.println("Error SQL al buscar el DNI" + e.getMessage());

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
    return existeDNI;
}

    
    
    
    
    
}
