package az.guavapay.util;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Random;


@Builder
@NoArgsConstructor
public class CardNumberUtil {

    public String generateCardNumber() {
        Random rand = new Random();
        return String.format((Locale) null,
                "52%02d-%04d-%04d-%04d",
                rand.nextInt(100),
                rand.nextInt(10000),
                rand.nextInt(10000),
                rand.nextInt(10000));
    }

}
