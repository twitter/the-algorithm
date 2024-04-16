package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableNahFeedback nfoParam
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Frown
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.DontL ke
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l nes.common.{thr ftscala => tlc}
 mport com.tw ter.t  l neserv ce.model.Feedback nfo
 mport com.tw ter.t  l neserv ce.model.Feedback tadata
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class DontL keFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs,
  authorCh ldFeedbackAct onBu lder: AuthorCh ldFeedbackAct onBu lder,
  ret eterCh ldFeedbackAct onBu lder: Ret eterCh ldFeedbackAct onBu lder,
  notRelevantCh ldFeedbackAct onBu lder: NotRelevantCh ldFeedbackAct onBu lder,
  unfollowUserCh ldFeedbackAct onBu lder: UnfollowUserCh ldFeedbackAct onBu lder,
  muteUserCh ldFeedbackAct onBu lder: MuteUserCh ldFeedbackAct onBu lder,
  blockUserCh ldFeedbackAct onBu lder: BlockUserCh ldFeedbackAct onBu lder,
  reportT etCh ldFeedbackAct onBu lder: ReportT etCh ldFeedbackAct onBu lder) {

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on] = {
    Cand datesUt l.getOr g nalAuthor d(cand dateFeatures).map { author d =>
      val feedbackEnt  es = Seq(
        tlc.FeedbackEnt y.T et d(cand date. d),
        tlc.FeedbackEnt y.User d(author d)
      )
      val feedback tadata = Feedback tadata(
        engage ntType = None,
        ent y ds = feedbackEnt  es,
        ttl = So (30.days)
      )
      val feedbackUrl = Feedback nfo.feedbackUrl(
        feedbackType = tls.FeedbackType.DontL ke,
        feedback tadata = feedback tadata,
         nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None)
      )
      val ch ldFeedbackAct ons =  f (query.params(EnableNahFeedback nfoParam)) {
        Seq(
          unfollowUserCh ldFeedbackAct onBu lder(cand dateFeatures),
          muteUserCh ldFeedbackAct onBu lder(cand dateFeatures),
          blockUserCh ldFeedbackAct onBu lder(cand dateFeatures),
          reportT etCh ldFeedbackAct onBu lder(cand date)
        ).flatten
      } else {
        Seq(
          authorCh ldFeedbackAct onBu lder(cand dateFeatures),
          ret eterCh ldFeedbackAct onBu lder(cand dateFeatures),
          notRelevantCh ldFeedbackAct onBu lder(cand date, cand dateFeatures)
        ).flatten
      }

      FeedbackAct on(
        feedbackType = DontL ke,
        prompt = So (str ngCenter.prepare(externalStr ngs.dontL keStr ng)),
        conf rmat on = So (str ngCenter.prepare(externalStr ngs.dontL keConf rmat onStr ng)),
        ch ldFeedbackAct ons =
           f (ch ldFeedbackAct ons.nonEmpty) So (ch ldFeedbackAct ons) else None,
        feedbackUrl = So (feedbackUrl),
        hasUndoAct on = So (true),
        conf rmat onD splayType = None,
        cl entEvent nfo = None,
         con = So (Frown),
        r chBehav or = None,
        subprompt = None,
        encodedFeedbackRequest = None
      )
    }
  }
}
