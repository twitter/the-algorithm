packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}

objelonct Timelonlinelonelonntryelonnvelonlopelon {
  delonf fromThrift(elonntryelonnvelonlopelon: thrift.Timelonlinelonelonntryelonnvelonlopelon): Timelonlinelonelonntryelonnvelonlopelon = {
    Timelonlinelonelonntryelonnvelonlopelon(
      elonntry = Timelonlinelonelonntry.fromThrift(elonntryelonnvelonlopelon.elonntry)
    )
  }
}

caselon class Timelonlinelonelonntryelonnvelonlopelon(elonntry: Timelonlinelonelonntry) {

  throwIfInvalid()

  delonf toThrift: thrift.Timelonlinelonelonntryelonnvelonlopelon = {
    thrift.Timelonlinelonelonntryelonnvelonlopelon(elonntry.toTimelonlinelonelonntryThrift)
  }

  delonf throwIfInvalid(): Unit = {
    elonntry.throwIfInvalid()
  }
}
