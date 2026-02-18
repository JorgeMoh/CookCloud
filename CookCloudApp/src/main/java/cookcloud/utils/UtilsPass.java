package cookcloud.utils;

import org.mindrot.jbcrypt.BCrypt;

public class UtilsPass {

    /**
     * Metodo que cifra una cadena de texto
     * @param pass casena de texto introducida
     * @return cadena de texto cifrada
     */
    public static String cifrarPass(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    /**
     * Metodo que compara una cadena de texto plano con otra cifrada
     * @param pass contraseña plana
     * @param hash contraseña cifrada
     * @return true en caso de que sean iguales y false en caso de que no lo sean
     */
    public static boolean verificarPass(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }
}
