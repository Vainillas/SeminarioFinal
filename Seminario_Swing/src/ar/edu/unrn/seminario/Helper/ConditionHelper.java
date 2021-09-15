package ar.edu.unrn.seminario.Helper;
//Hello there
public  class ConditionHelper {
	public static boolean stringIsEmpty(String s) {
		return s.equals("") || s.equals(" ");
	}
	
	public static boolean IsEmpty (Object obj) {
		return obj.equals("");
	}
	
	public static boolean stringIsNull(String s){
		return  s == null;
	}
	
	public static boolean IsActive(boolean state) {
		return state;
	}
	public static boolean IsActive(String state) {
		return state.equals("activo");
	}
	public static boolean IsInactive(boolean state) {
		return !state;
	}
	public static boolean IsInactive(String state) {
		return state.equals("inactivo");
	}
	public static boolean IsNull(Object obj) {
		return obj == null ;
	}
	public static boolean IsIncorrectEmail(String email) {
		if(email.contains("@") && email.endsWith(".com"))
			return false;
		else
			return true;
	}
	
	public static boolean IsNotNumber(String number) {
        boolean validacion;

        try {
            Integer num = Integer.parseInt(number);
            validacion = false;
        }
        catch(NumberFormatException e) {
            return true;
        }

        return validacion;

    }
	
}
