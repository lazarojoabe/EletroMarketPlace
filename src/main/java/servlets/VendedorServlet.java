package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Vendedores</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', sans-serif; background-color: #f0f4f8; padding: 40px; }");
        out.println(".card { background: #fff; padding: 30px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); max-width: 800px; margin: auto; }");
        out.println("h2 { color: #0077c7; font-weight: 600; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th { color: #555; text-align: left; padding: 15px; border-bottom: 2px solid #0077c7; }");
        out.println("td { padding: 15px; border-bottom: 1px solid #f0f0f0; color: #444; }");
        out.println("tr:hover { background: #fcfdfe; }");
        out.println(".btn-voltar { display: inline-block; margin-top: 20px; color: #0077c7; text-decoration: none; font-weight: 500; }");
        out.println("</style></head><body>");
        out.println("<div class='card'><h2>Vendedores Ativos</h2>");
        out.println("<table><tr><th>ID</th><th>Nome Completo</th><th>E-mail de Contato</th></tr>");
        for (Vendedor v : lista) {
            out.println("<tr><td>" + v.getIdVendedor() + "</td><td>" + v.getNome() + "</td><td>" + v.getEmail() + "</td></tr>");
        }
        out.println("</table><a href='vendedor.html' class='btn-voltar'>← Voltar para Gestão</a></div></body></html>");
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