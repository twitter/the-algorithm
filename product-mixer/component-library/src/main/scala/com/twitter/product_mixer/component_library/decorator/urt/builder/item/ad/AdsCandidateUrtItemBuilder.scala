package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.ad

 mport com.tw ter.ads.adserver.{thr ftscala => ads}
 mport com.tw ter.adserver.{thr ftscala => adserver}
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.contextual_ref.ContextualT etRefBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder.T etCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsT etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T etD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Ad tadataConta ner
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Ampl fy
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.CallToAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Cl ckTrack ng nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.DcmUrlOverr deType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D rectSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  r ssue
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  rPol  cal
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  rType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D sclosureType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Dynam cPrerollType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Earned
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. nd rectSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. ssue
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.L veTvEvent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Marketplace
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. d a nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.NoD sclosure
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.NoSponsorsh pSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Pol  cal
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.SkAdNetworkData
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Sponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.UnknownUrlOverr deType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.V deoVar ant
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.ut l.Ad tadataConta nerSer al zer
 mport com.tw ter.t  l nes.ut l.Preroll tadataSer al zer

/**
 * [[AdsCand dateUrt emBu lder]] takes a [[AdsCand date]] (w h a [[Query]] as add  onal context)
 * and converts    nto t  Product M xer URT representat on, or throws an error.
 *
 * Currently, t  only supported form for URT representat on of t  [[AdsCand date]]  s a [[T et]],
 * but  n t  future   could be expanded to handle ot r forms of ads.
 *
 * @param t etCl entEvent nfoBu lder Opt onally, prov de a Cl entEvent nfoBu lder for T ets
 *                                    that g ven an AdsT etCand date and ele nt of "t et".
 * @param t etD splayType Should be [[Emphas zedPromotedT et]] on Prof le t  l nes,
 *                         ot rw se [[T et]]
 */
case class AdsCand dateUrt emBu lder[Query <: P pel neQuery](
  t etCl entEvent nfoBu lder: Opt on[BaseCl entEvent nfoBu lder[Query, AdsT etCand date]] = None,
  contextualT etRefBu lder: Opt on[ContextualT etRefBu lder[AdsT etCand date]] = None,
  t etD splayType: T etD splayType = T et)
    extends Cand dateUrtEntryBu lder[Query, AdsCand date, T  l ne em] {

  overr de def apply(
    p pel neQuery: Query,
    cand date: AdsCand date,
    cand dateFeatures: FeatureMap
  ): T  l ne em = {
    cand date match {
      case t etCand date: AdsT etCand date =>
        T et em(
           d = t etCand date. d,
          entryNa space = T et em.PromotedT etEntryNa space,
          sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
          cl entEvent nfo = t etCl entEvent nfoBu lder.flatMap(
            _.apply(
              p pel neQuery,
              t etCand date,
              cand dateFeatures,
              So (T etCl entEvent nfoEle nt))),
          feedbackAct on nfo = None,
           sP nned = None,
          entry dToReplace = None,
          soc alContext = None,
          h ghl ghts = None,
           nnerTombstone nfo = None,
          t  l nesScore nfo = None,
          hasModeratedRepl es = None,
          forwardP vot = None,
           nnerForwardP vot = None,
          conversat onAnnotat on = None,
          promoted tadata = So (promoted tadata(t etCand date.ad mpress on)),
          d splayType = t etD splayType,
          contextualT etRef = contextualT etRefBu lder.flatMap(_.apply(t etCand date)),
          preroll tadata = preroll tadata(t etCand date.ad mpress on),
          replyBadge = None,
          dest nat on = None
        )
    }
  }

  pr vate def promoted tadata( mpress on: adserver.Ad mpress on) = {
    Promoted tadata(
      advert ser d =  mpress on.advert ser d,
       mpress onStr ng =  mpress on. mpress onStr ng,
      d sclosureType =  mpress on.d sclosureType.map(convertD sclosureType),
      exper  ntValues =  mpress on.exper  ntValues.map(_.toMap),
      promotedTrend d =  mpress on.promotedTrend d.map(_.toLong),
      promotedTrendNa  =  mpress on.promotedTrendNa ,
      promotedTrendQueryTerm =  mpress on.promotedTrendQueryTerm,
      promotedTrendDescr pt on =  mpress on.promotedTrendDescr pt on,
      cl ckTrack ng nfo =  mpress on.cl ckTrack ng nfo.map(convertCl ckTrack ng nfo),
      ad tadataConta ner = ad tadataConta ner( mpress on)
    )
  }

  pr vate def convertD sclosureType(
    d sclosureType: adserver.D sclosureType
  ): D sclosureType = d sclosureType match {
    case adserver.D sclosureType.None => NoD sclosure
    case adserver.D sclosureType.Pol  cal => Pol  cal
    case adserver.D sclosureType.Earned => Earned
    case adserver.D sclosureType. ssue =>  ssue
    case _ => throw new UnsupportedD sclosureTypeExcept on(d sclosureType)
  }

  pr vate def convertCl ckTrack ng nfo(
    cl ckTrack ng: adserver.Cl ckTrack ng nfo
  ): Cl ckTrack ng nfo = Cl ckTrack ng nfo(
    urlParams = cl ckTrack ng.urlParams.getOrElse(Map.empty),
    urlOverr de = cl ckTrack ng.urlOverr de,
    urlOverr deType = cl ckTrack ng.urlOverr deType.map {
      case adserver.UrlOverr deType.Unknown => UnknownUrlOverr deType
      case adserver.UrlOverr deType.Dcm => DcmUrlOverr deType
      case _ => throw new UnsupportedCl ckTrack ng nfoExcept on(cl ckTrack ng)
    }
  )

  pr vate def preroll tadata(ad mpress on: adserver.Ad mpress on): Opt on[Preroll tadata] = {
    ad mpress on.ser al zedPreroll tadata
      .flatMap(Preroll tadataSer al zer.deser al ze).map {  tadata =>
        Preroll tadata(
           tadata.preroll.map(convertPreroll),
           tadata.v deoAnalyt csScr bePassthrough
        )
      }
  }

  pr vate def ad tadataConta ner(
    ad mpress on: adserver.Ad mpress on
  ): Opt on[Ad tadataConta ner] = {
    ad mpress on.ser al zedAd tadataConta ner
      .flatMap(Ad tadataConta nerSer al zer.deser al ze).map { conta ner =>
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
  }

  pr vate def convertSponsorsh pType(
    sponsorsh pType: ads.Sponsorsh pType
  ): Sponsorsh pType = sponsorsh pType match {
    case ads.Sponsorsh pType.D rect => D rectSponsorsh pType
    case ads.Sponsorsh pType. nd rect =>  nd rectSponsorsh pType
    case ads.Sponsorsh pType.NoSponsorsh p => NoSponsorsh pSponsorsh pType
    // Thr ft has extras (e.g. Sponsorsh p4) that are not used  n pract ce
    case _ => throw new UnsupportedSponsorsh pTypeExcept on(sponsorsh pType)
  }

  pr vate def convertD scla  rType(
    d scla  rType: ads.D scla  rType
  ): D scla  rType = d scla  rType match {
    case ads.D scla  rType.Pol  cal => D scla  rPol  cal
    case ads.D scla  rType. ssue => D scla  r ssue
    case _ => throw new UnsupportedD scla  rTypeExcept on(d scla  rType)
  }

  pr vate def convertDynam cPrerollType(
    dynam cPrerollType: ads.Dynam cPrerollType
  ): Dynam cPrerollType =
    dynam cPrerollType match {
      case ads.Dynam cPrerollType.Ampl fy => Ampl fy
      case ads.Dynam cPrerollType.Marketplace => Marketplace
      case ads.Dynam cPrerollType.L veTvEvent => L veTvEvent
      case _ => throw new UnsupportedDynam cPrerollTypeExcept on(dynam cPrerollType)
    }

  pr vate def convert d a nfo( d a nfo: ads. d a nfo):  d a nfo = {
     d a nfo(
      uu d =  d a nfo.uu d,
      publ s r d =  d a nfo.publ s r d,
      callToAct on =  d a nfo.callToAct on.map(convertCallToAct on),
      durat onM ll s =  d a nfo.durat onM ll s,
      v deoVar ants =  d a nfo.v deoVar ants.map(convertV deoVar ants),
      advert serNa  =  d a nfo.advert serNa ,
      renderAdByAdvert serNa  =  d a nfo.renderAdByAdvert serNa ,
      advert serProf le mageUrl =  d a nfo.advert serProf le mageUrl
    )
  }

  pr vate def convertV deoVar ants(v deoVar ants: Seq[ads.V deoVar ant]): Seq[V deoVar ant] = {
    v deoVar ants.map(v deoVar ant =>
      V deoVar ant(
        url = v deoVar ant.url,
        contentType = v deoVar ant.contentType,
        b rate = v deoVar ant.b rate
      ))
  }

  pr vate def convertCallToAct on(callToAct on: ads.CallToAct on): CallToAct on = {
    CallToAct on(
      callToAct onType = callToAct on.callToAct onType,
      url = callToAct on.url
    )
  }

  pr vate def convertPreroll(
    preroll: ads.Preroll
  ): Preroll = {
    Preroll(
      preroll.preroll d,
      preroll.dynam cPrerollType.map(convertDynam cPrerollType),
      preroll. d a nfo.map(convert d a nfo)
    )
  }

  pr vate def convertSkAdNetworkDataL st(
    skAdNetworkDataL st: Seq[ads.SkAdNetworkData]
  ): Seq[SkAdNetworkData] = skAdNetworkDataL st.map(sdAdNetwork =>
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
    ))
}

class UnsupportedCl ckTrack ng nfoExcept on(cl ckTrack ng nfo: adserver.Cl ckTrack ng nfo)
    extends UnsupportedOperat onExcept on(
      s"Unsupported Cl ckTrack ng nfo: $cl ckTrack ng nfo"
    )

class UnsupportedD scla  rTypeExcept on(d scla  rType: ads.D scla  rType)
    extends UnsupportedOperat onExcept on(
      s"Unsupported D scla  rType: $d scla  rType"
    )

class UnsupportedD sclosureTypeExcept on(d sclosureType: adserver.D sclosureType)
    extends UnsupportedOperat onExcept on(
      s"Unsupported D sclosureType: $d sclosureType"
    )

class UnsupportedDynam cPrerollTypeExcept on(dynam cPrerollType: ads.Dynam cPrerollType)
    extends UnsupportedOperat onExcept on(
      s"Unsupported Dynam cPrerollType: $dynam cPrerollType"
    )

class UnsupportedSponsorsh pTypeExcept on(sponsorsh pType: ads.Sponsorsh pType)
    extends UnsupportedOperat onExcept on(
      s"Unsupported Sponsorsh pType: $sponsorsh pType"
    )
