<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="tienda">
    <div class="container">
        <div>
            <h1>Check Out</h1>
            <br/>
            <form action="/pagos/autorizar_pago" method="post">
                <table>
                    <tr>
                        <td>Description:</td>
                        <td><input type="text" name="descripcion" value="Description" /></td>
                    </tr>
                    <tr>
                        <td>Número de Volleys:</td>
                        <td><input type="text" name="numVolleys" value="numVolleys" /></td>
                    </tr>
                    <tr>
                        <td>Total:</td>
                        <td><input type="text" name="total" value="100" /></td>
                </table>
            </form>
        </div>
    </div>
</petclinic:layout>