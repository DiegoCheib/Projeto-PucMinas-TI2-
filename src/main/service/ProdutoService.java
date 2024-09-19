package service;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import dao.DAOProdutos;
import model.Produto;

public class ProdutoService {

    private DAOProdutos dao;

    public ProdutoService() {
        dao = new DAOProdutos();
    }

    // Método para adicionar um produto
    public Object addProduto(Request request, Response response) {
        dao.conectar();

        String nome = request.queryParams("nome");
        double preco = Double.parseDouble(request.queryParams("preco"));
        int codigoProduto = Integer.parseInt(request.queryParams("codigo_produto"));

        Produto produto = new Produto(codigoProduto, nome, preco);

        // Verifica se o produto já existe e remove o produto anterior com o mesmo código
        dao.excluirProduto(codigoProduto);
        
        // Tenta adicionar o novo produto
        boolean status = dao.addProduto(produto);

        dao.close();

        if (status) {
            response.status(201); // 201 Created
            return "Produto " + nome + " cadastrado com sucesso!";
        } else {
            response.status(500); // 500 Internal Server Error
            return "Erro ao cadastrar o produto.";
        }
    }

    // Método para remover um produto
    public Object removeProduto(Request request, Response response) {
        dao.conectar();

        int codigoProduto = Integer.parseInt(request.queryParams("codigo_produto"));
        boolean status = dao.excluirProduto(codigoProduto);

        dao.close();

        if (status) {
            response.status(200); // 200 OK
            return "Produto removido com sucesso!";
        } else {
            response.status(404); // 404 Not Found
            return "Produto não encontrado!";
        }
    }

    // Método para listar todos os produtos
    public Object getAllProdutos(Request request, Response response) {
        dao.conectar();

        StringBuffer returnValue = new StringBuffer("<produtos type=\"array\">");
        Produto[] produtos = dao.getProdutos();

        if (produtos == null || produtos.length == 0) {
            dao.close();
            return "Não há produtos cadastrados.";
        }

        for (Produto produto : produtos) {
            returnValue.append("<produto>\n" +
                    "\t<codigo>" + produto.getCodigoProduto() + "</codigo>\n" +
                    "\t<nome>" + produto.getNome() + "</nome>\n" +
                    "\t<preco>" + produto.getPreco() + "</preco>\n" +
                    "</produto>\n");
        }

        returnValue.append("</produtos>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");

        dao.close();
        return returnValue.toString();
    }

    // Método para atualizar um produto
    public Object updateProduto(Request request, Response response) {
        dao.conectar();

        int codigoProduto = Integer.parseInt(request.queryParams("codigo_produto"));
        String nome = request.queryParams("nome");
        double preco = Double.parseDouble(request.queryParams("preco"));

        Produto produtoAtualizado = new Produto(codigoProduto, nome, preco);

        boolean status = dao.atualizarProduto(produtoAtualizado);

        dao.close();

        if (status) {
            response.status(200); // 200 OK
            return "Produto atualizado com sucesso!";
        } else {
            response.status(404); // 404 Not Found
            return "Produto não encontrado!";
        }
    }
}
