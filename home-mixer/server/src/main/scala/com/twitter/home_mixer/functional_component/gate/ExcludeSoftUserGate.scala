packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.gizmoduck.{thriftscala => t}
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.UselonrTypelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A Soft Uselonr is a uselonr who is in thelon gradual onboarding statelon. This gatelon can belon
 * uselond to turn off celonrtain functionality likelon ads for thelonselon uselonrs.
 */
objelonct elonxcludelonSoftUselonrGatelon elonxtelonnds Gatelon[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("elonxcludelonSoftUselonr")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] = {
    val softUselonr = quelonry.felonaturelons
      .elonxists(_.gelontOrelonlselon(UselonrTypelonFelonaturelon, Nonelon).elonxists(_ == t.UselonrTypelon.Soft))
    Stitch.valuelon(!softUselonr)
  }
}
