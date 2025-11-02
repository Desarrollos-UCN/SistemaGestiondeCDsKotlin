package models

abstract class Obra(
    var clave: String, // similar a ISBN
    var nombre: String,
    var compañiaActual: CompaniaDiscografica
) {
    val artistas = mutableListOf<Artista>()
    val ediciones = mutableListOf<Edicion>()
    var ejemplaresDisponibles: Int = 0

    fun agregarArtista(artista: Artista) {
        artistas.add(artista)
    }

    fun eliminarArtista(artista: Artista) {
        artistas.remove(artista)
    }

    fun agregarEdicion(edicion: Edicion) {
        ediciones.add(edicion)
    }

    fun comprarEjemplares(cantidad: Int) {
        if (cantidad < 0) {
            throw IllegalArgumentException("La cantidad no puede ser negativa")
        }
        ejemplaresDisponibles += cantidad
    }

    fun venderEjemplares(cantidad: Int) {
        if (cantidad < 0) {
            throw IllegalArgumentException("La cantidad no puede ser negativa")
        }
        if (cantidad > ejemplaresDisponibles) {
            throw IllegalArgumentException("No hay suficientes ejemplares disponibles")
        }
        ejemplaresDisponibles -= cantidad
    }

    abstract fun mostrarDetalles()

    override fun toString(): String {
        return "Clave: $clave, Nombre: $nombre, Ejemplares: $ejemplaresDisponibles"
    }
}