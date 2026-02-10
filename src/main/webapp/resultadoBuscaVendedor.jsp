<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Vendedor" %>
<jsp:include page="includes/header.jsp" />
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2>Dados do Vendedor</h2>
    <% Vendedor v = (Vendedor) request.getAttribute("vendedorEncontrado"); %>
    <% if (v != null) { %>
        <p><strong>ID:</strong> <%= v.getIdVendedor() %></p>
        <p><strong>Nome:</strong> <%= v.getNome() %></p>
        <p><strong>E-mail:</strong> <%= v.getEmail() %></p>
    <% } %>
    <br>
    <a href="vendedor.jsp" class="btn" style="background: var(--azul-eletro); color:white; padding:10px; text-decoration:none;">Voltar</a>
</div>
</body></html>