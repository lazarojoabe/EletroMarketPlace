package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.PedidoDAO;
import model.Pedido;

@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.pedidoDAO = new PedidoDAO();
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
        String idParam = request.getParameter("id_pedido");
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
                    case "insert": mensagem = inserirPedido(request); break;
                    case "update": mensagem = atualizarPedido(request); break;
                    case "delete": mensagem = removerPedido(request); break;
                }
            }
        } catch (Exception e) { mensagem = "Erro: " + e.getMessage(); }
        response.getWriter().println("<h3>Resultado:</h3><p>" + mensagem + "</p><a href='/EletroMarketPlace/pedido.html'>Voltar</a>");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response, String idParam) throws IOException {
        try {
            Long id = Long.parseLong(idParam);
            Pedido p = pedidoDAO.buscarPorId(id);
            if (p != null) {
                response.getWriter().println("<h2>Pedido #" + p.getIdPedido() + "</h2>");
            } else { response.getWriter().println("Pedido ID " + id + " não encontrado."); }
        } catch (Exception e) { response.getWriter().println("Erro."); }
    }

    private void listarTodos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Pedido> lista = pedidoDAO.listarTodos();
        response.getWriter().println("<h2>Pedidos</h2><table border='1'>");
        for (Pedido p : lista) {
            response.getWriter().println("<tr><td>" + p.getIdPedido() + "</td><td>" + p.getQuantidade() + "</td></tr>");
        }
        response.getWriter().println("</table><br><a href='/EletroMarketPlace/pedido.html'>Voltar</a>");
    }

    private String inserirPedido(HttpServletRequest request) {
        Pedido p = new Pedido();
        p.setIdProduto(Long.parseLong(request.getParameter("id_produto")));
        p.setIdVendedor(Long.parseLong(request.getParameter("id_vendedor")));
        p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        return pedidoDAO.inserir(p) ? "Pedido inserido com sucesso! ID: " + p.getIdPedido() : "Falha ao inserir pedido.";
    }

    private String atualizarPedido(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_pedido"));
        Pedido p = pedidoDAO.buscarPorId(id);
        if (p == null) return "Erro: Pedido ID " + id + " não existe.";
        p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        return pedidoDAO.atualizar(p) ? "Pedido ID " + id + " atualizado com sucesso!" : "Falha ao atualizar ID " + id;
    }

    private String removerPedido(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_pedido"));
        return pedidoDAO.remover(id) ? "Pedido ID " + id + " removido com sucesso!" : "Falha ao remover ID " + id;
    }
}