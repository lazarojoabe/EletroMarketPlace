<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Pedido" %>
<jsp:include page="includes/header.jsp" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h2>Consulta de Pedido</h2>

    <% 
        Pedido p = (Pedido) request.getAttribute("pedidoEncontrado"); 
        if (p != null) {
    %>
        <div style="background: #f8f9fa; padding: 25px; border-radius: 10px; border-left: 5px solid var(--primary, #007bff); line-height: 1.8;">
            <p><strong>Número do Pedido:</strong> #<%= p.getIdPedido() %></p>
            <p><strong>Produto:</strong> <%= (p.getNomeProduto() != null) ? p.getNomeProduto() : p.getIdProduto() %></p>
            <p><strong>Vendedor:</strong> <%= (p.getNomeVendedor() != null) ? p.getNomeVendedor() : p.getIdVendedor() %></p>
            <p><strong>Quantidade:</strong> <%= p.getQuantidade() %></p>
            <p><strong>Data:</strong> <%= p.getDataPedido() %></p>
        </div>
    <% } else { %>
        <p style="color: var(--danger, #dc3545); font-weight: bold;">Pedido não localizado.</p>
    <% } %>

    <div style="margin-top: 20px;">
        <a href="pedido.jsp" class="btn" style="background: var(--primary, #007bff); color: white; padding: 10px 20px; text-decoration: none; border-radius: 6px; font-weight: bold; text-transform: uppercase; font-size: 0.8rem;">
            Nova Consulta
        </a>
    </div>

    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>