package com.tw ter.ho _m xer.marshaller.request

 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProduct
 mport com.tw ter.ho _m xer.model.request.L stT etsProduct
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerProductUnmarshaller @ nject() () {

  def apply(product: t.Product): Product = product match {
    case t.Product.Follow ng => Follow ngProduct
    case t.Product.For  => For Product
    case t.Product.L stManage nt =>
      throw new UnsupportedOperat onExcept on(s"T  product  s no longer used")
    case t.Product.ScoredT ets => ScoredT etsProduct
    case t.Product.L stT ets => L stT etsProduct
    case t.Product.L stRecom ndedUsers => L stRecom ndedUsersProduct
    case t.Product.Subscr bed => Subscr bedProduct
    case t.Product.EnumUnknownProduct(value) =>
      throw new UnsupportedOperat onExcept on(s"Unknown product: $value")
  }
}
