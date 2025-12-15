package teste;

import dao.*;
import model.*;

import java.util.List;
import java.util.Scanner;

public class TesteCRUD {
    
    // Scanner para leitura de entrada do usuário
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        // Chamada do menu principal
        exibirMenuPrincipal();
        
        // Fechar o scanner ao sair da aplicação
        scanner.close();
    }

    /**
     * Exibe o menu principal e gerencia a escolha do usuário.
     */
    private static void exibirMenuPrincipal() {
        int opcao;
        
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Categoria CRUD");
            System.out.println("2. Vendedor CRUD");
            System.out.println("3. Produto CRUD");
            System.out.println("4. Pedido CRUD");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    menuCategoria();
                    break;
                case 2:
                    menuVendedor();
                    break;
                case 3:
                    menuProduto();
                    break;
                case 4:
                    menuPedido();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    // --- Menus de CRUD Específicos ---

    private static void menuCategoria() {
        CategoriaDAO dao = new CategoriaDAO();
        int opcao;
        do {
            System.out.println("\n--- CRUD CATEGORIA ---");
            System.out.println("1. Inserir (Create)");
            System.out.println("2. Buscar por ID (Read)");
            System.out.println("3. Listar Todas (Read All)");
            System.out.println("4. Atualizar Nome (Update)");
            System.out.println("5. Deletar (Delete)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    inserirCategoria(dao);
                    break;
                case 2:
                    buscarCategoria(dao);
                    break;
                case 3:
                    listarCategorias(dao);
                    break;
                case 4:
                    atualizarCategoria(dao);
                    break;
                case 5:
                    deletarCategoria(dao); 
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuVendedor() {
        VendedorDAO dao = new VendedorDAO();
        int opcao;
        do {
            System.out.println("\n--- CRUD VENDEDOR ---");
            System.out.println("1. Inserir (Create)");
            System.out.println("2. Buscar por ID (Read)");
            System.out.println("3. Listar Todos (Read All)");
            System.out.println("4. Atualizar Nome e Email (Update)");
            System.out.println("5. Deletar (Delete)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    inserirVendedor(dao);
                    break;
                case 2:
                    buscarVendedor(dao);
                    break;
                case 3:
                    listarVendedores(dao);
                    break;
                case 4:
                    atualizarVendedor(dao);
                    break;
                case 5:
                    deletarVendedor(dao);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuProduto() {
        ProdutoDAO dao = new ProdutoDAO();
        int opcao;
        do {
            System.out.println("\n--- CRUD PRODUTO ---");
            System.out.println("1. Inserir (Create)");
            System.out.println("2. Buscar por ID (Read)");
            System.out.println("3. Listar Todos (Read All)");
            System.out.println("4. Atualizar Preço (Update)");
            System.out.println("5. Atualizar Estoque (Update)");
            System.out.println("6. Deletar (Delete)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    inserirProduto(dao);
                    break;
                case 2:
                    buscarProduto(dao);
                    break;
                case 3:
                    listarProdutos(dao);
                    break;
                case 4:
                    atualizarPrecoProduto(dao);
                    break;
                case 5:
                    atualizarEstoqueProduto(dao);
                    break;
                case 6:
                    deletarProduto(dao);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuPedido() {
        PedidoDAO dao = new PedidoDAO();
        int opcao;
        do {
            System.out.println("\n--- CRUD PEDIDO ---");
            System.out.println("1. Inserir (Create)");
            System.out.println("2. Buscar por ID (Read)");
            System.out.println("3. Listar Todos (Read All)");
            System.out.println("4. Atualizar Quantidade (Update)");
            System.out.println("5. Deletar (Delete)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    inserirPedido(dao);
                    break;
                case 2:
                    buscarPedido(dao);
                    break;
                case 3:
                    listarPedidos(dao);
                    break;
                case 4:
                    atualizarPedido(dao);
                    break;
                case 5:
                    deletarPedido(dao);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    // --- Métodos de Deletar (DELETE) ---

    private static void deletarCategoria(CategoriaDAO dao) {
        System.out.print("Digite o ID da Categoria a DELETAR: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            dao.remover(id); 
            System.out.println("Categoria com ID " + id + " DELETADA com sucesso (ou não existia).");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar categoria. Verifique se existem produtos ligados a ela. Erro: " + e.getMessage());
        }
    }

    private static void deletarVendedor(VendedorDAO dao) {
        System.out.print("Digite o ID do Vendedor a DELETAR: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            dao.remover(id);
            System.out.println("Vendedor com ID " + id + " DELETADO com sucesso (ou não existia).");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar vendedor. Verifique se existem produtos ligados a ele. Erro: " + e.getMessage());
        }
    }

    private static void deletarProduto(ProdutoDAO dao) {
        System.out.print("Digite o ID do Produto a DELETAR: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            dao.remover(id);
            System.out.println("Produto com ID " + id + " DELETADO com sucesso (ou não existia).");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto. Erro: " + e.getMessage());
        }
    }

    private static void deletarPedido(PedidoDAO dao) {
        System.out.print("Digite o ID do Pedido a DELETAR: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            dao.remover(id);
            System.out.println("Pedido com ID " + id + " DELETADO com sucesso (ou não existia).");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar pedido. Erro: " + e.getMessage());
        }
    }
    
    // --- Métodos de Operação CRUD (CREATE, READ, UPDATE) ---

    private static void inserirCategoria(CategoriaDAO dao) {
        System.out.print("Digite o nome da nova Categoria: ");
        String nome = scanner.nextLine();
        Categoria c = new Categoria(nome);
        dao.inserir(c);
        System.out.println("Categoria inserida com sucesso: " + c);
    }

    private static void buscarCategoria(CategoriaDAO dao) {
        System.out.print("Digite o ID da Categoria a buscar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Categoria buscada = dao.buscarPorId(id);
            if (buscada != null) {
                System.out.println("Categoria encontrada: " + buscada);
            } else {
                System.out.println("Categoria com ID " + id + " não encontrada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void listarCategorias(CategoriaDAO dao) {
        List<Categoria> lista = dao.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }
        System.out.println("\nLista de Categorias (" + lista.size() + " total):");
        lista.forEach(System.out::println);
    }

    private static void atualizarCategoria(CategoriaDAO dao) {
        System.out.print("Digite o ID da Categoria a atualizar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Categoria c = dao.buscarPorId(id);
            if (c == null) {
                System.out.println("Categoria com ID " + id + " não encontrada.");
                return;
            }
            
            System.out.print("Digite o novo nome para '" + c.getNome() + "': ");
            String novoNome = scanner.nextLine();
            c.setNome(novoNome);
            dao.atualizar(c);
            System.out.println("Categoria atualizada: " + c);
            
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    // --- Métodos para Vendedor ---
    private static void inserirVendedor(VendedorDAO dao) {
        System.out.print("Digite o nome do Vendedor/Loja: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o email do Vendedor/Loja: ");
        String email = scanner.nextLine();
        Vendedor v = new Vendedor(nome, email);
        dao.inserir(v);
        System.out.println("Vendedor inserido com sucesso: " + v);
    }
    
    private static void buscarVendedor(VendedorDAO dao) {
        System.out.print("Digite o ID do Vendedor a buscar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Vendedor buscado = dao.buscarPorId(id);
            if (buscado != null) {
                System.out.println("Vendedor encontrado: " + buscado);
            } else {
                System.out.println("Vendedor com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    private static void listarVendedores(VendedorDAO dao) {
        List<Vendedor> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }
        System.out.println("\nLista de Vendedores (" + lista.size() + " total):");
        lista.forEach(System.out::println);
    }
    
    private static void atualizarVendedor(VendedorDAO dao) {
        System.out.print("Digite o ID do Vendedor a atualizar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Vendedor v = dao.buscarPorId(id);
            if (v == null) {
                System.out.println("Vendedor com ID " + id + " não encontrado.");
                return;
            }
            
            System.out.print("Digite o novo nome para '" + v.getNome() + "': ");
            String novoNome = scanner.nextLine();
            v.setNome(novoNome);
            
            System.out.print("Digite o novo email para '" + v.getEmail() + "': ");
            String novoEmail = scanner.nextLine();
            v.setEmail(novoEmail);
            
            dao.atualizar(v);
            System.out.println("Vendedor atualizado: " + v);
            
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    // --- Métodos para Produto ---
    private static void inserirProduto(ProdutoDAO dao) {
        try {
            System.out.print("Nome do Produto: ");
            String nome = scanner.nextLine();
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();
            System.out.print("Preço (ex: 199.90): ");
            double preco = Double.parseDouble(scanner.nextLine()); 
            System.out.print("Quantidade em Estoque: ");
            int estoque = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Vendedor: ");
            long idVendedor = Long.parseLong(scanner.nextLine());
            System.out.print("ID da Categoria: ");
            long idCategoria = Long.parseLong(scanner.nextLine());

            Produto p = new Produto(nome, descricao, preco, estoque, idVendedor, idCategoria);
            dao.inserir(p);
            System.out.println("Produto inserido com sucesso: " + p);
        } catch (Exception e) {
            System.out.println("Erro ao inserir Produto. Verifique os dados e IDs. Erro: " + e.getMessage());
        }
    }

    private static void buscarProduto(ProdutoDAO dao) {
        System.out.print("Digite o ID do Produto a buscar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Produto buscado = dao.buscarPorId(id);
            if (buscado != null) {
                System.out.println("Produto encontrado: " + buscado);
            } else {
                System.out.println("Produto com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void listarProdutos(ProdutoDAO dao) {
        List<Produto> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\nLista de Produtos (" + lista.size() + " total):");
        lista.forEach(System.out::println);
    }

    private static void atualizarPrecoProduto(ProdutoDAO dao) {
        System.out.print("Digite o ID do Produto para atualizar o preço: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Produto p = dao.buscarPorId(id);
            if (p == null) {
                System.out.println("Produto com ID " + id + " não encontrado.");
                return;
            }
            
            System.out.print("Digite o novo Preço para '" + p.getNome() + "' (Preço atual: " + p.getPreco() + "): ");
            double novoPreco = Double.parseDouble(scanner.nextLine());
            p.setPreco(novoPreco);
            dao.atualizar(p);
            System.out.println("Preço do Produto atualizado: " + p);
            
        } catch (Exception e) {
            System.out.println("Erro na atualização do Preço. Verifique o ID/Formato. Erro: " + e.getMessage());
        }
    }
    
    private static void atualizarEstoqueProduto(ProdutoDAO dao) {
        System.out.print("Digite o ID do Produto para atualizar o estoque: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Produto p = dao.buscarPorId(id);
            if (p == null) {
                System.out.println("Produto com ID " + id + " não encontrado.");
                return;
            }
            
            System.out.print("Digite o novo Estoque para '" + p.getNome() + "' (Estoque atual: " + p.getEstoque() + "): ");
            int novoEstoque = Integer.parseInt(scanner.nextLine());
            dao.atualizarEstoque(id, novoEstoque);
            System.out.println("Estoque do Produto atualizado. Novo estoque: " + novoEstoque);
            
        } catch (Exception e) {
            System.out.println("Erro na atualização do Estoque. Verifique o ID/Formato. Erro: " + e.getMessage());
        }
    }

    // --- Métodos para Pedido ---
    private static void inserirPedido(PedidoDAO dao) {
        try {
            System.out.print("ID do Produto do Pedido: ");
            long idProduto = Long.parseLong(scanner.nextLine());
            System.out.print("ID do Cliente do Pedido: ");
            long idCliente = Long.parseLong(scanner.nextLine());
            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            Pedido pedido = new Pedido(idProduto, idCliente, quantidade);
            dao.inserir(pedido);
            System.out.println("Pedido inserido com sucesso: " + pedido);
        } catch (Exception e) {
            System.out.println("Erro ao inserir Pedido. Verifique os dados e IDs. Erro: " + e.getMessage());
        }
    }

    private static void buscarPedido(PedidoDAO dao) {
        System.out.print("Digite o ID do Pedido a buscar: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Pedido buscado = dao.buscarPorId(id);
            if (buscado != null) {
                System.out.println("Pedido encontrado: " + buscado);
            } else {
                System.out.println("Pedido com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    private static void listarPedidos(PedidoDAO dao) {
        List<Pedido> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        System.out.println("\nLista de Pedidos (" + lista.size() + " total):");
        lista.forEach(System.out::println);
    }
    
    private static void atualizarPedido(PedidoDAO dao) {
        System.out.print("Digite o ID do Pedido a atualizar a quantidade: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Pedido pedido = dao.buscarPorId(id);
            if (pedido == null) {
                System.out.println("Pedido com ID " + id + " não encontrado.");
                return;
            }
            
            System.out.print("Digite a nova Quantidade para o Pedido (Atual: " + pedido.getQuantidade() + "): ");
            int novaQuantidade = Integer.parseInt(scanner.nextLine());
            pedido.setQuantidade(novaQuantidade);
            dao.atualizar(pedido);
            System.out.println("Pedido atualizado: " + pedido);
            
        } catch (Exception e) {
            System.out.println("Erro na atualização da Quantidade. Verifique o ID/Formato. Erro: " + e.getMessage());
        }
    }
}