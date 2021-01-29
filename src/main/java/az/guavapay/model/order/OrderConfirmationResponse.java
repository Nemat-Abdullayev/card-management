package az.guavapay.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("model for confirmation response")
public class OrderConfirmationResponse {

    @ApiModelProperty("httpStatus for response result")
    private HttpStatus httpStatus;

    @ApiModelProperty("16 digit card Number for confirmed order")
    private String cardNumber;

    @ApiModelProperty("Account number for Confirmed order")
    private String accountNumber;
}
