package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.WhoToFollowFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => tl}
 mport com.tw ter.t  l nes.ut l.FeedbackRequestSer al zer
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport com.tw ter.t  l neserv ce.thr ftscala.FeedbackType

object Ho WhoToFollowFeedbackAct on nfoBu lder {
  pr vate val Feedback tadata = tl.Feedback tadata(
     nject onType = So (SuggestType.WhoToFollow),
    engage ntType = None,
    ent y ds = Seq.empty,
    ttlMs = None
  )
  pr vate val FeedbackRequest =
    tl.DefaultFeedbackRequest2(FeedbackType.SeeFe r, Feedback tadata)
  pr vate val EncodedFeedbackRequest =
    FeedbackRequestSer al zer.ser al ze(tl.FeedbackRequest.DefaultFeedbackRequest2(FeedbackRequest))
}

@S ngleton
case class Ho WhoToFollowFeedbackAct on nfoBu lder @ nject() (
  feedbackStr ngs: FeedbackStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseFeedbackAct on nfoBu lder[P pel neQuery, UserCand date] {

  pr vate val whoToFollowFeedbackAct on nfoBu lder = WhoToFollowFeedbackAct on nfoBu lder(
    seeLessOftenFeedbackStr ng = feedbackStr ngs.seeLessOftenFeedbackStr ng,
    seeLessOftenConf rmat onFeedbackStr ng = feedbackStr ngs.seeLessOftenConf rmat onFeedbackStr ng,
    str ngCenter = str ngCenterProv der.get(),
    encodedFeedbackRequest = So (Ho WhoToFollowFeedbackAct on nfoBu lder.EncodedFeedbackRequest)
  )

  overr de def apply(
    query: P pel neQuery,
    cand date: UserCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on nfo] =
    whoToFollowFeedbackAct on nfoBu lder.apply(query, cand date, cand dateFeatures)
}
