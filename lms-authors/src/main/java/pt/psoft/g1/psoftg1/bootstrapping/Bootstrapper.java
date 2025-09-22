package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

// import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
// import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@PropertySource({ "classpath:config/library.properties" })
@Order(2)
public class Bootstrapper implements CommandLineRunner {
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    private final AuthorRepository authorRepository;
//    private final BookRepository bookRepository;
    private final PhotoRepository photoRepository;

    private final ForbiddenNameService forbiddenNameService;

    @Override
    @Transactional
    public void run(final String... args) {
        createAuthors();
        loadForbiddenNames();
        createPhotos();
    }

    private void createAuthors() {
        if (authorRepository.searchByNameName("Manuel Antonio Pina").isEmpty()) {
            final Author author = new Author("Manuel Antonio Pina",
                    "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Antoine de Saint Exupéry").isEmpty()) {
            final Author author = new Author("Antoine de Saint Exupéry",
                    "Antoine de Saint-Exupéry nasceu a 29 de junho de 1900 em Lyon. Faz o seu batismo de voo aos 12 anos, aos 22 torna-se piloto militar e é como capitão que em 1939 se junta à Força Aérea francesa em luta contra a ocupação nazi. A aviação e a guerra viriam a revelar-se elementos centrais de toda a sua obra literária, onde se destacam títulos como Correio do Sul (1929), o seu primeiro romance, Voo Noturno (1931), que logo se tornou um êxito de vendas internacional, e Piloto de Guerra (1942), retrato da sua participação na Segunda Guerra Mundial. Em 1943 publicaria aquela que é reconhecida como a sua obra-prima, O Principezinho, um dos livros mais traduzidos em todo o mundo. A sua morte, aos 44 anos, num acidente de aviação durante uma missão de reconhecimento no sul de França, permanece ainda hoje um mistério.",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Alexandre Pereira").isEmpty()) {
            final Author author = new Author("Alexandre Pereira",
                    "Alexandre Pereira é licenciado e mestre em Engenharia Electrotécnica e de Computadores, pelo Instituto Superior Técnico. É, também, licenciado em Antropologia, pela Faculdade de Ciências Sociais e Humanas da Universidade Nova de Lisboa.\n"
                            + "É Professor Auxiliar Convidado na Universidade Lusófona de Humanidades e Tecnologias, desde Março de 1993, onde lecciona diversas disciplinas na Licenciatura de Informática e lecciona uma cadeira de introdução ao SPSS na Licenciatura de Psicologia.\n"
                            + "Tem também leccionado cursos de formação na área da aplicação da informática ao cálculo estatístico e processamento de dados utilizando o SPSS, em diversas instituições, nomeadamente no Instituto Nacional de Estatística.\n"
                            + "Para além disso, desenvolve aplicações informáticas na área da Psicologia Cognitiva, no âmbito de projectos de investigação do departamento de Psicologia Cognitiva da Faculdade de Psicologia da Universidade de Lisboa.\n"
                            + "Está ainda ligado a projectos de ensino à distância desenvolvidos na Faculdade de Motricidade Humana da Universidade Técnica de Lisboa.\n"
                            + "Paralelamente, tem desenvolvido aplicações de software comercial, área onde continua em actividade. ",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Filipe Portela").isEmpty()) {
            final Author author = new Author("Filipe Portela",
                    " «Docente convidado na Escola de Engenharia da Universidade do Minho. Investigador integrado do Centro Algoritmi. CEO e fundador da startup tecnológica IOTech - Innovation on Technology. Coautor do livro Introdução ao Desenvolvimento Moderno para a Web. ",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Ricardo Queirós").isEmpty()) {
            final Author author = new Author("Ricardo Queirós",
                    "Docente na Escola Superior de Media Artes e Design do Politécnico do Porto. Diretor da uniMAD (ESMAD) e membro efetivo do CRACS (INESC TEC). Autor de vários livros sobre tecnologias Web e programação móvel, publicados pela FCA. Coautor do livro Introdução ao Desenvolvimento Moderno para a Web.",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Freida Mcfadden").isEmpty()) {
            final Author author = new Author("Freida Mcfadden",
                    "Freida McFadden é médica e especialista em lesões cerebrais. Autora de diversos thrillers psicológicos, todos eles bestsellers, já traduzidos para mais de 30 idiomas. As suas obras foram selecionadas para «O Melhor Livro do Ano» na Amazon e também para «Melhor Thriller» dos Goodreads Choice Awards.\n"
                            + "Freida vive com a sua família e o gato preto numa casa de três andares com vista para o oceano, com escadas que rangem e gemem a cada passo, e ninguém conseguiria ouvi-la se gritasse. A menos que gritasse muito alto, talvez.",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("J R R Tolkien").isEmpty()) {
            final Author author = new Author("J R R Tolkien",
                    "J.R.R. Tolkien nasceu a 3 de Janeiro de 1892, em Bloemfontein.\n"
                            + "Depois de ter combatido na Primeira Guerra Mundial, dedicou-se a uma ilustre carreira académica e foi reconhecido como um dos grandes filólogos do planeta.\n"
                            + "Foi a criação da Terra Média, porém, a trazer-lhe a celebridade. Autor de extraordinários clássicos da ficção, de que são exemplo O Hobbit, O Senhor dos Anéis e O Silmarillion, os seus livros foram traduzidos em mais de 60 línguas e venderam largos milhões de exemplares no mundo inteiro.\n"
                            + "Tolkien foi nomeado Comandante da Ordem do Império Britânico e, em 1972, foi-lhe atribuído o título de Doutor Honoris Causa, pela Universidade de Oxford.\n"
                            + "Morreu em 1973, com 81 anos.",
                    "authorPhotoTest.jpg");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Gardner Dozois").isEmpty()) {
            final Author author = new Author("Gardner Dozois",
                    "Gardner Raymond Dozois (23 de julho de 1947 – 27 de maio de 2018) foi um autor de ficção científica norte-americano.\n"
                            + "Foi o fundador e editor do Melhores Do Ano de Ficção científica antologias (1984–2018) e foi editor da revista Asimov Ficção científica (1984-2004), ganhando vários prémios.",
                    null);
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Lisa Tuttle").isEmpty()) {
            final Author author = new Author("Lisa Tuttle",
                    "Lisa Gracia Tuttle (nascida a 16 de setembro de 1952) é uma autora americana de ficção científica, fantasia e terror. Publicou mais de uma dúzia de romances, sete coleções de contos e vários títulos de não-ficção, incluindo um livro de referência sobre feminismo, \"Enciclopédia do Feminismo\" (1986). Também editou várias antologias e fez críticas de livros para diversas publicações. Vive no Reino Unido desde 1981.\n"
                            + "Tuttle ganhou o Prémio John W. Campbell para Melhor Novo Escritor em 1974, recebeu o Prémio Nebula de Melhor Conto em 1982 por \"The Bone Flute\", que recusou, e o Prémio BSFA de Ficção Curta em 1989 por \"In Translation\".",
                    null);
            authorRepository.save(author);
        }
    }

    protected void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }

    private void createPhotos() {
        /*
         * Optional<Photo> photoJoao = photoRepository.findByPhotoFile("foto-joao.jpg"); if(photoJoao.isEmpty()) { Photo
         * photo = new Photo(Paths.get("")) }
         */
    }
}
