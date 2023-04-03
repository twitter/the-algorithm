packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelonrankelonr.modelonl.Timelonlinelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.TimelonlinelonQuelonry
import com.twittelonr.util.Futurelon

trait TimelonlinelonRelonpository {
  delonf gelont(quelonrielons: Selonq[TimelonlinelonQuelonry]): Selonq[Futurelon[Timelonlinelon]]
  delonf gelont(quelonry: TimelonlinelonQuelonry): Futurelon[Timelonlinelon] = gelont(Selonq(quelonry)).helonad
}

class elonmptyTimelonlinelonRelonpository elonxtelonnds TimelonlinelonRelonpository {
  delonf gelont(quelonrielons: Selonq[TimelonlinelonQuelonry]): Selonq[Futurelon[Timelonlinelon]] = {
    quelonrielons.map(q => Futurelon.valuelon(Timelonlinelon.elonmpty(q.id)))
  }
}
