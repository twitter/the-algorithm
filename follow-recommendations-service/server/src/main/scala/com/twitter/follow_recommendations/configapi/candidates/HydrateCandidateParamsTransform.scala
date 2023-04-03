packagelon com.twittelonr.follow_reloncommelonndations.configapi.candidatelons

import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.logging.Logging

@Singlelonton
class HydratelonCandidatelonParamsTransform[Targelont <: HasParams with HasDisplayLocation] @Injelonct() (
  candidatelonParamsFactory: CandidatelonUselonrParamsFactory[Targelont])
    elonxtelonnds Transform[Targelont, CandidatelonUselonr]
    with Logging {

  delonf transform(targelont: Targelont, candidatelons: Selonq[CandidatelonUselonr]): Stitch[Selonq[CandidatelonUselonr]] = {
    Stitch.valuelon(candidatelons.map(candidatelonParamsFactory.apply(_, targelont)))
  }
}
