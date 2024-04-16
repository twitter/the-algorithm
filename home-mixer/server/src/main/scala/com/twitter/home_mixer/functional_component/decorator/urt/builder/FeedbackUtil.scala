package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.SeeFe r
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng
 mport com.tw ter.t  l nes.common.{thr ftscala => tlc}
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}
 mport com.tw ter.t  l neserv ce.model.Feedback nfo
 mport com.tw ter.t  l neserv ce.model.Feedback tadata
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.t  l neserv ce.{thr ftscala => tlst}

object FeedbackUt l {

  val FeedbackTtl = 30.days

  def bu ldUserSeeFe rCh ldFeedbackAct on(
    user d: Long,
    na sByUser d: Map[Long, Str ng],
    promptExternalStr ng: ExternalStr ng,
    conf rmat onExternalStr ng: ExternalStr ng,
    engage ntType: t.FeedbackEngage ntType,
    str ngCenter: Str ngCenter,
     nject onType: Opt on[st.SuggestType]
  ): Opt on[Ch ldFeedbackAct on] = {
    na sByUser d.get(user d).map { userScreenNa  =>
      val prompt = str ngCenter.prepare(
        promptExternalStr ng,
        Map("user" -> userScreenNa )
      )
      val conf rmat on = str ngCenter.prepare(
        conf rmat onExternalStr ng,
        Map("user" -> userScreenNa )
      )
      val feedback tadata = Feedback tadata(
        engage ntType = So (engage ntType),
        ent y ds = Seq(tlc.FeedbackEnt y.User d(user d)),
        ttl = So (FeedbackTtl))
      val feedbackUrl = Feedback nfo.feedbackUrl(
        feedbackType = tlst.FeedbackType.SeeFe r,
        feedback tadata = feedback tadata,
         nject onType =  nject onType
      )

      Ch ldFeedbackAct on(
        feedbackType = SeeFe r,
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
    }
  }
}
