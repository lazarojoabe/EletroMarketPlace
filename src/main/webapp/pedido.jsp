<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Gerenciamento de Pedidos</h1>

    <h2>Registrar Novo Pedido</h2>
    <form action="pedido" method="post" style="display: flex; flex-direction: column; gap: 10px;">
        <input type="hidden" name="action" value="insert">
        
        <div style="display: flex; gap: 10px;">
            <input type="number" name="id_produto" placeholder="ID do Produto" required style="flex: 1;">
            <input type="number" name="id_vendedor" placeholder="ID do Vendedor" required style="flex: 1;">
        </div>
        
        <div style="display: flex; gap: 10px;">
            <input type="number" name="quantidade" placeholder="Quantidade" required style="flex: 1;">
            <input type="text" name="data_pedido" placeholder="Data (AAAA-MM-DD)" required style="flex: 1;">
        </div>
        
        <button type="submit" style="background-color: var(--success, #28a745); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; text-transform: uppercase;">
            Finalizar Pedido
        </button>
    </form>

    <h2>Consultar Pedido</h2>
    <form action="pedido" method="get" style="display: flex; gap: 10px;">
        <input type="number" name="id_pedido" placeholder="Número do pedido..." required style="flex: 3;">
        <button type="submit" style="background-color: var(--primary, #007bff); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; flex: 1; text-transform: uppercase;">
            Buscar
        </button>
    </form>

    <h2>Atualizar Pedido</h2>
    <form action="pedido" method="post">
        <input type="hidden" name="action" value="update">
        <div style="display: flex; gap: 10px; align-items: flex-start; flex-wrap: wrap;">
            <input type="number" name="id_pedido" placeholder="ID" required style="flex: 0.5; min-width: 60px;">
            <input type="number" name="quantidade" placeholder="Nova Qtd" required style="flex: 1; min-width: 100px;">
            
            <button type="submit" style="background-color: #f39c12; color: white; padding: 12px 20px; border: none; border-radius: 8px; cursor: pointer; flex: 1; min-width: 120px; font-weight: bold; text-transform: uppercase;">
                Atualizar
            </button>
        </div>
    </form>

    <h2>Cancelar Pedido</h2>
    <form action="pedido" method="post" style="display: flex; gap: 10px;">
        <input type="hidden" name="action" value="delete">
        <input type="number" name="id_pedido" placeholder="ID do pedido para cancelar..." required style="flex: 3;">
        <button type="submit" style="background-color: var(--danger, #dc3545); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; flex: 1; font-weight: bold; text-transform: uppercase;">
            Excluir
        </button>
    </form>

    <div style="margin-top: 20px;">
        <a href="pedido" style="background-color: var(--primary, #007bff); color: white; padding: 12px; text-decoration: none; border-radius: 8px; display: block; text-align: center; font-weight: bold; text-transform: uppercase;">
            Listar Todos os Pedidos
        </a>
    </div>

    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>