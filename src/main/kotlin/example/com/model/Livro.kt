package example.com.model

import example.com.dto.LivroResponse
import org.jetbrains.exposed.sql.Table

data class Livro(
    val id: Int,
    val ano: Int,
    val autor: String,
    val descricao: String,
    val genero: String,
    val imagem: String,
    val paginas: Int,
    val titulo: String
) {
    fun toLivroResponse() = LivroResponse(
        id = id,
        ano = ano,
        autor = autor,
        descricao = descricao,
        genero = genero,
        imagem = imagem,
        paginas = paginas,
        titulo = titulo
    )
}

object Livros : Table() {
    val id = integer("id").autoIncrement()
    val ano = integer("ano")
    val autor = text("autor")
    val descricao = text("descricao")
    val genero = text("genero")
    val imagem = text("imagem")
    val paginas = integer("paginas")
    val titulo = text("titulo")

    override val primaryKey = PrimaryKey(id)
}
