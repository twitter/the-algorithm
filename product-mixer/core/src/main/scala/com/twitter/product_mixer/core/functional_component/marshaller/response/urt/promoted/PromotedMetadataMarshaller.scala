package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Promoted tadataMarshaller @ nject() (
  d sclosureTypeMarshaller: D sclosureTypeMarshaller,
  ad tadataConta nerMarshaller: Ad tadataConta nerMarshaller,
  cl ckTrack ng nfoMarshaller: Cl ckTrack ng nfoMarshaller) {

  /** See com nts on [[com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata]]
   * regard ng  mpress on d and  mpress onStr ng
   *
   * TL;DR t  doma n model only has  mpress onStr ng (t  ne r vers on) an t  marshaller sets both
   *  mpress on d (t  older) and  mpress onStr ng based on   for compat b l y.
   * */
  def apply(promoted tadata: Promoted tadata): urt.Promoted tadata =
    urt.Promoted tadata(
      advert ser d = promoted tadata.advert ser d,
       mpress on d = promoted tadata. mpress onStr ng,
      d sclosureType = promoted tadata.d sclosureType.map(d sclosureTypeMarshaller(_)),
      exper  ntValues = promoted tadata.exper  ntValues,
      promotedTrend d = promoted tadata.promotedTrend d,
      promotedTrendNa  = promoted tadata.promotedTrendNa ,
      promotedTrendQueryTerm = promoted tadata.promotedTrendQueryTerm,
      ad tadataConta ner =
        promoted tadata.ad tadataConta ner.map(ad tadataConta nerMarshaller(_)),
      promotedTrendDescr pt on = promoted tadata.promotedTrendDescr pt on,
       mpress onStr ng = promoted tadata. mpress onStr ng,
      cl ckTrack ng nfo = promoted tadata.cl ckTrack ng nfo.map(cl ckTrack ng nfoMarshaller(_))
    )
}
