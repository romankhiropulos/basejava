package basejava.web;

import basejava.Config;
import basejava.model.*;
import basejava.storage.Storage;
import basejava.util.DateUtil;
import basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value == null || value.trim().length() == 0) {
                if (values.length < 2) {
                    resume.getSections().remove(type);
                }
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ProgressSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Location> locations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Location.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Location.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                locations.add(new Location(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(type, new LocationSection(locations));
                        break;
                }
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ProgressSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            LocationSection locationSection = (LocationSection) section;
                            List<Location> emptyFirstLocations = new ArrayList<>();
                            emptyFirstLocations.add(Location.EMPTY);
                            if (locationSection != null) {
                                for (Location location : locationSection.getLocations()) {
                                    List<Location.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Location.Position.EMPTY);
                                    emptyFirstPositions.addAll(location.getPositions());
                                    emptyFirstLocations.add(new Location(location.getLink(), emptyFirstPositions));
                                }
                            }
                            section = new LocationSection(emptyFirstLocations);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);



        // without using jsp

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
////        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        Writer writer = response.getWriter();
//        writer.write(
//                "<html>\n" +
//                        "<head>\n" +
//                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
//                        "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
//                        "    <title>Список всех резюме</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "<section>\n" +
//                        "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
//                        "    <tr>\n" +
//                        "        <th>Имя</th>\n" +
//                        "        <th>Email</th>\n" +
//                        "    </tr>\n");
//        for (Resume resume : storage.getAllSorted()) {
//            writer.write(
//                    "<tr>\n" +
//                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
//                            "     <td>" + resume.getContact(ContactType.MAIL) + "</td>\n" +
//                            "</tr>\n");
//        }
//        writer.write("</table>\n" +
//                "</section>\n" +
//                "</body>\n" +
//                "</html>\n");
    }
}
