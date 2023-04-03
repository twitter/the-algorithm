packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class AuthorChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(candidatelonFelonaturelons: FelonaturelonMap): Option[ChildFelonelondbackAction] = {
    CandidatelonsUtil.gelontOriginalAuthorId(candidatelonFelonaturelons).flatMap { authorId =>
      FelonelondbackUtil.buildUselonrSelonelonFelonwelonrChildFelonelondbackAction(
        uselonrId = authorId,
        namelonsByUselonrId = candidatelonFelonaturelons.gelontOrelonlselon(ScrelonelonnNamelonsFelonaturelon, Map.elonmpty[Long, String]),
        promptelonxtelonrnalString = elonxtelonrnalStrings.showFelonwelonrTwelonelontsString,
        confirmationelonxtelonrnalString = elonxtelonrnalStrings.showFelonwelonrTwelonelontsConfirmationString,
        elonngagelonmelonntTypelon = t.FelonelondbackelonngagelonmelonntTypelon.Twelonelont,
        stringCelonntelonr = stringCelonntelonr,
        injelonctionTypelon = candidatelonFelonaturelons.gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)
      )
    }
  }
}
