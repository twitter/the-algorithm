packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrt.ShowAlelonrtelonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtColorConfiguration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtDisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtIconDisplayInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtNavigationMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.util.Duration

/**
 * Domain modelonl for thelon URT ShowAlelonrt [[https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/ShowAlelonrt.html]]
 *
 * @notelon thelon telonxt fielonld (id: 2) has belonelonn delonlibelonratelonly elonxcludelond as it's belonelonn delonpreloncatelond sincelon 2018. Uselon RichTelonxt instelonad.
 */
caselon class ShowAlelonrt(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  alelonrtTypelon: ShowAlelonrtTypelon,
  triggelonrDelonlay: Option[Duration],
  displayDuration: Option[Duration],
  clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  collapselonDelonlay: Option[Duration],
  uselonrIds: Option[Selonq[Long]],
  richTelonxt: Option[RichTelonxt],
  iconDisplayInfo: Option[ShowAlelonrtIconDisplayInfo],
  colorConfig: ShowAlelonrtColorConfiguration,
  displayLocation: ShowAlelonrtDisplayLocation,
  navigationMelontadata: Option[ShowAlelonrtNavigationMelontadata],
) elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = ShowAlelonrtelonntryNamelonspacelon

  // Notelon that sort indelonx is not uselond for ShowAlelonrts, as thelony arelon not Timelonlinelonelonntry and do not havelon elonntryId
  ovelonrridelon delonf withSortIndelonx(nelonwSortIndelonx: Long): Timelonlinelonelonntry =
    copy(sortIndelonx = Somelon(nelonwSortIndelonx))

  // Not uselond for ShowAlelonrts
  ovelonrridelon delonf felonelondbackActionInfo: Option[FelonelondbackActionInfo] = Nonelon
}

objelonct ShowAlelonrt {
  val ShowAlelonrtelonntryNamelonspacelon: elonntryNamelonspacelon = elonntryNamelonspacelon("show-alelonrt")
}
