package az.guavapay.util;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class AccountNumberUtil {

    public String generateAccountNumber() {
        return new SecureRandom().ints(0, 36)
                .mapToObj(i -> Integer.toString(i, 36))
                .map(String::toUpperCase).distinct().limit(16).collect(Collectors.joining())
                .replaceAll("([A-Z0-9]{4})", "$1-").substring(0, 19);
    }
}
