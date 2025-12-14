package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import dao.CategoriaDAO;
import model.Categoria;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String nome = req.getParameter("nome");
		
		Categoria cat = new Categoria();
		cat.setNome(nome);
		
		CategoriaDAO catDao = new CategoriaDAO();
		
		catDao.inserir(cat);
		
		resp.getWriter().println("Categoria salva com sucesso!");
	}
}
