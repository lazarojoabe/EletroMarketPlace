<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 1. Incluindo o componente de cabeçalho e estilos --%>
<jsp:include page="includes/header.jsp" />

<style>
    /* Estilos específicos para o Grid do Painel */
    .grid-links {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
        gap: 30px;
        margin-top: 20px;
    }

    .card {
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s, box-shadow 0.3s;
        overflow: hidden;
        text-align: left;
    }

    .card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    }

    .card a {
        text-decoration: none;
        color: inherit;
        display: block;
        padding: 25px;
    }

    .card h2 {
        font-size: 1.5em;
        color: var(--azul-eletro);
        margin-top: 0;
        margin-bottom: 10px;
    }

    .card p {
        color: #666;
        font-size: 0.95em;
        margin: 0;
    }

    .icon { font-size: 3em; margin-bottom: 15px; line-height: 1; }
    .subtitle { font-size: 1.1em; color: #666; margin-bottom: 40px; text-align: center; }
</style>

<div class="container">
    <%-- 2. Incluindo a barra de navegação superior reusável --%>
    <jsp:include page="includes/menu.jsp" />

    <h1>Painel de Gerenciamento EletroMarketPlace</h1>
    <p class="subtitle">Acesse as interfaces de CRUD para administrar o catálogo e operações do marketplace.</p>

    <div class="grid-links">
        
        <div class="card">
            <a href="produto.jsp"> 
                <div class="icon" style="color: #f39c12;">📦</div>
                <h2>Produto</h2>
                <p>Gerencie eletrónicos, preços, stock e descrições.</p>
            </a>
        </div>

        <div class="card">
            <a href="vendedor.jsp"> 
                <div class="icon" style="color: #28a745;">👤</div>
                <h2>Vendedor</h2>
                <p>Cadastre, visualize e edite os dados dos parceiros de venda.</p>
            </a>
        </div>

        <div class="card">
            <a href="pedido.jsp"> 
                <div class="icon" style="color: #e74c3c;">🛒</div>
                <h2>Pedido</h2>
                <p>Acompanhe e atualize os pedidos de compra realizados na plataforma.</p>
            </a>
        </div>

        <div class="card">
            <%-- LINK CORRIGIDO PARA .JSP --%>
            <a href="categoria.jsp"> 
                <div class="icon" style="color: #9b59b6;">🏷️</div>
                <h2>Categoria</h2>
                <p>Mantenha a organização dos produtos em categorias específicas.</p>
            </a>
        </div>
    </div>
</div>

</body>
</html>