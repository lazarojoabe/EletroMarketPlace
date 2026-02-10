package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.VendedorDAO;
import dao.CategoriaDAO;
import model.Pedido;
import model.Produto;
import model.Vendedor;
import model.Categoria;

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
                // Captura obrigatória dos três IDs
                Long idProd = Long.parseLong(request.getParameter("id_produto"));
                Long idVend = Long.parseLong(request.getParameter("id_vendedor"));
                Long idCat = Long.parseLong(request.getParameter("id_categoria"));
                
                // DAOs para verificação de status (Soft Delete)
                ProdutoDAO prodDAO = new ProdutoDAO();
                VendedorDAO vendDAO = new VendedorDAO();
                CategoriaDAO catDAO = new CategoriaDAO();
                
                // O buscarPorId retorna null se a flag 'ativo' for false
                Produto p = prodDAO.buscarPorId(idProd);
                Vendedor v = vendDAO.buscarPorId(idVend);
                Categoria c = catDAO.buscarPorId(idCat);
                
                // Validação: Produto, Vendedor e Categoria DEVEM estar ativos
                if (p != null && v != null && c != null) {
                    Pedido novoPedido = new Pedido();
                    novoPedido.setIdProduto(idProd);
                    novoPedido.setIdVendedor(idVend);
                    novoPedido.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                    
                    if (pedidoDAO.inserir(novoPedido)) {
                        request.setAttribute("mensagem", "Pedido realizado com sucesso!");
                        request.getRequestDispatcher("sucesso.jsp").forward(request, response);
                        return;
                    }
                } else {
                    // Identifica qual entidade causou o bloqueio para informar o usuário
                    String erroMsg = "Operação negada: ";
                    if (p == null) erroMsg += "Produto inativo/inexistente. ";
                    if (v == null) erroMsg += "Vendedor inativo/inexistente. ";
                    if (c == null) erroMsg += "Categoria inativa/inexistente.";
                    
                    request.setAttribute("erro", erroMsg);
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                    return;
                }
            } else if ("update".equals(action)) {
                Pedido p = new Pedido();
                p.setIdPedido(Long.parseLong(request.getParameter("id_pedido")));
                p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                sucesso = pedidoDAO.atualizar(p);
                msg = "Quantidade do pedido atualizada!";
            } else if ("delete".equals(action)) {
                sucesso = pedidoDAO.remover(Long.parseLong(request.getParameter("id_pedido")));
                msg = "Pedido removido do histórico!";
            }

            if (sucesso) {
                request.setAttribute("mensagem", msg);
                request.getRequestDispatcher("sucesso.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Falha na operação de pedidos.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro técnico: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}