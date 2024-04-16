package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r

 mport com.tw ter.onboard ng. nject ons.{thr ftscala => fl p nject on}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne. nter d atePrompt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BasePromptCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.FullCoverPromptCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.HalfCoverPromptCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date. nl nePromptCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.PromptCarouselT leCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.RelevancePromptCand date
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller

object PromptResultsTransfor r
    extends Cand dateP pel neResultsTransfor r[
       nter d atePrompt,
      BasePromptCand date[Any]
    ] {

  /**
   * Transforms a Fl p  nject on to a Product M xer doma n object der v ng from BasePromptCand date.
   * Supported  nject on types have to match those declared  n com.tw ter.product_m xer.component_l brary.transfor r.flex ble_ nject on_p pel ne.Fl pQueryTransfor r#supportedPromptFormats
   */
  overr de def transform( nput:  nter d atePrompt): BasePromptCand date[Any] =
     nput. nject on match {
      case  nl nePrompt: fl p nject on. nject on. nl nePrompt =>
         nl nePromptCand date( d =  nl nePrompt. nl nePrompt. nject on dent f er
          .getOrElse(throw new M ss ng nject on d( nput. nject on)))
      case _: fl p nject on. nject on.FullCover =>
        FullCoverPromptCand date( d = "0")
      case _: fl p nject on. nject on.HalfCover =>
        HalfCoverPromptCand date( d = "0")
      case _: fl p nject on. nject on.T lesCarousel =>
        PromptCarouselT leCand date( d =
           nput.offset nModule.getOrElse(throw Fl pPromptOffset nModuleM ss ng))
      case relevancePrompt: fl p nject on. nject on.RelevancePrompt =>
        RelevancePromptCand date(
           d = relevancePrompt.relevancePrompt. nject on dent f er,
          pos  on = relevancePrompt.relevancePrompt.requestedPos  on.map(_.to nt))
      case  nject on => throw new Unsupported nject onType( nject on)
    }
}

class M ss ng nject on d( nject on: fl p nject on. nject on)
    extends  llegalArgu ntExcept on(
      s" nject on  dent f er  s m ss ng ${TransportMarshaller.getS mpleNa ( nject on.getClass)}")

class Unsupported nject onType( nject on: fl p nject on. nject on)
    extends UnsupportedOperat onExcept on(
      s"Unsupported FL P  nject on Type : ${TransportMarshaller.getS mpleNa ( nject on.getClass)}")

object Fl pPromptOffset nModuleM ss ng
    extends NoSuchEle ntExcept on(
      "Fl pPromptOffset nModuleFeature must be set for t  T lesCarousel FL P  nject on  n PromptCand dateS ce")
