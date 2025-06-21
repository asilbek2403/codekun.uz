package dasturlashasil.uz.util;

public class PageUtil {
    public static int page(int page) {
        if (page > 0) return page - 1;
        return 1;
    }
}