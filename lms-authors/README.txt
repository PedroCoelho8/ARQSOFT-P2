
> cd /path/to/lmsauthors

Dockerfile
> mvn package
> docker build -t lmsauthors .

DockerfileWithPackaging (inclui mvn package)
> docker build -f DockerfileWithPackaging -t lmsauthors .

Running:
> docker compose -f docker-compose-rabbitmq+postgres.yml up -d
> docker exec -it postgres_in_lms_network psql -U postgres
    psql (16.3 (Debian 16.3-1.pgdg120+1))
    Type "help" for help.

    postgres=# create database authors_1;
    CREATE DATABASE
    postgres=# create database authors_1;
    CREATE DATABASE
    postgres=# \l
                                                      List of databases
       Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | ICU Locale | ICU Rules |   Access privileges
    -----------+----------+----------+-----------------+------------+------------+------------+-----------+-----------------------
     authors_1   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
     authors_2   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
     authors_3   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
     postgres  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
     template0 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres          +
               |          |          |                 |            |            |            |           | postgres=CTc/postgres
     template1 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres          +
               |          |          |                 |            |            |            |           | postgres=CTc/postgres
     users_1   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
    (7 rows)
    postgres=# exit
> docker compose up