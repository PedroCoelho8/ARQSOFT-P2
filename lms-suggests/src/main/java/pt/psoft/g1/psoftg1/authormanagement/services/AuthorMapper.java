package pt.psoft.g1.psoftg1.authormanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper extends MapperInterface {
    public abstract Author create(CreateAuthorRequest request);

}
