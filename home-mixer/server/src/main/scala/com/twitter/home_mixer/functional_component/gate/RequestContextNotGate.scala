packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt.RelonquelonstContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Gatelon that felontchelons thelon relonquelonst contelonxt from thelon delonvicelon contelonxt and
 * continuelons if thelon relonquelonst contelonxt doelons not match any of thelon speloncifielond onelons.
 *
 * If no input relonquelonst contelonxt is speloncifielond, thelon gatelon continuelons
 */
caselon class RelonquelonstContelonxtNotGatelon(relonquelonstContelonxts: Selonq[RelonquelonstContelonxt.Valuelon])
    elonxtelonnds Gatelon[PipelonlinelonQuelonry with HasDelonvicelonContelonxt] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("RelonquelonstContelonxtNot")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry with HasDelonvicelonContelonxt): Stitch[Boolelonan] =
    Stitch.valuelon(
      !relonquelonstContelonxts.elonxists(quelonry.delonvicelonContelonxt.flatMap(_.relonquelonstContelonxtValuelon).contains))
}
