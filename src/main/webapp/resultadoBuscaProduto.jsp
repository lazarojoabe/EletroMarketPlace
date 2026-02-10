<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Produto" %> <%-- Diretiva de importação necessária para reconhecer o objeto --%>

<%-- Action Include para manter a identidade visual do EletroMarketPlace --%>
<jsp:include page="includes/header.jsp" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h2>Detalhes do Produto</h2>

    <% 
        // Recupera o objeto 'produtoEncontrado' que o Servlet enviou via request.setAttribute
        Produto p = (Produto) request.getAttribute("produtoEncontrado"); 
    %>

    <% if (p != null) { %>
        <div style="background: #f9f9f9; padding: 20px; border-radius: 8px; border-left: 5px solid var(--azul-eletro);">
            <p><strong>ID do Registro:</strong> <%= p.getIdProduto() %></p>
            <p><strong>Nome do Produto:</strong> <%= p.getNome() %></p>
            <p><strong>Descrição:</strong> <%= (p.getDescricao() != null) ? p.getDescricao() : "Sem descrição disponível." %></p>
            <p><strong>Preço Unitário:</strong> R$ <%= String.format("%.2f", p.getPreco()) %></p>
            <p><strong>Quantidade em Estoque:</strong> <%= p.getEstoque() %> unidades</p>
            <p><strong>ID Categoria:</strong> <%= p.getIdCategoria() %></p>
            <p><strong>ID Vendedor Responsável:</strong> <%= p.getIdVendedor() %></p>
        </div>
    <% } else { %>
        <p style="color: var(--cor-perigo-padrao);">Nenhum dado encontrado para este critério.</p>
    <% } %>

    <br>
    <div style="display: flex; gap: 10px;">
        <a href="produto.jsp" class="btn-link-listar" style="background-color: var(--azul-eletro); flex: 1; text-align: center; text-decoration: none; color: white; padding: 10px; border-radius: 8px;">
            Nova Pesquisa
        </a>
        <a href="menuPrincipal.jsp" class="btn-link-listar" style="background-color: var(--cor-secundaria, #6c757d); flex: 1; text-align: center; text-decoration: none; color: white; padding: 10px; border-radius: 8px;">
            Voltar ao Início
        </a>
    </div>
</div>

</body>
</html>