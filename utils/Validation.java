package utils;

import annotation.Email;
import annotation.Required;
import annotation.Numeric;
import java.lang.reflect.Field;
import annotation.Date;

@SuppressWarnings("null")
public class Validation {
    
    public boolean checkValidation (Field attr, String valeur) throws Exception {
        boolean result = true;
        if (attr.isAnnotationPresent(Numeric.class)) {
            if (!valeur.matches("-?\\d+(\\.\\d+)?")){
                result = false;
                throw new Exception("L'input de l'attribut "+attr.getName()+" doit etre de type numerique ");
            }
        }
        if (attr.isAnnotationPresent(Email.class)){
            if (!valeur.contains("@")){
                result = false;
                throw new Exception("L'input de l'attribut "+attr.getName()+" doit etre de type email ");
            }
        }
        if (attr.isAnnotationPresent(Date.class)){
            try {
                java.sql.Date.valueOf(valeur);
            }catch (Exception e){
                result = false;
                throw new Exception("L'input de l'attribut "+attr.getName()+" doit etre de type date ");
            }
        }
        if (attr.isAnnotationPresent(Required.class)){
            if (valeur == null || valeur.equals("")) {
                result = false;
                throw new Exception("L'input de l'attribut "+attr.getName()+" doit etre completer ");
            }
        }

        return result;
    }
}