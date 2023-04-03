packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelonrankelonr.modelonl.Timelonlinelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.TimelonlinelonQuelonry
import com.twittelonr.util.Futurelon

/**
 * A relonpository of rankelond homelon timelonlinelons.
 */
class RankelondHomelonTimelonlinelonRelonpository elonxtelonnds TimelonlinelonRelonpository {
  delonf gelont(quelonrielons: Selonq[TimelonlinelonQuelonry]): Selonq[Futurelon[Timelonlinelon]] = {
    quelonrielons.map { _ =>
      Futurelon.elonxcelonption(nelonw UnsupportelondOpelonrationelonxcelonption("rankelond timelonlinelons arelon not yelont supportelond."))
    }
  }
}
