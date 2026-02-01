package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProdutoDAO;
import model.Produto;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.produtoDAO = new ProdutoDAO();
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
        String idParam = request.getParameter("id_produto");
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
                    case "insert": mensagem = inserirProduto(request); break;
                    case "update": mensagem = atualizarProduto(request); break;
                    case "delete": mensagem = removerProduto(request); break;
                }
            }
        } catch (Exception e) { mensagem = "Erro: " + e.getMessage(); }
        response.getWriter().println("<h3>Resultado:</h3><p>" + mensagem + "</p><a href='/EletroMarketPlace/produto.html'>Voltar</a>");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response, String idParam) throws IOException {
        try {
            Long id = Long.parseLong(idParam);
            Produto p = produtoDAO.buscarPorId(id);
            if (p != null) {
                response.getWriter().println("<h2>Produto: " + p.getNome() + " (ID: " + p.getIdProduto() + ")</h2>");
            } else { response.getWriter().println("Produto ID " + id + " não encontrado."); }
        } catch (Exception e) { response.getWriter().println("Erro ao buscar."); }
    }

    private void listarTodos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Produto> lista = produtoDAO.listarTodos();
        response.getWriter().println("<h2>Produtos</h2><table border='1'>");
        for (Produto p : lista) {
            response.getWriter().println("<tr><td>" + p.getIdProduto() + "</td><td>" + p.getNome() + "</td></tr>");
        }
        response.getWriter().println("</table><br><a href='/EletroMarketPlace/produto.html'>Voltar</a>");
    }

    private String inserirProduto(HttpServletRequest request) {
        Produto p = new Produto();
        p.setNome(request.getParameter("nome"));
        p.setDescricao(request.getParameter("descricao"));
        p.setPreco(Double.parseDouble(request.getParameter("preco")));
        p.setEstoque(Integer.parseInt(request.getParameter("estoque")));
        p.setIdCategoria(Long.parseLong(request.getParameter("id_categoria")));
        p.setIdVendedor(Long.parseLong(request.getParameter("id_vendedor")));
        return produtoDAO.inserir(p) ? "Produto inserido com sucesso! ID: " + p.getIdProduto() : "Falha ao inserir produto.";
    }

    private String atualizarProduto(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_produto"));
        Produto p = produtoDAO.buscarPorId(id);
        if (p == null) return "Erro: Produto ID " + id + " não existe.";
        p.setNome(request.getParameter("nome"));
        p.setPreco(Double.parseDouble(request.getParameter("preco")));
        p.setEstoque(Integer.parseInt(request.getParameter("estoque")));
        return produtoDAO.atualizar(p) ? "Produto ID " + id + " atualizado com sucesso!" : "Falha ao atualizar ID " + id;
    }

    private String removerProduto(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id_produto"));
        return produtoDAO.remover(id) ? "Produto ID " + id + " removido com sucesso!" : "Falha ao remover ID " + id;
    }
}