package servlets;

import java.io.IOException;
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
        response.getWriter().println("<h3>Resultado:</h3><p>" + mensagem + "</p><a href='/EletroMarketPlace/categoria.html'>Voltar</a>");
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
        response.getWriter().println("<h2>Categorias</h2><table border='1'>");
        for (Categoria c : lista) {
            response.getWriter().println("<tr><td>" + c.getIdCategoria() + "</td><td>" + c.getNome() + "</td></tr>");
        }
        response.getWriter().println("</table><br><a href='/EletroMarketPlace/categoria.html'>Voltar</a>");
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