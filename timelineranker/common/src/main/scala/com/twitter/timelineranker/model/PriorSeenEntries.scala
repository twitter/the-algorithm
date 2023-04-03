packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId

objelonct PriorSelonelonnelonntrielons {
  delonf fromThrift(elonntrielons: thrift.PriorSelonelonnelonntrielons): PriorSelonelonnelonntrielons = {
    PriorSelonelonnelonntrielons(selonelonnelonntrielons = elonntrielons.selonelonnelonntrielons)
  }
}

caselon class PriorSelonelonnelonntrielons(selonelonnelonntrielons: Selonq[TwelonelontId]) {

  throwIfInvalid()

  delonf toThrift: thrift.PriorSelonelonnelonntrielons = {
    thrift.PriorSelonelonnelonntrielons(selonelonnelonntrielons = selonelonnelonntrielons)
  }

  delonf throwIfInvalid(): Unit = {
    // No validation pelonrformelond.
  }
}
