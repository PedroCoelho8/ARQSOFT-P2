package pt.psoft.g1.psoftg1.genremanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GenreViewMapper extends MapperInterface {

    @Mapping(target = "genre", source = "genre")
    public abstract GenreView toGenreView(Genre genre);

    public abstract GenreView mapStringToGenreView(String genre);

    public abstract GenreLendingsView toGenreAvgLendingsView(GenreLendingsDTO dto);

    public abstract List<GenreLendingsView> toGenreAvgLendingsView(List<GenreLendingsDTO> dtos);

    @Mapping(target = "lendingsCount", source = "values")
    public abstract GenreLendingsCountPerMonthView toGenreLendingsCountPerMonthView(GenreLendingsPerMonthDTO dto);

    public abstract List<GenreLendingsCountPerMonthView> toGenreLendingsCountPerMonthView(
            List<GenreLendingsPerMonthDTO> dtos);

    @Mapping(target = "durationAverages", source = "values")
    public abstract GenreLendingsAvgPerMonthView toGenreLendingsAveragePerMonthView(GenreLendingsPerMonthDTO dto);

    public abstract List<GenreLendingsAvgPerMonthView> toGenreLendingsAveragePerMonthView(
            List<GenreLendingsPerMonthDTO> dtos);

}
