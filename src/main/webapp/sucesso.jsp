<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2 style="color: var(--cor-sucesso-padrao); text-align: center;">Operação Realizada!</h2>
    <p style="text-align: center;"><%= request.getAttribute("mensagem") %></p>
    <div style="text-align: center;">
        <a href="categoria.jsp" style="color: var(--azul-eletro); font-weight: bold;">Voltar ao Início</a>
    </div>
</div>
</body>
</html>