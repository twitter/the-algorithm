packagelon com.twittelonr.product_mixelonr.corelon.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

caselon class ParamGatelon(namelon: String, param: Param[Boolelonan])(implicit filelon: sourceloncodelon.Filelon)
    elonxtelonnds Gatelon[PipelonlinelonQuelonry] {

  // From a customelonr-pelonrspelonctivelon, it's morelon uselonful to selonelon thelon filelon that crelonatelond thelon ParamGatelon
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr(namelon)(filelon)

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.params(param))
}

objelonct ParamGatelon {
  val elonnablelondGatelonSuffix = "elonnablelond"
  val SupportelondClielonntGatelonSuffix = "SupportelondClielonnt"
}
