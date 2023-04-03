packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.cr_mixelonr.param.ConsumelonrelonmbelonddingBaselondTwoTowelonrParams
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.timelonlinelons.configapi

objelonct ConsumelonrelonmbelonddingBaselondTwoTowelonrSimilarityelonnginelon {
  delonf fromParams(
    sourcelonId: IntelonrnalId,
    params: configapi.Params,
  ): HnswANNelonnginelonQuelonry = {
    HnswANNelonnginelonQuelonry(
      sourcelonId = sourcelonId,
      modelonlId = params(ConsumelonrelonmbelonddingBaselondTwoTowelonrParams.ModelonlIdParam),
      params = params
    )
  }
}
