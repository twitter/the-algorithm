packagelon com.twittelonr.follow_reloncommelonndations.common.storelons

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.TwelonelonpCrelondOnUselonrClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

// Not a candidatelon sourcelon sincelon it's a intelonrmelondiary.
@Singlelonton
class LowTwelonelonpCrelondFollowStorelon @Injelonct() (twelonelonpCrelondOnUselonrClielonntColumn: TwelonelonpCrelondOnUselonrClielonntColumn) {

  delonf gelontLowTwelonelonpCrelondUselonrs(targelont: HasReloncelonntFollowelondUselonrIds): Stitch[Selonq[CandidatelonUselonr]] = {
    val nelonwFollowings =
      targelont.reloncelonntFollowelondUselonrIds.gelontOrelonlselon(Nil).takelon(LowTwelonelonpCrelondFollowStorelon.NumFlockToRelontrielonvelon)

    val validTwelonelonpScorelonUselonrIdsStitch: Stitch[Selonq[Long]] = Stitch
      .travelonrselon(nelonwFollowings) { nelonwFollowingUselonrId =>
        val twelonelonpCrelondScorelonOptStitch = twelonelonpCrelondOnUselonrClielonntColumn.felontchelonr
          .felontch(nelonwFollowingUselonrId)
          .map(_.v)
        twelonelonpCrelondScorelonOptStitch.map(_.flatMap(twelonelonpCrelond =>
          if (twelonelonpCrelond < LowTwelonelonpCrelondFollowStorelon.TwelonelonpCrelondThrelonshold) {
            Somelon(nelonwFollowingUselonrId)
          } elonlselon {
            Nonelon
          }))
      }.map(_.flattelonn)

    validTwelonelonpScorelonUselonrIdsStitch
      .map(_.map(CandidatelonUselonr(_, Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon))))
  }
}

objelonct LowTwelonelonpCrelondFollowStorelon {
  val NumFlockToRelontrielonvelon = 500
  val TwelonelonpCrelondThrelonshold = 40
}
