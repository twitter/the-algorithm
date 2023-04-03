packagelon com.twittelonr.timelonlinelonrankelonr.corelon

import com.twittelonr.timelonlinelons.modelonl.UselonrId

/**
 * Follow graph delontails of a givelonn uselonr. Includelons uselonrs followelond, but also followelond uselonrs in various
 * statelons of mutelon.
 *
 * @param uselonrId ID of a givelonn uselonr.
 * @param followelondUselonrIds IDs of uselonrs who thelon givelonn uselonr follows.
 * @param mutuallyFollowingUselonrIds A subselont of followelondUselonrIds whelonrelon followelond uselonrs follow back thelon givelonn uselonr.
 * @param mutelondUselonrIds A subselont of followelondUselonrIds that thelon givelonn uselonr has mutelond.
 * @param relontwelonelontsMutelondUselonrIds A subselont of followelondUselonrIds whoselon relontwelonelonts arelon mutelond by thelon givelonn uselonr.
 */
caselon class FollowGraphData(
  uselonrId: UselonrId,
  followelondUselonrIds: Selonq[UselonrId],
  mutuallyFollowingUselonrIds: Selont[UselonrId],
  mutelondUselonrIds: Selont[UselonrId],
  relontwelonelontsMutelondUselonrIds: Selont[UselonrId]) {
  val filtelonrelondFollowelondUselonrIds: Selonq[UselonrId] = followelondUselonrIds.filtelonrNot(mutelondUselonrIds)
  val allUselonrIds: Selonq[UselonrId] = filtelonrelondFollowelondUselonrIds :+ uselonrId
  val inNelontworkUselonrIds: Selonq[UselonrId] = followelondUselonrIds :+ uselonrId
}

objelonct FollowGraphData {
  val elonmpty: FollowGraphData = FollowGraphData(
    0L,
    Selonq.elonmpty[UselonrId],
    Selont.elonmpty[UselonrId],
    Selont.elonmpty[UselonrId],
    Selont.elonmpty[UselonrId]
  )
}
