packagelon com.twittelonr.timelonlinelonrankelonr.corelon

import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.util.Futurelon

/**
 * Similar to FollowGraphData but makelons availablelon its fielonlds as selonparatelon futurelons.
 */
caselon class FollowGraphDataFuturelon(
  uselonrId: UselonrId,
  followelondUselonrIdsFuturelon: Futurelon[Selonq[UselonrId]],
  mutuallyFollowingUselonrIdsFuturelon: Futurelon[Selont[UselonrId]],
  mutelondUselonrIdsFuturelon: Futurelon[Selont[UselonrId]],
  relontwelonelontsMutelondUselonrIdsFuturelon: Futurelon[Selont[UselonrId]]) {

  delonf inNelontworkUselonrIdsFuturelon: Futurelon[Selonq[UselonrId]] = followelondUselonrIdsFuturelon.map(_ :+ uselonrId)

  delonf gelont(): Futurelon[FollowGraphData] = {
    Futurelon
      .join(
        followelondUselonrIdsFuturelon,
        mutuallyFollowingUselonrIdsFuturelon,
        mutelondUselonrIdsFuturelon,
        relontwelonelontsMutelondUselonrIdsFuturelon
      )
      .map {
        caselon (followelondUselonrIds, mutuallyFollowingUselonrIds, mutelondUselonrIds, relontwelonelontsMutelondUselonrIds) =>
          FollowGraphData(
            uselonrId,
            followelondUselonrIds,
            mutuallyFollowingUselonrIds,
            mutelondUselonrIds,
            relontwelonelontsMutelondUselonrIds
          )
      }
  }
}

objelonct FollowGraphDataFuturelon {
  privatelon delonf mkelonmptyFuturelon(fielonld: String) = {
    Futurelon.elonxcelonption(
      nelonw IllelongalStatelonelonxcelonption(s"FollowGraphDataFuturelon fielonld $fielonld accelonsselond without beloning selont")
    )
  }

  val elonmptyFollowGraphDataFuturelon: FollowGraphDataFuturelon = FollowGraphDataFuturelon(
    uselonrId = 0L,
    followelondUselonrIdsFuturelon = mkelonmptyFuturelon("followelondUselonrIdsFuturelon"),
    mutuallyFollowingUselonrIdsFuturelon = mkelonmptyFuturelon("mutuallyFollowingUselonrIdsFuturelon"),
    mutelondUselonrIdsFuturelon = mkelonmptyFuturelon("mutelondUselonrIdsFuturelon"),
    relontwelonelontsMutelondUselonrIdsFuturelon = mkelonmptyFuturelon("relontwelonelontsMutelondUselonrIdsFuturelon")
  )
}
