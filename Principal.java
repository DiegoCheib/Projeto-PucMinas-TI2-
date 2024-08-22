import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Listar produtos");
            System.out.println("2. Inserir produto");
            System.out.println("3. Atualizar produto");
            System.out.println("4. Excluir produto");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // Listar produtos
                    List<Produto> produtos = produtoDAO.listarProdutos();
                    for (Produto p : produtos) {
                        System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Preço: " + p.getPreco() + ", Quantidade: " + p.getQuantidade());
                    }
                    break;
                case 2:
                    // Inserir produto
                    System.out.print("Nome: ");
                    String nome = scanner.next();
                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();
                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();

                    Produto novoProduto = new Produto(nome, preco, quantidade);
                    produtoDAO.inserirProduto(novoProduto);
                    System.out.println("Produto inserido com sucesso!");
                    break;
                case 3:
                    // Atualizar produto
                    System.out.print("ID do produto a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.next();
                    System.out.print("Novo preço: ");
                    double novoPreco = scanner.nextDouble();
                    System.out.print("Nova quantidade: ");
                    int novaQuantidade = scanner.nextInt();

                    Produto produtoAtualizar = new Produto(novoNome, novoPreco, novaQuantidade);
                    produtoAtualizar.setId(idAtualizar);
                    produtoDAO.atualizarProduto(produtoAtualizar);
                    System.out.println("Produto atualizado com sucesso!");
                    break;
                case 4:
                    // Excluir produto
                    System.out.print("ID do produto a excluir: ");
                    int idExcluir = scanner.nextInt();
                    produtoDAO.excluirProduto(idExcluir);
                    System.out.println("Produto excluído com sucesso!");
                    break;
                case 5:
                    // Sair
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
