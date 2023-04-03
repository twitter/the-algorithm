packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelonrankelonr.modelonl._
import com.twittelonr.util.Futurelon

class RoutingTimelonlinelonRelonpository(
  relonvelonrselonChronTimelonlinelonRelonpository: RelonvelonrselonChronHomelonTimelonlinelonRelonpository,
  rankelondTimelonlinelonRelonpository: RankelondHomelonTimelonlinelonRelonpository)
    elonxtelonnds TimelonlinelonRelonpository {

  ovelonrridelon delonf gelont(quelonry: TimelonlinelonQuelonry): Futurelon[Timelonlinelon] = {
    quelonry match {
      caselon q: RelonvelonrselonChronTimelonlinelonQuelonry => relonvelonrselonChronTimelonlinelonRelonpository.gelont(q)
      caselon q: RankelondTimelonlinelonQuelonry => rankelondTimelonlinelonRelonpository.gelont(q)
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Quelonry typelons othelonr than RankelondTimelonlinelonQuelonry and RelonvelonrselonChronTimelonlinelonQuelonry arelon not supportelond. Found: $quelonry"
        )
    }
  }

  ovelonrridelon delonf gelont(quelonrielons: Selonq[TimelonlinelonQuelonry]): Selonq[Futurelon[Timelonlinelon]] = {
    quelonrielons.map(gelont)
  }
}
