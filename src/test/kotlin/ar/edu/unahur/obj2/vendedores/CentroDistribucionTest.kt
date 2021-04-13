package ar.edu.unahur.obj2.vendedores

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class CentroDistribucionTest : DescribeSpec({
    /*Primero una clase grande que inheritea describespec*/
    //--------Provincias-----------------/
    val yapeyu = Provincia(99500)
    val kayaki = Provincia(25500)
    val babilonia = Provincia(250000)
    val zorrillo = Provincia(50000)
    //--------Ciudades-------------------/
    val malabia = Ciudad(yapeyu)
    val esperanto = Ciudad(babilonia)
    val cachaquito = Ciudad(kayaki)
    val frandela = Ciudad(zorrillo)
    //--------Vendedores-----------------/
    val provinciasEstefania = listOf(yapeyu,kayaki,zorrillo) //auxiliar
    val ciudadesEduardo = listOf(malabia,esperanto,cachaquito,frandela) //auxiliar
    val candelaria = VendedorFijo(frandela)
    val estefania = Viajante(provinciasEstefania)
    val eduardo = ComercioCorresponsal(ciudadesEduardo)
    //-------Certificaciones---/
    val certT20 = Certificacion(true,20)
    val certT50 = Certificacion(true,50)
    val certT100 = Certificacion(true,100)
    val certF20 = Certificacion(false,20)
    val certF50 = Certificacion(false,50)
    val certF100 = Certificacion(false,100)
    //--------Centro-Distribucion--------/
    val vendedoresCD = mutableListOf(candelaria,estefania,eduardo)
    val centroDistribuciones = CentroDistribucion(esperanto,vendedoresCD)
    /*Ahora que estan todos los objetos creados, vienen los describe chiquitos*/



    describe("Agregar vendedor."){
        /*Ahora del ahora: vienen los IT chiquitos.*/
        it("Agregar un vendedor."){
            val vendedorNuevo = VendedorFijo(cachaquito)
            centroDistribuciones.agregarVendedor(vendedorNuevo)

        }
        it("Agregar al mismo vendedor."){
            val vendedorNuevo = VendedorFijo(cachaquito)
            centroDistribuciones.agregarVendedor(vendedorNuevo)
            shouldThrow<Exception>{ centroDistribuciones.agregarVendedor(vendedorNuevo) }
        }
    }


    describe("Vendedor estrella"){
        it("candelaria es la vendedora estrella"){
            candelaria.agregarCertificacion(certT100)
            candelaria.agregarCertificacion(certT20) //Candelaria: 120 pts
            eduardo.agregarCertificacion(certF100)   //Eduardo: 100 pts
            estefania.agregarCertificacion(certT50)  //Estefania: 50 pts
            centroDistribuciones.vendedorEstrella().shouldBe(candelaria)
        }
        it("Ahora es estefania"){
            candelaria.agregarCertificacion(certT100)
            candelaria.agregarCertificacion(certT20) //Candelaria: 120 pts
            eduardo.agregarCertificacion(certF100)   //Eduardo: 100 pts
            estefania.agregarCertificacion(certT50)  //Estefania: 50 pts
            //Le agregamos puntos a estefania para que supere a candelaria.
            estefania.agregarCertificacion(certT100)
            estefania.agregarCertificacion(certF100)
            centroDistribuciones.vendedorEstrella().shouldBe(estefania)
        }
    }

    describe("Puede cubrir"){
        it("Aca no se puede."){
            //Creamos una nueva ciudad, la cual no pueda ser cubierta.
            val prov1 = Provincia(50)
            val laferrere = Ciudad(prov1)
            centroDistribuciones.puedeCubrir(laferrere).shouldBe(false)
        }
        it("Ahora si se puede"){
            //Creamos una nueva ciudad, la cual no pueda ser cubierta.
            val prov1 = Provincia(50)
            val laferrere = Ciudad(prov1)
            //Añadimos a laurita, dispuesta a trabajar en la ferrere.
            val laurita = VendedorFijo(laferrere)
            centroDistribuciones.agregarVendedor(laurita)
            centroDistribuciones.puedeCubrir(laferrere).shouldBe(true)
        }
    }

    describe("Vendedores Genericos"){
        it("Ahora no tenemos vendedores genericos."){
            //Ningun vendedor posee certificaciones genericas.
            centroDistribuciones.vendedoresGenericos().shouldBeEmpty()
        }
        it("Ahora si!"){
            //Agregamos una certificacion generica.
            candelaria.agregarCertificacion(certF100) //agregamos certificacion generica.
            centroDistribuciones.vendedoresGenericos().shouldContain(candelaria)
        }
    }

    describe("Es robusto."){
        //Condicion: Puntaje mayor a 30 en 3 vendedores.

        it("No son robustos"){
            //solo 2 de los 3 vendedores superan los 30 puntos.
            candelaria.agregarCertificacion(certF50)
            estefania.agregarCertificacion(certT50)
            eduardo.agregarCertificacion(certF20)
            centroDistribuciones.esRobusto().shouldBe(false)
        }

        it("Son robustos."){
            //Ahora los 3 vendedores superan los 30 puntos.
            candelaria.agregarCertificacion(certF100)
            estefania.agregarCertificacion(certT100)
            eduardo.agregarCertificacion(certF50)
            centroDistribuciones.esRobusto().shouldBe(true)
        }
    }


})


