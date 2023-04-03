packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.quelonry.ads.AdsQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct NonelonmptyAdsQuelonryStringGatelon elonxtelonnds Gatelon[PipelonlinelonQuelonry with AdsQuelonry] {
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("NonelonmptyAdsQuelonryString")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry with AdsQuelonry): Stitch[Boolelonan] = {
    val quelonryString = quelonry.selonarchRelonquelonstContelonxt.flatMap(_.quelonryString)
    Stitch.valuelon(quelonryString.elonxists(_.trim.nonelonmpty))
  }
}
