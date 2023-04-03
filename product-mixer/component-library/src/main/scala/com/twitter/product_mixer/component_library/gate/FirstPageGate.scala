packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Gatelon uselond in first pagelon. Uselon relonquelonst cursor to delontelonrminelon if thelon gatelon should belon opelonn or closelond.
 */
objelonct FirstPagelonGatelon elonxtelonnds Gatelon[PipelonlinelonQuelonry with HasPipelonlinelonCursor[_]] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("FirstPagelon")

  // If cursor is first pagelon, thelonn gatelon should relonturn continuelon, othelonrwiselon relonturn stop
  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_]): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.isFirstPagelon)
}
