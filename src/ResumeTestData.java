import model.*;
import util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static model.ContactType.*;
import static model.SectionType.*;

public class ResumeTestData {

    static void print(Map<ContactType, String> contacts, Map<SectionType, AbstractSection> sectionType) {
        for (Map.Entry<ContactType, String> pair : contacts.entrySet())
        {
            ContactType key = pair.getKey();
            String value = pair.getValue();
            System.out.println(key + ": " + value);
        }

        for (Map.Entry<SectionType, AbstractSection> pair : sectionType.entrySet())
        {
            SectionType key = pair.getKey();
            AbstractSection value = pair.getValue();
            System.out.println(key + ": " + value);
        }
    }

    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин");
        resume.getContacts().put(PHONE, "+7(921) 855-0482");
        resume.getContacts().put(SKYPE, "grigory.kislin");
        resume.getContacts().put(MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(LINKEDIN, "Профиль LinkedIn");
        resume.getContacts().put(GITHUB, "github.com/gkislin");
        resume.getContacts().put(STATCKOVERFLOW, "user:548473");
        resume.getContacts().put(HOME_PAGE, "23423");

        resume.getSectionType().put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.getSectionType().put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));

        resume.getSectionType().put(ACHIEVEMENT, new ProgressSection(new ArrayList<>(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."))));

        resume.getSectionType().put(QUALIFICATIONS, new ProgressSection(new ArrayList<>(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n" +
                        "Version control: Subversion, Git, Mercury, ClearCase, Perforce\n" +
                        "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                        "MySQL, SQLite, MS SQL, HSQLDB\n" +
                        "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,\n" +
                        "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,\n" +
                        "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.\n" +
                        "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js\n" +
                        "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka\n" +
                        "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,\n" +
                        "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.\n" +
                        "Отличное знание и опыт применения концепций ООП, SOA, шаблонов\n" +
                        "проектрирования, архитектурных шаблонов, UML, функционального\n" +
                        "программирования\n" +
                        "Родной русский, английский \"upper intermediate\""))));

        resume.getSectionType().put(EXPERIENCE, new LocationSection(new ArrayList<>(Arrays.asList(new Location("Wrike", "wrike.com", new ArrayList<>(Arrays.asList(new Position(DateUtil.of(2003, FEBRUARY), DateUtil.of(2005, FEBRUARY), "Engineer", "Making code"))))))));
        resume.getSectionType().put(EDUCATION, new LocationSection(new ArrayList<>(Arrays.asList(new Location("ITMO", "itmo.com", new ArrayList<>(Arrays.asList(new Position(DateUtil.of(1987, FEBRUARY), DateUtil.of(1989, FEBRUARY), "Student"),
                new Position(DateUtil.of(1989, JANUARY), DateUtil.of(1992, JANUARY), "Scientist"))))))));

        print(resume.getContacts(), resume.getSectionType());

    }
}
