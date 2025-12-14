package servlets;

import javax.servlet.http.HttpServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import dao.VendedorDAO;
import model.Produto;
import model.Vendedor;


@WebServlet("/vendedor")
public class VendedorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		Vendedor vend = new Vendedor();
		vend.setNome(nome);
		vend.setEmail(email);
		
		VendedorDAO vendDAO = new VendedorDAO();
		vendDAO.inserir(vend);

		response.getWriter().println("v Inserido com sucesso");
		
		
	}
}
