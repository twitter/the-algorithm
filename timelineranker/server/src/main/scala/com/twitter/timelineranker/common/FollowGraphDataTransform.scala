packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.visibility.FollowGraphDataProvidelonr
import com.twittelonr.util.Futurelon

class FollowGraphDataTransform(
  followGraphDataProvidelonr: FollowGraphDataProvidelonr,
  maxFollowelondUselonrsProvidelonr: DelonpelonndelonncyProvidelonr[Int])
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {

    val followGraphData = followGraphDataProvidelonr.gelontAsync(
      elonnvelonlopelon.quelonry.uselonrId,
      maxFollowelondUselonrsProvidelonr(elonnvelonlopelon.quelonry)
    )

    Futurelon.valuelon(elonnvelonlopelon.copy(followGraphData = followGraphData))
  }
}
