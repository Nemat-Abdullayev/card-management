package az.guavapay.mapper;

import az.guavapay.model.order.OrderUpdateRequest;
import az.guavapay.model.user.User;
import az.guavapay.model.order.Order;
import az.guavapay.model.order.OrderDTO;
import az.guavapay.model.order.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mappings({
            @Mapping(target = "cardType", source = "request.cardType"),
            @Mapping(target = "cardHolderName", source = "request.cardHolderName"),
            @Mapping(target = "period", source = "request.period"),
            @Mapping(target = "urgent", source = "request.urgent"),
            @Mapping(target = "codeWord", source = "request.codeWord"),
            @Mapping(target = "user", source = "user"),
    })
    void setname() {
    }

    public abstract Order mapToEntity(OrderRequest request, User user);


    @Mappings({
            @Mapping(target = "id", source = "order.id"),
            @Mapping(target = "orderCreationTime", source = "order.regDate"),
            @Mapping(target = "status", source = "order.status"),
    })
    void setname1() {
    }

    public abstract OrderDTO mapResponse(Order order);

    @Mappings({
            @Mapping(target = "cardType", source = "updateRequest.cardType"),
            @Mapping(target = "cardHolderName", source = "updateRequest.cardHolderName"),
            @Mapping(target = "period", source = "updateRequest.period"),
            @Mapping(target = "urgent", source = "updateRequest.urgent"),
            @Mapping(target = "codeWord", source = "updateRequest.codeWord"),
            @Mapping(target = "user", source = "user"),
    })
    void setname2() {
    }

    public abstract Order mapToEntity(OrderUpdateRequest updateRequest, User user);
}
