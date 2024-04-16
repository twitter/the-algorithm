package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne

 mport com.tw ter.onboard ng. nject ons.thr ftscala. nject on
 mport com.tw ter.onboard ng. nject ons.{thr ftscala => onboard ngthr ft}
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Automat cUn queModule d
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Module dGenerat on
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BasePromptCand date
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPrompt nject onsFeature
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseT  l neModuleBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Carousel
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Fl pPromptUrtModuleBu lder[-Query <: P pel neQuery](
  module dGenerat on: Module dGenerat on = Automat cUn queModule d())
    extends BaseT  l neModuleBu lder[Query, BasePromptCand date[Any]] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[BasePromptCand date[Any]]]
  ): T  l neModule = {
    val f rstCand date = cand dates. ad
    val  nject on = f rstCand date.features.get(Fl pPrompt nject onsFeature)
     nject on match {
      case  nject on.T lesCarousel(cand date) =>
        T  l neModule(
           d = module dGenerat on.module d,
          sort ndex = None,
          entryNa space = EntryNa space("fl p-t  l ne-module"),
          cl entEvent nfo =
            So (Onboard ng nject onConvers ons.convertCl entEvent nfo(cand date.cl entEvent nfo)),
          feedbackAct on nfo =
            cand date.feedback nfo.map(Onboard ng nject onConvers ons.convertFeedback nfo),
           sP nned = So (cand date. sP nnedEntry),
          //  ems are automat cally set  n t  doma n marshaller phase
           ems = Seq.empty,
          d splayType = Carousel,
           ader = cand date. ader.map(T lesCarouselConvers ons.convertModule ader),
          footer = None,
           tadata = None,
          showMoreBehav or = None
        )
      case _ => throw new UnsupportedFl pPrompt nModuleExcept on( nject on)
    }
  }
}

class UnsupportedFl pPrompt nModuleExcept on( nject on: onboard ngthr ft. nject on)
    extends UnsupportedOperat onExcept on(
      "Unsupported t  l ne  em  n a Fl p prompt module " + TransportMarshaller.getS mpleNa (
         nject on.getClass))
