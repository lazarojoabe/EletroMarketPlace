<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Vendedor" %>
<jsp:include page="includes/header.jsp" />
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2>Vendedores Cadastrados</h2>
    <% List<Vendedor> lista = (List<Vendedor>) request.getAttribute("listaVendedores"); %>
    <table border="1" style="width: 100%; border-collapse: collapse;">
        <tr style="background-color: var(--azul-eletro); color: white;">
            <th>ID</th><th>Nome</th><th>E-mail</th>
        </tr>
        <% if (lista != null) { 
            for (Vendedor v : lista) { %>
            <tr>
                <td><%= v.getIdVendedor() %></td>
                <td><%= v.getNome() %></td>
                <td><%= v.getEmail() %></td>
            </tr>
        <% } } %>
    </table>
    <br><a href="vendedor.jsp">Voltar</a>
</div>
</body></html>