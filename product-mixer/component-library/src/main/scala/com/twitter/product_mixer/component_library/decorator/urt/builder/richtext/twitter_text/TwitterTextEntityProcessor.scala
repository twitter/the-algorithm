package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.R chTextReferenceObjectBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text.Tw terTextEnt yProcessor.DefaultReferenceObjectBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.ExternalUrl
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.ReferenceObject
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextCashtag
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextHashtag
 mport com.tw ter.tw tertext.Extractor
 mport scala.collect on.convert. mpl c Convers ons._

object Tw terTextEnt yProcessor {
  object DefaultReferenceObjectBu lder extends R chTextReferenceObjectBu lder {
    def apply(tw terEnt y: Extractor.Ent y): Opt on[ReferenceObject] = {
      tw terEnt y.getType match {
        case Extractor.Ent y.Type.URL =>
          So (Url(ExternalUrl, tw terEnt y.getValue))
        case Extractor.Ent y.Type.HASHTAG =>
          So (R chTextHashtag(tw terEnt y.getValue))
        case Extractor.Ent y.Type.CASHTAG =>
          So (R chTextCashtag(tw terEnt y.getValue))
        case _ => None
      }
    }
  }
}

/**
 * Add t  correspond ng  [[R chTextEnt y]] extract on log c  nto [[Tw terTextRenderer]].
 * T  [[Tw terTextRenderer]] after be ng processed w ll extract t  def ned ent  es.
 */
case class Tw terTextEnt yProcessor(
  tw terTextReferenceObjectBu lder: R chTextReferenceObjectBu lder = DefaultReferenceObjectBu lder)
    extends Tw terTextRendererProcessor {

  pr vate[t ] val extractor = new Extractor()

  def process(
    tw terTextRenderer: Tw terTextRenderer
  ): Tw terTextRenderer = {
    val tw terEnt  es = extractor.extractEnt  esW h nd ces(tw terTextRenderer.text)

    tw terEnt  es.foreach { tw terEnt y =>
      tw terTextReferenceObjectBu lder(tw terEnt y).foreach { refObject =>
        tw terTextRenderer.setRefObject(tw terEnt y.getStart, tw terEnt y.getEnd, refObject)
      }
    }
    tw terTextRenderer
  }
}
