package com.tw ter.ho _m xer.funct onal_component.gate

 mport com.tw ter.ho _m xer.model.request.Dev ceContext.RequestContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Gate that fetc s t  request context from t  dev ce context and
 * cont nues  f t  request context does not match any of t  spec f ed ones.
 *
 *  f no  nput request context  s spec f ed, t  gate cont nues
 */
case class RequestContextNotGate(requestContexts: Seq[RequestContext.Value])
    extends Gate[P pel neQuery w h HasDev ceContext] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("RequestContextNot")

  overr de def shouldCont nue(query: P pel neQuery w h HasDev ceContext): St ch[Boolean] =
    St ch.value(
      !requestContexts.ex sts(query.dev ceContext.flatMap(_.requestContextValue).conta ns))
}
