package servlets;

import java.io.IOException;
import java.util.List; // Import necessário para a lista

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VendedorDAO;
import model.Vendedor;

@WebServlet("/vendedor")
public class VendedorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; // Adicione o serialVersionUID
	private VendedorDAO vendedorDAO;

	public void init() throws ServletException{
		super.init();
		this.vendedorDAO = new VendedorDAO(); 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String idParam = request.getParameter("id_vendedor");
		
		if (idParam != null && !idParam.isEmpty()) {
			// CENÁRIO 1: BUSCAR POR ID
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
					// Adiciona links para voltar
					response.getWriter().println("<p><a href='/db_connection/vendedor'>Voltar para a Lista</a> | <a href='/db_connection/indexVendedor.html'>Voltar ao CRUD</a></p>"); 
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
			// CENÁRIO 2: LISTAR TODOS OS VENDEDORES
            try {
                // É NECESSÁRIO QUE O VendedorDAO tenha o método listarTodos()
                List<Vendedor> vendedores = vendedorDAO.listarTodos(); 

                // Cabeçalho HTML com estilo para a tabela
                response.getWriter().println("<!DOCTYPE html><html><head><title>Lista de Vendedores</title>");
                response.getWriter().println("<style>");
                response.getWriter().println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f7f9; }");
                response.getWriter().println(".container { max-width: 800px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
                response.getWriter().println("h2 { color: #0077c7; border-bottom: 2px solid #0077c7; padding-bottom: 10px; }");
                response.getWriter().println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                response.getWriter().println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; font-size: 0.9em; }");
                response.getWriter().println("th { background-color: #0077c7; color: white; font-weight: bold; }");
                response.getWriter().println("tr:nth-child(even) { background-color: #f9f9f9; }");
                response.getWriter().println("a { color: #0077c7; text-decoration: none; margin-right: 15px; }");
                response.getWriter().println("a:hover { text-decoration: underline; }");
                response.getWriter().println("</style>");
                response.getWriter().println("</head><body>");
                response.getWriter().println("<div class='container'>");
                response.getWriter().println("<h2>Lista de Todos os Vendedores</h2>");

                if (vendedores == null || vendedores.isEmpty()) {
                    response.getWriter().println("<p>Nenhum vendedor cadastrado.</p>");
                } else {
                    response.getWriter().println("<table>");
                    response.getWriter().println("<thead><tr><th>ID</th><th>Nome</th><th>Email</th></tr></thead>");
                    response.getWriter().println("<tbody>");
                    
                    for (Vendedor v : vendedores) {
                        response.getWriter().println("<tr>");
                        response.getWriter().println("<td>" + v.getIdVendedor() + "</td>");
                        response.getWriter().println("<td>" + v.getNome() + "</td>");
                        response.getWriter().println("<td>" + v.getEmail() + "</td>");
                        response.getWriter().println("</tr>");
                    }
                    
                    response.getWriter().println("</tbody></table>");
                }
                response.getWriter().println("<p style='margin-top: 20px;'><a href='/db_connection/indexVendedor.html'>Voltar ao CRUD</a></p>"); 
                response.getWriter().println("</div>");
                response.getWriter().println("</body></html>");

            } catch (Exception e) {
                response.getWriter().println("Erro ao listar vendedores: " + e.getMessage());
            }
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