package example.com.model

import org.jetbrains.exposed.sql.Table

data class Petshop(
    val uid: Int,
    var name: String,
    var imagem: String,
    var localizacao: String,
    var descricao: String,
    var servicos: String,
    val cnpj: String
)

object Petshops : Table() {
    val uid = integer("id").autoIncrement()
    val name = text("name")
    val imagem = text("imagem")
    val localizacao = text("localizacao")
    val descricao = text("descricao")
    val servicos = text("servicos")
    val cnpj = text("cnpj")
    override val primaryKey = PrimaryKey(uid)
}