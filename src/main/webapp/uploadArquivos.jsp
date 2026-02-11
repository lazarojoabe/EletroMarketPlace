<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<jsp:include page="includes/header.jsp" />
<jsp:useBean id="upload" scope="page" class="javaBeans.MultiUploadBean" />

<div class="container">
    <jsp:include page="includes/menu.jsp" />
    
    <h1>Upload de Documentos</h1>
    <p>Selecione ate dois arquivos para realizar o envio ao servidor.</p>

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
            
            <div style="margin-top: 25px;">
                <button type="submit" style="
                    background-color: #27ae60; 
                    color: white; 
                    padding: 6px 15px;
                    border: none; 
                    border-radius: 4px; 
                    cursor: pointer; 
                    font-weight: bold;
                    font-size: 0.85em;
                    letter-spacing: 1px;
                    display: inline-block;">
                    ENVIAR ARQUIVOS
                </button>
            </div>
        </form>
    </div>

    <%-- Processamento do Upload baseado no ciclo de vida JSP [cite: 6, 15, 1284] --%>
    <% 
        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
            if (upload.processarUpload(request, application)) {
    %>
                <div style="margin-top: 20px; padding: 15px; background: #d4edda; color: #155724; border-radius: 5px;">
                    <strong>Sucesso:</strong> Arquivos enviados corretamente.
                </div>
    <% 
            } else {
    %>
                <div style="margin-top: 20px; padding: 15px; background: #f8d7da; color: #721c24; border-radius: 5px;">
                    Erro ao processar o upload. Verifique as dependencias Apache Commons.
                </div>
    <% 
            }
        } 
    %>

    <hr style="margin-top: 40px;">

    <%-- Listagem de Arquivos no Diretorio [cite: 1234] --%>
    <div style="margin-top: 30px;">
        <h3>Arquivos no Servidor</h3>
        <ul style="list-style-type: none; padding: 0;">
            <%
                String path = application.getRealPath("/uploads");
                File diretorio = new File(path);
                
                if (diretorio.exists() && diretorio.isDirectory()) {
                    File[] arquivos = diretorio.listFiles();
                    
                    if (arquivos != null && arquivos.length > 0) {
                        for (File arquivo : arquivos) {
                            if (arquivo.isFile()) {
            %>
                                <li style="margin-bottom: 10px; padding: 10px; border: 1px solid #eee; border-radius: 5px;">
                                    <strong><%= arquivo.getName() %></strong> 
                                    <span style="font-size: 0.8em; color: #666;"> 
                                        (<%= arquivo.length() / 1024 %> KB) 
                                    </span>
                                    <br>
                                    <a href="uploads/<%= arquivo.getName() %>" download 
                                       style="color: #2980b9; text-decoration: none; font-size: 0.9em; font-weight: bold;">
                                       BAIXAR ARQUIVO
                                    </a>
                                </li>
            <%
                            }
                        }
                    } else {
                        out.println("<li>Nenhum arquivo encontrado no diretorio uploads.</li>");
                    }
                }
            %>
        </ul>
    </div>

    <jsp:include page="includes/footer.jsp" />
</div>
</body>
</html>