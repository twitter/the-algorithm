packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonScribelonConfig

caselon class Pagelon(
  id: String,
  pagelonBody: PagelonBody,
  scribelonConfig: Option[TimelonlinelonScribelonConfig] = Nonelon,
  pagelonHelonadelonr: Option[PagelonHelonadelonr] = Nonelon,
  pagelonNavBar: Option[PagelonNavBar] = Nonelon)
    elonxtelonnds HasMarshalling
