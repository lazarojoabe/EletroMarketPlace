<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Inclusão do cabeçalho e menu reusáveis [cite: 1832, 2435] --%>
<jsp:include page="includes/header.jsp" />

<title>Gerenciamento de Vendedores</title>
</head>
<body>
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Gerenciamento de Vendedores</h1>

    <%-- 1. INSERIR --%>
    <h2>Cadastrar Vendedor</h2>
    <form action="vendedor" method="post">
        <input type="hidden" name="action" value="insert">
        <input type="text" name="nome" placeholder="Nome Completo" required>
        <input type="email" name="email" placeholder="E-mail de Contato" required>
        <button type="submit" style="background-color: var(--cor-sucesso-padrao); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer;">Salvar</button>
    </form>

    <%-- 2. PESQUISAR (GET) [cite: 1951] --%>
    <h2>Pesquisar Vendedor por ID</h2>
    <form action="vendedor" method="get">
        <input type="number" name="id_vendedor" placeholder="ID do vendedor" required>
        <button type="submit" style="background-color: var(--azul-eletro); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer;">Pesquisar</button>
    </form>

    <%-- 3. ATUALIZAR --%>
    <h2>Atualizar Dados do Vendedor</h2>
<form action="vendedor" method="post">
    <input type="hidden" name="action" value="update">
    
    <%-- Container para alinhar os elementos lado a lado --%>
    <div style="display: flex; gap: 10px; align-items: flex-start; flex-wrap: wrap;">
        <input type="number" name="id_vendedor" placeholder="ID" required 
               style="flex: 0.5; min-width: 80px; margin-bottom: 0;">
        
        <input type="text" name="nome" placeholder="Novo Nome Completo" required 
               style="flex: 2; min-width: 200px; margin-bottom: 0;">
        
        <input type="email" name="email" placeholder="Novo E-mail" required 
               style="flex: 2; min-width: 200px; margin-bottom: 0;">
        
        <button type="submit" 
                style="background-color: #f39c12; color: white; padding: 12px 20px; 
                       border: none; border-radius: 6px; cursor: pointer; flex: 1; 
                       min-width: 120px; font-weight: bold; text-transform: uppercase;">
            Atualizar
        </button>
    </div>
</form>

    <%-- 4. EXCLUIR --%>
    <h2>Excluir Vendedor</h2>
    <form action="vendedor" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="number" name="id_vendedor" placeholder="ID do vendedor para excluir" required>
        <button type="submit" style="background-color: var(--cor-perigo-padrao); color: white; padding: 12px; border: none; border-radius: 8px; cursor: pointer;">Excluir</button>
    </form>

    <hr>

    <%-- 5. LISTAGEM --%>
    <h2>Listar Todos os Vendedores</h2>
    <p>
        <a href="vendedor" class="btn-link-listar" style="background-color: var(--azul-eletro); color: white; padding: 10px; text-decoration: none; border-radius: 8px; display: inline-block;">
            Visualizar Todos os Registros
        </a>
    </p>
    
    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>