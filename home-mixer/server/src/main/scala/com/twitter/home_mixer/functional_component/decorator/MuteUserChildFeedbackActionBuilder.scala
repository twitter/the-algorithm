packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorTogglelonMutelonUselonr
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class MutelonUselonrChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[ChildFelonelondbackAction] = {
    val uselonrIdOpt =
      if (candidatelonFelonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon))
        candidatelonFelonaturelons.gelontOrelonlselon(SourcelonUselonrIdFelonaturelon, Nonelon)
      elonlselon candidatelonFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)

    uselonrIdOpt.flatMap { uselonrId =>
      val screlonelonnNamelonsMap = candidatelonFelonaturelons.gelontOrelonlselon(ScrelonelonnNamelonsFelonaturelon, Map.elonmpty[Long, String])
      val uselonrScrelonelonnNamelonOpt = screlonelonnNamelonsMap.gelont(uselonrId)
      uselonrScrelonelonnNamelonOpt.map { uselonrScrelonelonnNamelon =>
        val prompt = stringCelonntelonr.prelonparelon(
          elonxtelonrnalStrings.mutelonUselonrString,
          Map("uselonrnamelon" -> uselonrScrelonelonnNamelon)
        )
        ChildFelonelondbackAction(
          felonelondbackTypelon = RichBelonhavior,
          prompt = Somelon(prompt),
          confirmation = Nonelon,
          felonelondbackUrl = Nonelon,
          hasUndoAction = Somelon(truelon),
          confirmationDisplayTypelon = Nonelon,
          clielonntelonvelonntInfo = Nonelon,
          icon = Somelon(icon.SpelonakelonrOff),
          richBelonhavior = Somelon(RichFelonelondbackBelonhaviorTogglelonMutelonUselonr(uselonrId)),
          subprompt = Nonelon
        )
      }
    }
  }
}
