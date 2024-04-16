package com.tw ter.follow_recom ndat ons.common.ut ls

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Product
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product

object D splayLocat onProductConverterUt l {
  def productToD splayLocat on(product: Product): D splayLocat on = {
    product match {
      case Product.Mag cRecs => D splayLocat on.Mag cRecs
      case _ =>
        throw Unconvert bleProductM xerProductExcept on(
          s"Cannot convert Product M xer Product ${product. dent f er.na }  nto a FRS D splayLocat on.")
    }
  }

  def d splayLocat onToProduct(d splayLocat on: D splayLocat on): Product = {
    d splayLocat on match {
      case D splayLocat on.Mag cRecs => Product.Mag cRecs
      case _ =>
        throw Unconvert bleProductM xerProductExcept on(
          s"Cannot convert D splayLocat on ${d splayLocat on.toFsNa }  nto a Product M xer Product.")
    }
  }
}

case class Unconvert bleProductM xerProductExcept on( ssage: Str ng) extends Except on( ssage)
