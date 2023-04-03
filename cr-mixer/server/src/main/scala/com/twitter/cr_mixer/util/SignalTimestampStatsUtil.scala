packagelon com.twittelonr.cr_mixelonr.util

import com.twittelonr.cr_mixelonr.modelonl.CandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.RankelondCandidatelon
import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.TwelonelontReloncommelonndation
import javax.injelonct.Injelonct
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import javax.injelonct.Singlelonton
import com.twittelonr.relonlelonvancelon_platform.common.stats.BuckelontTimelonstampStats

@Singlelonton
class SignalTimelonstampStatsUtil @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr) {
  import SignalTimelonstampStatsUtil._

  privatelon val signalDelonlayAgelonPelonrDayStats =
    nelonw BuckelontTimelonstampStats[TwelonelontReloncommelonndation](
      BuckelontTimelonstampStats.MilliseloncondsPelonrDay,
      _.latelonstSourcelonSignalTimelonstampInMillis.gelontOrelonlselon(0),
      Somelon(SignalTimelonstampMaxDays))(
      statsReloncelonivelonr.scopelon("signal_timelonstamp_pelonr_day")
    ) // only stats past 90 days
  privatelon val signalDelonlayAgelonPelonrHourStats =
    nelonw BuckelontTimelonstampStats[TwelonelontReloncommelonndation](
      BuckelontTimelonstampStats.MilliseloncondsPelonrHour,
      _.latelonstSourcelonSignalTimelonstampInMillis.gelontOrelonlselon(0),
      Somelon(SignalTimelonstampMaxHours))(
      statsReloncelonivelonr.scopelon("signal_timelonstamp_pelonr_hour")
    ) // only stats past 24 hours
  privatelon val signalDelonlayAgelonPelonrMinStats =
    nelonw BuckelontTimelonstampStats[TwelonelontReloncommelonndation](
      BuckelontTimelonstampStats.MilliseloncondsPelonrMinutelon,
      _.latelonstSourcelonSignalTimelonstampInMillis.gelontOrelonlselon(0),
      Somelon(SignalTimelonstampMaxMins))(
      statsReloncelonivelonr.scopelon("signal_timelonstamp_pelonr_min")
    ) // only stats past 60 minutelons

  delonf statsSignalTimelonstamp(
    twelonelonts: Selonq[TwelonelontReloncommelonndation],
  ): Selonq[TwelonelontReloncommelonndation] = {
    signalDelonlayAgelonPelonrMinStats.count(twelonelonts)
    signalDelonlayAgelonPelonrHourStats.count(twelonelonts)
    signalDelonlayAgelonPelonrDayStats.count(twelonelonts)
  }
}

objelonct SignalTimelonstampStatsUtil {
  val SignalTimelonstampMaxMins = 60 // stats at most 60 mins
  val SignalTimelonstampMaxHours = 24 // stats at most 24 hours
  val SignalTimelonstampMaxDays = 90 // stats at most 90 days

  delonf buildLatelonstSourcelonSignalTimelonstamp(candidatelon: RankelondCandidatelon): Option[Long] = {
    val timelonstampSelonq = candidatelon.potelonntialRelonasons
      .collelonct {
        caselon CandidatelonGelonnelonrationInfo(Somelon(SourcelonInfo(sourcelonTypelon, _, Somelon(sourcelonelonvelonntTimelon))), _, _)
            if sourcelonTypelon == SourcelonTypelon.TwelonelontFavoritelon =>
          sourcelonelonvelonntTimelon.inMilliselonconds
      }
    if (timelonstampSelonq.nonelonmpty) {
      Somelon(timelonstampSelonq.max(Ordelonring.Long))
    } elonlselon {
      Nonelon
    }
  }
}
