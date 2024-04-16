package com.tw ter.ho _m xer.marshaller.request

 mport com.tw ter.ho _m xer.model.request.Follow ngProductContext
 mport com.tw ter.ho _m xer.model.request.For ProductContext
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProductContext
 mport com.tw ter.ho _m xer.model.request.L stT etsProductContext
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProductContext
 mport com.tw ter.ho _m xer.model.request.Subscr bedProductContext
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.ProductContext
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerProductContextUnmarshaller @ nject() (
  dev ceContextUnmarshaller: Dev ceContextUnmarshaller) {

  def apply(productContext: t.ProductContext): ProductContext = productContext match {
    case t.ProductContext.Follow ng(p) =>
      Follow ngProductContext(
        dev ceContext = p.dev ceContext.map(dev ceContextUnmarshaller(_)),
        seenT et ds = p.seenT et ds,
        dspCl entContext = p.dspCl entContext
      )
    case t.ProductContext.For (p) =>
      For ProductContext(
        dev ceContext = p.dev ceContext.map(dev ceContextUnmarshaller(_)),
        seenT et ds = p.seenT et ds,
        dspCl entContext = p.dspCl entContext,
        pushToHo T et d = p.pushToHo T et d
      )
    case t.ProductContext.L stManage nt(p) =>
      throw new UnsupportedOperat onExcept on(s"T  product  s no longer used")
    case t.ProductContext.ScoredT ets(p) =>
      ScoredT etsProductContext(
        dev ceContext = p.dev ceContext.map(dev ceContextUnmarshaller(_)),
        seenT et ds = p.seenT et ds,
        servedT et ds = p.servedT et ds,
        backf llT et ds = p.backf llT et ds
      )
    case t.ProductContext.L stT ets(p) =>
      L stT etsProductContext(
        l st d = p.l st d,
        dev ceContext = p.dev ceContext.map(dev ceContextUnmarshaller(_)),
        dspCl entContext = p.dspCl entContext
      )
    case t.ProductContext.L stRecom ndedUsers(p) =>
      L stRecom ndedUsersProductContext(
        l st d = p.l st d,
        selectedUser ds = p.selectedUser ds,
        excludedUser ds = p.excludedUser ds,
        l stNa  = p.l stNa 
      )
    case t.ProductContext.Subscr bed(p) =>
      Subscr bedProductContext(
        dev ceContext = p.dev ceContext.map(dev ceContextUnmarshaller(_)),
        seenT et ds = p.seenT et ds,
      )
    case t.ProductContext.UnknownUn onF eld(f eld) =>
      throw new UnsupportedOperat onExcept on(s"Unknown d splay context: ${f eld.f eld.na }")
  }
}
