import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class GeneralTesting {
    public static void main(String[] args) {
        BigDecimal bd = new BigDecimal("2.39");
        BigDecimal bd2 = new BigDecimal("1.3");
        BigDecimal sum;
        sum = (bd.add(bd2)).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println(sum);

        double test = 363;
        double result = 363.00 / 25.00;
        System.out.println(result);

        BigDecimal numberOfPennies = bd.multiply(BigDecimal.valueOf(100));
        System.out.println(numberOfPennies);

        BigDecimal test2 = numberOfPennies.divide(BigDecimal.valueOf(25));
        System.out.println(test2);

        if (test2.scale() != 0) {
            BigDecimal newBD = test2.setScale(0, RoundingMode.FLOOR);
            System.out.println(newBD);
        }
        System.out.println(test2);


        System.out.println("-------------------------------------------------");
        BigDecimal testChange = new BigDecimal("3.63");
        BigDecimal numberOfP = (testChange.multiply(BigDecimal.valueOf(100)));
        //234.00
        Map<String, Integer> coinMap = new HashMap<>();

        //if equal = 0
        //if a > b = 1
        //if b > a = -1

        System.out.println("change: $" + testChange);
        System.out.println("change in pennies: " + numberOfP);

        //System.out.println("testing int value of test change: " + testChange.intValue()); returns 3

        //checking if pennnies enough to cover quarters
        int res = numberOfP.compareTo(BigDecimal.valueOf(25));
        if (res == 0 || res == 1) {
            int quarterAmount = getQuarterAmount(numberOfP);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(quarterAmount).multiply(BigDecimal.valueOf(25));
            numberOfP = numberOfP.subtract(penniesToSubtract);
            coinMap.put("quarter", quarterAmount);
        }

        System.out.println("Current pennies " + numberOfP);


        int res2 = numberOfP.compareTo(BigDecimal.valueOf(10));
        if (res2 == 0 || res2 == 1) {
            int dimeAmount = getDimeAmount(numberOfP);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(dimeAmount).multiply(BigDecimal.valueOf(10));
            numberOfP = numberOfP.subtract(penniesToSubtract);
            coinMap.put("dimes", dimeAmount);
        }

        System.out.println("Current pennies " + numberOfP);

        int res3 = numberOfP.compareTo(BigDecimal.valueOf(5));
        if (res3 == 0 || res3 == 1) {
            int nickelAmount = getNickelAmount(numberOfP);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(nickelAmount).multiply(BigDecimal.valueOf(5));
            numberOfP = numberOfP.subtract(penniesToSubtract);
            coinMap.put("nickel", nickelAmount);
        }

        int res4 = numberOfP.compareTo(BigDecimal.valueOf(1));
        if (res4 == 0 || res4 == 1) {
            int penniesAmount = getPennies(numberOfP);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(penniesAmount).multiply(BigDecimal.valueOf(1));
            numberOfP = numberOfP.subtract(penniesToSubtract);
            coinMap.put("pennies", penniesAmount);
        }



        System.out.println("Current pennies " + numberOfP);

        System.out.println(coinMap.entrySet());
    }

    public static int getQuarterAmount(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(25))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }

    public static int getDimeAmount(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(10))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }
    public static int getNickelAmount(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(5))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }
    public static int getPennies(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(1))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }

}
