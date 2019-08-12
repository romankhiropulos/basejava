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
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="basejava.model.Resume" scope="request"/>
    <%--Made object of class Resume and wrote him to variable "resume"--%>

    <title>Резюме ${resume.fullName}</title> <%--Name of title web tab: "Резюме + fullName"--%>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <%--view of current full name--%>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="fix"></a></h2>

    <%--view of contact list--%>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}"> <%--var is current pair of Map "contacts"--%>
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<basejava.model.ContactType, java.lang.String>"/> <%--id here is var
                         "contactEntry" with type - pair(key, value) from Map"contacts"--%>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/> <%--Get key(type(enam)) of current pair
                of contactEntry and invoke method "toHtml" with parameter "value of current pair of contactEntry"--%>
        </c:forEach>
    </p>
</section>
    <hr>
<section>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sectionType}"> <%--var is current pair of Map "sectionType"--%>
            <%--id here is (var="sectionEntry") with TYPE - "java.util.Map.Entry<basejava.model.SectionType, basejava.model.AbstractSection>"--%>
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<basejava.model.SectionType, basejava.model.AbstractSection>"/>
            <c:set var="searchKey" value="<%=sectionEntry.getKey()%>"/>
            <c:choose>
                <c:when test="${searchKey.equals(SectionType.PERSONAL) || searchKey.equals(SectionType.OBJECTIVE)}">
                    <h3><%=sectionEntry.getKey().getTitle()%></h3>
                    <% TextSection textSection = (TextSection) sectionEntry.getValue(); %>
                    <%=textSection.getContent()%>
                    <br/>
                </c:when>
                <c:when test="${searchKey.equals(SectionType.ACHIEVEMENT) || searchKey.equals(SectionType.QUALIFICATIONS)}">
                    <h3><%=sectionEntry.getKey().getTitle()%></h3>
                    <% ProgressSection progressSection = (ProgressSection) sectionEntry.getValue(); %>
                    <c:set var="progress" value="<%=progressSection.getProgress()%>"/>
                    <c:forEach var="item" items="${progress}">
                    <p>${item}</p>
                     </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>${""}</p>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </p>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
