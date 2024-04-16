package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Ch ldFeedbackAct onMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.FeedbackAct onMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.TransportMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conta nsFeedbackAct on nfos
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.t  l nes.render.thr ftscala.T  l neResponse
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * [[TransportMarshaller]] for URT types
 *
 * @note to make an  nstance of a [[UrtTransportMarshaller]]   can use [[UrtTransportMarshallerBu lder.marshaller]]
 */
@S ngleton
class UrtTransportMarshaller @ nject() (
  t  l ne nstruct onMarshaller: T  l ne nstruct onMarshaller,
  feedbackAct onMarshaller: FeedbackAct onMarshaller,
  ch ldFeedbackAct onMarshaller: Ch ldFeedbackAct onMarshaller,
  t  l ne tadataMarshaller: T  l ne tadataMarshaller)
    extends TransportMarshaller[T  l ne, urt.T  l neResponse] {

  overr de val  dent f er: TransportMarshaller dent f er =
    TransportMarshaller dent f er("Un f edR chT  l ne")

  overr de def apply(t  l ne: T  l ne): urt.T  l neResponse = {
    val feedbackAct ons: Opt on[Map[Str ng, urt.FeedbackAct on]] = {
      collectAndMarshallFeedbackAct ons(t  l ne. nstruct ons)
    }
    urt.T  l neResponse(
      state = urt.T  l neState.Ok,
      t  l ne = urt.T  l ne(
         d = t  l ne. d,
         nstruct ons = t  l ne. nstruct ons.map(t  l ne nstruct onMarshaller(_)),
        responseObjects =
          feedbackAct ons.map(act ons => urt.ResponseObjects(feedbackAct ons = So (act ons))),
         tadata = t  l ne. tadata.map(t  l ne tadataMarshaller(_))
      )
    )
  }

  // Currently, feedbackAct on nfo at t  URT T  l ne em level  s supported, wh ch covers almost all
  // ex st ng use cases. Ho ver,  f add  onal feedbackAct on nfos are def ned on t  URT
  // T  l ne emContent level for "compound" URT types (see deprecated Top cCollect on /
  // Top cCollect onData), t   s not supported.  f "compound" URT types are added  n t  future,
  // support must be added w h n that type (see Module em) to handle t  collect on and marshall ng
  // of t se feedbackAct on nfos.

  pr vate[t ] def collectAndMarshallFeedbackAct ons(
     nstruct ons: Seq[T  l ne nstruct on]
  ): Opt on[Map[Str ng, urt.FeedbackAct on]] = {
    val feedbackAct ons: Seq[FeedbackAct on] = for {
      feedbackAct on nfos <-  nstruct ons.collect {
        case c: Conta nsFeedbackAct on nfos => c.feedbackAct on nfos
      }
      feedback nfoOpt <- feedbackAct on nfos
      feedback nfo <- feedback nfoOpt.toSeq
      feedbackAct on <- feedback nfo.feedbackAct ons
    } y eld feedbackAct on

     f (feedbackAct ons.nonEmpty) {
      val urtFeedbackAct ons = feedbackAct ons.map(feedbackAct onMarshaller(_))

      val urtCh ldFeedbackAct ons: Seq[urt.FeedbackAct on] = for {
        feedbackAct on <- feedbackAct ons
        ch ldFeedbackAct ons <- feedbackAct on.ch ldFeedbackAct ons.toSeq
        ch ldFeedbackAct on <- ch ldFeedbackAct ons
      } y eld ch ldFeedbackAct onMarshaller(ch ldFeedbackAct on)

      val allUrtFeedbackAct ons = urtFeedbackAct ons ++ urtCh ldFeedbackAct ons

      So (
        allUrtFeedbackAct ons.map { urtAct on =>
          FeedbackAct onMarshaller.generateKey(urtAct on) -> urtAct on
        }.toMap
      )
    } else {
      None
    }
  }
}

object UrtTransportMarshaller {
  def unava lable(t  l ne d: Str ng): T  l neResponse = {
    urt.T  l neResponse(
      state = urt.T  l neState.Unava lable,
      t  l ne = urt.T  l ne(
         d = t  l ne d,
         nstruct ons = Seq.empty,
        responseObjects = None,
         tadata = None
      )
    )
  }
}
