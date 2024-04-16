package com.tw ter.ho _m xer.product

 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProduct
 mport com.tw ter.ho _m xer.model.request.L stT etsProduct
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.ho _m xer.product.follow ng.Follow ngProductP pel neConf g
 mport com.tw ter.ho _m xer.product.for_ .For ProductP pel neConf g
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.L stRecom ndedUsersProductP pel neConf g
 mport com.tw ter.ho _m xer.product.scored_t ets.ScoredT etsProductP pel neConf g
 mport com.tw ter.ho _m xer.product.l st_t ets.L stT etsProductP pel neConf g
 mport com.tw ter.ho _m xer.product.subscr bed.Subscr bedProductP pel neConf g
 mport com.tw ter. nject. njector
 mport com.tw ter.product_m xer.core.product.gu ce.ProductScope
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stryConf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho ProductP pel neReg stryConf g @ nject() (
   njector:  njector,
  productScope: ProductScope)
    extends ProductP pel neReg stryConf g {

  pr vate val follow ngProductP pel neConf g = productScope.let(Follow ngProduct) {
     njector. nstance[Follow ngProductP pel neConf g]
  }

  pr vate val for ProductP pel neConf g = productScope.let(For Product) {
     njector. nstance[For ProductP pel neConf g]
  }

  pr vate val scoredT etsProductP pel neConf g = productScope.let(ScoredT etsProduct) {
     njector. nstance[ScoredT etsProductP pel neConf g]
  }

  pr vate val l stT etsProductP pel neConf g = productScope.let(L stT etsProduct) {
     njector. nstance[L stT etsProductP pel neConf g]
  }

  pr vate val l stRecom ndedUsersProductP pel neConf g =
    productScope.let(L stRecom ndedUsersProduct) {
       njector. nstance[L stRecom ndedUsersProductP pel neConf g]
    }

  pr vate val subscr bedProductP pel neConf g = productScope.let(Subscr bedProduct) {
     njector. nstance[Subscr bedProductP pel neConf g]
  }

  overr de val productP pel neConf gs = Seq(
    follow ngProductP pel neConf g,
    for ProductP pel neConf g,
    scoredT etsProductP pel neConf g,
    l stT etsProductP pel neConf g,
    l stRecom ndedUsersProductP pel neConf g,
    subscr bedProductP pel neConf g,
  )
}
