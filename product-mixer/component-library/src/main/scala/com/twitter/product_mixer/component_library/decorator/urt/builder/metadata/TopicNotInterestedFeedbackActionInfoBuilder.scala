package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseTop cCand date
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orMarkNot nterestedTop c
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Top cNot nterestedFeedbackAct on nfoBu lder[-Query <: P pel neQuery]()
    extends BaseFeedbackAct on nfoBu lder[Query, BaseTop cCand date] {

  overr de def apply(
    query: Query,
    top cCand date: BaseTop cCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on nfo] = {
    So (
      FeedbackAct on nfo(
        feedbackAct ons = Seq(
          FeedbackAct on(
            feedbackType = R chBehav or,
            r chBehav or = So (
              R chFeedbackBehav orMarkNot nterestedTop c(top cCand date. d.toStr ng)
            ),
            hasUndoAct on = So (true),
            prompt = None,
            conf rmat on = None,
            feedbackUrl = None,
            cl entEvent nfo = None,
            ch ldFeedbackAct ons = None,
            conf rmat onD splayType = None,
             con = None,
            subprompt = None,
            encodedFeedbackRequest = None
          )
        ),
        feedback tadata = None,
        d splayContext = None,
        cl entEvent nfo = None
      ))
  }
}
