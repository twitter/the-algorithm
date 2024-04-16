package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.marshaller.t  l nes.Recom ndedUsersCursorUnmarshaller
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProduct
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProductContext
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParamConf g
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAccessPol cy.DefaultHo M xerAccessPol cy
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g.DefaultNot f cat onGroup
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fBelow
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fLatencyAbove
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.LatencyAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.P99
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.SuccessRateAlert
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neConf g
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport com.tw ter.t  l nes.ut l.RequestCursorSer al zer
 mport com.tw ter.ut l.Try

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stRecom ndedUsersProductP pel neConf g @ nject() (
  l stRecom ndedUsersM xerP pel neConf g: L stRecom ndedUsersM xerP pel neConf g,
  l stRecom ndedUsersParamConf g: L stRecom ndedUsersParamConf g)
    extends ProductP pel neConf g[
      Ho M xerRequest,
      L stRecom ndedUsersQuery,
      urt.T  l neResponse
    ] {

  overr de val  dent f er: ProductP pel ne dent f er =
    ProductP pel ne dent f er("L stRecom ndedUsers")
  overr de val product: request.Product = L stRecom ndedUsersProduct
  overr de val paramConf g: ProductParamConf g = l stRecom ndedUsersParamConf g

  overr de def p pel neQueryTransfor r(
    request: Ho M xerRequest,
    params: Params
  ): L stRecom ndedUsersQuery = {
    val context = request.productContext match {
      case So (context: L stRecom ndedUsersProductContext) => context
      case _ => throw P pel neFa lure(BadRequest, "L stRecom ndedUsersProductContext not found")
    }

    val debugOpt ons = request.debugParams.flatMap(_.debugOpt ons)

    val p pel neCursor = request.ser al zedRequestCursor.flatMap { cursor =>
      Try(UrtCursorSer al zer.deser al zeUnorderedExclude dsCursor(cursor))
        .getOrElse(Recom ndedUsersCursorUnmarshaller(RequestCursorSer al zer.deser al ze(cursor)))
    }

    L stRecom ndedUsersQuery(
      l st d = context.l st d,
      params = params,
      cl entContext = request.cl entContext,
      features = None,
      p pel neCursor = p pel neCursor,
      requestedMaxResults = So (params(ServerMaxResultsParam)),
      debugOpt ons = debugOpt ons,
      selectedUser ds = context.selectedUser ds,
      excludedUser ds = context.excludedUser ds,
      l stNa  = context.l stNa 
    )
  }

  overr de def p pel nes: Seq[P pel neConf g] = Seq(l stRecom ndedUsersM xerP pel neConf g)

  overr de def p pel neSelector(query: L stRecom ndedUsersQuery): Component dent f er =
    l stRecom ndedUsersM xerP pel neConf g. dent f er

  overr de val alerts: Seq[Alert] = Seq(
    SuccessRateAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      warnPred cate = Tr gger fBelow(99.9, 20, 30),
      cr  calPred cate = Tr gger fBelow(99.9, 30, 30),
    ),
    LatencyAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      percent le = P99,
      warnPred cate = Tr gger fLatencyAbove(1000.m ll s, 15, 30),
      cr  calPred cate = Tr gger fLatencyAbove(1500.m ll s, 15, 30)
    )
  )

  overr de val debugAccessPol c es: Set[AccessPol cy] = DefaultHo M xerAccessPol cy
}
