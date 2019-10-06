# Курс BaseJava (обновленный и переработанный)

## Разработка Web приложения "База данных резюме"
  -  используем: Java 8, <a href="https://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-2016-trends/#java-ides-adoption">IntelliJ IDEA</a>,
    <a href="https://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-2016-trends/#java-vcs-adoption">GitHib/Git</a>, Сервлеты, JSP, JSTL, Tomcat, JUnit, PostgreSQL, GSON, JAXB
  - хранение резюме
     -  в памяти на основе массива, отсортированного массива, списка и ассоциированного массива (Map)
     -  в файловой системе (File API и <a href="http://www.quizful.net/post/java-nio-tutorial">Java 7 NIO File API</a>)
        - в стандартной и кастомной сериализации Java
        - в формате JSON (<a href="https://github.com/google/gson">Google Gson</a>)
        - в формате XML (<a href="https://ru.wikipedia.org/wiki/Java_Architecture_for_XML_Binding">JAXB</a>)
     -  в реляционной базе <a href="https://ru.wikipedia.org/wiki/PostgreSQL">PostgreSQL</a>
  -  деплой веб приложения
     - в контейнер сервлетов <a href="http://tomcat.apache.org/">Tomcat</a>
     - в облачный сервис <a href="https://www.heroku.com/">Heroku</a>

Приложение будет разрабатываться начиная со первого занятия, основываясь на базовых темах курса:
**объектная модель, коллекции, система ввода-вывода, работа с файлами, сериализация, работа с XML, JSON, SQL, персистентность в базу данных (PostgreSQL), сервлеты, HTML/JSP/JSTL, веб-контейнер Tomcat, модульные тесты JUnit, java.basejava.util.Logging, система контроля версий Git.**

