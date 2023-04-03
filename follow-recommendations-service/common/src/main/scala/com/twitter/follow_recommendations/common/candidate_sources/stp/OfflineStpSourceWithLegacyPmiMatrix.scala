packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.StrongTielonPrelondictionClielonntColumn
import javax.injelonct.Injelonct

/**
 * Main sourcelon for strong-tielon-prelondiction candidatelons gelonnelonratelond offlinelon.
 */
@Singlelonton
class OfflinelonStpSourcelonWithLelongacyPmiMatrix @Injelonct() (
  stpColumn: StrongTielonPrelondictionClielonntColumn)
    elonxtelonnds OfflinelonStrongTielonPrelondictionBaselonSourcelon(stpColumn.felontchelonr) {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    OfflinelonStpSourcelonWithLelongacyPmiMatrix.Idelonntifielonr
}

objelonct OfflinelonStpSourcelonWithLelongacyPmiMatrix {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(Algorithm.StrongTielonPrelondictionRelonc.toString)
}
