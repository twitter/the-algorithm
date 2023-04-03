packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OnlinelonSTPSourcelonScorelonr @Injelonct() (
  onlinelonSTPSourcelonWithelonPScorelonr: OnlinelonSTPSourcelonWithelonPScorelonr)
    elonxtelonnds CandidatelonSourcelon[
      HasClielonntContelonxt with HasParams with HasReloncelonntFollowelondUselonrIds,
      CandidatelonUselonr
    ] {

  ovelonrridelon delonf apply(
    relonquelonst: HasClielonntContelonxt with HasParams with HasReloncelonntFollowelondUselonrIds
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    onlinelonSTPSourcelonWithelonPScorelonr(relonquelonst)
  }

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = BaselonOnlinelonSTPSourcelon.Idelonntifielonr
}
