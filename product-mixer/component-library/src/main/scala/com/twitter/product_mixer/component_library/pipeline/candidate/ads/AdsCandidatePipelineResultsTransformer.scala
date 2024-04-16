package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.thr ftscala.Ad mpress on
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult

object AdsCand dateP pel neResultsTransfor r
    extends Cand dateP pel neResultsTransfor r[Ad mpress on, AdsCand date] {

  overr de def transform(s ceResult: Ad mpress on): AdsCand date =
    (s ceResult.nat veRtbCreat ve, s ceResult.promotedT et d) match {
      case (None, So (promotedT et d)) =>
        AdsT etCand date(
           d = promotedT et d,
          ad mpress on = s ceResult
        )
      case (So (_), None) =>
        throw unsupportedAd mpress onP pel neFa lure(
           mpress on = s ceResult,
          reason = "Rece ved ad  mpress on w h rtbCreat ve")
      case (So (_), So (_)) =>
        throw unsupportedAd mpress onP pel neFa lure(
           mpress on = s ceResult,
          reason = "Rece ved ad  mpress on w h both rtbCreat ve and promoted t et d")
      case (None, None) =>
        throw unsupportedAd mpress onP pel neFa lure(
           mpress on = s ceResult,
          reason = "Rece ved ad  mpress on w h ne  r rtbCreat ve nor promoted t et d")
    }

  pr vate def unsupportedAd mpress onP pel neFa lure( mpress on: Ad mpress on, reason: Str ng) =
    P pel neFa lure(
      UnexpectedCand dateResult,
      reason =
        s"Unsupported Ad mpress on ($reason).  mpress onStr ng: ${ mpress on. mpress onStr ng}")
}
