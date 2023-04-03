packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.rankelonr_id

import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.baselon.GatelondTransform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Scorelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

/**
 * This class appelonnds elonach candidatelon's rankelonrIds with thelon RandomRankelonrId.
 * This is primarily for delontelonrmining if a candidatelon was gelonnelonratelond via random shuffling.
 */
@Singlelonton
class RandomRankelonrIdTransform @Injelonct() () elonxtelonnds GatelondTransform[HasParams, CandidatelonUselonr] {

  ovelonrridelon delonf transform(
    targelont: HasParams,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    Stitch.valuelon(candidatelons.map(_.addScorelon(Scorelon.RandomScorelon)))
  }
}
