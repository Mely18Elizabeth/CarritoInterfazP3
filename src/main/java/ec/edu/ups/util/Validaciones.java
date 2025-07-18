package ec.edu.ups.util;

public class Validaciones {

    public static void validarCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            throw new CedulaInvalidaException("La cédula debe tener exactamente 10 dígitos numéricos.");
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            throw new CedulaInvalidaException("Código de provincia inválido en la cédula.");
        }

        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int total = 0;

        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            int resultado = digito * coeficientes[i];
            total += (resultado > 9) ? resultado - 9 : resultado;
        }

        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int calculado = (total % 10 == 0) ? 0 : 10 - (total % 10);

        if (digitoVerificador != calculado) {
            throw new CedulaInvalidaException("Dígito verificador incorrecto.");
        }
    }
}
