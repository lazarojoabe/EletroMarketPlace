<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Inclui o cabeçalho reusável com CSS e abertura de tags [cite: 313, 913] --%>
<jsp:include page="includes/header.jsp" />

<title>Gerenciamento de Categoria</title>
</head>
<body>
<div class="container">
    <%-- Inclui o menu de navegação [cite: 315] --%>
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Gerenciamento de Categoria</h1>

    <%-- 1. INSERIR --%>
    <h2>Inserir Categoria</h2>
    <form action="categoria" method="post">
        <input type="hidden" name="action" value="insert">
        <input type="text" name="nome" placeholder="Nome da categoria" required>
        <button type="submit" class="btn-submit" style="background-color: var(--cor-sucesso-padrao);">Salvar</button>
    </form>

    <%-- 2. BUSCAR (GET) --%>
    <h2>Pesquisar Categoria por ID</h2>
    <form action="categoria" method="get">
        <input type="number" name="id_categoria" placeholder="ID da categoria" required>
        <button type="submit" class="btn-submit" style="background-color: var(--azul-eletro);">Pesquisar</button>
    </form>

    <%-- 3. ATUALIZAR --%>
    <h2>Atualizar Categoria</h2>
    <form action="categoria" method="post">
        <input type="hidden" name="action" value="update">
        <input type="number" name="id_categoria" placeholder="ID para atualizar" required>
        <input type="text" name="nome" placeholder="Novo nome da categoria" required>
        <button type="submit" class="btn-submit" style="background-color: var(--cor-alerta);">Atualizar</button>
    </form>

    <%-- 4. EXCLUIR --%>
    <h2>Excluir Categoria</h2>
    <form action="categoria" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="number" name="id_categoria" placeholder="ID para excluir" required>
        <button type="submit" class="btn-submit" style="background-color: var(--cor-perigo-padrao);">Excluir</button>
    </form>

    <hr>

    <%-- 5. LISTAR TODAS --%>
    <h2>Listar Todas as Categorias</h2>
    <p>
        <a href="categoria" class="btn-link-listar" style="background-color: var(--azul-eletro); color: white; padding: 10px; text-decoration: none; border-radius: 8px; display: inline-block;">
            Visualizar Todas
        </a>
    </p>
    
    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>