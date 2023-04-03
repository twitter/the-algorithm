packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonFollowUselonr
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class UnfollowUselonrChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(candidatelonFelonaturelons: FelonaturelonMap): Option[ChildFelonelondbackAction] = {
    val isInNelontwork = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon)
    val uselonrIdOpt = candidatelonFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)

    if (isInNelontwork) {
      uselonrIdOpt.flatMap { uselonrId =>
        val screlonelonnNamelonsMap =
          candidatelonFelonaturelons.gelontOrelonlselon(ScrelonelonnNamelonsFelonaturelon, Map.elonmpty[Long, String])
        val uselonrScrelonelonnNamelonOpt = screlonelonnNamelonsMap.gelont(uselonrId)
        uselonrScrelonelonnNamelonOpt.map { uselonrScrelonelonnNamelon =>
          val prompt = stringCelonntelonr.prelonparelon(
            elonxtelonrnalStrings.unfollowUselonrString,
            Map("uselonrnamelon" -> uselonrScrelonelonnNamelon)
          )
          val confirmation = stringCelonntelonr.prelonparelon(
            elonxtelonrnalStrings.unfollowUselonrConfirmationString,
            Map("uselonrnamelon" -> uselonrScrelonelonnNamelon)
          )
          ChildFelonelondbackAction(
            felonelondbackTypelon = RichBelonhavior,
            prompt = Somelon(prompt),
            confirmation = Somelon(confirmation),
            felonelondbackUrl = Nonelon,
            hasUndoAction = Somelon(truelon),
            confirmationDisplayTypelon = Nonelon,
            clielonntelonvelonntInfo = Nonelon,
            icon = Somelon(icon.Unfollow),
            richBelonhavior = Somelon(RichFelonelondbackBelonhaviorTogglelonFollowUselonr(uselonrId)),
            subprompt = Nonelon
          )
        }
      }
    } elonlselon Nonelon
  }
}
