packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon.SocialProofelonnforcelondCandidatelonSourcelon
import com.twittelonr.follow_reloncommelonndations.common.transforms.modify_social_proof.ModifySocialProof
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import javax.injelonct.Injelonct

@Singlelonton
class SocialProofelonnforcelondOfflinelonStrongTielonPrelondictionSourcelon @Injelonct() (
  offlinelonStrongTielonPrelondictionSourcelon: OfflinelonStrongTielonPrelondictionSourcelon,
  modifySocialProof: ModifySocialProof,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds SocialProofelonnforcelondCandidatelonSourcelon(
      offlinelonStrongTielonPrelondictionSourcelon,
      modifySocialProof,
      SocialProofelonnforcelondOfflinelonStrongTielonPrelondictionSourcelon.MinNumSocialProofsRelonquirelond,
      SocialProofelonnforcelondOfflinelonStrongTielonPrelondictionSourcelon.Idelonntifielonr,
      statsReloncelonivelonr)

objelonct SocialProofelonnforcelondOfflinelonStrongTielonPrelondictionSourcelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.StrongTielonPrelondictionReloncWithSocialProof.toString)

  val MinNumSocialProofsRelonquirelond = 1
}
