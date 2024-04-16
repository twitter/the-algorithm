package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.promoted

 mport com.tw ter.ads.adserver.{thr ftscala => ads}
 mport com.tw ter.ads.common.base.{thr ftscala => ac}
 mport com.tw ter.adserver.{thr ftscala => ad}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.promoted.BasePromoted tadataBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.ut l.Ad tadataConta nerSer al zer

case class FeaturePromoted tadataBu lder(ad mpress onFeature: Feature[_, Opt on[ad.Ad mpress on]])
    extends BasePromoted tadataBu lder[P pel neQuery, Un versalNoun[Any]] {

  def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[Promoted tadata] = {
    cand dateFeatures.getOrElse(ad mpress onFeature, None).map {  mpress on =>
      Promoted tadata(
        advert ser d =  mpress on.advert ser d,
        d sclosureType =  mpress on.d sclosureType.map(convertD sclosureType),
        exper  ntValues =  mpress on.exper  ntValues.map(_.toMap),
        promotedTrend d =  mpress on.promotedTrend d.map(_.toLong),
        promotedTrendNa  =  mpress on.promotedTrendNa ,
        promotedTrendQueryTerm =  mpress on.promotedTrendQueryTerm,
        ad tadataConta ner =
           mpress on.ser al zedAd tadataConta ner.flatMap(convertAd tadataConta ner),
        promotedTrendDescr pt on =  mpress on.promotedTrendDescr pt on,
         mpress onStr ng =  mpress on. mpress onStr ng,
        cl ckTrack ng nfo =  mpress on.cl ckTrack ng nfo.map(convertCl ckTrack ng nfo),
      )
    }
  }

  pr vate def convertAd tadataConta ner(
    ser al zedAd tadataConta ner: ac.Ser al zedThr ft
  ): Opt on[Ad tadataConta ner] =
    Ad tadataConta nerSer al zer.deser al ze(ser al zedAd tadataConta ner).map { conta ner =>
      Ad tadataConta ner(
        removePromotedAttr but onForPreroll = conta ner.removePromotedAttr but onForPreroll,
        sponsorsh pCand date = conta ner.sponsorsh pCand date,
        sponsorsh pOrgan zat on = conta ner.sponsorsh pOrgan zat on,
        sponsorsh pOrgan zat on bs e = conta ner.sponsorsh pOrgan zat on bs e,
        sponsorsh pType = conta ner.sponsorsh pType.map(convertSponsorsh pType),
        d scla  rType = conta ner.d scla  rType.map(convertD scla  rType),
        skAdNetworkDataL st = conta ner.skAdNetworkDataL st.map(convertSkAdNetworkDataL st),
        un f edCardOverr de = conta ner.un f edCardOverr de
      )
    }

  pr vate def convertD sclosureType(d sclosureType: ad.D sclosureType): D sclosureType =
    d sclosureType match {
      case ad.D sclosureType.None => NoD sclosure
      case ad.D sclosureType.Pol  cal => Pol  cal
      case ad.D sclosureType.Earned => Earned
      case ad.D sclosureType. ssue =>  ssue
      case _ => throw new UnsupportedOperat onExcept on(s"Unsupported: $d sclosureType")
    }

  pr vate def convertSponsorsh pType(sponsorsh pType: ads.Sponsorsh pType): Sponsorsh pType =
    sponsorsh pType match {
      case ads.Sponsorsh pType.D rect => D rectSponsorsh pType
      case ads.Sponsorsh pType. nd rect =>  nd rectSponsorsh pType
      case ads.Sponsorsh pType.NoSponsorsh p => NoSponsorsh pSponsorsh pType
      case _ => throw new UnsupportedOperat onExcept on(s"Unsupported: $sponsorsh pType")
    }

  pr vate def convertD scla  rType(d scla  rType: ads.D scla  rType): D scla  rType =
    d scla  rType match {
      case ads.D scla  rType.Pol  cal => D scla  rPol  cal
      case ads.D scla  rType. ssue => D scla  r ssue
      case _ => throw new UnsupportedOperat onExcept on(s"Unsupported: $d scla  rType")
    }

  pr vate def convertSkAdNetworkDataL st(
    skAdNetworkDataL st: Seq[ads.SkAdNetworkData]
  ): Seq[SkAdNetworkData] = skAdNetworkDataL st.map { sdAdNetwork =>
    SkAdNetworkData(
      vers on = sdAdNetwork.vers on,
      srcApp d = sdAdNetwork.srcApp d,
      dstApp d = sdAdNetwork.dstApp d,
      adNetwork d = sdAdNetwork.adNetwork d,
      campa gn d = sdAdNetwork.campa gn d,
       mpress onT   nM ll s = sdAdNetwork. mpress onT   nM ll s,
      nonce = sdAdNetwork.nonce,
      s gnature = sdAdNetwork.s gnature,
      f del yType = sdAdNetwork.f del yType
    )
  }

  pr vate def convertCl ckTrack ng nfo(cl ckTrack ng: ad.Cl ckTrack ng nfo): Cl ckTrack ng nfo =
    Cl ckTrack ng nfo(
      urlParams = cl ckTrack ng.urlParams.getOrElse(Map.empty),
      urlOverr de = cl ckTrack ng.urlOverr de,
      urlOverr deType = cl ckTrack ng.urlOverr deType.map {
        case ad.UrlOverr deType.Unknown => UnknownUrlOverr deType
        case ad.UrlOverr deType.Dcm => DcmUrlOverr deType
        case ad.UrlOverr deType.EnumUnknownUrlOverr deType(value) =>
          throw new UnsupportedOperat onExcept on(s"Unsupported: $value")
      }
    )
}
