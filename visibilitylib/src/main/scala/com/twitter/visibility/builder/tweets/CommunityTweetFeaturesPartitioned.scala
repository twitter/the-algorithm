packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

class CommunityTwelonelontFelonaturelonsPartitionelond(
  a: CommunityTwelonelontFelonaturelons,
  b: CommunityTwelonelontFelonaturelons,
  belonnablelond: Gatelon[Unit],
) elonxtelonnds CommunityTwelonelontFelonaturelons {
  ovelonrridelon delonf forTwelonelont(
    twelonelont: Twelonelont,
    vielonwelonrContelonxt: VielonwelonrContelonxt
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr =
    belonnablelond.pick(
      b.forTwelonelont(twelonelont, vielonwelonrContelonxt),
      a.forTwelonelont(twelonelont, vielonwelonrContelonxt),
    )

  ovelonrridelon delonf forTwelonelontOnly(twelonelont: Twelonelont): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = belonnablelond.pick(
    b.forTwelonelontOnly(twelonelont),
    a.forTwelonelontOnly(twelonelont),
  )
}
