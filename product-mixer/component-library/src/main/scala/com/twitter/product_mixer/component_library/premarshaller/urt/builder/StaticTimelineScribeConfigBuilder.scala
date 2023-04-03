packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonScribelonConfig
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class StaticTimelonlinelonScribelonConfigBuildelonr(
  timelonlinelonScribelonConfig: TimelonlinelonScribelonConfig)
    elonxtelonnds TimelonlinelonScribelonConfigBuildelonr[PipelonlinelonQuelonry] {

  delonf build(
    quelonry: PipelonlinelonQuelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Option[TimelonlinelonScribelonConfig] = Somelon(timelonlinelonScribelonConfig)
}
