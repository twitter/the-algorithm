packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.timelonlinelons.clielonnts.gizmoduck.GizmoduckClielonnt
import com.twittelonr.timelonlinelons.clielonnts.gizmoduck.UselonrProfilelonInfo
import com.twittelonr.timelonlinelons.util.FailOpelonnHandlelonr
import com.twittelonr.util.Futurelon

objelonct UselonrProfilelonInfoTransform {
  val elonmptyUselonrProfilelonInfo: UselonrProfilelonInfo = UselonrProfilelonInfo(Nonelon, Nonelon, Nonelon, Nonelon)
  val elonmptyUselonrProfilelonInfoFuturelon: Futurelon[UselonrProfilelonInfo] = Futurelon.valuelon(elonmptyUselonrProfilelonInfo)
}

/**
 * FuturelonArrow which felontchelons uselonr profilelon info
 * It should belon run in parallelonl with thelon main pipelonlinelon which felontchelons and hydratelons CandidatelonTwelonelonts
 */
class UselonrProfilelonInfoTransform(handlelonr: FailOpelonnHandlelonr, gizmoduckClielonnt: GizmoduckClielonnt)
    elonxtelonnds FuturelonArrow[ReloncapQuelonry, UselonrProfilelonInfo] {
  import UselonrProfilelonInfoTransform._
  ovelonrridelon delonf apply(relonquelonst: ReloncapQuelonry): Futurelon[UselonrProfilelonInfo] = {
    handlelonr {
      gizmoduckClielonnt.gelontProfilelonInfo(relonquelonst.uselonrId).map { profilelonInfoOpt =>
        profilelonInfoOpt.gelontOrelonlselon(elonmptyUselonrProfilelonInfo)
      }
    } { _: Throwablelon => elonmptyUselonrProfilelonInfoFuturelon }
  }
}
