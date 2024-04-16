package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Ad tadataConta ner
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ad tadataConta nerMarshaller @ nject() (
  sponsorsh pTypeMarshaller: Sponsorsh pTypeMarshaller,
  d scla  rTypeMarshaller: D scla  rTypeMarshaller,
  skAdNetworkDataMarshaller: SkAdNetworkDataMarshaller) {

  def apply(ad tadataConta ner: Ad tadataConta ner): urt.Ad tadataConta ner =
    urt.Ad tadataConta ner(
      removePromotedAttr but onForPreroll = ad tadataConta ner.removePromotedAttr but onForPreroll,
      sponsorsh pCand date = ad tadataConta ner.sponsorsh pCand date,
      sponsorsh pOrgan zat on = ad tadataConta ner.sponsorsh pOrgan zat on,
      sponsorsh pOrgan zat on bs e = ad tadataConta ner.sponsorsh pOrgan zat on bs e,
      sponsorsh pType = ad tadataConta ner.sponsorsh pType.map(sponsorsh pTypeMarshaller(_)),
      d scla  rType = ad tadataConta ner.d scla  rType.map(d scla  rTypeMarshaller(_)),
      skAdNetworkDataL st =
        ad tadataConta ner.skAdNetworkDataL st.map(_.map(skAdNetworkDataMarshaller(_))),
      un f edCardOverr de = ad tadataConta ner.un f edCardOverr de
    )
}
