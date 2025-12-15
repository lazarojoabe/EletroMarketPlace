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
	private ProdutoDAO produtoDAO;

	public void init() throws ServletException{
		super.init();
		this.produtoDAO = new ProdutoDAO(); // Inicializa o DAO
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        
		String idParam = request.getParameter("id_produto");
        
		if (idParam != null && !idParam.isEmpty()) {
			try {
				Long id = Long.parseLong(idParam);
				Produto produto = produtoDAO.buscarPorId(id);
                
				if (produto != null) {
					// Exibe os detalhes do produto encontrado (ou redireciona para uma página de visualização)
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
					response.getWriter().println("<p><a href='/'>Voltar</a></p>"); // Altere para a URL correta do seu formulário
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
			response.getWriter().println("Por favor, forneça um ID de produto para pesquisar.");
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
		p.setPreco(preco);
		p.setEstoque(estoque);

        
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