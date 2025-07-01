package dasturlashasil.uz.util;


public class PhoneUtil {

    public static String toLocalPhone(String phone) {
        if (phone == null) {
            return null;
        }
        phone = phone.trim();
        if (phone.startsWith("+")) {
            return phone;
        } else {
            return "+" + phone;
        }
    }
}
