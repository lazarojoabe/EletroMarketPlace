package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CategoriaDAO;
import model.Categoria;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoriaDAO catDao;

    @Override
    public void init() throws ServletException {
        super.init();
        this.catDao = new CategoriaDAO();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        super.service(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String idParam = request.getParameter("id_categoria");
        if (idParam != null && !idParam.isEmpty()) {
            buscarPorId(request, response, idParam);
        } else {
            listarTodas(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String mensagem = "";
        try {
            if (action != null) {
                switch (action) {
                    case "insert": mensagem = inserirCategoria(request); break;
                    case "update": mensagem = atualizarCategoria(request); break;
                    case "delete": mensagem = removerCategoria(request); break;
                }
            }
        } catch (Exception e) { mensagem = "Erro: " + e.getMessage(); }
        response.getWriter().println("<h3>Resultado:</h3><p>" + mensagem + "</p><a href='categoria.html'>Voltar</a>");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response, String idParam) throws IOException {
        try {
            Long id = Long.parseLong(idParam);
            Categoria c = catDao.buscarPorId(id);
            if (c != null) {
                response.getWriter().println("<h2>Categoria: " + c.getNome() + " (ID: " + c.getIdCategoria() + ")</h2>");
            } else { response.getWriter().println("Categoria ID " + id + " não encontrada."); }
        } catch (Exception e) { response.getWriter().println("Erro."); }
    }

    private void listarTodas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Categoria> lista = catDao.listarTodas();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Categorias</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, sans-serif; background-color: #eef3f7; padding: 40px; display: flex; justify-content: center; }");
        out.println(".container { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 600px; }");
        out.println("h2 { color: #0077c7; border-bottom: 2px solid #0077c7; padding-bottom: 10px; margin-top: 0; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th { background-color: #0077c7; color: white; padding: 12px; text-align: left; }");
        out.println("td { padding: 12px; border-bottom: 1px solid #eee; color: #2c3e50; }");
        out.println("tr:hover { background-color: #f1f7fc; }");
        out.println(".btn { display: inline-block; margin-top: 25px; padding: 10px 20px; background: #0077c7; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px; }");
        out.println("</style></head><body>");
        out.println("<div class='container'><h2>Categorias Cadastradas</h2>");
        out.println("<table><tr><th>ID</th><th>Nome da Categoria</th></tr>");
        for (Categoria c : lista) {
            out.println("<tr><td>" + c.getIdCategoria() + "</td><td>" + c.getNome() + "</td></tr>");
        }
        out.println("</table><a href='categoria.html' class='btn'>← Voltar ao CRUD</a></div></body></html>");
    }

    private String inserirCategoria(HttpServletRequest request) {
        Categoria c = new Categoria();
        c.setNome(request.getParameter("nome"));
        return catDao.inserir(c) ? "Categoria inserida com sucesso! ID: " + c.getIdCategoria() : "Falha ao inserir categoria.";
    }

    private String atualizarCategoria(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_categoria"));
        Categoria c = catDao.buscarPorId(id);
        if (c == null) return "Erro: Categoria ID " + id + " não existe.";
        c.setNome(request.getParameter("nome"));
        return catDao.atualizar(c) ? "Categoria ID " + id + " atualizada com sucesso!" : "Falha ao atualizar ID " + id;
    }

    private String removerCategoria(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_categoria"));
        return catDao.remover(id) ? "Categoria ID " + id + " removida com sucesso!" : "Falha ao remover ID " + id;
    }
}