package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should

class VendedorTest : DescribeSpec({
    val misiones = Provincia(1300000)
    val sanIgnacio = Ciudad(misiones)

    describe("Vendedor fijo") {
        val obera = Ciudad(misiones)
        val vendedorFijo = VendedorFijo(obera)

        describe("puedeTrabajarEn") {
            it("su ciudad de origen") {
                vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
            }
            it("otra ciudad") {
                vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
            }
        }
    }

    describe("Viajante") {
        //Vendedor 1
        val cordoba = Provincia(2000000)
        val villaDolores = Ciudad(cordoba)
        val viajante = Viajante(listOf(misiones))
        //Vendedor 2
        val buenosAires = Provincia(15000000)
        val ituzaingo = Ciudad(buenosAires)
        val viajante2 = Viajante(listOf(buenosAires))
        describe("puedeTrabajarEn") {
            it("una ciudad que pertenece a una provincia habilitada") {
                viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
            }
            it("una ciudad que no pertenece a una provincia habilitada") {
                viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
            }
        }
        describe(name = "esInfluyente") {
            it(name = "Poblacion total del vendedor (menor a 10millones)") {
                viajante.esInfluyente().shouldBeFalse()
            }
            it("Poblacion total del vendedor2 (mayor a 10millones)"){
                viajante2.esInfluyente().shouldBeTrue()
            }
        }
    }

    describe("Comercio corresponsal") {
        //comercio nro1
        val moron = Provincia(100000)
        val merlo = Provincia(80000)
        val hurlingham = Provincia(60000)
        val buenosAires = Ciudad(moron)
        val mediosAires = Ciudad(merlo)
        val malosAires = Ciudad(hurlingham)
        val conAire = Ciudad(moron)
        val sinAire = Ciudad(merlo)
        val listaDeCiudades = listOf<Ciudad>(buenosAires,mediosAires,malosAires,conAire,sinAire)
        val comercio1 = ComercioCorresponsal(listaDeCiudades)

        describe(name = "Comercio influyente"){
            it(name = "Comercio SI influyente"){
                comercio1.esInfluyente().shouldBeTrue()
            }
        }
    }
})
