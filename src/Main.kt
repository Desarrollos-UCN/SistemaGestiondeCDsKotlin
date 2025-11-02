//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// GRUPO: Jeiner Aldana Arenas, Milton Danny Grajales Orozco, Santiago Orozco Alvarez
import models.*

fun main() {
    val inventario = Inventario()
    inicializarDatos(inventario)

    var salir = false
    while (!salir) {
        mostrarMenu()
        val opcion = readLine()?.toIntOrNull() ?: -1

        try {
            when (opcion) {
                1 -> registrarObra(inventario)
                2 -> modificarObra(inventario)
                3 -> consultarObra(inventario)
                4 -> eliminarObra(inventario)
                5 -> comprarEjemplares(inventario)
                6 -> venderEjemplares(inventario)
                7 -> listarObras(inventario)
                8 -> valorizacionInventario(inventario)
                9 -> consultarArtistas(inventario)
                10 -> {
                    salir = true
                    println("¡Hasta luego!")
                }
                else -> println("Opción inválida")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println()
    }
}

fun mostrarMenu() {
    println("\n=== SISTEMA DE GESTIÓN DE CDs ===")
    println("1. Registrar nueva obra")
    println("2. Modificar obra existente")
    println("3. Consultar obra")
    println("4. Eliminar obra")
    println("5. Comprar ejemplares")
    println("6. Vender ejemplares")
    println("7. Listar todas las obras")
    println("8. Valorización del inventario")
    println("9. Consultar artistas con canciones")
    println("10. Salir")
    print("Seleccione opción: ")
}

fun valorizacionInventario(inventario: Inventario) {
    println("\n╔════════════════════════════════════════════════════════════════════╗")
    println("║         VALORIZACIÓN DEL INVENTARIO DISPONIBLE                     ║")
    println("╚════════════════════════════════════════════════════════════════════╝")

    val valorizacion = inventario.obtenerValorizacionInventario()
    val detalles = valorizacion["detalles"] as List<Map<String, Any>>
    val totalObras = valorizacion["totalObras"] as Int
    val valorTotal = valorizacion["valorTotal"] as Double

    if (detalles.isEmpty()) {
        println("No hay obras en el inventario")
        return
    }

    println()

    println("│    CLAVE      │           NOMBRE              │ CANTIDAD    │   PVP    │ VALOR TOTAL  │")


    detalles.forEach { detalle ->
        val clave = detalle["clave"] as String
        val nombre = detalle["nombre"] as String
        val cantidad = detalle["cantidad"] as Int
        val pvp = detalle["pvp"] as Double
        val valorTotalItem = detalle["valorTotal"] as Double

        val claveFormateada = clave.padEnd(11)
        val nombreFormateado = if (nombre.length > 27) {
            nombre.substring(0, 24) + "..."
        } else {
            nombre.padEnd(27)
        }
        val cantidadFormateada = cantidad.toString().padStart(8)
        val pvpFormateado = String.format("%4.2f", pvp)
        val valorFormateado = String.format("%4.2f", valorTotalItem)

        println(" $claveFormateada      $nombreFormateado     $cantidadFormateada        $$pvpFormateado    $$valorFormateado ")
    }

    println()
    println(" TOTAL OBRAS: $totalObras                                             VALOR TOTAL: $${String.format("%.2f", valorTotal)} ")

}


fun consultarArtistas(inventario: Inventario) {
    println("\n╔════════════════════════════════════════════════════════════════════╗")
    println("║           LISTADO DE ARTISTAS CON CANCIONES                        ║")
    println("╚════════════════════════════════════════════════════════════════════╝")

    val artistasConCanciones = inventario.obtenerArtistasConCanciones()

    if (artistasConCanciones.isEmpty()) {
        println("No hay artistas registrados en el inventario")
        return
    }

    artistasConCanciones.forEach { (artista, canciones) ->
        println()
        println("┌────────────────────────────────────────────────────────────────────┐")

        if (artista is Grupo) {
            println("│ ARTISTA: ${artista.nombre} (${artista.pais} - ${artista.estilo})")
            println("│ TIPO: Grupo (${artista.numArtistas} miembros)")
            if (artista.miembros.isNotEmpty()) {
                println("│ MIEMBROS: ${artista.miembros.joinToString(", ")}")
            }
        } else {
            println("│ ARTISTA: ${artista.getNombreCompleto()} (${artista.pais} - ${artista.estilo})")
            println("│ TIPO: Artista individual")
        }

        println("├────────────────────────────────────────────────────────────────────┤")

        if (canciones.isEmpty()) {
            println("│ No tiene canciones registradas en CDs individuales")
        } else {
            println("│ CANCIONES:")
            canciones.forEach { cancion ->
                val titulo = cancion["titulo"]!!
                val duracion = cancion["duracion"]!!
                val nombreObra = cancion["nombreObra"]!!
                println("│   • $titulo (${duracion}s) - CD: $nombreObra")
            }
            println("│")
            println("│ TOTAL CANCIONES: ${canciones.size}")
        }

        println("└────────────────────────────────────────────────────────────────────┘")
    }

    println("\n📊 TOTAL ARTISTAS REGISTRADOS: ${artistasConCanciones.size}")
}

fun registrarObra(inventario: Inventario) {
    println("\n--- Registrar Obra ---")
    println("¿Es CD (1) o Colección (2)? ")
    val tipo = readLine()?.toIntOrNull() ?: -1

    print("Clave: ")
    val clave = readLine() ?: return

    print("Nombre: ")
    val nombre = readLine() ?: return

    // Para simplificar, usamos una compañía por defecto
    val compañia = CompaniaDiscografica(
        "Sony Music", "Calle Principal", "123", "28001", "España"
    )

    when (tipo) {
        1 -> {
            print("Número CD (#): ")
            val numeroCd = readLine() ?: return

            print("PVP: $")
            val pvp = readLine()?.toDoubleOrNull() ?: 0.0

            val cd = CD(clave, nombre, numeroCd, pvp, compañia)
            inventario.registrarObra(cd)
        }
        2 -> {
            print("Promotor: ")
            val promotor = readLine() ?: return

            print("PVP: $")
            val pvp = readLine()?.toDoubleOrNull() ?: 0.0

            val coleccion = Coleccion(clave, nombre, promotor, pvp, compañia)
            inventario.registrarObra(coleccion)
        }
    }
}

fun consultarObra(inventario: Inventario) {
//    println("\n--- Consultar Obra ---")
    print("Ingrese la clave: ")
    val clave = readLine() ?: return

    inventario.consultarObra(clave).mostrarDetalles()
}

fun modificarObra(inventario: Inventario) {
    println("\n--- Modificar Obra ---")
    print("Ingrese la clave de la obra a modificar: ")
    val clave = readLine() ?: return

    val obra = inventario.consultarObra(clave)

    println("¿Qué desea modificar?")
    println("1. Nombre")
    println("2. PVP")
    println("3. Volver")
    print("Opción: ")
    val opcion = readLine()?.toIntOrNull() ?: -1

    when (opcion) {
        1 -> {
            print("Nuevo nombre: ")
            val nuevoNombre = readLine() ?: return
            obra.nombre = nuevoNombre
        }
        2 -> {
            print("Nuevo PVP: $")
            val nuevoPvp = readLine()?.toDoubleOrNull() ?: 0.0
            when (obra) {
                is CD -> obra.pvp = nuevoPvp
                is Coleccion -> obra.pvp = nuevoPvp
            }
        }
    }

    inventario.modificarObra(clave, obra)
}

fun eliminarObra(inventario: Inventario) {
    println("\n--- Eliminar Obra ---")
    print("Ingrese la clave de la obra a eliminar: ")
    val clave = readln() ?: return

    print("¿Está seguro de eliminar esta obra? (s/n): ")
    val confirmacion = readln().lowercase()

    if (confirmacion == "s") {
        inventario.eliminarObra(clave)
    } else {
        println("Operación cancelada")
    }
}

fun comprarEjemplares(inventario: Inventario) {
    println("\n--- Comprar Ejemplares ---")
    print("Ingrese la clave de la obra: ")
    val clave = readLine() ?: return

    print("Cantidad a comprar: ")
    val cantidad = readln().toIntOrNull() ?: 0

    inventario.comprarEjemplares(clave, cantidad)
}

fun venderEjemplares(inventario: Inventario) {
    println("\n--- Vender Ejemplares ---")
    print("Ingrese la clave de la obra: ")
    val clave = readLine() ?: return

    print("Cantidad a vender: ")
    val cantidad = readLine()?.toIntOrNull() ?: 0

    inventario.venderEjemplares(clave, cantidad)
}

fun listarObras(inventario: Inventario) {
    inventario.listarTodasLasObras()
}

fun inicializarDatos(inventario: Inventario) {
    // ===== CREAR COMPAÑÍAS =====
    val sony = CompaniaDiscografica(
        "Sony Music Colombia", "Carrera 7", "71-21", "110231", "Colombia"
    )
    val universal = CompaniaDiscografica(
        "Universal Music Colombia", "Calle 93", "13-24", "110221", "Colombia"
    )
    val codiscos = CompaniaDiscografica(
        "Codiscos", "Calle 26", "69D-91", "111321", "Colombia"
    )

    // ===== CREAR ARTISTAS INDIVIDUALES (4) =====
    val shakira = Artista("Shakira", "Mebarak", "Colombia", "Pop/Rock Latino")
    val juanes = Artista("Juanes", "Aristizábal", "Colombia", "Rock/Pop Latino")
    val carlosvives = Artista("Carlos", "Vives", "Colombia", "Vallenato/Pop")
    val jbalvin = Artista("J", "Balvin", "Colombia", "Reggaeton/Urbano")

    // ===== CREAR GRUPOS (2) =====
    val aterciopelados = Grupo("Aterciopelados", "", "Colombia", "Rock Alternativo", 4).apply {
        agregarMiembro("Andrea Echeverri")
        agregarMiembro("Héctor Buitrago")
        agregarMiembro("Francisco Lopera")
        agregarMiembro("Mauricio Cepeda")
    }

    val moratBanda = Grupo("Morat", "", "Colombia", "Pop/Rock", 4).apply {
        agregarMiembro("Juan Pablo Isaza")
        agregarMiembro("Juan Pablo Villamil")
        agregarMiembro("Simón Vargas")
        agregarMiembro("Martín Vargas")
    }

    // ===== CREAR 5 CDs =====

    // CD 1: Shakira
    val cd1 = CD("SHAKIRA-001", "Pies Descalzos", "CD001", 18.99, sony).apply {
        agregarArtista(shakira)
        agregarPista(Pista(1, "Estoy Aquí", 234))
        agregarPista(Pista(2, "Antología", 267))
        agregarPista(Pista(3, "Un Poco de Amor", 243))
        agregarPista(Pista(4, "Pies Descalzos, Sueños Blancos", 276))
        agregarPista(Pista(5, "Se Quiere, Se Mata", 219))
        agregarEdicion(Edicion(sony, "1995-10-06"))
        comprarEjemplares(25)
    }

    // CD 2: Juanes
    val cd2 = CD("JUANES-001", "Fíjate Bien", "CD002", 19.99, universal).apply {
        agregarArtista(juanes)
        agregarPista(Pista(1, "Fíjate Bien", 245))
        agregarPista(Pista(2, "Nada", 287))
        agregarPista(Pista(3, "Podemos Hacernos Daño", 301))
        agregarPista(Pista(4, "Vulnerable", 234))
        agregarPista(Pista(5, "Es Por Ti", 256))
        agregarEdicion(Edicion(universal, "2000-10-17"))
        comprarEjemplares(30)
    }

    // CD 3: Carlos Vives
    val cd3 = CD("VIVES-001", "Clásicos de la Provincia", "CD003", 17.99, codiscos).apply {
        agregarArtista(carlosvives)
        agregarPista(Pista(1, "La Gota Fría", 268))
        agregarPista(Pista(2, "La Casa en el Aire", 289))
        agregarPista(Pista(3, "Matilde Lina", 234))
        agregarPista(Pista(4, "Alicia Adorada", 256))
        agregarPista(Pista(5, "La Tierra del Olvido", 312))
        agregarEdicion(Edicion(codiscos, "1993-12-20"))
        comprarEjemplares(20)
    }

    // CD 4: Aterciopelados
    val cd4 = CD("ATERCI-001", "El Dorado", "CD004", 16.99, sony).apply {
        agregarArtista(aterciopelados)
        agregarPista(Pista(1, "Bolero Falaz", 198))
        agregarPista(Pista(2, "El Estuche", 223))
        agregarPista(Pista(3, "Florecita Rockera", 267))
        agregarPista(Pista(4, "Baracunatana", 234))
        agregarPista(Pista(5, "El Álbum", 189))
        agregarEdicion(Edicion(sony, "1995-09-25"))
        comprarEjemplares(18)
    }

    // CD 5: J Balvin
    val cd5 = CD("JBALVIN-001", "Energía", "CD005", 21.99, universal).apply {
        agregarArtista(jbalvin)
        agregarPista(Pista(1, "Ginza", 189))
        agregarPista(Pista(2, "Ay Vamos", 234))
        agregarPista(Pista(3, "Tranquila", 267))
        agregarPista(Pista(4, "Safari", 243))
        agregarPista(Pista(5, "Bobo", 198))
        agregarEdicion(Edicion(universal, "2016-06-24"))
        comprarEjemplares(35)
    }

    // ===== CREAR 3 COLECCIONES =====

    // Colección 1: Lo Mejor del Pop Colombiano
    val coleccion1 = Coleccion(
        "COLPOP-001",
        "Lo Mejor del Pop Colombiano",
        "Sony Music Colombia",
        49.99,
        sony
    ).apply {
        agregarCD(cd1)  // Shakira
        agregarCD(cd2)  // Juanes
        agregarArtista(shakira)
        agregarArtista(juanes)
        agregarEdicion(Edicion(sony, "2023-01-15"))
        comprarEjemplares(15)
    }

    // Colección 2: Rock Colombiano Clásico
    val coleccion2 = Coleccion(
        "COLROCK-001",
        "Rock Colombiano Clásico",
        "Codiscos Especial",
        54.99,
        codiscos
    ).apply {
        agregarCD(cd3)  // Carlos Vives
        agregarCD(cd4)  // Aterciopelados
        agregarArtista(carlosvives)
        agregarArtista(aterciopelados)
        agregarEdicion(Edicion(codiscos, "2022-08-20"))
        comprarEjemplares(12)
    }

    // Colección 3: Antología Musical Colombiana (MEGA COLECCIÓN)
    val coleccion3 = Coleccion(
        "COLOMBIA-001",
        "Antología Musical Colombiana - 5 CDs",
        "Universal Music Colombia",
        79.99,
        universal
    ).apply {
        agregarCD(cd1)  // Shakira
        agregarCD(cd2)  // Juanes
        agregarCD(cd3)  // Carlos Vives
        agregarCD(cd4)  // Aterciopelados
        agregarCD(cd5)  // J Balvin
        agregarArtista(shakira)
        agregarArtista(juanes)
        agregarArtista(carlosvives)
        agregarArtista(aterciopelados)
        agregarArtista(jbalvin)
        agregarEdicion(Edicion(universal, "2024-03-15"))
        comprarEjemplares(10)
    }

    // ===== REGISTRAR TODAS LAS OBRAS =====
    try {
        // Registrar CDs
        inventario.registrarObra(cd1)
        inventario.registrarObra(cd2)
        inventario.registrarObra(cd3)
        inventario.registrarObra(cd4)
        inventario.registrarObra(cd5)

        // Registrar Colecciones
        inventario.registrarObra(coleccion1)
        inventario.registrarObra(coleccion2)
        inventario.registrarObra(coleccion3)

        println("╔════════════════════════════════════════════════════════════════╗")
        println("║  ✓ Datos de música colombiana cargados exitosamente            ║")
        println("║                                                                ║")
        println("║  📀 5 CDs registrados                                          ║")
        println("║  📦 3 Colecciones registradas                                  ║")
        println("║  🎤 4 Artistas individuales                                    ║")
        println("║  🎸 2 Grupos colombianos                                       ║")
        println("║  📊 Total ejemplares: 135                                      ║")
        println("╚════════════════════════════════════════════════════════════════╝")
        println()
    } catch (e: Exception) {
        println("❌ Error al cargar datos: ${e.message}")
    }
}