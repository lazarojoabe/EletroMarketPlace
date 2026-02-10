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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_vendedor");
        
        if (idParam != null && !idParam.isEmpty()) {
            // OPERAÇÃO BUSCAR POR ID
            try {
                Long id = Long.parseLong(idParam);
                Vendedor v = vendedorDAO.buscarPorId(id);
                if (v != null) {
                    request.setAttribute("vendedorEncontrado", v);
                    // Encaminha para a página de resultado de busca
                    request.getRequestDispatcher("resultadoBuscaVendedor.jsp").forward(request, response);
                } else {
                    request.setAttribute("erro", "Vendedor com ID " + id + " não encontrado.");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("erro", "ID inválido.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            // OPERAÇÃO LISTAR TODOS
            List<Vendedor> lista = vendedorDAO.listarTodos();
            request.setAttribute("listaVendedores", lista);
            request.getRequestDispatcher("listaVendedores.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String mensagem = "";
        boolean sucesso = false;

        try {
            if ("insert".equals(action)) {
                Vendedor v = new Vendedor();
                v.setNome(request.getParameter("nome"));
                v.setEmail(request.getParameter("email"));
                sucesso = vendedorDAO.inserir(v);
                mensagem = "Vendedor inserido com sucesso!";
            } 
            else if ("update".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id_vendedor"));
                Vendedor v = new Vendedor();
                v.setIdVendedor(id);
                v.setNome(request.getParameter("nome"));
                v.setEmail(request.getParameter("email"));
                sucesso = vendedorDAO.atualizar(v);
                mensagem = "Vendedor atualizado com sucesso!";
            } 
            else if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id_vendedor"));
                sucesso = vendedorDAO.remover(id);
                mensagem = "Vendedor removido com sucesso!";
            }

            if (sucesso) {
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("sucesso.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "A operação falhou no banco de dados.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}