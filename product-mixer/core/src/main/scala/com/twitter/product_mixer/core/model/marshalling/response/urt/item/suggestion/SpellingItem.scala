packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo

objelonct SpelonllingItelonm {
  val SpelonllingelonntryNamelonspacelon = elonntryNamelonspacelon("spelonlling")
}

/**
 * Relonprelonselonnts a Spelonlling Suggelonstion URT itelonm. This is primary uselond by Selonarch timelonlinelons for
 * displaying Spelonlling correlonction information.
 *
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/Spelonlling.html
 */
caselon class SpelonllingItelonm(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  telonxtRelonsult: TelonxtRelonsult,
  spelonllingActionTypelon: Option[SpelonllingActionTypelon],
  originalQuelonry: Option[String])
    elonxtelonnds TimelonlinelonItelonm {

  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = SpelonllingItelonm.SpelonllingelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
