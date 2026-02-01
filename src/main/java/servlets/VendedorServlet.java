package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.VendedorDAO;
import model.Vendedor;

@WebServlet("/vendedor")
public class VendedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VendedorDAO vendedorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.vendedorDAO = new VendedorDAO();
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
        String idParam = request.getParameter("id_vendedor");
        if (idParam != null && !idParam.isEmpty()) {
            buscarPorId(request, response, idParam);
        } else {
            listarTodos(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String mensagem = "";
        try {
            if (action != null) {
                switch (action) {
                    case "insert": mensagem = inserirVendedor(request); break;
                    case "update": mensagem = atualizarVendedor(request); break;
                    case "delete": mensagem = removerVendedor(request); break;
                }
            }
        } catch (Exception e) { mensagem = "Erro: " + e.getMessage(); }
        response.getWriter().println("<h3>Resultado:</h3><p>" + mensagem + "</p><a href='/EletroMarketPlace/vendedor.html'>Voltar</a>");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response, String idParam) throws IOException {
        try {
            Long id = Long.parseLong(idParam);
            Vendedor v = vendedorDAO.buscarPorId(id);
            if (v != null) {
                response.getWriter().println("<h2>Vendedor: " + v.getNome() + " (ID: " + v.getIdVendedor() + ")</h2>");
            } else { response.getWriter().println("Vendedor ID " + id + " não encontrado."); }
        } catch (Exception e) { response.getWriter().println("Erro."); }
    }

    private void listarTodos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Vendedor> lista = vendedorDAO.listarTodos();
        response.getWriter().println("<h2>Vendedores</h2><table border='1'>");
        for (Vendedor v : lista) {
            response.getWriter().println("<tr><td>" + v.getIdVendedor() + "</td><td>" + v.getNome() + "</td></tr>");
        }
        response.getWriter().println("</table><br><a href='/EletroMarketPlace/vendedor.html'>Voltar</a>");
    }

    private String inserirVendedor(HttpServletRequest request) {
        Vendedor v = new Vendedor();
        v.setNome(request.getParameter("nome"));
        v.setEmail(request.getParameter("email"));
        return vendedorDAO.inserir(v) ? "Vendedor inserido com sucesso! ID: " + v.getIdVendedor() : "Falha ao inserir vendedor.";
    }

    private String atualizarVendedor(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_vendedor"));
        Vendedor v = vendedorDAO.buscarPorId(id);
        if (v == null) return "Erro: Vendedor ID " + id + " não existe.";
        v.setNome(request.getParameter("nome"));
        v.setEmail(request.getParameter("email"));
        return vendedorDAO.atualizar(v) ? "Vendedor ID " + id + " atualizado com sucesso!" : "Falha ao atualizar ID " + id;
    }

    private String removerVendedor(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_vendedor"));
        return vendedorDAO.remover(id) ? "Vendedor ID " + id + " removido com sucesso!" : "Falha ao remover ID " + id;
    }
}