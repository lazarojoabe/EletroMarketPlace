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

    // doGet: Responsável pela Listagem e pela Busca por ID
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_categoria");

        if (idParam != null && !idParam.isEmpty()) {
            // OPERAÇÃO BUSCAR POR ID
            try {
                Long id = Long.parseLong(idParam);
                Categoria c = catDao.buscarPorId(id);
                if (c != null) {
                    request.setAttribute("categoriaEncontrada", c);
                    request.getRequestDispatcher("resultadoBusca.jsp").forward(request, response);
                } else {
                    request.setAttribute("erro", "Categoria com ID " + id + " não encontrada.");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("erro", "ID inválido.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            // OPERAÇÃO LISTAR TODAS
            List<Categoria> lista = catDao.listarTodas();
            request.setAttribute("listaCategorias", lista);
            request.getRequestDispatcher("listaCategorias.jsp").forward(request, response);
        }
    }

    // doPost: Responsável por Inserir, Atualizar e Excluir
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String mensagem = "";
        boolean sucesso = false;

        try {
            if ("insert".equals(action)) {
                Categoria c = new Categoria();
                c.setNome(request.getParameter("nome"));
                sucesso = catDao.inserir(c);
                mensagem = "Categoria inserida com sucesso!";
            } 
            else if ("update".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id_categoria"));
                String novoNome = request.getParameter("nome");
             
                Categoria c = new Categoria(); 
                c.setIdCategoria(id);
                c.setNome(novoNome);
                
                sucesso = catDao.atualizar(c);
                mensagem = sucesso ? "Categoria atualizada com sucesso!" : "Falha ao atualizar.";
            } 
            else if ("delete".equals(action)) {

                Long id = Long.parseLong(request.getParameter("id_categoria"));
                sucesso = catDao.remover(id);
                mensagem = sucesso ? "Categoria excluída com sucesso!" : "Falha ao excluir.";
            }

            if (sucesso) {
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("sucesso.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "A operação no banco de dados falhou.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("erro", "Erro técnico: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}