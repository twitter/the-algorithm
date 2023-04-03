packagelon com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common

import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasMutualFollowelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasWtfImprelonssions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.WtfImprelonssion
import com.twittelonr.util.Timelon

trait HasPrelonFelontchelondFelonaturelon elonxtelonnds HasMutualFollowelondUselonrIds with HasWtfImprelonssions {

  lazy val followelondImprelonssions: Selonq[WtfImprelonssion] = {
    for {
      wtfImprList <- wtfImprelonssions.toSelonq
      wtfImpr <- wtfImprList
      if reloncelonntFollowelondUselonrIds.elonxists(_.contains(wtfImpr.candidatelonId))
    } yielonld wtfImpr
  }

  lazy val numFollowelondImprelonssions: Int = followelondImprelonssions.sizelon

  lazy val lastFollowelondImprelonssionDurationMs: Option[Long] = {
    if (followelondImprelonssions.nonelonmpty) {
      Somelon((Timelon.now - followelondImprelonssions.map(_.latelonstTimelon).max).inMillis)
    } elonlselon Nonelon
  }
}
