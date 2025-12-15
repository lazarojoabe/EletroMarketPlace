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
					default:
						msg = "Ação não reconhecida: " + action;
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
			// CENÁRIO 1: BUSCAR POR ID
			try {
				Long id = Long.parseLong(idCategoria);
				Categoria categoria = catDao.buscarPorId(id);
				if (categoria != null) {
					response.getWriter().println("<!DOCTYPE html><html><head><title>Resultado da Pesquisa</title></head><body>");
					response.getWriter().println("<h2>Categoria Encontrada:</h2>");
					response.getWriter().println("<ul>");
					response.getWriter().println("<li><strong>ID:</strong> " + categoria.getIdCategoria() + "</li>");
					response.getWriter().println("<li><strong>Nome:</strong> " + categoria.getNome() + "</li>");
					response.getWriter().println("</ul>");
					response.getWriter().println("<p><a href='/db_connection/categoria'>Voltar para a Lista</a> | <a href='/db_connection/index.html'>Voltar ao CRUD</a></p>");
					response.getWriter().println("</body></html>");
				} else {
					response.getWriter().println("Categoria com ID " + id + " não encontrada.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().println("Erro: ID de categoria inválida.");
			} catch (Exception e) {
				response.getWriter().println("Erro ao buscar a categoria: " + e.getMessage());
			}
		} else {
			// CENÁRIO 2: LISTAR TODAS
            try {
                List<Categoria> categorias = catDao.listarTodas();

                // Cabeçalho HTML com estilo básico para a tabela
                response.getWriter().println("<!DOCTYPE html><html><head><title>Lista de Categorias</title>");
                response.getWriter().println("<style>");
                response.getWriter().println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f7f9; }");
                response.getWriter().println(".container { max-width: 800px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
                response.getWriter().println("h2 { color: #0077c7; border-bottom: 2px solid #0077c7; padding-bottom: 10px; }");
                response.getWriter().println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                response.getWriter().println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
                response.getWriter().println("th { background-color: #0077c7; color: white; font-weight: bold; }");
                response.getWriter().println("tr:nth-child(even) { background-color: #f9f9f9; }");
                response.getWriter().println("a { color: #0077c7; text-decoration: none; margin-right: 15px; }");
                response.getWriter().println("a:hover { text-decoration: underline; }");
                response.getWriter().println("</style>");
                response.getWriter().println("</head><body>");
                response.getWriter().println("<div class='container'>");
                response.getWriter().println("<h2>Lista de Todas as Categorias</h2>");

                if (categorias.isEmpty()) {
                    response.getWriter().println("<p>Nenhuma categoria cadastrada.</p>");
                } else {
                    response.getWriter().println("<table>");
                    response.getWriter().println("<thead><tr><th>ID</th><th>Nome</th></tr></thead>");
                    response.getWriter().println("<tbody>");
                    
                    for (Categoria c : categorias) {
                        response.getWriter().println("<tr>");
                        response.getWriter().println("<td>" + c.getIdCategoria() + "</td>");
                        response.getWriter().println("<td>" + c.getNome() + "</td>");
                        response.getWriter().println("</tr>");
                    }
                    
                    response.getWriter().println("</tbody></table>");
                }
                response.getWriter().println("<p style='margin-top: 20px;'><a href='/db_connection/index.html'>Voltar ao CRUD</a></p>");
                response.getWriter().println("</div>");
                response.getWriter().println("</body></html>");

            } catch (Exception e) {
                response.getWriter().println("Erro ao listar categorias: " + e.getMessage());
            }
		}
	}
	
	private String inserirCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		String nome = request.getParameter("nome");
	
		Categoria cat = new Categoria();
		cat.setNome(nome);
		
		if(catDao.inserir(cat)) {
			// Correção: Use getNome() ou uma mensagem mais limpa
			return "Categoria inserida com sucesso! ID gerado: " + cat.getIdCategoria(); 
		} else {
			return "Erro ao inserir categoria!";
		}
	}
	
	private String atualizarCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_categoria"));
		
		String nome = request.getParameter("nome");
		
		Categoria cat = catDao.buscarPorId(id);
		
		if(cat == null) {
			return "Erro ao buscar categoria com ID " + id + " para atualização. Categoria não existe!";
		}
		
		cat.setNome(nome);
		
		if(catDao.atualizar(cat)) {
			return "Categoria ID " + id + " atualizada com sucesso para: " + nome;
		} else {
			return "Erro ao atualizar categoria ID " + id;
		}
		
	}
	
	private String removerCategoria(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_categoria"));
		
		if(catDao.remover(id)) {
			return "Categoria com id " + id + " removida com sucesso!";
		} else {
			return "Erro ao remover a categoria! Verifique se o ID existe ou se há dependências.";
		}
	}
}