packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.reloncommelonndations

import com.twittelonr.follow_reloncommelonndations.{thriftscala => fr}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.follow_reloncommelonndations_selonrvicelon.GelontReloncommelonndationsClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Relonturns a list of FollowReloncommelonndations as [[fr.UselonrReloncommelonndation]]s felontchelond from Strato
 */
@Singlelonton
class UselonrFollowReloncommelonndationsCandidatelonSourcelon @Injelonct() (
  gelontReloncommelonndationsClielonntColumn: GelontReloncommelonndationsClielonntColumn)
    elonxtelonnds StratoKelonyVielonwFelontchelonrSourcelon[
      fr.ReloncommelonndationRelonquelonst,
      Unit,
      fr.ReloncommelonndationRelonsponselon,
      fr.UselonrReloncommelonndation
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "FollowReloncommelonndationsSelonrvicelon")

  ovelonrridelon val felontchelonr: Felontchelonr[fr.ReloncommelonndationRelonquelonst, Unit, fr.ReloncommelonndationRelonsponselon] =
    gelontReloncommelonndationsClielonntColumn.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoKelony: fr.ReloncommelonndationRelonquelonst,
    stratoRelonsult: fr.ReloncommelonndationRelonsponselon
  ): Selonq[fr.UselonrReloncommelonndation] = {
    stratoRelonsult.reloncommelonndations.map {
      caselon fr.Reloncommelonndation.Uselonr(uselonrRelonc: fr.UselonrReloncommelonndation) =>
        uselonrRelonc
      caselon _ =>
        throw nelonw elonxcelonption("Invalid reloncommelonndation typelon relonturnelond from FRS")
    }
  }
}
