package javaBeans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MultiUploadBean {
    private String diretorio = "uploads";
    private List<String> arquivosSalvos = new ArrayList<>();

    public void setDiretorio(String diretorio) { this.diretorio = diretorio; }
    public List<String> getArquivosSalvos() { return arquivosSalvos; }

    public boolean processarUpload(HttpServletRequest request, ServletContext context) {
        // Verifica se a requisição é do tipo multipart [cite: 1284, 1285]
        if (!ServletFileUpload.isMultipartContent(request)) return false;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        // Tenta obter o caminho físico [cite: 1284]
        String path = context.getRealPath("/" + diretorio);
        
        // Se o path for null, usamos um caminho temporário para não abortar a conexão
        if (path == null) {
            path = System.getProperty("java.io.tmpdir") + File.separator + diretorio;
        }

        File uploadDir = new File(path);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try {
            // Analisa a requisição [cite: 1284]
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                // Processa apenas campos que não são de formulário comum [cite: 1284]
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    if (fileName != null && !fileName.isEmpty()) {
                        File storeFile = new File(path + File.separator + fileName);
                        item.write(storeFile); // Grava o arquivo [cite: 1284]
                        arquivosSalvos.add(fileName);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}