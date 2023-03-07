<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="volleymate" tagdir="/WEB-INF/tags" %>

<%@ attribute name="menuName" required="true" rtexprvalue="true"
              description="Name of the active menu: home, owners, vets or error" %>

<volleymate:menu name="${menuName}"/>
