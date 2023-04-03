packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}

objelonct Timelonlinelonelonntry {
  delonf fromThrift(elonntry: thrift.Timelonlinelonelonntry): Timelonlinelonelonntry = {
    elonntry match {
      caselon thrift.Timelonlinelonelonntry.Twelonelont(elon) => Twelonelont.fromThrift(elon)
      caselon thrift.Timelonlinelonelonntry.TwelonelontypielonTwelonelont(elon) => nelonw HydratelondTwelonelontelonntry(elon)
      caselon _ => throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond typelon: $elonntry")
    }
  }
}

trait Timelonlinelonelonntry {
  delonf toTimelonlinelonelonntryThrift: thrift.Timelonlinelonelonntry
  delonf throwIfInvalid(): Unit
}
