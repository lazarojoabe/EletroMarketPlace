<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Pedido" %>
<jsp:include page="includes/header.jsp" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Histórico de Pedidos</h1>

    <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
        <thead style="background-color: var(--primary, #007bff); color: white;">
            <tr>
                <th style="padding: 12px;">ID</th>
                <th style="padding: 12px;">Produto</th>
                <th style="padding: 12px;">Vendedor</th>
                <th style="padding: 12px;">Qtd</th>
                <th style="padding: 12px;">Data</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Pedido> lista = (List<Pedido>) request.getAttribute("listaPedidos"); 
                if (lista != null && !lista.isEmpty()) {
                    for (Pedido p : lista) {
            %>
                <tr style="border-bottom: 1px solid #eee;">
                    <td style="padding: 10px; text-align: center;"><%= p.getIdPedido() %></td>
                    <td style="padding: 10px;"><%= (p.getNomeProduto() != null) ? p.getNomeProduto() : p.getIdProduto() %></td>
                    <td style="padding: 10px;"><%= (p.getNomeVendedor() != null) ? p.getNomeVendedor() : p.getIdVendedor() %></td>
                    <td style="padding: 10px; text-align: center;"><%= p.getQuantidade() %></td>
                    <td style="padding: 10px; text-align: center;"><%= p.getDataPedido() %></td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" style="padding: 20px; text-align: center;">Nenhum pedido encontrado.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>