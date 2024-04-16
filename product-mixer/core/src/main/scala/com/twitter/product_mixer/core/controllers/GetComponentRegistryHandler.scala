package com.tw ter.product_m xer.core.controllers

 mport com.tw ter.f nagle.http.Request
 mport com.tw ter. nject. njector
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.W hDebugAccessPol c es
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neConf g
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g
 mport com.tw ter.product_m xer.core.serv ce.component_reg stry
 mport com.tw ter.product_m xer.core.serv ce.component_reg stry.ComponentReg stry
 mport com.tw ter.product_m xer.core.serv ce.component_reg stry.ComponentReg strySnapshot
 mport com.tw ter.ut l.Future

case class GetComponentReg stryHandler( njector:  njector) {
  lazy val componentReg stry: ComponentReg stry =  njector. nstance[ComponentReg stry]

  def apply(request: Request): Future[ComponentReg stryResponse] = {
    componentReg stry.get.map { currentComponentReg stry: ComponentReg strySnapshot =>
      val reg steredComponents = currentComponentReg stry.getAllReg steredComponents.map {
        reg steredComponent =>
          val component dent f er = reg steredComponent. dent f er
          val ch ldComponents = currentComponentReg stry
            .getCh ldComponents(component dent f er)
            .map { ch ldComponent =>
              Ch ldComponent(
                componentType = ch ldComponent.componentType,
                na  = ch ldComponent.na ,
                relat veScopes = component dent f er.toScopes ++ ch ldComponent.toScopes,
                qual yFactorMon or ngConf g =
                  bu ldQual yFactor ngMon or ngConf g(reg steredComponent, ch ldComponent)
              )
            }

          Reg steredComponent(
            componentType = component dent f er.componentType,
            na  = component dent f er.na ,
            scopes = component dent f er.toScopes,
            ch ldren = ch ldComponents,
            alertConf g = So (reg steredComponent.component.alerts.map(AlertConf g.apply)),
            s ceF le = So (reg steredComponent.s ceF le),
            debugAccessPol c es = So (reg steredComponent.component match {
              case w hDebugAccessPol c es: W hDebugAccessPol c es =>
                w hDebugAccessPol c es.debugAccessPol c es
              case _ => Set.empty
            })
          )
      }

      ComponentReg stryResponse(reg steredComponents)
    }
  }

  pr vate def bu ldQual yFactor ngMon or ngConf g(
    parent: component_reg stry.Reg steredComponent,
    ch ld: Component dent f er
  ): Opt on[Qual yFactorMon or ngConf g] = {
    val qual yFactorConf gs: Opt on[Map[Component dent f er, Qual yFactorConf g]] =
      parent.component match {
        case p pel ne: P pel ne[_, _] =>
          p pel ne.conf g match {
            case conf g: Recom ndat onP pel neConf g[_, _, _, _] =>
              So (conf g.qual yFactorConf gs)
            case conf g: M xerP pel neConf g[_, _, _] =>
              So (
                conf g.qual yFactorConf gs
                  .as nstanceOf[Map[Component dent f er, Qual yFactorConf g]])
            case conf g: ProductP pel neConf g[_, _, _] =>
              So (conf g.qual yFactorConf gs)
            case _ => None
          }
        case _ => None
      }

    val qfConf gForCh ld: Opt on[Qual yFactorConf g] = qual yFactorConf gs.flatMap(_.get(ch ld))

    qfConf gForCh ld.map { qfConf g =>
      Qual yFactorMon or ngConf g(
        boundM n = qfConf g.qual yFactorBounds.bounds.m n nclus ve,
        boundMax = qfConf g.qual yFactorBounds.bounds.max nclus ve
      )
    }
  }
}

case class Reg steredComponent(
  componentType: Str ng,
  na : Str ng,
  scopes: Seq[Str ng],
  ch ldren: Seq[Ch ldComponent],
  alertConf g: Opt on[Seq[AlertConf g]],
  s ceF le: Opt on[Str ng],
  debugAccessPol c es: Opt on[Set[AccessPol cy]])

case class Ch ldComponent(
  componentType: Str ng,
  na : Str ng,
  relat veScopes: Seq[Str ng],
  qual yFactorMon or ngConf g: Opt on[Qual yFactorMon or ngConf g])

/**
 * T  shape of t  data returned to callers after h t ng t  `component-reg stry` endpo nt
 *
 * @note changes to [[ComponentReg stryResponse]] or conta ned types should be reflected
 *        n dashboard generat on code  n t  `mon or ng-conf gs/product_m xer` d rectory.
 */
case class ComponentReg stryResponse(
  reg steredComponents: Seq[Reg steredComponent])

case class ProductP pel ne( dent f er: Str ng)
case class ProductP pel nesResponse(productP pel nes: Seq[ProductP pel ne])
