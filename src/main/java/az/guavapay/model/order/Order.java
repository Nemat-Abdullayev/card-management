package az.guavapay.model.order;


import az.guavapay.model.user.User;
import az.guavapay.model.base.BaseEntity;
import az.guavapay.model.enums.CardType;
import az.guavapay.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_INFO")
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String cardHolderName;
    private Integer period;
    private boolean urgent;
    private String codeWord;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.NOT_CONFIRMED;

    private String cardNumber;
    private String accountNumber;
    @JsonIgnore

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Transient
    private Long tempId;
}
