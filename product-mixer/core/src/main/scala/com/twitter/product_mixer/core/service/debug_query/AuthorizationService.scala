package com.tw ter.product_m xer.core.serv ce.debug_query

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cyEvaluator
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Aut nt cat on
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.turntable.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Bas c class that prov des a ver f cat on  thod for c ck ng  f a call to   debugg ng
 * features  s allo d/author zed to make sa d call.
 * @param  sServ ceLocal W t r t  serv ce  s be ng run locally.
 */
@S ngleton
class Author zat onServ ce @ nject() (@Flag(Serv ceLocal)  sServ ceLocal: Boolean) {
   mport Author zat onServ ce._

  /**
   * C ck w t r a call to a g ven product  s author zed. Throws an [[Unauthor zedServ ceCallExcept on]]
   *  f not.
   * @param request ngServ ce dent f er T  Serv ce  dent f er of t  call ng serv ce
   * @param productAccessPol c es T  access pol c es of t  product be ng called.
   * @param requestContext T  request context of t  caller.
   */
  def ver fyRequestAuthor zat on(
    component dent f erStack: Component dent f erStack,
    request ngServ ce dent f er: Serv ce dent f er,
    productAccessPol c es: Set[AccessPol cy],
    requestContext: t.TurntableRequestContext
  ): Un  = {
    val  sServ ceCallAuthor zed =
      request ngServ ce dent f er.role == Allo dServ ce dent f erRole && request ngServ ce dent f er.serv ce == Allo dServ ce dent f erNa 
    val userLdapGroups = requestContext.ldapGroups.map(_.toSet)

    val accessPol cyAllo d = AccessPol cyEvaluator.evaluate(
      productAccessPol c es = productAccessPol c es,
      userLdapGroups = userLdapGroups.getOrElse(Set.empty)
    )

     f (! sServ ceLocal && ! sServ ceCallAuthor zed) {
      throw new Unauthor zedServ ceCallExcept on(
        request ngServ ce dent f er,
        component dent f erStack)
    }

     f (! sServ ceLocal && !accessPol cyAllo d) {
      throw new  nsuff c entAccessExcept on(
        userLdapGroups,
        productAccessPol c es,
        component dent f erStack)
    }
  }
}

object Author zat onServ ce {
  f nal val Allo dServ ce dent f erRole = "turntable"
  f nal val Allo dServ ce dent f erNa  = "turntable"
}

class Unauthor zedServ ceCallExcept on(
  serv ce dent f er: Serv ce dent f er,
  component dent f erStack: Component dent f erStack)
    extends P pel neFa lure(
      BadRequest,
      s"Unexpected Serv ce tr ed to call Turntable Debug endpo nt: ${Serv ce dent f er.asStr ng(serv ce dent f er)}",
      componentStack = So (component dent f erStack))

class  nsuff c entAccessExcept on(
  ldapGroups: Opt on[Set[Str ng]],
  des redAccessPol c es: Set[AccessPol cy],
  component dent f erStack: Component dent f erStack)
    extends P pel neFa lure(
      Aut nt cat on,
      s"Request d d not sat sfy access pol c es: $des redAccessPol c es w h ldapGroups = $ldapGroups",
      componentStack = So (component dent f erStack))
