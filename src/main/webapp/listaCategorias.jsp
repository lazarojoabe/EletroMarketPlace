<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Categoria" %> <%-- Diretiva de importação [cite: 1770] --%>

<%-- Action Include para os componentes reusáveis [cite: 2429] --%>
<jsp:include page="includes/header.jsp" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Categorias Cadastradas</h1>

    <% 
        // Recupera o objeto implícito 'request' [cite: 1917, 1920]
        List<Categoria> lista = (List<Categoria>) request.getAttribute("listaCategorias"); 
    %>

    <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
        <thead style="background-color: var(--azul-eletro); color: white;">
            <tr>
                <th style="padding: 10px;">ID</th>
                <th style="padding: 10px;">Nome da Categoria</th>
            </tr>
        </thead>
        <tbody>
            <%-- Lógica Java para iterar na lista [cite: 1599] --%>
            <% if (lista != null && !lista.isEmpty()) { 
                for (Categoria c : lista) { %>
                <tr>
                    <td style="padding: 10px; text-align: center;"><%= c.getIdCategoria() %></td>
                    <td style="padding: 10px;"><%= c.getNome() %></td>
                </tr>
            <%  } 
            } else { %>
                <tr>
                    <td colspan="2" style="padding: 10px; text-align: center;">Nenhuma categoria encontrada.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <br>
    <a href="categoria.jsp" class="btn" style="background-color: var(--azul-eletro); color: white; padding: 10px; text-decoration: none; border-radius: 8px;">
        ← Voltar ao Cadastro
    </a>
</div>
</body>
</html>