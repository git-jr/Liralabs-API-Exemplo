package example.com.model

import org.jetbrains.exposed.sql.Table

data class Pet(
    val id: Long = 0,
    var name: String,
    var descricao: String,
    var peso: String,
    var idade: String,
    var imagem: String
)

object Pets : Table() {
    val id = long("id").autoIncrement()
    val name = text("name")
    val descricao = text("descricao")
    val peso = text("peso")
    val idade = text("idade")
    val imagem = text("imagem")

    override val primaryKey = PrimaryKey(id)
}