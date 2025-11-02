package models

data class Edicion(
    var compañia: CompaniaDiscografica,
    var fecha: String // formato YYYY-MM-DD
) {
    override fun toString(): String {
        return "${compañia.nombre} ($fecha)"
    }
}