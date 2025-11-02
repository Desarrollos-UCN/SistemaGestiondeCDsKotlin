package models

class CD(
    clave: String,
    nombre: String,
    var numeroCd: String, // #cd asignado por la tienda
    var pvp: Double, // precio de venta al público
    compañiaActual: CompaniaDiscografica
) : Obra(clave, nombre, compañiaActual) {
    val pistas = mutableListOf<Pista>()

    fun agregarPista(pista: Pista) {
        pistas.add(pista)
    }

    fun eliminarPista(pista: Pista) {
        pistas.remove(pista)
    }

    override fun mostrarDetalles() {
        println("\n=== DETALLES DEL CD ===")
        println("Clave: $clave")
        println("Nombre: $nombre")
        println("Número CD: $numeroCd")
        println("PVP: $$pvp")
        println("Ejemplares disponibles: $ejemplaresDisponibles")
        println("Compañía actual: ${compañiaActual.nombre}")

        println("\nArtistas:")
        artistas.forEach { println("  - $it") }

        println("\nPistas:")
        pistas.forEach { println("  $it") }

        println("\nEdiciones:")
        ediciones.forEach { println("  - $it") }
    }

    override fun toString(): String {
        return "CD { ${super.toString()}, PVP: $$pvp }"
    }
}
