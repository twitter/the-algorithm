packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonNahFelonelondbackInfoParam
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon.Frown
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DontLikelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.timelonlinelons.common.{thriftscala => tlc}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackInfo
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackMelontadata
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => tls}

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class DontLikelonFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  authorChildFelonelondbackActionBuildelonr: AuthorChildFelonelondbackActionBuildelonr,
  relontwelonelontelonrChildFelonelondbackActionBuildelonr: RelontwelonelontelonrChildFelonelondbackActionBuildelonr,
  notRelonlelonvantChildFelonelondbackActionBuildelonr: NotRelonlelonvantChildFelonelondbackActionBuildelonr,
  unfollowUselonrChildFelonelondbackActionBuildelonr: UnfollowUselonrChildFelonelondbackActionBuildelonr,
  mutelonUselonrChildFelonelondbackActionBuildelonr: MutelonUselonrChildFelonelondbackActionBuildelonr,
  blockUselonrChildFelonelondbackActionBuildelonr: BlockUselonrChildFelonelondbackActionBuildelonr,
  relonportTwelonelontChildFelonelondbackActionBuildelonr: RelonportTwelonelontChildFelonelondbackActionBuildelonr) {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackAction] = {
    CandidatelonsUtil.gelontOriginalAuthorId(candidatelonFelonaturelons).map { authorId =>
      val felonelondbackelonntitielons = Selonq(
        tlc.Felonelondbackelonntity.TwelonelontId(candidatelon.id),
        tlc.Felonelondbackelonntity.UselonrId(authorId)
      )
      val felonelondbackMelontadata = FelonelondbackMelontadata(
        elonngagelonmelonntTypelon = Nonelon,
        elonntityIds = felonelondbackelonntitielons,
        ttl = Somelon(30.days)
      )
      val felonelondbackUrl = FelonelondbackInfo.felonelondbackUrl(
        felonelondbackTypelon = tls.FelonelondbackTypelon.DontLikelon,
        felonelondbackMelontadata = felonelondbackMelontadata,
        injelonctionTypelon = candidatelonFelonaturelons.gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)
      )
      val childFelonelondbackActions = if (quelonry.params(elonnablelonNahFelonelondbackInfoParam)) {
        Selonq(
          unfollowUselonrChildFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
          mutelonUselonrChildFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
          blockUselonrChildFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
          relonportTwelonelontChildFelonelondbackActionBuildelonr(candidatelon)
        ).flattelonn
      } elonlselon {
        Selonq(
          authorChildFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
          relontwelonelontelonrChildFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
          notRelonlelonvantChildFelonelondbackActionBuildelonr(candidatelon, candidatelonFelonaturelons)
        ).flattelonn
      }

      FelonelondbackAction(
        felonelondbackTypelon = DontLikelon,
        prompt = Somelon(stringCelonntelonr.prelonparelon(elonxtelonrnalStrings.dontLikelonString)),
        confirmation = Somelon(stringCelonntelonr.prelonparelon(elonxtelonrnalStrings.dontLikelonConfirmationString)),
        childFelonelondbackActions =
          if (childFelonelondbackActions.nonelonmpty) Somelon(childFelonelondbackActions) elonlselon Nonelon,
        felonelondbackUrl = Somelon(felonelondbackUrl),
        hasUndoAction = Somelon(truelon),
        confirmationDisplayTypelon = Nonelon,
        clielonntelonvelonntInfo = Nonelon,
        icon = Somelon(Frown),
        richBelonhavior = Nonelon,
        subprompt = Nonelon,
        elonncodelondFelonelondbackRelonquelonst = Nonelon
      )
    }
  }
}
