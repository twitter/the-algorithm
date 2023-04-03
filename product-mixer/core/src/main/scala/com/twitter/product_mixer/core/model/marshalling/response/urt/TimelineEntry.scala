packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ContainsFelonelondbackActionInfos
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HasClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.HasFelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Pinnablelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Relonplacelonablelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.MarkUnrelonadablelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonFootelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonHelonadelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonShowMorelonBelonhavior

selonalelond trait Timelonlinelonelonntry
    elonxtelonnds HaselonntryIdelonntifielonr
    with HasSortIndelonx
    with HaselonxpirationTimelon
    with Pinnablelonelonntry
    with Relonplacelonablelonelonntry
    with MarkUnrelonadablelonelonntry

trait TimelonlinelonItelonm elonxtelonnds Timelonlinelonelonntry with HasClielonntelonvelonntInfo with HasFelonelondbackActionInfo

caselon class ModulelonItelonm(
  itelonm: TimelonlinelonItelonm,
  dispelonnsablelon: Option[Boolelonan],
  trelonelonDisplay: Option[ModulelonItelonmTrelonelonDisplay])

caselon class TimelonlinelonModulelon(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon,
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  ovelonrridelon val isPinnelond: Option[Boolelonan],
  itelonms: Selonq[ModulelonItelonm],
  displayTypelon: ModulelonDisplayTypelon,
  helonadelonr: Option[ModulelonHelonadelonr],
  footelonr: Option[ModulelonFootelonr],
  melontadata: Option[ModulelonMelontadata],
  showMorelonBelonhavior: Option[ModulelonShowMorelonBelonhavior])
    elonxtelonnds Timelonlinelonelonntry
    with HasClielonntelonvelonntInfo
    with HasFelonelondbackActionInfo
    with ContainsFelonelondbackActionInfos {
  ovelonrridelon delonf felonelondbackActionInfos: Selonq[Option[FelonelondbackActionInfo]] = {
    itelonms.map(_.itelonm.felonelondbackActionInfo) :+ felonelondbackActionInfo
  }

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}

trait TimelonlinelonOpelonration elonxtelonnds Timelonlinelonelonntry
