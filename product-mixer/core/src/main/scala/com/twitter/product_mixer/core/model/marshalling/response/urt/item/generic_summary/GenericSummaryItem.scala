packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Melondia
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.util.Timelon

objelonct GelonnelonricSummaryItelonm {
  val GelonnelonricSummaryItelonmNamelonspacelon: elonntryNamelonspacelon = elonntryNamelonspacelon("gelonnelonricsummary")
}

caselon class GelonnelonricSummaryItelonm(
  ovelonrridelon val id: String,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  helonadlinelon: RichTelonxt,
  displayTypelon: GelonnelonricSummaryItelonmDisplayTypelon,
  uselonrAttributionIds: Selonq[Long],
  melondia: Option[Melondia],
  contelonxt: Option[GelonnelonricSummaryContelonxt],
  timelonstamp: Option[Timelon],
  onClickAction: Option[GelonnelonricSummaryAction],
  promotelondMelontadata: Option[PromotelondMelontadata])
    elonxtelonnds TimelonlinelonItelonm {
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon = GelonnelonricSummaryItelonm.GelonnelonricSummaryItelonmNamelonspacelon

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
