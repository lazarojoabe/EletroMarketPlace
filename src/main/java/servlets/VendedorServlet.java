package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VendedorDAO;
import model.Vendedor;

@WebServlet("/vendedor")
public class VendedorServlet extends HttpServlet{
	private VendedorDAO vendedorDAO;

	public void init() throws ServletException{
		super.init();
		this.vendedorDAO = new VendedorDAO(); 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        
		String idParam = request.getParameter("id_vendedor");
        
		if (idParam != null && !idParam.isEmpty()) {
			try {
				Long id = Long.parseLong(idParam);
				Vendedor vendedor = vendedorDAO.buscarPorId(id);
                
				if (vendedor != null) {
					// Exibe os detalhes do vendedor encontrado
					response.getWriter().println("<!DOCTYPE html><html><head><title>Resultado da Pesquisa</title></head><body>");
					response.getWriter().println("<h2>Vendedor Encontrado:</h2>");
					response.getWriter().println("<ul>");
					response.getWriter().println("<li><strong>ID:</strong> " + vendedor.getIdVendedor() + "</li>");
					response.getWriter().println("<li><strong>Nome:</strong> " + vendedor.getNome() + "</li>");
					response.getWriter().println("<li><strong>Email:</strong> " + vendedor.getEmail() + "</li>");
					response.getWriter().println("</ul>");
					response.getWriter().println("</body></html>");
				} else {
					response.getWriter().println("Vendedor com ID " + id + " não encontrado.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().println("Erro: ID do vendedor inválido.");
			} catch (Exception e) {
				response.getWriter().println("Erro ao buscar o vendedor: " + e.getMessage());
			}
		} else {
			response.getWriter().println("Por favor, forneça um ID de vendedor para pesquisar.");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		String mensagem = "";

		if (action == null) {
			mensagem = "Erro: Ação não especificada.";
		} else {
			try {
				switch (action) {
					case "insert":
						mensagem = inserirVendedor(request);
						break;
					case "update":
						mensagem = atualizarVendedor(request);
						break;
					case "delete":
						mensagem = removerVendedor(request); 
						break;
					default:
						mensagem = "Ação '" + action + "' não reconhecida.";
						break;
				}
			} catch (NumberFormatException e) {
				mensagem = "Erro de formato: O ID do vendedor está inválido. Detalhe: " + e.getMessage();
			} catch (Exception e) {
				mensagem = "Erro interno do servidor: " + e.getMessage();
			}
		}
		
		response.getWriter().println(mensagem);
	}

    /** Lógica para inserir um novo vendedor (CREATE) */
	private String inserirVendedor(HttpServletRequest request) throws Exception {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		Vendedor v = new Vendedor();
		v.setNome(nome);
		v.setEmail(email);
		
		if (vendedorDAO.inserir(v)) {
			return "Vendedor Inserido com sucesso! ID gerado: " + v.getIdVendedor();
		} else {
			return "Falha ao inserir o vendedor (pode ser problema de conexão ou email duplicado).";
		}
	}

    /** Lógica para atualizar um vendedor existente (UPDATE) */
	private String atualizarVendedor(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_vendedor"));
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		Vendedor v = new Vendedor();
		v.setIdVendedor(id);
		v.setNome(nome);
		v.setEmail(email);

		if (vendedorDAO.atualizar(v)) {
			return "Vendedor Atualizado com sucesso!";
		} else {
            return "Falha ao atualizar o vendedor (ID " + id + " não encontrado ou problema de conexão).";
        }
	}

    /** Lógica para remover um vendedor (DELETE) */
	private String removerVendedor(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_vendedor"));
		
		if (vendedorDAO.remover(id)) {
			return "Vendedor com ID " + id + " Removido com sucesso!";
		} else {
			return "Falha ao remover o vendedor (pode ser que o ID não exista ou haja uma restrição de chave estrangeira).";
		}
	}
}