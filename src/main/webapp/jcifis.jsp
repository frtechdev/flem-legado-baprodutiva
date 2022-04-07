<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>

<body>
    <p>Carregando ...</p>
    <%response.sendRedirect((String)request.getAttribute("Home.do"));%>
</body>
</html>