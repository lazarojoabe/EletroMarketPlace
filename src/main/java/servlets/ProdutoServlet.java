package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import dao.VendedorDAO;
import model.Categoria;
import model.Produto;
import model.Vendedor;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.produtoDAO = new ProdutoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_produto");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Produto p = produtoDAO.buscarPorId(id);
                if (p != null) {
                    request.setAttribute("produtoEncontrado", p);
                    request.getRequestDispatcher("resultadoBuscaProduto.jsp").forward(request, response);
                } else {
                    request.setAttribute("erro", "Produto não encontrado.");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("erro", "Erro na busca.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            List<Produto> lista = produtoDAO.listarTodos();
            request.setAttribute("listaProdutos", lista);
            request.getRequestDispatcher("listaProdutos.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String mensagem = "";
        boolean sucesso = false;

        try {
            if ("insert".equals(action)) {
            		Long idCat = Long.parseLong(request.getParameter("id_categoria"));
            		Long idVend = Long.parseLong(request.getParameter("id_vendedor"));
                CategoriaDAO catDAO = new CategoriaDAO();
                VendedorDAO vendDAO = new VendedorDAO();
                Categoria c = catDAO.buscarPorId(idCat);
                Vendedor v = vendDAO.buscarPorId(idVend);
                
                if (c != null && v != null) {
                		Produto p = new Produto();
                    p.setNome(request.getParameter("nome"));
                    p.setDescricao(request.getParameter("descricao"));
                    p.setPreco(Double.parseDouble(request.getParameter("preco")));
                    p.setEstoque(Integer.parseInt(request.getParameter("estoque")));
                    p.setIdCategoria(Long.parseLong(request.getParameter("id_categoria")));
                    p.setIdVendedor(Long.parseLong(request.getParameter("id_vendedor")));
                    sucesso = produtoDAO.inserir(p);
                    mensagem = "Produto inserido com sucesso!";
                } else {
                    request.setAttribute("erro", "Erro: A categoria ou vendedor selecionado(a) está inativo(a) ou não existe.");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                }
                
            } else if ("update".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id_produto"));
                Produto p = new Produto();
                p.setIdProduto(id);
                p.setNome(request.getParameter("nome"));
                p.setPreco(Double.parseDouble(request.getParameter("preco")));
                p.setEstoque(Integer.parseInt(request.getParameter("estoque")));
                sucesso = produtoDAO.atualizar(p);
                mensagem = "Produto atualizado com sucesso!";
            } else if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id_produto"));
                sucesso = produtoDAO.remover(id);
                mensagem = "Produto removido com sucesso!";
            }

            if (sucesso) {
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("sucesso.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Falha na operação.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}