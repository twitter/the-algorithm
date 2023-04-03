packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonSelonndScorelonsToClielonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.twelonelont.BaselonTimelonlinelonsScorelonInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TimelonlinelonsScorelonInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct HomelonTimelonlinelonsScorelonInfoBuildelonr
    elonxtelonnds BaselonTimelonlinelonsScorelonInfoBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val UndelonfinelondTwelonelontScorelon = -1.0

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[TimelonlinelonsScorelonInfo] = {
    if (quelonry.params(elonnablelonSelonndScorelonsToClielonnt)) {
      val scorelon = candidatelonFelonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon).gelontOrelonlselon(UndelonfinelondTwelonelontScorelon)
      Somelon(TimelonlinelonsScorelonInfo(scorelon))
    } elonlselon Nonelon
  }
}
