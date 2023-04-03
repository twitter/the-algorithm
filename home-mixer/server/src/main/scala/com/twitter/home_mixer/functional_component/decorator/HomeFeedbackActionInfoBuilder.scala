packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.FollowingProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ForYouProduct
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonNahFelonelondbackInfoParam
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}
import com.twittelonr.timelonlinelons.util.FelonelondbackMelontadataSelonrializelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HomelonFelonelondbackActionInfoBuildelonr @Injelonct() (
  notIntelonrelonstelondTopicFelonelondbackActionBuildelonr: NotIntelonrelonstelondTopicFelonelondbackActionBuildelonr,
  dontLikelonFelonelondbackActionBuildelonr: DontLikelonFelonelondbackActionBuildelonr)
    elonxtelonnds BaselonFelonelondbackActionInfoBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackActionInfo] = {
    val supportelondProduct = quelonry.product match {
      caselon FollowingProduct => quelonry.params(elonnablelonNahFelonelondbackInfoParam)
      caselon ForYouProduct => truelon
      caselon _ => falselon
    }
    val isAuthorelondByVielonwelonr = CandidatelonsUtil.isAuthorelondByVielonwelonr(quelonry, candidatelonFelonaturelons)

    if (supportelondProduct && !isAuthorelondByVielonwelonr) {
      val felonelondbackActions = Selonq(
        notIntelonrelonstelondTopicFelonelondbackActionBuildelonr(candidatelonFelonaturelons),
        dontLikelonFelonelondbackActionBuildelonr(quelonry, candidatelon, candidatelonFelonaturelons)
      ).flattelonn
      val felonelondbackMelontadata = FelonelondbackMelontadataSelonrializelonr.selonrializelon(
        t.FelonelondbackMelontadata(injelonctionTypelon = candidatelonFelonaturelons.gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)))

      Somelon(
        FelonelondbackActionInfo(
          felonelondbackActions = felonelondbackActions,
          felonelondbackMelontadata = Somelon(felonelondbackMelontadata),
          displayContelonxt = Nonelon,
          clielonntelonvelonntInfo = Nonelon
        ))
    } elonlselon Nonelon
  }
}
