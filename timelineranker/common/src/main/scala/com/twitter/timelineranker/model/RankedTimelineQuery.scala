packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.TimelonlinelonId

caselon class RankelondTimelonlinelonQuelonry(
  ovelonrridelon val id: TimelonlinelonId,
  ovelonrridelon val maxCount: Option[Int] = Nonelon,
  ovelonrridelon val rangelon: Option[TimelonlinelonRangelon] = Nonelon,
  ovelonrridelon val options: Option[RankelondTimelonlinelonQuelonryOptions] = Nonelon)
    elonxtelonnds TimelonlinelonQuelonry(thrift.TimelonlinelonQuelonryTypelon.Rankelond, id, maxCount, rangelon, options) {

  throwIfInvalid()
}
