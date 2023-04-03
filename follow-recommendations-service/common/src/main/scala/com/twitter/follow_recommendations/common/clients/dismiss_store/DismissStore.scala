packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.dismiss_storelon

import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.onboarding.relonlelonvancelon.storelon.thriftscala.WhoToFollowDismisselonvelonntDelontails
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.catalog.Scan.Slicelon
import com.twittelonr.strato.clielonnt.Scannelonr
import com.twittelonr.util.logging.Logging
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

/**
 * this storelon gelonts thelon list of dismisselond candidatelons sincelon a celonrtain timelon
 * primarily uselond for filtelonring out accounts that a uselonr has elonxplicitly dismisselond
 *
 * welon fail opelonn on timelonouts, but loudly on othelonr elonrrors
 */
@Singlelonton
class DismissStorelon @Injelonct() (
  @Namelond(GuicelonNamelondConstants.DISMISS_STORelon_SCANNelonR)
  scannelonr: Scannelonr[(Long, Slicelon[
      (Long, Long)
    ]), Unit, (Long, (Long, Long)), WhoToFollowDismisselonvelonntDelontails],
  stats: StatsReloncelonivelonr)
    elonxtelonnds Logging {

  privatelon val MaxCandidatelonsToRelonturn = 100

  // gelonts a list of dismisselond candidatelons. if numCandidatelonsToFelontchOption is nonelon, welon will felontch thelon delonfault numbelonr of candidatelons
  delonf gelont(
    uselonrId: Long,
    nelongStartTimelonMs: Long,
    maxCandidatelonsToFelontchOption: Option[Int]
  ): Stitch[Selonq[Long]] = {

    val maxCandidatelonsToFelontch = maxCandidatelonsToFelontchOption.gelontOrelonlselon(MaxCandidatelonsToRelonturn)

    scannelonr
      .scan(
        (
          uselonrId,
          Slicelon(
            from = Nonelon,
            to = Somelon((nelongStartTimelonMs, Long.MaxValuelon)),
            limit = Somelon(maxCandidatelonsToFelontch)
          )
        )
      )
      .map {
        caselon s: Selonq[((Long, (Long, Long)), WhoToFollowDismisselonvelonntDelontails)] if s.nonelonmpty =>
          s.map {
            caselon ((_: Long, (_: Long, candidatelonId: Long)), _: WhoToFollowDismisselonvelonntDelontails) =>
              candidatelonId
          }
        caselon _ => Nil
      }
  }
}
