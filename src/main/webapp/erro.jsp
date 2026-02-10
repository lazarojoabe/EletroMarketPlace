<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
    <jsp:include page="includes/menu.jsp" />
    <h2 style="color: var(--cor-perigo-padrao); text-align: center;">Ocorreu um Erro</h2>
    <p style="text-align: center;">
        <%= request.getAttribute("erro") != null ? request.getAttribute("erro") : request.getAttribute("mensagem") %>
    </p>
    <div style="text-align: center;">
        <a href="javascript:history.back()" style="color: var(--azul-eletro); font-weight: bold;">Tentar Novamente</a>
    </div>
</div>
</body>
</html>