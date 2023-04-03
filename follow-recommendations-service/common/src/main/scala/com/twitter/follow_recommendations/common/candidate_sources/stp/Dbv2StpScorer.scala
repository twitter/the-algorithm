packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.cortelonx.delonelonpbird.runtimelon.prelondiction_elonnginelon.TelonnsorflowPrelondictionelonnginelon
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.ml.api.Felonaturelon.Continuous
import com.twittelonr.ml.api.util.SRichDataReloncord
import com.twittelonr.ml.prelondiction_selonrvicelon.PrelondictionRelonquelonst
import com.twittelonr.stitch.Stitch
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.STPReloncord
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.STPReloncordAdaptelonr
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

/**
 * STP ML rankelonr trainelond using DelonelonpBirdV2
 */
@Singlelonton
class Dbv2StpScorelonr @Injelonct() (
  @Namelond(GuicelonNamelondConstants.STP_DBV2_SCORelonR) tfPrelondictionelonnginelon: TelonnsorflowPrelondictionelonnginelon) {
  delonf gelontScorelondRelonsponselon(reloncord: STPReloncord): Stitch[Option[Doublelon]] = {
    val relonquelonst: PrelondictionRelonquelonst = nelonw PrelondictionRelonquelonst(
      STPReloncordAdaptelonr.adaptToDataReloncord(reloncord))
    val relonsponselonStitch = Stitch.callFuturelon(tfPrelondictionelonnginelon.gelontPrelondiction(relonquelonst))
    relonsponselonStitch.map { relonsponselon =>
      val richDr = SRichDataReloncord(relonsponselon.gelontPrelondiction)
      richDr.gelontFelonaturelonValuelonOpt(nelonw Continuous("output"))
    }
  }
}
