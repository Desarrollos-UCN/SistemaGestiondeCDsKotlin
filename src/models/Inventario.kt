package models

class Inventario {
    private val obras = mutableMapOf<String, Obra>()

    fun registrarObra(obra: Obra) {
        if (obras.containsKey(obra.clave)) {
            throw IllegalArgumentException("Ya existe una obra con esa clave")
        }
        obras[obra.clave] = obra
        println("✓ Obra registrada exitosamente")
    }

    fun consultarObra(clave: String): Obra {
        return obras[clave] ?: throw IllegalArgumentException("No existe obra con esa clave")
    }

    fun modificarObra(clave: String, obraModificada: Obra) {
        if (!obras.containsKey(clave)) {
            throw IllegalArgumentException("No existe obra con esa clave")
        }
        obras[clave] = obraModificada
        println("✓ Obra modificada exitosamente")
    }

    fun eliminarObra(clave: String) {
        if (!obras.containsKey(clave)) {
            throw IllegalArgumentException("No existe obra con esa clave")
        }
        obras.remove(clave)
        println("✓ Obra eliminada exitosamente")
    }

    fun comprarEjemplares(clave: String, cantidad: Int) {
        consultarObra(clave).comprarEjemplares(cantidad)
        println("✓ Se agregaron $cantidad ejemplares")
    }

    fun venderEjemplares(clave: String, cantidad: Int) {
        consultarObra(clave).venderEjemplares(cantidad)
        println("✓ Se vendieron $cantidad ejemplares")
    }

    fun listarTodasLasObras() {
        if (obras.isEmpty()) {
            println("El inventario está vacío")
            return
        }
        println("\n=== LISTADO DE TODAS LAS OBRAS ===")
        obras.values.forEach { println(it) }
    }

    fun getTodasLasObras(): List<Obra> {
        return obras.values.toList()
    }

    fun obtenerValorizacionInventario(): Map<String, Any> {
        var totalGeneral = 0.0
        val detalles = mutableListOf<Map<String, Any>>()

        obras.values.forEach { obra ->
            val pvp = when (obra) {
                is CD -> obra.pvp
                is Coleccion -> obra.pvp
                else -> 0.0
            }

            val cantidad = obra.ejemplaresDisponibles
            val valorTotal = pvp * cantidad

            detalles.add(mapOf(
                "clave" to obra.clave,
                "nombre" to obra.nombre,
                "cantidad" to cantidad,
                "pvp" to pvp,
                "valorTotal" to valorTotal
            ))

            totalGeneral += valorTotal
        }

        return mapOf(
            "detalles" to detalles,
            "totalObras" to obras.size,
            "valorTotal" to totalGeneral
        )
    }

    fun obtenerArtistasConCanciones(): Map<Artista, List<Map<String, String>>> {
        val artistasMap = mutableMapOf<Artista, MutableList<Map<String, String>>>()

        obras.values.forEach { obra ->
            obra.artistas.forEach { artista ->
                if (!artistasMap.containsKey(artista)) {
                    artistasMap[artista] = mutableListOf()
                }

                if (obra is CD) {
                    obra.pistas.forEach { pista ->
                        artistasMap[artista]?.add(mapOf(
                            "titulo" to pista.titulo,
                            "duracion" to pista.duracion.toString(),
                            "nombreObra" to obra.nombre
                        ))
                    }
                }
            }
        }

        return artistasMap
    }


    fun getTotalObras() = obras.size
}