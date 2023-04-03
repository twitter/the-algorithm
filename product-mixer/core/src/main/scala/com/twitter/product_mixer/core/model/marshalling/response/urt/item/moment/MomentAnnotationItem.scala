packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.momelonnt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt

objelonct MomelonntAnnotationItelonm {
  val MomelonntAnnotationelonntryNamelonspacelon = elonntryNamelonspacelon("momelonntannotation")
}

/**
 * Relonprelonselonnts a MomelonntAnnotation URT itelonm.
 * This is primarily uselond by Trelonnds Selonarth Relonsult Pagelon for displaying Trelonnds Titlelon or Delonscription
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/MomelonntAnnotation.html
 */
caselon class MomelonntAnnotationItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  ovelonrridelon val isPinnelond: Option[Boolelonan],
  telonxt: Option[RichTelonxt],
  helonadelonr: Option[RichTelonxt],
) elonxtelonnds TimelonlinelonItelonm {

  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon =
    MomelonntAnnotationItelonm.MomelonntAnnotationelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
