packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont
import com.twittelonr.twelonelontypielon.{thriftscala => twelonelontypielon}

/**
 * elonnablelons HydratelondTwelonelont elonntrielons to belon includelond in a Timelonlinelon.
 */
class HydratelondTwelonelontelonntry(twelonelont: twelonelontypielon.Twelonelont) elonxtelonnds HydratelondTwelonelont(twelonelont) with Timelonlinelonelonntry {

  delonf this(hydratelondTwelonelont: HydratelondTwelonelont) = this(hydratelondTwelonelont.twelonelont)

  ovelonrridelon delonf toTimelonlinelonelonntryThrift: thrift.Timelonlinelonelonntry = {
    thrift.Timelonlinelonelonntry.TwelonelontypielonTwelonelont(twelonelont)
  }

  ovelonrridelon delonf throwIfInvalid(): Unit = {
    // No validation pelonrformelond.
  }
}
