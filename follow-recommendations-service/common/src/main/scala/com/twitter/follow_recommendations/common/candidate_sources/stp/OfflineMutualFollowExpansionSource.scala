packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.MutualFollowelonxpansionClielonntColumn
import javax.injelonct.Injelonct

/**
 * A sourcelon that finds thelon mutual follows of onelon's mutual follows that onelon isn't following alrelonady.
 */
@Singlelonton
class OfflinelonMutualFollowelonxpansionSourcelon @Injelonct() (
  column: MutualFollowelonxpansionClielonntColumn)
    elonxtelonnds OfflinelonStrongTielonPrelondictionBaselonSourcelon(column.felontchelonr) {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    OfflinelonMutualFollowelonxpansionSourcelon.Idelonntifielonr
}

objelonct OfflinelonMutualFollowelonxpansionSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(Algorithm.MutualFollowelonxpansion.toString)
}
