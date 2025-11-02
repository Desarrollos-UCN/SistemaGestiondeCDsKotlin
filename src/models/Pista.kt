package models

data class Pista(
    var numero: Int,
    var titulo: String,
    var duracion: Int // en segundos
) {
    override fun toString(): String {
        return "$numero. $titulo (${duracion}s)"
    }
}