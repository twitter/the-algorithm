package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ch ldFeedbackAct onMarshaller @ nject() (
  feedbackTypeMarshaller: FeedbackTypeMarshaller,
  conf rmat onD splayTypeMarshaller: Conf rmat onD splayTypeMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  hor zon conMarshaller: Hor zon conMarshaller,
  r chFeedbackBehav orMarshaller: R chFeedbackBehav orMarshaller) {

  def apply(feedbackAct on: Ch ldFeedbackAct on): urt.FeedbackAct on = {
    urt.FeedbackAct on(
      feedbackType = feedbackTypeMarshaller(feedbackAct on.feedbackType),
      prompt = feedbackAct on.prompt,
      conf rmat on = feedbackAct on.conf rmat on,
      ch ldKeys = None,
      feedbackUrl = feedbackAct on.feedbackUrl,
      hasUndoAct on = feedbackAct on.hasUndoAct on,
      conf rmat onD splayType =
        feedbackAct on.conf rmat onD splayType.map(conf rmat onD splayTypeMarshaller(_)),
      cl entEvent nfo = feedbackAct on.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
       con = feedbackAct on. con.map(hor zon conMarshaller(_)),
      r chBehav or = feedbackAct on.r chBehav or.map(r chFeedbackBehav orMarshaller(_)),
      subprompt = feedbackAct on.subprompt
    )
  }
}
