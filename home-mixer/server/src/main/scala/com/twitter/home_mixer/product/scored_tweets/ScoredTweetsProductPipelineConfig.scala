package com.tw ter.ho _m xer.product.scored_t ets

 mport com.tw ter.ho _m xer.model.Ho Features.ServedT et dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T  l neServ ceT etsFeature
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProductContext
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParamConf g
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAccessPol cy.DefaultHo M xerAccessPol cy
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neConf g
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScoredT etsProductP pel neConf g @ nject() (
  scoredT etsRecom ndat onP pel neConf g: ScoredT etsRecom ndat onP pel neConf g,
  scoredT etsParamConf g: ScoredT etsParamConf g)
    extends ProductP pel neConf g[Ho M xerRequest, ScoredT etsQuery, t.ScoredT ets] {

  overr de val  dent f er: ProductP pel ne dent f er = ProductP pel ne dent f er("ScoredT ets")

  overr de val product: Product = ScoredT etsProduct

  overr de val paramConf g: ProductParamConf g = scoredT etsParamConf g

  overr de def p pel neQueryTransfor r(
    request: Ho M xerRequest,
    params: Params
  ): ScoredT etsQuery = {
    val context = request.productContext match {
      case So (context: ScoredT etsProductContext) => context
      case _ => throw P pel neFa lure(BadRequest, "ScoredT etsProductContext not found")
    }

    val featureMap = FeatureMapBu lder()
      .add(ServedT et dsFeature, context.servedT et ds.getOrElse(Seq.empty))
      .add(T  l neServ ceT etsFeature, context.backf llT et ds.getOrElse(Seq.empty))
      .bu ld()

    ScoredT etsQuery(
      params = params,
      cl entContext = request.cl entContext,
      p pel neCursor =
        request.ser al zedRequestCursor.flatMap(UrtCursorSer al zer.deser al zeOrderedCursor),
      requestedMaxResults = So (params(ServerMaxResultsParam)),
      debugOpt ons = request.debugParams.flatMap(_.debugOpt ons),
      features = So (featureMap),
      dev ceContext = context.dev ceContext,
      seenT et ds = context.seenT et ds,
      qual yFactorStatus = None
    )
  }

  overr de val p pel nes: Seq[P pel neConf g] = Seq(scoredT etsRecom ndat onP pel neConf g)

  overr de def p pel neSelector(query: ScoredT etsQuery): Component dent f er =
    scoredT etsRecom ndat onP pel neConf g. dent f er

  overr de val debugAccessPol c es: Set[AccessPol cy] = DefaultHo M xerAccessPol cy
}
