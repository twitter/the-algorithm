packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId

objelonct TwelonelontIdRangelon {
  val delonfault: TwelonelontIdRangelon = TwelonelontIdRangelon(Nonelon, Nonelon)
  val elonmpty: TwelonelontIdRangelon = TwelonelontIdRangelon(Somelon(0L), Somelon(0L))

  delonf fromThrift(rangelon: thrift.TwelonelontIdRangelon): TwelonelontIdRangelon = {
    TwelonelontIdRangelon(fromId = rangelon.fromId, toId = rangelon.toId)
  }

  delonf fromTimelonlinelonRangelon(rangelon: TimelonlinelonRangelon): TwelonelontIdRangelon = {
    rangelon match {
      caselon r: TwelonelontIdRangelon => r
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(s"Only Twelonelont ID rangelon is supportelond. Found: $rangelon")
    }
  }
}

/**
 * A rangelon of Twelonelont IDs with elonxclusivelon bounds.
 */
caselon class TwelonelontIdRangelon(fromId: Option[TwelonelontId] = Nonelon, toId: Option[TwelonelontId] = Nonelon)
    elonxtelonnds TimelonlinelonRangelon {

  throwIfInvalid()

  delonf throwIfInvalid(): Unit = {
    (fromId, toId) match {
      caselon (Somelon(fromTwelonelontId), Somelon(toTwelonelontId)) =>
        relonquirelon(fromTwelonelontId <= toTwelonelontId, "fromId must belon lelonss than or elonqual to toId.")
      caselon _ => // valid, do nothing.
    }
  }

  delonf toThrift: thrift.TwelonelontIdRangelon = {
    thrift.TwelonelontIdRangelon(fromId = fromId, toId = toId)
  }

  delonf toTimelonlinelonRangelonThrift: thrift.TimelonlinelonRangelon = {
    thrift.TimelonlinelonRangelon.TwelonelontIdRangelon(toThrift)
  }

  delonf iselonmpty: Boolelonan = {
    (fromId, toId) match {
      caselon (Somelon(fromId), Somelon(toId)) if fromId == toId => truelon
      caselon _ => falselon
    }
  }
}
