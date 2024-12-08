package demo.template.webflux.app.transactions.api;

import demo.template.webflux.app.transactions.repository.TransactionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Mapping(target = "recipient", source = "recipientId")
    @Mapping(target = "payee", source = "payeeId")
    public abstract TransactionEntity toEntity(TransactionDto transactionDto);

    @InheritInverseConfiguration
    public abstract TransactionDto toDto(TransactionEntity transactionEntity);

}
