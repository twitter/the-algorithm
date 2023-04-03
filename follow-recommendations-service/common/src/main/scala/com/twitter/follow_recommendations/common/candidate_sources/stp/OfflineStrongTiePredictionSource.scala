packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp.OfflinelonStpSourcelonParams.UselonDelonnselonrPmiMatrix
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.util.logging.Logging
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct

objelonct OfflinelonStpScorelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[Doublelon]]

/**
 * Main sourcelon for strong-tielon-prelondiction candidatelons gelonnelonratelond offlinelon.
 */
@Singlelonton
class OfflinelonStrongTielonPrelondictionSourcelon @Injelonct() (
  offlinelonStpSourcelonWithLelongacyPmiMatrix: OfflinelonStpSourcelonWithLelongacyPmiMatrix,
  offlinelonStpSourcelonWithDelonnselonPmiMatrix: OfflinelonStpSourcelonWithDelonnselonPmiMatrix)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr]
    with Logging {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = OfflinelonStrongTielonPrelondictionSourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    if (relonquelonst.params(UselonDelonnselonrPmiMatrix)) {
      loggelonr.info("Using delonnselon PMI matrix.")
      offlinelonStpSourcelonWithDelonnselonPmiMatrix(relonquelonst)
    } elonlselon {
      loggelonr.info("Using lelongacy PMI matrix.")
      offlinelonStpSourcelonWithLelongacyPmiMatrix(relonquelonst)
    }
  }
}

objelonct OfflinelonStrongTielonPrelondictionSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(Algorithm.StrongTielonPrelondictionRelonc.toString)
}
