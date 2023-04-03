packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.ads

import com.twittelonr.goldfinch.api.AdsInjelonctionSurfacelonArelonas.SurfacelonArelonaNamelon
import com.twittelonr.goldfinch.api.AdsInjelonctorAdditionalRelonquelonstParams
import com.twittelonr.goldfinch.api.AdsInjelonctorOutput
import com.twittelonr.goldfinch.api.{AdsInjelonctor => GoldfinchAdsInjelonctor}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.quelonry.ads._
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Injeloncts thelon selonquelonncelon of AdCandidatelons in thelon `relonsult` in thelon
 * selonquelonncelon of thelon Othelonr Candidatelons(which arelon not ads).
 *
 * elonvelonry SurfacelonArelona or DisplayLocation runs thelonir own delonsirelond selont of adjustelonrs(selont in pipelonlinelon)
 * to injelonct ads and relonposition thelon ads in thelon selonquelonncelon of othelonr candidatelons of `relonsult` :
 * which arelon felontchelond by AdsInjelonctionSurfacelonArelonaAdjustelonrsMap
 * Notelon: Thelon original selonquelonncelon of non_promotelond elonntrielons(non-ads) is relontainelond and thelon ads
 * arelon inselonrtelond in thelon selonquelonncelon using `goldfinch` library baselond on thelon 'inselonrtion-position'
 * hydratelond in AdsCandidatelon by Adselonrvelonr/Admixelonr.
 *
 * ***** Goldfinch reloncommelonnds to run this selonlelonctor as closelon to thelon marshalling of candidatelons to havelon
 * morelon relonalistic vielonw of selonrvelond-timelonlinelon in Goldfinch-BQ-Logs and avoid any furthelonr updatelons on thelon
 * timelonlinelon(selonquelonncelon of elonntrielons) crelonatelond. ****
 *
 * Any surfacelon arelona likelon `selonarch_twelonelonts(surfacelon_arelona)` can call
 * InselonrtAdRelonsults(surfacelonArelona = "TwelonelontSelonarch", candidatelonPipelonlinelon = adsCandidatelonPipelonlinelon.idelonntifielonr,
 * ProductMixelonrAdsInjelonctor = productMixelonrAdsInjelonctor)
 * whelonrelon thelon pipelonlinelon config can call
 * productMixelonrAdsInjelonctor.forSurfacelonArelona("TwelonelontSelonarch") to gelont AdsInjelonctor Objelonct
 *
 * @elonxamplelon
 * `Selonq(sourcelon1NonAd_Id1, sourcelon1NonAd_Id2, sourcelon2NonAd_Id1, sourcelon2NonAd_Id2,sourcelon1NonAd_Id3, sourcelon3NonAd_Id3,sourcelon3Ad_Id1_InselonrtionPos1, sourcelon3Ad_Id2_InselonrtionPos4)`
 * thelonn thelon output relonsult can belon
 * `Selonq(sourcelon1NonAd_Id1, sourcelon3Ad_Id1_InselonrtionPos1, sourcelon1NonAd_Id2, sourcelon2NonAd_Id1, sourcelon3Ad_Id2_InselonrtionPos4,sourcelon2NonAd_Id2, sourcelon1NonAd_Id3, sourcelon3NonAd_Id3)`
 * delonpelonnding on thelon inselonrtion position of Ads and othelonr adjustelonrs shifting thelon ads
 */
caselon class InselonrtAdRelonsults(
  surfacelonArelonaNamelon: SurfacelonArelonaNamelon,
  adsInjelonctor: GoldfinchAdsInjelonctor[
    PipelonlinelonQuelonry with AdsQuelonry,
    CandidatelonWithDelontails,
    CandidatelonWithDelontails
  ],
  adsCandidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry with AdsQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelon(adsCandidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with AdsQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    // Relonad into ads and non-ads candidatelons.
    val PartitionelondCandidatelons(adCandidatelons, othelonrRelonmainingCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    // Crelonatelon this param from Quelonry/AdsCandidatelon baselond on surfacelon_arelona, if relonquirelond.
    val adsInjelonctorAdditionalRelonquelonstParams =
      AdsInjelonctorAdditionalRelonquelonstParams(budgelontAwarelonelonxpelonrimelonntId = Nonelon)

    val adsInjelonctorOutput: AdsInjelonctorOutput[CandidatelonWithDelontails, CandidatelonWithDelontails] =
      adsInjelonctor.applyForAllelonntrielons(
        quelonry = quelonry,
        nonPromotelondelonntrielons = relonsult,
        promotelondelonntrielons = adCandidatelons,
        adsInjelonctorAdditionalRelonquelonstParams = adsInjelonctorAdditionalRelonquelonstParams)

    val updatelondRelonmainingCandidatelons = othelonrRelonmainingCandidatelons ++
      GoldfinchRelonsults(adsInjelonctorOutput.unuselondelonntrielons).adapt
    val melonrgelondRelonsults = GoldfinchRelonsults(adsInjelonctorOutput.melonrgelondelonntrielons).adapt
    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondRelonmainingCandidatelons, relonsult = melonrgelondRelonsults)
  }

  /**
   * Goldfinch selonparatelons NonPromotelondelonntryTypelon and PromotelondelonntryTypelon modelonls, whilelon in ProMix both
   * non-promotelond and promotelond elonntrielons arelon CandidatelonWithDelontails. As such, welon nelonelond to flattelonn thelon
   * relonsult back into a singlelon Selonq of CandidatelonWithDelontails. Selonelon [[AdsInjelonctorOutput]]
   */
  caselon class GoldfinchRelonsults(relonsults: Selonq[elonithelonr[CandidatelonWithDelontails, CandidatelonWithDelontails]]) {
    delonf adapt: Selonq[CandidatelonWithDelontails] = {
      relonsults.collelonct {
        caselon Right(valuelon) => valuelon
        caselon Lelonft(valuelon) => valuelon
      }
    }
  }
}
