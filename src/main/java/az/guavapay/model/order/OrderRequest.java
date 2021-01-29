package az.guavapay.model.order;

import az.guavapay.model.enums.CardType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for order request")
public class OrderRequest {

    @ApiModelProperty("cardType for card order")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ApiModelProperty("Name and Surname for order")
    private String cardHolderName;

    @ApiModelProperty("cardPeriod duration for order")
    private Integer period;

    @ApiModelProperty("card priority for for order")
    private boolean urgent;

    @ApiModelProperty("codeword for security")
    private String codeWord;
}
