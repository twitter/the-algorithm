package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.str ngcenter.Str
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Frown
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.SeeFe r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter

case class WhoToFollowFeedbackAct on nfoBu lder[
  -Query <: P pel neQuery,
  -Cand date <: Un versalNoun[Any]
](
  externalStr ngReg stry: ExternalStr ngReg stry,
  str ngCenter: Str ngCenter,
  encodedFeedbackRequest: Opt on[Str ng])
    extends BaseFeedbackAct on nfoBu lder[Query, Cand date] {

  pr vate val seeLessOftenFeedback =
    externalStr ngReg stry.createProdStr ng("Feedback.seeLessOften")
  pr vate val seeLessOftenConf rmat onFeedback =
    externalStr ngReg stry.createProdStr ng("Feedback.seeLessOftenConf rmat on")

  overr de def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on nfo] = So (
    FeedbackAct on nfo(
      feedbackAct ons = Seq(
        FeedbackAct on(
          feedbackType = SeeFe r,
          prompt = So (
            Str(seeLessOftenFeedback, str ngCenter, None)
              .apply(query, cand date, cand dateFeatures)),
          conf rmat on = So (
            Str(seeLessOftenConf rmat onFeedback, str ngCenter, None)
              .apply(query, cand date, cand dateFeatures)),
          ch ldFeedbackAct ons = None,
          feedbackUrl = None,
          conf rmat onD splayType = None,
          cl entEvent nfo = None,
          r chBehav or = None,
          subprompt = None,
           con = So (Frown), //  gnored by unsupported cl ents
          hasUndoAct on = So (true),
          encodedFeedbackRequest = encodedFeedbackRequest
        )
      ),
      feedback tadata = None,
      d splayContext = None,
      cl entEvent nfo = None
    )
  )
}
