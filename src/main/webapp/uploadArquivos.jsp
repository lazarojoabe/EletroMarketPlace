<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<jsp:include page="includes/header.jsp" />
<jsp:useBean id="upload" scope="page" class="javaBeans.MultiUploadBean" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Upload de Documentos do Usuário</h1>
    <p>Utilize o formulário abaixo para enviar dois arquivos (ex: Comprovantes ou Manuais).</p>

    <div style="background: #fdfdfd; padding: 20px; border: 1px solid #ddd; border-radius: 10px;">
        <form method="post" action="uploadArquivos.jsp" enctype="multipart/form-data">
            <div style="margin-bottom: 15px;">
                <label>Arquivo 1:</label><br>
                <input type="file" name="file1" required />
            </div>
            <div style="margin-bottom: 15px;">
                <label>Arquivo 2:</label><br>
                <input type="file" name="file2" required />
            </div>
            <button type="submit" style="background-color: #27ae60; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">
                SUBIR ARQUIVOS
            </button>
        </form>
    </div>

    <%-- Lógica de processamento baseada nos slides (Parte 4) --%>
    <% 
        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
            if (upload.processarUpload(request, application)) {
    %>
                <div style="margin-top: 20px; padding: 15px; background: #d4edda; color: #155724; border-radius: 5px;">
                    <strong>Sucesso!</strong> Arquivos disponíveis para download no contexto:
                    <ul>
                        <% 
                            List<String> nomes = upload.getArquivosSalvos();
                            for (String nome : nomes) {
                        %>
                            <li><a href="uploads/<%= nome %>" download style="color: #155724; font-weight: bold;"><%= nome %> (Baixar)</a></li>
                        <% } %>
                    </ul>
                </div>
    <% 
            } else {
    %>
                <div style="margin-top: 20px; padding: 15px; background: #f8d7da; color: #721c24; border-radius: 5px;">
                    Erro ao processar o upload. Verifique as bibliotecas Apache Commons.
                </div>
    <% 
            }
        } 
    %>

    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>