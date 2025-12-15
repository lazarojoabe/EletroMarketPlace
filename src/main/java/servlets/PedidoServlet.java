package servlets;

import java.io.IOException;
import java.util.List; // Import necessário para a lista

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PedidoDAO;
import model.Pedido;


@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; // Adicione o serialVersionUID
	private PedidoDAO pedidoDAO;

	public void init() throws ServletException{
		super.init();
		this.pedidoDAO = new PedidoDAO(); 
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String idParam = request.getParameter("id_pedido");
		
		if (idParam != null && !idParam.isEmpty()) {
			// CENÁRIO 1: BUSCAR POR ID
			try {
				Long id = Long.parseLong(idParam);
				Pedido pedido = pedidoDAO.buscarPorId(id);
				
				if (pedido != null) {
					// Exibe os detalhes do pedido encontrado
					response.getWriter().println("<!DOCTYPE html><html><head><title>Resultado da Pesquisa</title></head><body>");
					response.getWriter().println("<h2>Pedido Encontrado:</h2>");
					response.getWriter().println("<ul>");
					response.getWriter().println("<li><strong>ID Pedido:</strong> " + pedido.getIdPedido() + "</li>");
					response.getWriter().println("<li><strong>ID Produto:</strong> " + pedido.getIdProduto() + "</li>");
					response.getWriter().println("<li><strong>ID Vendedor:</strong> " + pedido.getIdVendedor() + "</li>");
					response.getWriter().println("<li><strong>Quantidade:</strong> " + pedido.getQuantidade() + "</li>");
                    response.getWriter().println("<li><strong>Data do Pedido:</strong> " + pedido.getDataPedido() + "</li>");
					response.getWriter().println("</ul>");
					response.getWriter().println("<p><a href='/db_connection/pedido'>Voltar para a Lista</a> | <a href='/db_connection/indexPedido.html'>Voltar ao CRUD</a></p>"); // Assume indexPedido.html
					response.getWriter().println("</body></html>");
				} else {
					response.getWriter().println("Pedido com ID " + id + " não encontrado.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().println("Erro: ID do pedido inválido.");
			} catch (Exception e) {
				response.getWriter().println("Erro ao buscar o pedido: " + e.getMessage());
			}
		} else {
			// CENÁRIO 2: LISTAR TODOS OS PEDIDOS
            try {
                // Necessário implementar o método listarTodos() em PedidoDAO
                List<Pedido> pedidos = pedidoDAO.listarTodos(); 

                // Cabeçalho HTML com estilo básico para a tabela
                response.getWriter().println("<!DOCTYPE html><html><head><title>Lista de Pedidos</title>");
                response.getWriter().println("<style>");
                response.getWriter().println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f7f9; }");
                response.getWriter().println(".container { max-width: 900px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
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
                response.getWriter().println("<h2>Lista de Todos os Pedidos</h2>");

                if (pedidos == null || pedidos.isEmpty()) {
                    response.getWriter().println("<p>Nenhum pedido cadastrado.</p>");
                } else {
                    response.getWriter().println("<table>");
                    response.getWriter().println("<thead><tr><th>ID Pedido</th><th>ID Produto</th><th>ID Vendedor</th><th>Quantidade</th><th>Data Pedido</th></tr></thead>");
                    response.getWriter().println("<tbody>");
                    
                    for (Pedido p : pedidos) {
                        response.getWriter().println("<tr>");
                        response.getWriter().println("<td>" + p.getIdPedido() + "</td>");
                        response.getWriter().println("<td>" + p.getIdProduto() + "</td>");
                        response.getWriter().println("<td>" + p.getIdVendedor() + "</td>");
                        response.getWriter().println("<td>" + p.getQuantidade() + "</td>");
                        response.getWriter().println("<td>" + p.getDataPedido() + "</td>");
                        response.getWriter().println("</tr>");
                    }
                    
                    response.getWriter().println("</tbody></table>");
                }
                response.getWriter().println("<p style='margin-top: 20px;'><a href='/db_connection/indexPedido.html'>Voltar ao CRUD</a></p>"); // Assume indexPedido.html
                response.getWriter().println("</div>");
                response.getWriter().println("</body></html>");

            } catch (Exception e) {
                response.getWriter().println("Erro ao listar pedidos: " + e.getMessage());
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
						mensagem = inserirPedido(request);
						break;
					case "update":
						mensagem = atualizarPedido(request);
						break;
					case "delete":
						mensagem = removerPedido(request); 
						break;
					default:
						mensagem = "Ação '" + action + "' não reconhecida.";
						break;
				}
			} catch (NumberFormatException e) {
				mensagem = "Erro de formato nos parâmetros numéricos: " + e.getMessage();
			} catch (Exception e) {
				mensagem = "Erro interno do servidor: " + e.getMessage();
			}
		}
		
		response.getWriter().println(mensagem);
	}

    /** Lógica para inserir um novo pedido (CREATE) */
	private String inserirPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		Long idProduto = Long.parseLong(request.getParameter("id_produto"));
		Long idVendedor = Long.parseLong(request.getParameter("id_vendedor"));
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		
		Pedido p = new Pedido();
		p.setIdProduto(idProduto);
		p.setIdVendedor(idVendedor);
		p.setQuantidade(quantidade);
		
		if (pedidoDAO.inserir(p)) {
			return "Pedido Inserido com sucesso! ID gerado: " + p.getIdPedido();
		} else {
			return "Falha ao inserir o pedido (verifique as chaves estrangeiras ou a conexão).";
		}
	}

    /** Lógica para atualizar um pedido existente (UPDATE) */
	private String atualizarPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		Long idPedido = Long.parseLong(request.getParameter("id_pedido"));
		int novaQuantidade = Integer.parseInt(request.getParameter("quantidade"));
		
		// 1. Buscar o pedido existente para preservar id_produto e id_vendedor
		Pedido pedidoExistente = pedidoDAO.buscarPorId(idPedido);
		
		if (pedidoExistente == null) {
			return "Falha ao atualizar: Pedido com ID " + idPedido + " não encontrado.";
		}

		// 2. Atualizar APENAS a quantidade
		pedidoExistente.setQuantidade(novaQuantidade);
        
        // 3. Persistir a atualização
		if (pedidoDAO.atualizar(pedidoExistente)) {
			return "Pedido Atualizado com sucesso!";
		} else {
            return "Falha ao atualizar o pedido.";
        }
	}

    /** Lógica para remover um pedido (DELETE) */
	private String removerPedido(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_pedido"));
		
		if (pedidoDAO.remover(id)) {
			return "Pedido com ID " + id + " Removido com sucesso!";
		} else {
			return "Falha ao remover o pedido (ID não existe ou problema de conexão).";
		}
	}
}