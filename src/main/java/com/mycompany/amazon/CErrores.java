// Clase para manejar los errores
package com.mycompany.amazon;

public class CErrores {
    
    public class CedulaException extends Exception {
    private int errorCode;

    public CedulaException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
    // Método para obtener el mensaje de error según el número de error proporcionado
        public String getMensajeError(int numError) {
        String mensaje = "null"; // Mensaje predeterminado en caso de que el número de error no coincida con ningún caso

        // Switch para determinar el mensaje de error según el número de error
        switch (numError) {
            case 1:
                mensaje = "Cédula tiene más o menos de 10 dígitos";
                break;
            case 2:
                mensaje = "Cédula no es numérica";
                break;
            case 3:
                mensaje = "Cédula no es válida";
                break;
            case 4:
                mensaje = "Ingrese cédula";
                break;
            case 5:
                mensaje = "Cédula ya ingresada";
                break;
            default:
                break;
        }
        return mensaje; // Devuelve el mensaje de error correspondiente
    }

    // Método para lanzar la errores mediante Exceptions
    public void lanzarExcepcion(int numError) throws CedulaException {
        String mensaje = getMensajeError(numError);
        throw new CedulaException(numError, mensaje);
    }
}
