package models

open class Artista(
    var nombre: String,
    var apellido: String,
    var pais: String,
    var estilo: String // rock, country, flamenco, etc.
) {
    fun getNombreCompleto(): String {
        return "$nombre $apellido"
    }
    override fun toString(): String {
        return "$nombre ($estilo, $pais)"
    }
}