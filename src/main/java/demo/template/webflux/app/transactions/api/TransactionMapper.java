package demo.template.webflux.app.transactions.api;

import static org.mapstruct.ReportingPolicy.IGNORE;

import demo.template.webflux.app.transactions.repository.TransactionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public abstract class TransactionMapper {

    @Mapping(target = "recipientId", source = "recipientId")
    @Mapping(target = "payeeId", source = "payeeId")
    public abstract TransactionEntity toEntity(TransactionDto transactionDto);

    @InheritInverseConfiguration
    public abstract TransactionDto toDto(TransactionEntity transactionEntity);

}
