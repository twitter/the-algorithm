package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.FeedbackAct onMarshaller.generateKey
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

object FeedbackAct onMarshaller {
  def generateKey(feedbackAct on: urt.FeedbackAct on): Str ng = {
    feedbackAct on.hashCode.toStr ng
  }
}

@S ngleton
class FeedbackAct onMarshaller @ nject() (
  ch ldFeedbackAct onMarshaller: Ch ldFeedbackAct onMarshaller,
  feedbackTypeMarshaller: FeedbackTypeMarshaller,
  conf rmat onD splayTypeMarshaller: Conf rmat onD splayTypeMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  hor zon conMarshaller: Hor zon conMarshaller,
  r chFeedbackBehav orMarshaller: R chFeedbackBehav orMarshaller) {

  def apply(feedbackAct on: FeedbackAct on): urt.FeedbackAct on = {
    val ch ldKeys = feedbackAct on.ch ldFeedbackAct ons
      .map(_.map { ch ldFeedbackAct on =>
        val urtCh ldFeedbackAct on = ch ldFeedbackAct onMarshaller(ch ldFeedbackAct on)
        generateKey(urtCh ldFeedbackAct on)
      })

    urt.FeedbackAct on(
      feedbackType = feedbackTypeMarshaller(feedbackAct on.feedbackType),
      prompt = feedbackAct on.prompt,
      conf rmat on = feedbackAct on.conf rmat on,
      ch ldKeys = ch ldKeys,
      feedbackUrl = feedbackAct on.feedbackUrl,
      hasUndoAct on = feedbackAct on.hasUndoAct on,
      conf rmat onD splayType =
        feedbackAct on.conf rmat onD splayType.map(conf rmat onD splayTypeMarshaller(_)),
      cl entEvent nfo = feedbackAct on.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
       con = feedbackAct on. con.map(hor zon conMarshaller(_)),
      r chBehav or = feedbackAct on.r chBehav or.map(r chFeedbackBehav orMarshaller(_)),
      subprompt = feedbackAct on.subprompt,
      encodedFeedbackRequest = feedbackAct on.encodedFeedbackRequest
    )
  }
}
