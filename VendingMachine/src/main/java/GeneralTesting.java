import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeneralTesting {
    public static void main(String[] args) {
        BigDecimal bd = new BigDecimal("2.39");
        BigDecimal bd2 = new BigDecimal("1.3");
        BigDecimal sum;
        sum = (bd.add(bd2)).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println(sum);
    }
}
