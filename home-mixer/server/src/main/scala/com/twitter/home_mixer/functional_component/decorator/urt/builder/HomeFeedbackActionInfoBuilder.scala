package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableNahFeedback nfoParam
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}
 mport com.tw ter.t  l nes.ut l.Feedback tadataSer al zer
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho FeedbackAct on nfoBu lder @ nject() (
  not nterestedTop cFeedbackAct onBu lder: Not nterestedTop cFeedbackAct onBu lder,
  dontL keFeedbackAct onBu lder: DontL keFeedbackAct onBu lder)
    extends BaseFeedbackAct on nfoBu lder[P pel neQuery, T etCand date] {

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on nfo] = {
    val supportedProduct = query.product match {
      case Follow ngProduct => query.params(EnableNahFeedback nfoParam)
      case For Product => true
      case _ => false
    }
    val  sAuthoredByV e r = Cand datesUt l. sAuthoredByV e r(query, cand dateFeatures)

     f (supportedProduct && ! sAuthoredByV e r) {
      val feedbackAct ons = Seq(
        not nterestedTop cFeedbackAct onBu lder(cand dateFeatures),
        dontL keFeedbackAct onBu lder(query, cand date, cand dateFeatures)
      ).flatten
      val feedback tadata = Feedback tadataSer al zer.ser al ze(
        t.Feedback tadata( nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None)))

      So (
        FeedbackAct on nfo(
          feedbackAct ons = feedbackAct ons,
          feedback tadata = So (feedback tadata),
          d splayContext = None,
          cl entEvent nfo = None
        ))
    } else None
  }
}
