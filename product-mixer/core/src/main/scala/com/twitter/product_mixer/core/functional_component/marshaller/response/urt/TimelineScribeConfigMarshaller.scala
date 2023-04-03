packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonScribelonConfig
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonScribelonConfigMarshallelonr @Injelonct() () {

  delonf apply(timelonlinelonScribelonConfig: TimelonlinelonScribelonConfig): urt.TimelonlinelonScribelonConfig =
    urt.TimelonlinelonScribelonConfig(
      pagelon = timelonlinelonScribelonConfig.pagelon,
      selonction = timelonlinelonScribelonConfig.selonction,
      elonntityTokelonn = timelonlinelonScribelonConfig.elonntityTokelonn
    )
}
