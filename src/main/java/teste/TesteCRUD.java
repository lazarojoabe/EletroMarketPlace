package teste;

import dao.*;
import model.*;

import java.math.BigDecimal;
import java.util.List;

public class TesteCRUD {
    public static void main(String[] args) {
    	
    	Vendedor vendedor = new Vendedor("Joao", "joao@email.com");
    	VendedorDAO vendDAO = new VendedorDAO();
    	
    	
    	
    	
        //testarCategorias();
        //testarVendedores();
        //testarProdutos();
        //testarPedidos();
    	
    	
    }

    private static void testarCategorias() {
        System.out.println("\n--- TESTE CATEGORIA ---");
        CategoriaDAO dao = new CategoriaDAO();

        Categoria c = new Categoria("Periféricos");
        dao.inserir(c);
        System.out.println("Inserida: " + c);

        Categoria buscada = dao.buscarPorId(c.getIdCategoria());
        System.out.println("Buscada: " + buscada);

        c.setNome("Periféricos Gamer");
        dao.atualizar(c);
        System.out.println("Atualizada.");

        List<Categoria> lista = dao.listarTodas();
        System.out.println("Categorias: " + lista.size());
    }

    private static void testarVendedores() {
        System.out.println("\n--- TESTE VENDEDOR ---");
        VendedorDAO dao = new VendedorDAO();

        Vendedor v = new Vendedor("Loja XPTO", "contato@xpto.com");
        dao.inserir(v);
        System.out.println("Inserido: " + v);

        Vendedor buscado = dao.buscarPorId(v.getIdVendedor());
        System.out.println("Buscado: " + buscado);

        v.setNome("Loja XPTO Digital");
        dao.atualizar(v);
        System.out.println("Atualizado.");

        List<Vendedor> lista = dao.listarTodos();
        System.out.println("Total de vendedores: " + lista.size());
    }

    private static void testarProdutos() {
        System.out.println("\n--- TESTE PRODUTOS ---");
        ProdutoDAO dao = new ProdutoDAO();

		/*
		 * Produto p = new Produto( "Webcam Full HD", "Webcam 1080p com microfone", new
		 * BigDecimal("199.90"), 10, 3L, 2L );
		 * 
		 * Produto p2 = new Produto("Celular", "Celular muito boom", new
		 * BigDecimal("199.90"), 10, 3L, 2L);
		 * 
		 * dao.inserir(p); dao.inserir(p2); System.out.println("Inserido: " +
		 * p2.toString()); System.out.println("Inserido: " + p);
		 */

        // Produto buscado = dao.buscarPorId(p.getIdProduto());
        //System.out.println("Buscado: " + buscado);

      
        
        //p.setPreco(new BigDecimal("179.90"));
		/*
		 * dao.atualizar(p); System.out.println("Atualizado.");
		 * 
		 * dao.atualizarEstoque(p.getIdProduto(), 25);
		 * System.out.println("Estoque atualizado.");
		 */

        List<Produto> lista = dao.listarTodos();
        System.out.println("Produtos: " + lista.size());
    }

    private static void testarPedidos() {
        System.out.println("\n--- TESTE PEDIDOS ---");
        PedidoDAO dao = new PedidoDAO();

        Pedido pedido = new Pedido(1L, 1L, 2);
        dao.inserir(pedido);
        System.out.println("Inserido: " + pedido);

        Pedido buscado = dao.buscarPorId(pedido.getIdPedido());
        System.out.println("Buscado: " + buscado);

        pedido.setQuantidade(5);
        dao.atualizar(pedido);
        System.out.println("Atualizado.");

        List<Pedido> lista = dao.listarTodos();
        System.out.println("Pedidos: " + lista.size());
    }
    
}
