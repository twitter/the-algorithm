packagelon com.twittelonr.visibility.configapi

import com.twittelonr.timelonlinelons.configapi._

caselon class VisibilityRelonquelonstContelonxt(
  uselonrId: Option[Long],
  guelonstId: Option[Long],
  elonxpelonrimelonntContelonxt: elonxpelonrimelonntContelonxt = NullelonxpelonrimelonntContelonxt,
  felonaturelonContelonxt: FelonaturelonContelonxt = NullFelonaturelonContelonxt)
    elonxtelonnds BaselonRelonquelonstContelonxt
    with WithUselonrId
    with WithGuelonstId
    with WithelonxpelonrimelonntContelonxt
    with WithFelonaturelonContelonxt
