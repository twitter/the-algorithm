packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.ads

import com.twittelonr.adselonrvelonr.thriftscala.AdImprelonssion
import com.twittelonr.adselonrvelonr.thriftscala.AdRelonquelonstParams
import com.twittelonr.adselonrvelonr.thriftscala.AdRelonquelonstRelonsponselon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.ads.admixelonr.MakelonAdRelonquelonstStagingClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AdsStagingCandidatelonSourcelon @Injelonct() (adsClielonnt: MakelonAdRelonquelonstStagingClielonntColumn)
    elonxtelonnds StratoKelonyFelontchelonrSourcelon[
      AdRelonquelonstParams,
      AdRelonquelonstRelonsponselon,
      AdImprelonssion
    ] {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("AdsStaging")

  ovelonrridelon val felontchelonr: Felontchelonr[AdRelonquelonstParams, Unit, AdRelonquelonstRelonsponselon] = adsClielonnt.felontchelonr

  ovelonrridelon protelonctelond delonf stratoRelonsultTransformelonr(
    stratoRelonsult: AdRelonquelonstRelonsponselon
  ): Selonq[AdImprelonssion] =
    stratoRelonsult.imprelonssions
}
