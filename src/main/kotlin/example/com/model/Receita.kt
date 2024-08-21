package example.com.model

import org.jetbrains.exposed.sql.Table

data class Receita(
    val idReceita: Int = 0,
    val imagem: String,
    val nome: String,
    val tipo: String,
    val url: String,
)

object Receitas : Table() {
    val idReceita = integer("idReceita").autoIncrement()
    val imagem = text("imagem")
    val nome = text("nome")
    val tipo = text("tipo")
    val url = text("url")

    override val primaryKey = PrimaryKey(idReceita)
}
