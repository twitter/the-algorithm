packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.helonrmit

import com.twittelonr.helonrmit.thriftscala.ReloncommelonndationRelonquelonst
import com.twittelonr.helonrmit.thriftscala.ReloncommelonndationRelonsponselon
import com.twittelonr.helonrmit.thriftscala.RelonlatelondUselonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.HelonrmitReloncommelonndUselonrsClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrsSimilarToMelonCandidatelonSourcelon @Injelonct() (
  column: HelonrmitReloncommelonndUselonrsClielonntColumn)
    elonxtelonnds StratoKelonyVielonwFelontchelonrSourcelon[
      Long,
      ReloncommelonndationRelonquelonst,
      ReloncommelonndationRelonsponselon,
      RelonlatelondUselonr
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("UselonrsSimilarToMelon")

  ovelonrridelon val felontchelonr: Felontchelonr[Long, ReloncommelonndationRelonquelonst, ReloncommelonndationRelonsponselon] =
    column.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoKelony: Long,
    relonsult: ReloncommelonndationRelonsponselon
  ): Selonq[RelonlatelondUselonr] = relonsult.suggelonstions.gelontOrelonlselon(Selonq.elonmpty).filtelonr(_.id.isDelonfinelond)
}
