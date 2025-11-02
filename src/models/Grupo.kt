package models

class Grupo(
    nombre: String,
    apellido: String,
    pais: String,
    estilo: String,
    var numArtistas: Int
) : Artista(nombre, apellido, pais, estilo) {
    val miembros = mutableListOf<String>()

    fun agregarMiembro(nombre: String) {
        miembros.add(nombre)
    }

    fun eliminarMiembro(nombre: String) {
        miembros.remove(nombre)
    }

    override fun toString(): String {
        return super.toString() + " - Grupo ($numArtistas miembros)"
    }
}