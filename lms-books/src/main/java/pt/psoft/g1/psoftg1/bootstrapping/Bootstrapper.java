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
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;

import java.time.LocalDate;
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

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PhotoRepository photoRepository;

    private final ForbiddenNameService forbiddenNameService;

    @Override
    @Transactional
    public void run(final String... args) {
        createAuthors();
        createGenres();
        createBooks();
        loadForbiddenNames();
        createPhotos();
    }

    private void createAuthors() {
        if (authorRepository.searchByNameName("Manuel Antonio Pina").isEmpty()) {
            final Author author = new Author("Manuel Antonio Pina");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Antoine de Saint Exupéry").isEmpty()) {
            final Author author = new Author("Antoine de Saint Exupéry");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Alexandre Pereira").isEmpty()) {
            final Author author = new Author("Alexandre Pereira");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Filipe Portela").isEmpty()) {
            final Author author = new Author("Filipe Portela");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Ricardo Queirós").isEmpty()) {
            final Author author = new Author("Ricardo Queirós");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Freida Mcfadden").isEmpty()) {
            final Author author = new Author("Freida Mcfadden");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("J R R Tolkien").isEmpty()) {
            final Author author = new Author("J R R Tolkien");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Gardner Dozois").isEmpty()) {
            final Author author = new Author("Gardner Dozois");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Lisa Tuttle").isEmpty()) {
            final Author author = new Author("Lisa Tuttle");
            authorRepository.save(author);
        }
    }

    private void createGenres() {
        if (genreRepository.findByString("Fantasia").isEmpty()) {
            final Genre g1 = new Genre("Fantasia");
            genreRepository.save(g1);
        }
        if (genreRepository.findByString("Informação").isEmpty()) {
            final Genre g2 = new Genre("Informação");
            genreRepository.save(g2);
        }
        if (genreRepository.findByString("Romance").isEmpty()) {
            final Genre g3 = new Genre("Romance");
            genreRepository.save(g3);
        }
        if (genreRepository.findByString("Infantil").isEmpty()) {
            final Genre g4 = new Genre("Infantil");
            genreRepository.save(g4);
        }
        if (genreRepository.findByString("Thriller").isEmpty()) {
            final Genre g5 = new Genre("Thriller");
            genreRepository.save(g5);
        }
    }

    protected void createBooks() {
        Optional<Genre> genre = Optional.ofNullable(genreRepository.findByString("Infantil"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        List<Author> author = authorRepository.searchByNameName("Manuel Antonio Pina");

        // 1 - O País das Pessoas de Pernas Para o Ar
        if (bookRepository.findByIsbn("9789720706386").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789720706386", "O País das Pessoas de Pernas Para o Ar ",
                        "Fazendo uso do humor e do nonsense, o livro reúne quatro histórias divertidas e com múltiplos significados: um país onde as pessoas vivem de pernas para o ar, que nos é apresentado por um passarinho chamado Fausto; a vida de um peixinho vermelho que escrevia um livro que a Sara não sabia ler; um Menino Jesus que não queria ser Deus, pois só queria brincar como as outras crianças; um bolo que queria ser comido, mas que não foi, por causa do pecado da gula. ",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 2 - Como se Desenha Uma Casa
        if (bookRepository.findByIsbn("9789723716160").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789723716160", "Como se Desenha Uma Casa",
                        "Como quem, vindo de países distantes fora de / si, chega finalmente aonde sempre esteve / e encontra tudo no seu lugar, / o passado no passado, o presente no presente, / assim chega o viajante à tardia idade / em que se confundem ele e o caminho. [...]",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 3 - C e Algoritmos
        if (bookRepository.findByIsbn("9789895612864").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Informação"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("Alexandre Pereira");
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789895612864", "C e Algoritmos",
                        "O C é uma linguagem de programação incontornável no estudo e aprendizagem das linguagens de programação. É um precursor das linguagens de programação estruturadas e a sua sintaxe foi reutilizada em muitas linguagens posteriores, mesmo de paradigmas diferentes, entre as quais se contam o Java, o Javascript, o Actionscript, o PHP, o Perl, o C# e o C++.\n"
                                + "\n"
                                + "Este livro apresenta a sintaxe da linguagem C tal como especificada pelas normas C89, C99, C11 e C17, da responsabilidade do grupo de trabalho ISO/IEC JTC1/SC22/WG14.",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 4 - Introdução ao Desenvolvimento Moderno para a Web
        if (bookRepository.findByIsbn("9782722203402").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Informação"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("Filipe Portela");
            List<Author> author2 = authorRepository.searchByNameName("Ricardo Queirós");
            if (genre.isPresent() && !author.isEmpty() && !author2.isEmpty()) {
                authors.add(author.get(0));
                authors.add(author2.get(0));
                Book book = new Book("9782722203402", "Introdução ao Desenvolvimento Moderno para a Web",
                        "Este livro foca o desenvolvimento moderno de aplicações Web, sendo apresentados os princípios básicos associados à programação para a Web, divididos em duas partes: front-end e back-end. Na parte do front-end, são introduzidos os conceitos de estruturação, estilização e interação, através das suas principais linguagens HTML, CSS e JavaScript. Na parte do back-end, é feita uma introdução aos servidores Web e respetivas linguagem (Node.js) e framework (Express), às bases de dados (SQL) e aos serviços na Web (REST). De forma a consolidar todos os conceitos teóricos apresentados, é descrita a implementação de um projeto prático completo.\n"
                                + "\n"
                                + "Com capítulos que podem ser lidos sequencialmente ou de forma alternada, o livro é dirigido a todos aqueles que com conhecimentos básicos de programação pretendem (re)entrar no mundo da Web e a quem pretenda colocar-se rapidamente a par de todas as novidades introduzidas nos últimos anos.\n"
                                + "\n"
                                + "O ambiente de desenvolvimento onde todos os exemplos da obra foram escritos é o Visual Studio Code e o controlo de versões foi feito no GitHub. Para colocar o servidor a correr, foi utilizada a plataforma Heroku.\n"
                                + "\n" + "Principais temas abordados:\n"
                                + "· Estruturação de conteúdos na Web com o HTML;\n"
                                + "· Estilização de conteúdos através de CSS e do Bootstrap;\n"
                                + "· Programação Web com o JavaScript;\n"
                                + "· Programação do lado do servidor com o Node.js;\n"
                                + "· Construção de API com o Express e o paradigma REST;\n"
                                + "· Armazenamento de dados com o MySQL;\n"
                                + "· Segurança e proteção dos dados na Web.\n" + "\n"
                                + "O que pode encontrar neste livro:\n" + "· 14 Tecnologias Web;\n"
                                + "· Capítulos organizados para uma leitura sequencial ou alternada;\n"
                                + "· Um projeto Web completo explicado passo a passo;\n"
                                + "· Secção de boas práticas no final de cada capítulo;\n"
                                + "· Resumo dos principais conceitos;\n" + "· Linguagem simples e acessível. ",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 5 - O Principezinho
        if (bookRepository.findByIsbn("9789722328296").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Infantil"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("Antoine de Saint Exupéry");
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789722328296", "O Principezinho",
                        "Depois de deixar o seu asteroide e embarcar numa viagem pelo espaço, o principezinho chega, finalmente, à Terra. No deserto, o menino de cabelos da cor do ouro conhece um aviador, a quem conta todas as aventuras que viveu e tudo o que viu ao longo da sua jornada.",
                        genre.get(), authors, "bookPhotoTest.jpg");

                bookRepository.save(book);
            }
        }

        // 6 - A Criada Está a Ver
        if (bookRepository.findByIsbn("9789895702756").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Thriller"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("Freida Mcfadden");
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789895702756", "A Criada Está a Ver",
                        "A Sra. Lowell transborda simpatia ao acenar-me através da cerca que separa as nossas casas. “Devem ser os nossos novos vizinhos!” Agarro na mão da minha filha e sorrio de volta. No entanto, assim que vê o meu marido, uma expressão estranha atravessa-lhe o rosto. MILLIE, A MEMORÁVEL PROTAGONISTA DOS BESTSELLERS A CRIADA E O SEGREDO DA CRIADA, ESTÁ DE VOLTA!Eu costumava limpar a casa de outras pessoas. Nem posso acreditar que esta casa é realmente minha...",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 7 - O Hobbit
        if (bookRepository.findByIsbn("9789897776090").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Fantasia"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("J R R Tolkien");
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book book = new Book("9789897776090", "O Hobbit",
                        "\"Esta é a história de como um Baggins viveu uma aventura e deu por si a fazer e a dizer coisas totalmente inesperadas...\n"
                                + "Bilbo Baggins goza de uma vida confortável, calma e pouco ambiciosa. Raramente viaja mais longe do que a despensa ou a adega do seu buraco de hobbit, em Fundo-do-Saco.\n"
                                + "Mas a sua tranquilidade é perturbada quando, um dia, o feiticeiro Gandalf e uma companhia de treze anões aparecem à sua porta, para o levar numa perigosa aventura.\n"
                                + "Eles têm um plano: saquear o tesouro guardado por Smaug, O Magnífico, um dragão enorme e muito perigoso... Bilbo, embora relutante, junta-se a esta missão, desconhecendo que nesta viagem até à Montanha Solitária vai encontrar um anel mágico e uma estranha criatura conhecida como Gollum. Livro com nova tradução e edição.\n"
                                + "Inclui mapas e ilustrações originais do autor. Situado no mundo imaginário da Terra Média,\n"
                                + "O Hobbit, o prelúdio de O Senhor dos Anéis, vendeu milhões de exemplares em todo o mundo desde a sua publicação em 1937, impondo-se como um clássico intemporal e um dos livros mais adorados e influentes do século xx.\" ",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 8 - Histórias de Vigaristas e Canalhas
        if (bookRepository.findByIsbn("9789896379636").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Fantasia"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("J R R Tolkien");
            List<Author> author2 = authorRepository.searchByNameName("Gardner Dozois");
            if (genre.isPresent() && !author.isEmpty() && !author2.isEmpty()) {
                authors.add(author.get(0));
                authors.add(author2.get(0));
                Book book = new Book("9789896379636", "Histórias de Vigaristas e Canalhas",
                        "Recomendamos cautela ao ler estes contos: há muitos vigaristas e canalhas à solta.\n"
                                + "Se gostou de ler \"Histórias de Aventureiros e Patifes\", então não vai querer perder novas histórias com alguns dos maiores vigaristas e canalhas. São personagens infames que se recusam a agir preto no branco, e escolhem trilhar os seus próprios caminhos, à margem das leis dos homens. Personagens carismáticas, eloquentes, sem escrúpulos, que chegam até nós através de um formidável elenco de autores.\n"
                                + "Com organização de George R. R. Martin, um nome que já dispensa apresentações, e Gardner Dozois, tem nas mãos uma antologia de géneros multifacetados e que reúne algumas das mentes mais perversas da literatura fantástica.",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }

        // 9 - Histórias de Aventureiros e Patifes
        if (bookRepository.findByIsbn("9789896378905").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Fantasia"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("J R R Tolkien");
            List<Author> author2 = authorRepository.searchByNameName("Gardner Dozois");
            if (genre.isPresent() && !author.isEmpty() && !author2.isEmpty()) {
                authors.add(author.get(0));
                authors.add(author2.get(0));
                Book book = new Book("9789896378905", "Histórias de Aventureiros e Patifes",
                        "Recomendamos cautela a ler estes contos: Há muitos patifes à solta.\n" + "\n"
                                + "Há personagens malandras e sem escrúpulos cujo carisma e presença de espírito nos faz estimá-las mais do que devíamos. São patifes, mercenários e vigaristas com códigos de honra duvidosos mas que fazem de qualquer aventura uma delícia de ler.\n"
                                + "George R. R. Martin é um grande admirador desse tipo de personagens – ou não fosse ele o autor de \"A Guerra dos Tronos\". Nesta monumental antologia, não só participa com um prefácio e um conto introduzindo uma das personagens mais canalhas da história de Westeros, como também a organiza com Gardner Dozois. Se é fã de literatura fantástica, vai deliciar-se!",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }
        // 10 - Windhaven
        if (bookRepository.findByIsbn("9789896375225").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Fantasia"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.searchByNameName("J R R Tolkien");
            List<Author> author2 = authorRepository.searchByNameName("Lisa Tuttle");
            if (genre.isPresent() && !author.isEmpty() && !author2.isEmpty()) {
                authors.add(author.get(0));
                authors.add(author2.get(0));
                Book book = new Book("9789896375225", "Windhaven",
                        "Ao descobrirem neste novo planeta a habilidade de voar com asas de metal, os voadores de asas prateadas "
                                + "tornam-se a elite e levam a todo o lado notícias, canções e histórias. Atravessam oceanos, enfrentam as "
                                + "tempestades e são heróis lendários que enfrentam a morte a cada golpe traiçoeiro do vento. Maris de Amberly,"
                                + " filha de um pescador, foi criada por um voador e nada mais deseja do que conquistar os céus de Windhaven. "
                                + "A sua ambição é tão forte que a jovem desafia a tradição para se juntar à elite. Mas cedo irá descobrir que"
                                + " nem todos os voadores estão dispostos a aceitá-la e terá de lutar e arriscar a vida pelo seu sonho. "
                                + "Conseguirá Maris vencer ou tornar-se-á uma testemunha do fim de Windhaven?",
                        genre.get(), authors, null);

                bookRepository.save(book);
            }
        }
    }

    protected void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }

    private void createLendings() {
        int i;
        int seq = 0;
        final var book1 = bookRepository.findByIsbn("9789720706386");
        final var book2 = bookRepository.findByIsbn("9789723716160");
        final var book3 = bookRepository.findByIsbn("9789895612864");
        final var book4 = bookRepository.findByIsbn("9782722203402");
        final var book5 = bookRepository.findByIsbn("9789722328296");
        final var book6 = bookRepository.findByIsbn("9789895702756");
        final var book7 = bookRepository.findByIsbn("9789897776090");
        final var book8 = bookRepository.findByIsbn("9789896379636");
        final var book9 = bookRepository.findByIsbn("9789896378905");
        final var book10 = bookRepository.findByIsbn("9789896375225");
        List<Book> books = new ArrayList<>();
        if (book1.isPresent() && book2.isPresent() && book3.isPresent() && book4.isPresent() && book5.isPresent()
                && book6.isPresent() && book7.isPresent() && book8.isPresent() && book9.isPresent()
                && book10.isPresent()) {
            books = List.of(new Book[] { book1.get(), book2.get(), book3.get(), book4.get(), book5.get(), book6.get(),
                    book7.get(), book8.get(), book9.get(), book10.get() });
        }
    }

    private void createPhotos() {
        /*
         * Optional<Photo> photoJoao = photoRepository.findByPhotoFile("foto-joao.jpg"); if(photoJoao.isEmpty()) { Photo
         * photo = new Photo(Paths.get("")) }
         */
    }
}
