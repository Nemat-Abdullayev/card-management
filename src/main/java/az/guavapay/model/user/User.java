package az.guavapay.model.user;


import az.guavapay.model.base.BaseEntity;
import az.guavapay.model.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String username;
    private String password;
    private String fullName;
    private String token;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> order;


}
