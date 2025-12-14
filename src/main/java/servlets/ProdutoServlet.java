package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import model.Produto;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet{
	public void init() throws ServletException{
		super.init();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String descricao = request.getParameter("descricao");
		double preco = Double.parseDouble(request.getParameter("preco"));
		int estoque = Integer.parseInt(request.getParameter("estoque"));
		Long idCategoria = Long.parseLong(request.getParameter("id_categoria"));
		Long idVendedor = Long.parseLong(request.getParameter("id_vendedor"));
		
		Produto p = new Produto();
		p.setNome(nome);
		p.setDescricao(descricao);
		p.setPreco(preco);
		p.setEstoque(estoque);
		p.setIdCategoria(idCategoria);
		p.setIdVendedor(idVendedor);
		
		ProdutoDAO pDao = new ProdutoDAO();
		pDao.inserir(p);
		
		response.getWriter().println("Produto Inserido com sucesso");
		
		
	}
}
