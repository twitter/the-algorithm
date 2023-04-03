packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.ContelonxtualTwelonelontRelonf
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.ConvelonrsationAnnotation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.ForwardPivot
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TombstonelonInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Badgelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PrelonrollMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url

objelonct TwelonelontItelonm {
  val TwelonelontelonntryNamelonspacelon = elonntryNamelonspacelon("twelonelont")
  val PromotelondTwelonelontelonntryNamelonspacelon = elonntryNamelonspacelon("promotelond-twelonelont")
}

caselon class TwelonelontItelonm(
  ovelonrridelon val id: Long,
  ovelonrridelon val elonntryNamelonspacelon: elonntryNamelonspacelon,
  ovelonrridelon val sortIndelonx: Option[Long],
  ovelonrridelon val clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  ovelonrridelon val felonelondbackActionInfo: Option[FelonelondbackActionInfo],
  ovelonrridelon val isPinnelond: Option[Boolelonan],
  ovelonrridelon val elonntryIdToRelonplacelon: Option[String],
  socialContelonxt: Option[SocialContelonxt],
  highlights: Option[TwelonelontHighlights],
  displayTypelon: TwelonelontDisplayTypelon,
  innelonrTombstonelonInfo: Option[TombstonelonInfo],
  timelonlinelonsScorelonInfo: Option[TimelonlinelonsScorelonInfo],
  hasModelonratelondRelonplielons: Option[Boolelonan],
  forwardPivot: Option[ForwardPivot],
  innelonrForwardPivot: Option[ForwardPivot],
  promotelondMelontadata: Option[PromotelondMelontadata],
  convelonrsationAnnotation: Option[ConvelonrsationAnnotation],
  contelonxtualTwelonelontRelonf: Option[ContelonxtualTwelonelontRelonf],
  prelonrollMelontadata: Option[PrelonrollMelontadata],
  relonplyBadgelon: Option[Badgelon],
  delonstination: Option[Url])
    elonxtelonnds TimelonlinelonItelonm {

  /**
   * Promotelond twelonelonts nelonelond to includelon thelon imprelonssion ID in thelon elonntry ID sincelon somelon clielonnts havelon
   * clielonnt-sidelon logic that delonduplicatelons ads imprelonssion callbacks baselond on a combination of thelon
   * twelonelont and imprelonssion IDs. Not including thelon imprelonssion ID will lelonad to ovelonr delonduplication.
   */
  ovelonrridelon lazy val elonntryIdelonntifielonr: String = promotelondMelontadata
    .map { melontadata =>
      val imprelonssionId = melontadata.imprelonssionString match {
        caselon Somelon(imprelonssionString) if imprelonssionString.nonelonmpty => imprelonssionString
        caselon _ => throw nelonw IllelongalStatelonelonxcelonption(s"Promotelond Twelonelont $id missing imprelonssion ID")
      }
      s"$elonntryNamelonspacelon-$id-$imprelonssionId"
    }.gelontOrelonlselon(s"$elonntryNamelonspacelon-$id")

  ovelonrridelon delonf withSortIndelonx(sortIndelonx: Long): Timelonlinelonelonntry = copy(sortIndelonx = Somelon(sortIndelonx))
}
