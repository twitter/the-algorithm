packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import scala.relonflelonct.runtimelon.univelonrselon._

caselon class NonelonmptySelonqFelonaturelonGatelon[T: TypelonTag](
  felonaturelon: Felonaturelon[PipelonlinelonQuelonry, Selonq[T]])
    elonxtelonnds Gatelon[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr(s"NonelonmptySelonq$felonaturelon")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.felonaturelons.elonxists(_.gelont(felonaturelon).nonelonmpty))
}
