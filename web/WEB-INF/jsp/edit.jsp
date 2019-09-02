<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 7/26/19
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="basejava.model.*" %>
<%@ page import="basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
<%--        Атрибут "name" тега <input> присваивает элементу формы имя,--%>
<%--        которое может быть использовано скриптами или серверным обработчиком--%>
<%--        данных формы (для опознавания элемента).--%>
<%--        Атрибут "value" тега <input> указывает начальное значение для поля ввода.--%>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type"
                   items="<%=ContactType.values()%>">  <%--values() - enum's method. Returns array of enums--%>
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="abstractSection" value="${resume.getSection(type)}"/>
            <jsp:useBean id="abstractSection" type="basejava.model.AbstractSection"/>
            <h4>${type.title}</h4>
            
                <c:choose>
                    <c:when test="${type.equals(SectionType.OBJECTIVE)}">

<%--                        Value here introduce content of current TextSection --%>
                        <input type='text' name='${type}' size=75 value='<%=abstractSection%>'>
                    </c:when>
                    <c:when test="${type.equals(SectionType.PERSONAL)}">
                        <textarea name='${type}' cols=75 rows=5><%=abstractSection%></textarea>
                    </c:when>
                    <c:when test="${type.equals(SectionType.QUALIFICATIONS) || type.equals(SectionType.ACHIEVEMENT)}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ProgressSection) abstractSection).getProgress())%></textarea>
                    </c:when>
                    <c:when test="${type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)}">
                        <c:forEach var="location" items="<%=((LocationSection) abstractSection).getLocations()%>"
                                   varStatus="counter">
                            <dl>
                                <dt style="width: 200px">Название учреждения:</dt>
                                <dd><input type="text" name='${type}' size=45 value="${location.link.locationName}"></dd>
                            </dl>
                            <dl>
                                <dt style="width: 200px">Сайт учреждения:</dt>
                                <dd><input type="text" name='${type}url' size=45 value="${location.link.locationLink}"></dd>
                                </dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <c:forEach var="position" items="${location.positions}">
                                    <jsp:useBean id="position" type="basejava.model.Location.Position"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}startDate" size=10
                                                   value="<%=DateUtil.format(position.getStartDate())%>" placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}endDate" size=10
                                                   value="<%=DateUtil.format(position.getEndDate())%>" placeholder="MM/yyyy">
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${type}${counter.index}title' size=75
                                                   value="${position.title}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${type}${counter.index}description" rows=5
                                                      cols=75>${position.description}</textarea></dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
