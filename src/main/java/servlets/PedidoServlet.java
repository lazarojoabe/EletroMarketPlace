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
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        this.pedidoDAO = new PedidoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_pedido");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Pedido p = pedidoDAO.buscarPorId(Long.parseLong(idParam));
                if (p != null) {
                    request.setAttribute("pedidoEncontrado", p);
                    request.getRequestDispatcher("resultadoBuscaPedido.jsp").forward(request, response);
                } else {
                    request.setAttribute("erro", "Pedido não encontrado.");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("erro", "Erro ao processar busca.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            List<Pedido> lista = pedidoDAO.listarTodos();
            request.setAttribute("listaPedidos", lista);
            request.getRequestDispatcher("listaPedidos.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        boolean sucesso = false;
        String msg = "";

        try {
            if ("insert".equals(action)) {
                Pedido p = new Pedido();
                p.setIdProduto(Long.parseLong(request.getParameter("id_produto")));
                p.setIdVendedor(Long.parseLong(request.getParameter("id_vendedor")));
                p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                // Lógica de inserção no DAO...
                sucesso = pedidoDAO.inserir(p);
                msg = "Pedido registrado com sucesso!";
            } else if ("update".equals(action)) {
                Pedido p = new Pedido();
                p.setIdPedido(Long.parseLong(request.getParameter("id_pedido")));
                p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                // Outros campos de status...
                sucesso = pedidoDAO.atualizar(p);
                msg = "Pedido atualizado!";
            } else if ("delete".equals(action)) {
                sucesso = pedidoDAO.remover(Long.parseLong(request.getParameter("id_pedido")));
                msg = "Pedido cancelado!";
            }

            if (sucesso) {
                request.setAttribute("mensagem", msg);
                request.getRequestDispatcher("sucesso.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Falha na operação de pedidos.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}