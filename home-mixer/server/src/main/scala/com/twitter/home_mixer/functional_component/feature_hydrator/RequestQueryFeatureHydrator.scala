package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.f nagle.trac ng.Annotat on.B naryAnnotat on
 mport com.tw ter.f nagle.trac ng.ForwardAnnotat on
 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.model.request.Dev ceContext.RequestContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.jo nkey.context.RequestJo nKeyContext
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.adapters.request_context.RequestContextAdapter.dowFromT  stamp
 mport com.tw ter.t  l nes.pred ct on.adapters.request_context.RequestContextAdapter.h FromT  stamp
 mport java.ut l.UU D
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RequestQueryFeatureHydrator[
  Query <: P pel neQuery w h HasP pel neCursor[UrtOrderedCursor] w h HasDev ceContext] @ nject() (
) extends QueryFeatureHydrator[Query] {

  overr de val features: Set[Feature[_, _]] = Set(
    AccountAgeFeature,
    Cl ent dFeature,
    Dev ceLanguageFeature,
    Get n  alFeature,
    GetM ddleFeature,
    GetNe rFeature,
    GetOlderFeature,
    Guest dFeature,
    HasDarkRequestFeature,
     sForegroundRequestFeature,
     sLaunchRequestFeature,
    Poll ngFeature,
    PullToRefreshFeature,
    RequestJo n dFeature,
    ServedRequest dFeature,
    T  stampFeature,
    T  stampGMTDowFeature,
    T  stampGMTH Feature,
    V e r dFeature
  )

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Request")

  pr vate val DarkRequestAnnotat on = "clnt/has_dark_request"

  // Convert Language code to  SO 639-3 format
  pr vate def getLanguage SOFormatByCode(languageCode: Str ng): Str ng =
    Thr ftLanguageUt l.getLanguageCodeOf(Thr ftLanguageUt l.getThr ftLanguageOf(languageCode))

  pr vate def getRequestJo n d(servedRequest d: Long): Opt on[Long] =
    So (RequestJo nKeyContext.current.flatMap(_.requestJo n d).getOrElse(servedRequest d))

  pr vate def hasDarkRequest: Opt on[Boolean] = ForwardAnnotat on.current
    .getOrElse(Seq[B naryAnnotat on]())
    .f nd(_.key == DarkRequestAnnotat on)
    .map(_.value.as nstanceOf[Boolean])

  overr de def hydrate(query: Query): St ch[FeatureMap] = {
    val requestContext = query.dev ceContext.flatMap(_.requestContextValue)
    val servedRequest d = UU D.randomUU D.getMostS gn f cantB s
    val t  stamp = query.queryT  . nM ll seconds

    val featureMap = FeatureMapBu lder()
      .add(AccountAgeFeature, query.getOpt onalUser d.flatMap(Snowflake d.t  From dOpt))
      .add(Cl ent dFeature, query.cl entContext.app d)
      .add(Dev ceLanguageFeature, query.getLanguageCode.map(getLanguage SOFormatByCode))
      .add(
        Get n  alFeature,
        query.p pel neCursor.forall(cursor => cursor. d. sEmpty && cursor.gapBoundary d. sEmpty))
      .add(
        GetM ddleFeature,
        query.p pel neCursor.ex sts(cursor =>
          cursor. d. sDef ned && cursor.gapBoundary d. sDef ned &&
            cursor.cursorType.conta ns(GapCursor)))
      .add(
        GetNe rFeature,
        query.p pel neCursor.ex sts(cursor =>
          cursor. d. sDef ned && cursor.gapBoundary d. sEmpty &&
            cursor.cursorType.conta ns(TopCursor)))
      .add(
        GetOlderFeature,
        query.p pel neCursor.ex sts(cursor =>
          cursor. d. sDef ned && cursor.gapBoundary d. sEmpty &&
            cursor.cursorType.conta ns(BottomCursor)))
      .add(Guest dFeature, query.cl entContext.guest d)
      .add( sForegroundRequestFeature, requestContext.conta ns(RequestContext.Foreground))
      .add( sLaunchRequestFeature, requestContext.conta ns(RequestContext.Launch))
      .add(Poll ngFeature, query.dev ceContext.ex sts(_. sPoll ng.conta ns(true)))
      .add(PullToRefreshFeature, requestContext.conta ns(RequestContext.PullToRefresh))
      .add(ServedRequest dFeature, So (servedRequest d))
      .add(RequestJo n dFeature, getRequestJo n d(servedRequest d))
      .add(T  stampFeature, t  stamp)
      .add(T  stampGMTDowFeature, dowFromT  stamp(t  stamp))
      .add(T  stampGMTH Feature, h FromT  stamp(t  stamp))
      .add(HasDarkRequestFeature, hasDarkRequest)
      .add(
        V e r dFeature,
        query.getOpt onalUser d
          .orElse(query.getGuest d).getOrElse(
            throw P pel neFa lure(BadRequest, "M ss ng v e r  d")))
      .bu ld()

    St ch.value(featureMap)
  }
}
