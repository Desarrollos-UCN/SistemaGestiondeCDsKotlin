package models

class Coleccion(
    clave: String,
    nombre: String,
    var promotor: String,
    var pvp: Double,
    compañiaActual: CompaniaDiscografica
) : Obra(clave, nombre, compañiaActual) {
    val cdsIncluidos = mutableListOf<CD>()

    fun agregarCD(cd: CD) {
        cdsIncluidos.add(cd)
    }

    fun eliminarCD(cd: CD) {
        cdsIncluidos.remove(cd)
    }

    fun calcularPvpSumaCDs(): Double {
        return cdsIncluidos.sumOf { it.pvp }
    }

    override fun mostrarDetalles() {
        println("\n=== DETALLES DE LA COLECCIÓN ===")
        println("Clave: $clave")
        println("Nombre: $nombre")
        println("Promotor: $promotor")
        println("PVP: $$pvp")
        println("PVP suma de CDs: $${calcularPvpSumaCDs()}")
        println("Descuento: $${calcularPvpSumaCDs() - pvp}")
        println("Ejemplares disponibles: $ejemplaresDisponibles")
        println("Compañía actual: ${compañiaActual.nombre}")

        println("\nArtistas:")
        artistas.forEach { println("  - $it") }

        println("\nCDs incluidos:")
        cdsIncluidos.forEach { println("  - ${it.nombre} (#${it.numeroCd})") }

        println("\nEdiciones:")
        ediciones.forEach { println("  - $it") }
    }

    override fun toString(): String {
        return "Colección { ${super.toString()}, PVP: $$pvp, CDs: ${cdsIncluidos.size} }"
    }
}