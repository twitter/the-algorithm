packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.reloncommelonndation_flow_idelonntifielonr

import com.googlelon.injelonct.Injelonct
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncommelonndationFlowIdelonntifielonr
import com.twittelonr.stitch.Stitch

class AddReloncommelonndationFlowIdelonntifielonrTransform @Injelonct()
    elonxtelonnds Transform[HasReloncommelonndationFlowIdelonntifielonr, CandidatelonUselonr] {

  ovelonrridelon delonf transform(
    targelont: HasReloncommelonndationFlowIdelonntifielonr,
    itelonms: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    Stitch.valuelon(itelonms.map { candidatelonUselonr =>
      candidatelonUselonr.copy(reloncommelonndationFlowIdelonntifielonr = targelont.reloncommelonndationFlowIdelonntifielonr)
    })
  }
}
