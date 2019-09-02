<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 7/26/19
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>

<%--View of current resume on Web site--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="basejava.model.SectionType" %>
<%@ page import="basejava.model.TextSection" %>
<%@ page import="basejava.model.ProgressSection" %>
<%@ page import="java.util.List" %>
<%@ page import="basejava.model.LocationSection" %>
<%@ page import="basejava.util.DateUtil" %>
<%@ page import="basejava.util.HtmlUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">

    <%--Made object of class Resume and wrote him to variable "resume"--%>
    <jsp:useBean id="resume" type="basejava.model.Resume" scope="request"/>

    <%--Name of title web tab: "Резюме + fullName"--%>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>

    <%--View of current full name--%>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"
                                                                                      alt="fix"></a></h1>

    <%--View of contact list--%>
    <p>
        <%--var is current pair of Map "contacts"--%>
        <c:forEach var="contactEntry" items="${resume.contacts}">

            <%--id here is var "contactEntry" with type - pair(key, value) from Map"contacts"--%>
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<basejava.model.ContactType, java.lang.String>"/>

            <%--Get key(type(enam)) of current pair of contactEntry and invoke method "toHtml" with parameter
            "value of current pair of contactEntry"--%>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">

        <%--var is current pair of Map "sectionType"--%>
        <c:forEach var="sectionEntry" items="${resume.sections}">

            <%--id here is (var="sectionEntry") with TYPE - "java.util.Map.Entry<basejava.model.SectionType,
            basejava.model.AbstractSection>"--%>
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<basejava.model.SectionType, basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="abstractSection" value="${sectionEntry.value}"/>
            <jsp:useBean id="abstractSection" type="basejava.model.AbstractSection"/>
            <tr>
                <td colspan="2"><h3><a name="type.name">${type.title}</a></h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection) abstractSection).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection) abstractSection).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ProgressSection) abstractSection).getProgress()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="location" items="<%=((LocationSection) abstractSection).getLocations()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty location.link.locationLink}">
                                        <h3>${location.link.locationName}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${location.link.locationLink}">${location.link.locationName}</a>
                                        </h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${location.positions}">
                            <jsp:useBean id="position" type="basejava.model.Location.Position"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                                </td>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
