package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Feedback nfoMarshaller @ nject() (
  feedbackAct onMarshaller: FeedbackAct onMarshaller,
  feedbackD splayContextMarshaller: FeedbackD splayContextMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply(feedbackAct on nfo: FeedbackAct on nfo): urt.Feedback nfo = urt.Feedback nfo(
    // Generate key from t  hashcode of t  marshalled feedback act on URT
    feedbackKeys = feedbackAct on nfo.feedbackAct ons
      .map(feedbackAct onMarshaller(_)).map(FeedbackAct onMarshaller.generateKey),
    feedback tadata = feedbackAct on nfo.feedback tadata,
    d splayContext = feedbackAct on nfo.d splayContext.map(feedbackD splayContextMarshaller(_)),
    cl entEvent nfo = feedbackAct on nfo.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
  )
}
