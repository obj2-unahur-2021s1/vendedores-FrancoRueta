package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int) //Clase

abstract class Vendedor {
    // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
    // Además, a una MutableList se le pueden agregar elementos
    private val certificaciones = mutableListOf<Certificacion>() //Lista de certificaciones

    // Definimos el método abstracto.
    // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
    abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean //Tipo bool

    // En las funciones declaradas con = no es necesario explicitar el tipo
    fun esVersatil() = //Retorna bool
            certificaciones.size >= 3
                    && this.certificacionesDeProducto() >= 1
                    && this.otrasCertificaciones() >= 1

    // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
    fun agregarCertificacion(certificacion: Certificacion) { //No retorna
        certificaciones.add(certificacion)
    }

    fun esFirme() = this.puntajeCertificaciones() >= 30 //Tipo Bool

    private fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto } //Tipo Int
    fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto } //Tipo Int

    fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje } //Tipo Int

    //Agregado debido al punto Vendedor Influyente, el cual nos pide como caracteristica que
    //los vendedores puedan o no ser "influyentes", el cual cambia acorde a cada uno de ellos.
    abstract fun esInfluyente(): Boolean //Tipo Bool

    fun esGenerico() = otrasCertificaciones() > 0
}

// En los parámetros, es obligatorio poner el tipo
class VendedorFijo(private val ciudadOrigen: Ciudad) : Vendedor() {
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {  //Tipo Bool
        return ciudad == ciudadOrigen
    }
    override fun esInfluyente() = false //Tipo Bool

}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(private val provinciasHabilitadas: List<Provincia>) : Vendedor() {
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean { //Tipo Bool
        return provinciasHabilitadas.contains(ciudad.provincia)
    }

    override fun esInfluyente(): Boolean { //Tipo Bool
        return (
                (this.provinciasHabilitadas.sumBy { provincia -> provincia.poblacion  }) >= 10000000
                )
    }
}

class ComercioCorresponsal(private val ciudades: List<Ciudad>) : Vendedor() { //Tipo Vendedor
    override fun puedeTrabajarEn(ciudad: Ciudad): Boolean { //tipo bool
        return ciudades.contains(ciudad)
    }

    override fun esInfluyente(): Boolean { //Tipo Bool
        val ciudadAProvincia = ciudades.map { ciudad -> ciudad.provincia }.toSet()
        return ((this.ciudades.size) >= 5 || (ciudadAProvincia.size >= 3))
    }
}


//-------------------------------------------------------CENTRO-DISTRIBUCION--------/
//-----------------------------------------------------------------------------/

class CentroDistribucion(val ciudadOrigen: Ciudad,private val vendedores: MutableList<Vendedor>){

    fun agregarVendedor(vendedor: Vendedor){
        if (vendedores.contains(vendedor)){
            throw Exception("El vendedor ya esta en el centro!")
        }
        else {
            vendedores.add(vendedor)
        }
    }

    fun vendedorEstrella() = (vendedores.maxBy { vendedor -> vendedor.puntajeCertificaciones() })

    fun puedeCubrir(ciudad: Ciudad): Boolean {
        return vendedores.any { vendedor -> vendedor.puedeTrabajarEn(ciudad) }
    }

    fun vendedoresGenericos(): List<Vendedor> {
        return vendedores.filter { vendedor -> vendedor.esGenerico() }
    }

    fun esRobusto(): Boolean {
        return vendedores.count { vendedor -> vendedor.esFirme() } >= 3
    }

}