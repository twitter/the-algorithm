package com.tw ter.follow_recom ndat ons.products.common

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on

tra  ProductReg stry {
  def products: Seq[Product]
  def d splayLocat onProductMap: Map[D splayLocat on, Product]
  def getProductByD splayLocat on(d splayLocat on: D splayLocat on): Product
}
