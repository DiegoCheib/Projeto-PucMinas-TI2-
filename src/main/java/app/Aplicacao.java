import static spark.Spark.*;
import com.google.gson.Gson;

public class Aplicacao {

    private static ProdutoService produtoService = new ProdutoService();
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        // Definir a porta do servidor
        port(6789);

        // Rota para inserir um novo produto
        post("/produto", (request, response) -> {
            Produto produto = gson.fromJson(request.body(), Produto.class);
            produtoService.add(produto);
            response.status(201);  // 201 Created
            return gson.toJson(produto);
        });

        // Rota para obter um produto pelo ID
        get("/produto/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoService.get(id);
            if (produto != null) {
                return gson.toJson(produto);
            } else {
                response.status(404);  // 404 Not Found
                return "Produto não encontrado";
            }
        });

        // Rota para atualizar um produto
        put("/produto/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produtoAtualizado = gson.fromJson(request.body(), Produto.class);
            produtoAtualizado.setId(id);
            produtoService.update(produtoAtualizado);
            return gson.toJson(produtoAtualizado);
        });

        // Rota para excluir um produto
        delete("/produto/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            produtoService.remove(id);
            response.status(204);  // 204 No Content
            return "";
        });

        // Rota para listar todos os produtos
        get("/produto", (request, response) -> {
            return gson.toJson(produtoService.getAll());
        });
    }
}
