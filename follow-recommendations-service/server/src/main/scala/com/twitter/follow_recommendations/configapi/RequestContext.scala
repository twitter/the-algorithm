packagelon com.twittelonr.follow_reloncommelonndations.configapi

import com.twittelonr.timelonlinelons.configapi.BaselonRelonquelonstContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.NullFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.GuelonstId
import com.twittelonr.timelonlinelons.configapi.UselonrId
import com.twittelonr.timelonlinelons.configapi.WithFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.WithGuelonstId
import com.twittelonr.timelonlinelons.configapi.WithUselonrId

caselon class RelonquelonstContelonxt(
  uselonrId: Option[UselonrId],
  guelonstId: Option[GuelonstId],
  felonaturelonContelonxt: FelonaturelonContelonxt = NullFelonaturelonContelonxt)
    elonxtelonnds BaselonRelonquelonstContelonxt
    with WithUselonrId
    with WithGuelonstId
    with WithFelonaturelonContelonxt
