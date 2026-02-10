<jsp:include page="header.jsp" />
<jsp:include page="menu.jsp" />

<div style="background: white; padding: 40px; border-radius: 16px; max-width: 600px; margin: auto; box-shadow: 0 4px 15px rgba(0,0,0,0.1);">
    <h2>Inserir Nova Categoria</h2>
    <form action="categoria" method="post">
        <input type="hidden" name="action" value="insert">
        <input type="text" name="nome" placeholder="Nome da categoria" required style="width:100%; padding:10px; margin-bottom:15px;">
        <button type="submit" style="background:#2ecc71; color:white; border:none; padding:12px; width:100%; cursor:pointer; font-weight:bold;">SALVAR</button>
    </form>
</div>

</body>
</html>