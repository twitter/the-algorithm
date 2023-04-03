packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm

selonalelond trait VelonrticalGridItelonm elonxtelonnds TimelonlinelonItelonm

objelonct VelonrticalGridItelonmTopicTilelon {
  val VelonrticalGridItelonmTopicTilelonelonntryNamelonspacelon = elonntryNamelonspacelon("velonrticalgriditelonmtopictilelon")
}

caselon class VelonrticalGridItelonmTopicTilelon(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  stylelon: Option[VelonrticalGridItelonmTilelonStylelon],
  functionalityTypelon: Option[VelonrticalGridItelonmTopicFunctionalityTypelon],
  url: Option[Url])
    elonxtelonnds VelonrticalGridItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon =
    VelonrticalGridItelonmTopicTilelon.VelonrticalGridItelonmTopicTilelonelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
