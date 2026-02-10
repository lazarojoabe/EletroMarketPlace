<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Produto" %>
<jsp:include page="includes/header.jsp" />
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2>Estoque de Produtos</h2>
    <table border="1" style="width:100%; border-collapse: collapse;">
        <tr style="background: var(--azul-eletro); color: white;">
            <th>ID</th><th>Nome</th><th>Preço</th><th>Estoque</th>
        </tr>
        <% List<Produto> lista = (List<Produto>) request.getAttribute("listaProdutos");
           if(lista != null) { for(Produto p : lista) { %>
            <tr>
                <td><%= p.getIdProduto() %></td>
                <td><%= p.getNome() %></td>
                <td>R$ <%= String.format("%.2f", p.getPreco()) %></td>
                <td><%= p.getEstoque() %></td>
            </tr>
        <% } } %>
    </table>
    <br><a href="produto.jsp" class="btn-link-listar">Voltar</a>
</div>
</body></html>