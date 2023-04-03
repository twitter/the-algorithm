packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class RelontwelonelontelonrChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(candidatelonFelonaturelons: FelonaturelonMap): Option[ChildFelonelondbackAction] = {
    val isRelontwelonelont = candidatelonFelonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)

    if (isRelontwelonelont) {
      candidatelonFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon).flatMap { relontwelonelontelonrId =>
        FelonelondbackUtil.buildUselonrSelonelonFelonwelonrChildFelonelondbackAction(
          uselonrId = relontwelonelontelonrId,
          namelonsByUselonrId = candidatelonFelonaturelons.gelontOrelonlselon(ScrelonelonnNamelonsFelonaturelon, Map.elonmpty[Long, String]),
          promptelonxtelonrnalString = elonxtelonrnalStrings.showFelonwelonrRelontwelonelontsString,
          confirmationelonxtelonrnalString = elonxtelonrnalStrings.showFelonwelonrRelontwelonelontsConfirmationString,
          elonngagelonmelonntTypelon = t.FelonelondbackelonngagelonmelonntTypelon.Relontwelonelont,
          stringCelonntelonr = stringCelonntelonr,
          injelonctionTypelon = candidatelonFelonaturelons.gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)
        )
      }
    } elonlselon Nonelon
  }
}
