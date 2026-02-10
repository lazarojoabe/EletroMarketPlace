<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Categoria" %>
<jsp:include page="includes/header.jsp" />
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2>Resultado da Busca</h2>
    <% 
        // Recupera o objeto do request enviado pelo Servlet
        Categoria c = (Categoria) request.getAttribute("categoriaEncontrada"); 
    %>
    <% if (c != null) { %>
        <p><strong>ID:</strong> <%= c.getIdCategoria() %></p>
        <p><strong>Nome:</strong> <%= c.getNome() %></p>
    <% } %>
    <br>
    <a href="categoria.jsp" style="color: var(--azul-eletro); font-weight: bold;">Voltar</a>
</div>
</body></html>