package Validacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validaciones {
    
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
}
