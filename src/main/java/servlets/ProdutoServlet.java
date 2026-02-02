package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Lista de Produtos</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, sans-serif; background-color: #eef3f7; padding: 40px; }");
        out.println(".card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); max-width: 900px; margin: auto; }");
        out.println("h2 { color: #0077c7; border-bottom: 2px solid #0077c7; padding-bottom: 10px; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th { background-color: #0077c7; color: white; padding: 12px; text-align: left; }");
        out.println("td { padding: 12px; border-bottom: 1px solid #ddd; color: #2c3e50; }");
        out.println("tr:hover { background-color: #f1f7fc; }");
        out.println(".btn-back { display: inline-block; margin-top: 20px; padding: 10px 20px; background: #0077c7; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }");
        out.println("</style></head><body>");
        out.println("<div class='card'><h2>Estoque de Produtos</h2>");
        out.println("<table><tr><th>ID</th><th>Nome</th><th>Preço</th><th>Estoque</th></tr>");
        for (Produto p : lista) {
            out.println("<tr><td>" + p.getIdProduto() + "</td><td>" + p.getNome() + "</td><td>R$ " + String.format("%.2f", p.getPreco()) + "</td><td>" + p.getEstoque() + "</td></tr>");
        }
        out.println("</table><a href='produto.html' class='btn-back'>Voltar ao Cadastro</a></div></body></html>");
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