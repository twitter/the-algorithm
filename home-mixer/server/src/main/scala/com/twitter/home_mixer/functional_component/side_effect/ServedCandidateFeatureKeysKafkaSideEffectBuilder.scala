packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class SelonrvelondCandidatelonFelonaturelonKelonysKafkaSidelonelonffelonctBuildelonr @Injelonct() (
  injelonctelondSelonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr) {
  delonf build(
    sourcelonIdelonntifielonrs: Selont[CandidatelonPipelonlinelonIdelonntifielonr]
  ): SelonrvelondCandidatelonFelonaturelonKelonysKafkaSidelonelonffelonct = {
    val topic = injelonctelondSelonrvicelonIdelonntifielonr.elonnvironmelonnt.toLowelonrCaselon match {
      caselon "prod" => "tq_ct_selonrvelond_candidatelon_felonaturelon_kelonys"
      caselon _ => "tq_ct_selonrvelond_candidatelon_felonaturelon_kelonys_staging"
    }
    nelonw SelonrvelondCandidatelonFelonaturelonKelonysKafkaSidelonelonffelonct(topic, sourcelonIdelonntifielonrs)
  }
}
