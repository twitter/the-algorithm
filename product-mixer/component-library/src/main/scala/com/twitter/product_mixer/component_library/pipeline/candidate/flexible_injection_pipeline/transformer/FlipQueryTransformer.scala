package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r

 mport com.tw ter.onboard ng.task.serv ce.thr ftscala.PromptType
 mport com.tw ter.onboard ng.task.serv ce.{thr ftscala => fl p}
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Fl pQueryTransfor r
    extends Cand dateP pel neQueryTransfor r[
      P pel neQuery w h HasFl p nject onParams,
      fl p.Get nject onsRequest
    ] {

  val SUPPORTED_PROMPT_TYPES: Set[PromptType] = Set(
    PromptType. nl nePrompt,
    PromptType.FullCover,
    PromptType.HalfCover,
    PromptType.T leCarousel,
    PromptType.RelevancePrompt)

  overr de def transform(
    query: P pel neQuery w h HasFl p nject onParams
  ): fl p.Get nject onsRequest = {
    val cl entContext = fl p.Cl entContext(
      user d = query.cl entContext.user d,
      guest d = query.cl entContext.guest d,
      cl entAppl cat on d = query.cl entContext.app d,
      dev ce d = query.cl entContext.dev ce d,
      countryCode = query.cl entContext.countryCode,
      languageCode = query.cl entContext.languageCode,
      userAgent = query.cl entContext.userAgent,
      guest dMarket ng = query.cl entContext.guest dMarket ng,
      guest dAds = query.cl entContext.guest dAds,
       s nternalOrTwoff ce = query.cl entContext. sTwoff ce,
       pAddress = query.cl entContext. pAddress
    )
    val d splayContext: fl p.D splayContext =
      fl p.D splayContext(
        d splayLocat on = query.d splayLocat on,
        t  l ne d = query.cl entContext.user d
      )

    val requestTarget ngContext: fl p.RequestTarget ngContext =
      fl p.RequestTarget ngContext(
        rank ngD sablerW hLatestControlsAval able =
          query.rank ngD sablerW hLatestControlsAva lable,
        react vePromptContext = None,
         sEmptyState = query. sEmptyState,
         sF rstRequestAfterS gnup = query. sF rstRequestAfterS gnup,
         sEndOfT  l ne = query. sEndOfT  l ne
      )

    fl p.Get nject onsRequest(
      cl entContext = cl entContext,
      d splayContext = d splayContext,
      requestTarget ngContext = So (requestTarget ngContext),
      userRoles = query.cl entContext.userRoles,
      t  l neContext = None,
      supportedPromptTypes = So (SUPPORTED_PROMPT_TYPES)
    )
  }
}
