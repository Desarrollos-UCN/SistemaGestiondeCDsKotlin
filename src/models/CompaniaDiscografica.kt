package models

data class CompaniaDiscografica (
    val nombre: String, // identificador único
    var calle: String,
    var numero: String,
    var codigoPostal: String,
    var pais: String
) {
    override fun toString(): String {
        return "$nombre ($calle $numero, $codigoPostal $pais)"
    }
}