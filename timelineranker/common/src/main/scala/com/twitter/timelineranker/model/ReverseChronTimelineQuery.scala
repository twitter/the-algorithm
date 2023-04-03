packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.TimelonlinelonId

objelonct RelonvelonrselonChronTimelonlinelonQuelonry {
  delonf fromTimelonlinelonQuelonry(quelonry: TimelonlinelonQuelonry): RelonvelonrselonChronTimelonlinelonQuelonry = {
    quelonry match {
      caselon q: RelonvelonrselonChronTimelonlinelonQuelonry => q
      caselon _ => throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond quelonry typelon: $quelonry")
    }
  }
}

caselon class RelonvelonrselonChronTimelonlinelonQuelonry(
  ovelonrridelon val id: TimelonlinelonId,
  ovelonrridelon val maxCount: Option[Int] = Nonelon,
  ovelonrridelon val rangelon: Option[TimelonlinelonRangelon] = Nonelon,
  ovelonrridelon val options: Option[RelonvelonrselonChronTimelonlinelonQuelonryOptions] = Nonelon)
    elonxtelonnds TimelonlinelonQuelonry(thrift.TimelonlinelonQuelonryTypelon.RelonvelonrselonChron, id, maxCount, rangelon, options) {

  throwIfInvalid()
}
