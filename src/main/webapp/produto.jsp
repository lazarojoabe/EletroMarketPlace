<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp" />

<title>Gerenciamento de Produtos</title>
</head>
<body>
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Gerenciamento de Produtos</h1>

    <h2>Inserir Novo Produto</h2>
    <form action="produto" method="post" style="display: flex; flex-direction: column; gap: 10px;">
        <input type="hidden" name="action" value="insert">
        <input type="text" name="nome" placeholder="Nome do Produto" required>
        <textarea name="descricao" placeholder="Descrição detalhada do produto" style="height: 80px;"></textarea>
        
        <div style="display: flex; gap: 10px;">
            <input type="number" step="0.01" name="preco" placeholder="Preço (R$)" required style="flex: 1;">
            <input type="number" name="estoque" placeholder="Qtd Estoque" required style="flex: 1;">
        </div>
        
        <div style="display: flex; gap: 10px;">
            <input type="number" name="id_categoria" placeholder="ID Categoria" required style="flex: 1;">
            <input type="number" name="id_vendedor" placeholder="ID Vendedor" required style="flex: 1;">
        </div>
        
        <button type="submit" style="background-color: var(--cor-sucesso-padrao, #2ecc71); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; text-transform: uppercase;">
            Salvar Produto
        </button>
    </form>

    <h2>Pesquisar por ID</h2>
    <form action="produto" method="get" style="display: flex; gap: 10px;">
        <input type="number" name="id_produto" placeholder="Digite o ID para buscar..." required style="flex: 3;">
        <button type="submit" style="background-color: var(--azul-eletro, #0077c7); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; flex: 1; text-transform: uppercase;">
            Buscar
        </button>
    </form>

    <h2>Atualizar Produto</h2>
    <form action="produto" method="post">
        <input type="hidden" name="action" value="update">
        <div style="display: flex; gap: 10px; align-items: flex-start; flex-wrap: wrap;">
            <input type="number" name="id_produto" placeholder="ID" required style="flex: 0.5; min-width: 60px;">
            <input type="text" name="nome" placeholder="Nome" required style="flex: 2; min-width: 150px;">
            <input type="number" step="0.01" name="preco" placeholder="Preço" required style="flex: 1; min-width: 100px;">
            <input type="number" name="estoque" placeholder="Qtd" required style="flex: 1; min-width: 80px;">
            
            <button type="submit" style="background-color: #f39c12; color: white; padding: 12px 20px; border: none; border-radius: 8px; cursor: pointer; flex: 1; min-width: 120px; font-weight: bold; text-transform: uppercase;">
                Atualizar
            </button>
        </div>
    </form>

    <h2>Excluir Produto</h2>
    <form action="produto" method="post" style="display: flex; gap: 10px;">
        <input type="hidden" name="action" value="delete">
        <input type="number" name="id_produto" placeholder="ID do produto para excluir..." required style="flex: 3;">
        <button type="submit" style="background-color: var(--cor-perigo-padrao, #e74c3c); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer; flex: 1; font-weight: bold; text-transform: uppercase;">
            Excluir
        </button>
    </form>

    <div style="display: flex; gap: 15px; margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee;">
        <a href="produto" style="background-color: var(--azul-eletro, #0077c7); color: white; padding: 12px; text-decoration: none; border-radius: 8px; flex: 1; text-align: center; font-weight: bold; text-transform: uppercase;">
            Listar Todos
        </a>
    </div>
    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>