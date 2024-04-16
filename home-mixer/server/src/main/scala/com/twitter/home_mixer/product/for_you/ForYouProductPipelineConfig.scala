package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.marshaller.t  l nes.Chronolog calCursorUnmarshaller
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.For ProductContext
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnablePushToHo M xerP pel neParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnableScoredT etsM xerP pel neParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.product.for_ .param.For ParamConf g
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAccessPol cy.DefaultHo M xerAccessPol cy
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g.DefaultNot f cat onGroup
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.EmptyResponseRateAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.LatencyAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.P99
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.SuccessRateAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.ThroughputAlert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fAbove
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fBelow
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fLatencyAbove
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neConf g
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.product_m xer.core.ut l.Sort ndexBu lder
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport com.tw ter.t  l nes.ut l.RequestCursorSer al zer
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For ProductP pel neConf g @ nject() (
  for T  l neScorerM xerP pel neConf g: For T  l neScorerM xerP pel neConf g,
  for ScoredT etsM xerP pel neConf g: For ScoredT etsM xerP pel neConf g,
  for PushToHo M xerP pel neConf g: For PushToHo M xerP pel neConf g,
  for ParamConf g: For ParamConf g)
    extends ProductP pel neConf g[Ho M xerRequest, For Query, urt.T  l neResponse] {

  overr de val  dent f er: ProductP pel ne dent f er = ProductP pel ne dent f er("For ")

  overr de val product: Product = For Product

  overr de val paramConf g: ProductParamConf g = for ParamConf g

  overr de def p pel neQueryTransfor r(
    request: Ho M xerRequest,
    params: Params
  ): For Query = {
    val context = request.productContext match {
      case So (context: For ProductContext) => context
      case _ => throw P pel neFa lure(BadRequest, "For ProductContext not found")
    }

    val debugOpt ons = request.debugParams.flatMap(_.debugOpt ons)

    /**
     * Unl ke ot r cl ents, newly created t ets on Andro d have t  sort  ndex set to t  current
     * t    nstead of t  top sort  ndex + 1, so t se t ets get stuck at t  top of t  t  l ne
     *  f subsequent t  l ne responses use t  sort  ndex from t  prev ous response  nstead of
     * t  current t  .
     */
    val p pel neCursor = request.ser al zedRequestCursor.flatMap { cursor =>
      Try(UrtCursorSer al zer.deser al zeOrderedCursor(cursor))
        .getOrElse(Chronolog calCursorUnmarshaller(RequestCursorSer al zer.deser al ze(cursor)))
        .map {
          case topCursor @ UrtOrderedCursor(_, _, So (TopCursor), _) =>
            val queryT   = debugOpt ons.flatMap(_.requestT  Overr de).getOrElse(T  .now)
            topCursor.copy( n  alSort ndex = Sort ndexBu lder.t  To d(queryT  ))
          case cursor => cursor
        }
    }

    For Query(
      params = params,
      cl entContext = request.cl entContext,
      features = None,
      p pel neCursor = p pel neCursor,
      requestedMaxResults = So (params(ServerMaxResultsParam)),
      debugOpt ons = debugOpt ons,
      dev ceContext = context.dev ceContext,
      seenT et ds = context.seenT et ds,
      dspCl entContext = context.dspCl entContext,
      pushToHo T et d = context.pushToHo T et d
    )
  }

  overr de val p pel nes: Seq[P pel neConf g] = Seq(
    for T  l neScorerM xerP pel neConf g,
    for ScoredT etsM xerP pel neConf g,
    for PushToHo M xerP pel neConf g
  )

  overr de def p pel neSelector(
    query: For Query
  ): Component dent f er = {
     f (query.pushToHo T et d. sDef ned && query.params(EnablePushToHo M xerP pel neParam))
      for PushToHo M xerP pel neConf g. dent f er
    else  f (query.params(EnableScoredT etsM xerP pel neParam))
      for ScoredT etsM xerP pel neConf g. dent f er
    else for T  l neScorerM xerP pel neConf g. dent f er
  }

  overr de val alerts: Seq[Alert] = Seq(
    SuccessRateAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      warnPred cate = Tr gger fBelow(99.9, 20, 30),
      cr  calPred cate = Tr gger fBelow(99.9, 30, 30),
    ),
    LatencyAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      percent le = P99,
      warnPred cate = Tr gger fLatencyAbove(2300.m ll s, 15, 30),
      cr  calPred cate = Tr gger fLatencyAbove(2800.m ll s, 15, 30)
    ),
    ThroughputAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      warnPred cate = Tr gger fAbove(70000),
      cr  calPred cate = Tr gger fAbove(80000)
    ),
    EmptyResponseRateAlert(
      not f cat onGroup = DefaultNot f cat onGroup,
      warnPred cate = Tr gger fAbove(2),
      cr  calPred cate = Tr gger fAbove(3)
    )
  )

  overr de val debugAccessPol c es: Set[AccessPol cy] = DefaultHo M xerAccessPol cy
}
