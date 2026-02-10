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
import model.Pedido;
import model.Produto;
import model.Vendedor;

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
        	    Long idProd = Long.parseLong(request.getParameter("id_produto"));
        	    Long idVend = Long.parseLong(request.getParameter("id_vendedor"));
        	    
        	    // 1. Instanciar os DAOs para verificação
        	    ProdutoDAO prodDAO = new ProdutoDAO();
        	    VendedorDAO vendDAO = new VendedorDAO();
        	    
        	    // 2. Buscar as entidades (o buscarPorId já filtra por ativo=true no seu DAO refatorado)
        	    Produto p = prodDAO.buscarPorId(idProd);
        	    Vendedor v = vendDAO.buscarPorId(idVend);
        	    
        	    // 3. Validar se ambos estão ativos/existentes
        	    if (p != null && v != null) {
        	        Pedido novoPedido = new Pedido();
        	        novoPedido.setIdProduto(idProd);
        	        novoPedido.setIdVendedor(idVend);
        	        novoPedido.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        	        
        	        if (pedidoDAO.inserir(novoPedido)) {
        	            request.setAttribute("mensagem", "Pedido realizado com sucesso!");
        	            request.getRequestDispatcher("sucesso.jsp").forward(request, response);
        	        }
        	    } else {
        	        // Caso um deles esteja inativo (Soft Delete) ou não exista
        	        request.setAttribute("erro", "Não é possível realizar o pedido: Produto ou Vendedor inativo ou inexistente.");
        	        request.getRequestDispatcher("erro.jsp").forward(request, response);
        	    }
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