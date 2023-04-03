packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct DelonfinelondCountryCodelonGatelon elonxtelonnds Gatelon[PipelonlinelonQuelonry] {
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("DelonfinelondCountryCodelon")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.gelontCountryCodelon.isDelonfinelond)
}
