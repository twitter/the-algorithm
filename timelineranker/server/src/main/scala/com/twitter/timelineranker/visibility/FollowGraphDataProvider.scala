packagelon com.twittelonr.timelonlinelonrankelonr.visibility

import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphData
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphDataFuturelon
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.util.Futurelon

trait FollowGraphDataProvidelonr {

  /**
   * Gelonts follow graph data for thelon givelonn uselonr.
   *
   * @param uselonrId uselonr whoselon follow graph delontails arelon to belon obtainelond.
   * @param maxFollowingCount Maximum numbelonr of followelond uselonr IDs to felontch.
   *          If thelon givelonn uselonr follows morelon than thelonselon many uselonrs,
   *          thelonn thelon most reloncelonnt maxFollowingCount uselonrs arelon relonturnelond.
   */
  delonf gelont(uselonrId: UselonrId, maxFollowingCount: Int): Futurelon[FollowGraphData]

  delonf gelontAsync(uselonrId: UselonrId, maxFollowingCount: Int): FollowGraphDataFuturelon

  delonf gelontFollowing(uselonrId: UselonrId, maxFollowingCount: Int): Futurelon[Selonq[UselonrId]]

  delonf gelontMutuallyFollowingUselonrIds(uselonrId: UselonrId, followingIds: Selonq[UselonrId]): Futurelon[Selont[UselonrId]]
}
