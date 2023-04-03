packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata

objelonct UselonrItelonm {
  val UselonrelonntryNamelonspacelon: elonntryNamelonspacelon = elonntryNamelonspacelon("uselonr")
}

caselon class UselonrItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  ovelonrridelon val isMarkUnrelonad: Option[Boolelonan],
  displayTypelon: UselonrDisplayTypelon,
  promotelondMelontadata: Option[PromotelondMelontadata],
  socialContelonxt: Option[SocialContelonxt],
  relonactivelonTriggelonrs: Option[UselonrRelonactivelonTriggelonrs],
  elonnablelonRelonactivelonBlelonnding: Option[Boolelonan])
    elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = UselonrItelonm.UselonrelonntryNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
