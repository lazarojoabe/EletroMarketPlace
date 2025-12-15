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
import model.Produto;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	CategoriaDAO catDao = new CategoriaDAO();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String action = req.getParameter("action");
		String msg = "";
		
		if(action == null) {
			msg = "Erro: Ação não especificada!";
		} else {
			try {
				switch(action) {
					case "insert":
						msg = inserirCategoria(req);
						break;
					case "delete":
						msg = removerCategoria(req);
						break;
					case "update":
						msg = atualizarCategoria(req);
						break;
				}
			} catch (NumberFormatException e) {
				msg = "Erro de formato: Um ou mais campos numéricos estão inválidos. Detalhe: " + e.getMessage();
			} catch (Exception e) {
				msg = "Erro interno do servidor: " + e.getMessage();
			}
		}
		
		resp.getWriter().println(msg);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String idCategoria = request.getParameter("id_categoria");
		
		if (idCategoria != null && !idCategoria.isEmpty()) {
			try {
				Long id = Long.parseLong(idCategoria);
				Categoria categoria = catDao.buscarPorId(id);
                
				if (categoria != null) {
					// Exibe os detalhes do produto encontrado (ou redireciona para uma página de visualização)
					response.getWriter().println("<!DOCTYPE html><html><head><title>Resultado da Pesquisa</title></head><body>");
					response.getWriter().println("<h2> Categoria Encontrada:</h2>");
					response.getWriter().println("<ul>");
					response.getWriter().println("<li><strong>Nome:</strong> " + categoria.getNome() + "</li>");
					response.getWriter().println("</ul>");
					response.getWriter().println("<p><a href='/'>Voltar</a></p>");
					response.getWriter().println("</body></html>");
				} else {
					response.getWriter().println("Produto com ID " + id + " não encontrado.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().println("Erro: ID do categoria inválida.");
			} catch (Exception e) {
				response.getWriter().println("Erro ao buscar o categoria: " + e.getMessage());
			}
		} else {
			response.getWriter().println("Por favor, forneça um ID de produto para pesquisar.");
		}
	}
	
	private String inserirCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		String nome = request.getParameter("nome");
	
		Categoria cat = new Categoria();
		cat.setNome(nome);
		
		if(catDao.inserir(cat)) {
			return "Categoria inserida com sucesso!";
		} else {
			return "Erro ao inserir categoria!";
		}
	}
	
	private String atualizarCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_categoria"));
		
		String nome = request.getParameter("nome");
		
		Categoria cat = catDao.buscarPorId(id);
		
		if(cat == null) {
			return "Erro ao buscar esse ID de categoria!";
		}
		
		cat.setNome(nome);
		
		if(catDao.atualizar(cat)) {
			return "Categoria atualizada com sucesso!";
		} else {
			return "Erro ao atualizar categoria";
		}
		
	}
	
	private String removerCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_categoria"));
		
		if(catDao.remover(id)) {
			return "Categoria com id" + id + "removida com sucesso!";
		} else {
			return "Erro ao remover a categoria!";
		}
	}
	
}
