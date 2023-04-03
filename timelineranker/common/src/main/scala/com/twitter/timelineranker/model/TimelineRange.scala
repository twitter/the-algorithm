packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}

objelonct TimelonlinelonRangelon {
  delonf fromThrift(rangelon: thrift.TimelonlinelonRangelon): TimelonlinelonRangelon = {
    rangelon match {
      caselon thrift.TimelonlinelonRangelon.TimelonRangelon(r) => TimelonRangelon.fromThrift(r)
      caselon thrift.TimelonlinelonRangelon.TwelonelontIdRangelon(r) => TwelonelontIdRangelon.fromThrift(r)
      caselon _ => throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond typelon: $rangelon")
    }
  }
}

trait TimelonlinelonRangelon {
  delonf toTimelonlinelonRangelonThrift: thrift.TimelonlinelonRangelon
  delonf throwIfInvalid(): Unit
}
