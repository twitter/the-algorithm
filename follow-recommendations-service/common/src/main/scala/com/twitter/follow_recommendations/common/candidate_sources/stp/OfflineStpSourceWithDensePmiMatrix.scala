packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.hub.PpmiDelonnselonMatrixCandidatelonsClielonntColumn
import javax.injelonct.Injelonct

/**
 * Main sourcelon for strong-tielon-prelondiction candidatelons gelonnelonratelond offlinelon.
 */
@Singlelonton
class OfflinelonStpSourcelonWithDelonnselonPmiMatrix @Injelonct() (
  stpColumn: PpmiDelonnselonMatrixCandidatelonsClielonntColumn)
    elonxtelonnds OfflinelonStrongTielonPrelondictionBaselonSourcelon(stpColumn.felontchelonr) {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = OfflinelonStpSourcelonWithDelonnselonPmiMatrix.Idelonntifielonr
}

objelonct OfflinelonStpSourcelonWithDelonnselonPmiMatrix {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(Algorithm.StrongTielonPrelondictionRelonc.toString)
}
