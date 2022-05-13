package uz.nt.springhibernate.helper;

public class NumberHelper {

    public static Integer tiInt(String str){
        try {
            return StringHelper.isValid(str)  ? Integer.valueOf(str) : null ;
        }catch (Exception e){
            return null;
        }
    }

    public static boolean isValidNumber(String num){
        if (num!= null && StringHelper.isValid(num)){
            try {
                Integer.valueOf(num);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
