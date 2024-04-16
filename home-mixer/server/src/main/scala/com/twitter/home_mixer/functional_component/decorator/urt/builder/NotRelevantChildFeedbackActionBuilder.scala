package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.NotRelevant
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l nes.common.{thr ftscala => tlc}
 mport com.tw ter.t  l neserv ce.model.Feedback nfo
 mport com.tw ter.t  l neserv ce.model.Feedback tadata
 mport com.tw ter.t  l neserv ce.{thr ftscala => tlst}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class NotRelevantCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Ch ldFeedbackAct on] = {
    val prompt = str ngCenter.prepare(externalStr ngs.notRelevantStr ng)
    val conf rmat on = str ngCenter.prepare(externalStr ngs.notRelevantConf rmat onStr ng)
    val feedback tadata = Feedback tadata(
      engage ntType = None,
      ent y ds = Seq(tlc.FeedbackEnt y.T et d(cand date. d)),
      ttl = So (FeedbackUt l.FeedbackTtl))
    val feedbackUrl = Feedback nfo.feedbackUrl(
      feedbackType = tlst.FeedbackType.NotRelevant,
      feedback tadata = feedback tadata,
       nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None)
    )

    So (
      Ch ldFeedbackAct on(
        feedbackType = NotRelevant,
        prompt = So (prompt),
        conf rmat on = So (conf rmat on),
        feedbackUrl = So (feedbackUrl),
        hasUndoAct on = So (true),
        conf rmat onD splayType = None,
        cl entEvent nfo = None,
         con = None,
        r chBehav or = None,
        subprompt = None
      )
    )
  }
}
