package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PedidoDAO;
import model.Pedido;

@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idProduto = Long.parseLong(request.getParameter("id_produto"));
		Long idVendedor = Long.parseLong(request.getParameter("id_vendedor"));
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		
		
		Pedido p = new Pedido();
		p.setIdProduto(idProduto);
		p.setIdVendedor(idVendedor);
		p.setQuantidade(quantidade);
		
		PedidoDAO pDAO = new PedidoDAO();
		pDAO.inserir(p);
		
		response.getWriter().println("Pedido Inserido com sucesso!!!!");
	}
}
