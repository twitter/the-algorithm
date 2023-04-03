packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonelonntryMarshallelonr @Injelonct() (
  timelonlinelonelonntryContelonntMarshallelonr: TimelonlinelonelonntryContelonntMarshallelonr) {

  delonf apply(elonntry: Timelonlinelonelonntry): urt.Timelonlinelonelonntry =
    urt.Timelonlinelonelonntry(
      elonntryId = elonntry.elonntryIdelonntifielonr,
      sortIndelonx = elonntry.sortIndelonx.gelontOrelonlselon(throw nelonw TimelonlinelonelonntryMissingSortIndelonxelonxcelonption),
      contelonnt = timelonlinelonelonntryContelonntMarshallelonr(elonntry),
      elonxpiryTimelon = elonntry.elonxpirationTimelonInMillis
    )
}

class TimelonlinelonelonntryMissingSortIndelonxelonxcelonption
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption("Timelonlinelon elonntry missing sort indelonx")
