package servlets;

import java.io.IOException;
import java.util.List; // Import necessário para a lista

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import model.Produto;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; // Adicionado serialVersionUID
	private ProdutoDAO produtoDAO;

	public void init() throws ServletException{
		super.init();
		this.produtoDAO = new ProdutoDAO(); // Inicializa o DAO
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String idParam = request.getParameter("id_produto");
		
		if (idParam != null && !idParam.isEmpty()) {
			// CENÁRIO 1: BUSCAR POR ID
			try {
				Long id = Long.parseLong(idParam);
				Produto produto = produtoDAO.buscarPorId(id);
				
				if (produto != null) {
					// Exibe os detalhes do produto encontrado
					response.getWriter().println("<!DOCTYPE html><html><head><title>Resultado da Pesquisa</title></head><body>");
					response.getWriter().println("<h2> Produto Encontrado:</h2>");
					response.getWriter().println("<ul>");
					response.getWriter().println("<li><strong>ID:</strong> " + produto.getIdProduto() + "</li>");
					response.getWriter().println("<li><strong>Nome:</strong> " + produto.getNome() + "</li>");
					response.getWriter().println("<li><strong>Descrição:</strong> " + produto.getDescricao() + "</li>");
					response.getWriter().println("<li><strong>Preço:</strong> " + produto.getPreco() + "</li>");
					response.getWriter().println("<li><strong>Estoque:</strong> " + produto.getEstoque() + "</li>");
					response.getWriter().println("<li><strong>ID Categoria:</strong> " + produto.getIdCategoria() + "</li>");
					response.getWriter().println("<li><strong>ID Vendedor:</strong> " + produto.getIdVendedor() + "</li>");
					response.getWriter().println("</ul>");
					response.getWriter().println("<p><a href='/db_connection/produto'>Voltar para a Lista</a>"); // Link de volta ajustado
					response.getWriter().println("</body></html>");
				} else {
					response.getWriter().println("Produto com ID " + id + " não encontrado.");
				}
			} catch (NumberFormatException e) {
				response.getWriter().println("Erro: ID do produto inválido.");
			} catch (Exception e) {
				response.getWriter().println("Erro ao buscar o produto: " + e.getMessage());
			}
		} else {
			// CENÁRIO 2: LISTAR TODOS OS PRODUTOS
            try {
                // É NECESSÁRIO implementar o método listarTodos() em ProdutoDAO
                List<Produto> produtos = produtoDAO.listarTodos(); 

                // Cabeçalho HTML com estilo para a tabela
                response.getWriter().println("<!DOCTYPE html><html><head><title>Lista de Produtos</title>");
                response.getWriter().println("<style>");
                response.getWriter().println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f7f9; }");
                response.getWriter().println(".container { max-width: 1000px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
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
                response.getWriter().println("<h2>Lista de Todos os Produtos</h2>");

                if (produtos == null || produtos.isEmpty()) {
                    response.getWriter().println("<p>Nenhum produto cadastrado.</p>");
                } else {
                    response.getWriter().println("<table>");
                    response.getWriter().println("<thead><tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Preço</th><th>Estoque</th><th>ID Categoria</th><th>ID Vendedor</th></tr></thead>");
                    response.getWriter().println("<tbody>");
                    
                    for (Produto p : produtos) {
                        response.getWriter().println("<tr>");
                        response.getWriter().println("<td>" + p.getIdProduto() + "</td>");
                        response.getWriter().println("<td>" + p.getNome() + "</td>");
                        response.getWriter().println("<td>" + p.getDescricao() + "</td>");
                        response.getWriter().println("<td>R$ " + String.format("%.2f", p.getPreco()) + "</td>");
                        response.getWriter().println("<td>" + p.getEstoque() + "</td>");
                        response.getWriter().println("<td>" + p.getIdCategoria() + "</td>");
                        response.getWriter().println("<td>" + p.getIdVendedor() + "</td>");
                        response.getWriter().println("</tr>");
                    }
                    
                    response.getWriter().println("</tbody></table>");
                }
                response.getWriter().println("<p style='margin-top: 20px;'><a href='/db_connection/indexProduto.html'>Voltar ao CRUD</a></p>"); // Link ajustado
                response.getWriter().println("</div>");
                response.getWriter().println("</body></html>");

            } catch (Exception e) {
                response.getWriter().println("Erro ao listar produtos: " + e.getMessage());
            }
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		String mensagem = "";

		if (action == null) {
			mensagem = "Erro: Ação não especificada. Por favor, verifique se o campo 'action' está presente no formulário.";
		} else {
			try {
				switch (action) {
					case "insert":
						mensagem = inserirProduto(request);
						break;
					case "update":
						mensagem = atualizarProduto(request);
						break;
					case "delete":
						mensagem = removerProduto(request); // Usando 'remover' conforme seu DAO
						break;
					default:
						mensagem = "Ação '" + action + "' não reconhecida.";
						break;
				}
			} catch (NumberFormatException e) {
				mensagem = "Erro de formato: Um ou mais campos numéricos estão inválidos. Detalhe: " + e.getMessage();
			} catch (Exception e) {
				mensagem = "Erro interno do servidor: " + e.getMessage();
			}
		}
		
		response.getWriter().println(mensagem);
	}

    /** Lógica para inserir um novo produto (CREATE) */
	private String inserirProduto(HttpServletRequest request) throws NumberFormatException, Exception {
        // Os campos 'id_categoria' e 'id_vendedor' são requeridos pelo DAO
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
		
		if (produtoDAO.inserir(p)) {
			return " Produto Inserido com sucesso! ID gerado: " + p.getIdProduto();
		} else {
			return " Falha ao inserir o produto.";
		}
	}

    /** Lógica para atualizar um produto existente (UPDATE) */
	private String atualizarProduto(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_produto"));
		String nome = request.getParameter("nome");
		double preco = Double.parseDouble(request.getParameter("preco"));
		int estoque = Integer.parseInt(request.getParameter("estoque"));
		
		// 1. Buscar o produto existente para obter os dados não incluídos no formulário (descricao, id_categoria, id_vendedor)
		Produto p = produtoDAO.buscarPorId(id);
		
		if (p == null) {
			return " Produto com ID " + id + " não encontrado para atualização.";
		}

		// 2. Atualizar APENAS os campos fornecidos pelo formulário
		p.setNome(nome);
		// Manter a descrição original, pois não está no formulário de update
		// p.setDescricao(descricao); 
		p.setPreco(preco);
		p.setEstoque(estoque);
        // Manter ID Categoria e Vendedor originais
		
        // 3. Persistir a atualização
		if (produtoDAO.atualizar(p)) {
			return "Produto Atualizado com sucesso!";
		} else {
            return "Falha ao atualizar o produto.";
        }
	}

    /** Lógica para remover um produto (DELETE) */
	private String removerProduto(HttpServletRequest request) throws NumberFormatException, Exception {
		Long id = Long.parseLong(request.getParameter("id_produto"));
		
		if (produtoDAO.remover(id)) {
			return "Produto com ID " + id + " Removido com sucesso!";
		} else {
			return "Falha ao remover o produto (pode ser que o ID não exista ou haja uma restrição de chave estrangeira).";
		}
	}
}