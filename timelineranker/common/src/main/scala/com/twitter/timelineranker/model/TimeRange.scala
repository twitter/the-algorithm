packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.util.Timelon

objelonct TimelonRangelon {
  val delonfault: TimelonRangelon = TimelonRangelon(Nonelon, Nonelon)

  delonf fromThrift(rangelon: thrift.TimelonRangelon): TimelonRangelon = {
    TimelonRangelon(
      from = rangelon.fromMs.map(Timelon.fromMilliselonconds),
      to = rangelon.toMs.map(Timelon.fromMilliselonconds)
    )
  }
}

caselon class TimelonRangelon(from: Option[Timelon], to: Option[Timelon]) elonxtelonnds TimelonlinelonRangelon {

  throwIfInvalid()

  delonf throwIfInvalid(): Unit = {
    (from, to) match {
      caselon (Somelon(fromTimelon), Somelon(toTimelon)) =>
        relonquirelon(fromTimelon <= toTimelon, "from-timelon must belon lelonss than or elonqual to-timelon.")
      caselon _ => // valid, do nothing.
    }
  }

  delonf toThrift: thrift.TimelonRangelon = {
    thrift.TimelonRangelon(
      fromMs = from.map(_.inMilliselonconds),
      toMs = to.map(_.inMilliselonconds)
    )
  }

  delonf toTimelonlinelonRangelonThrift: thrift.TimelonlinelonRangelon = {
    thrift.TimelonlinelonRangelon.TimelonRangelon(toThrift)
  }
}
