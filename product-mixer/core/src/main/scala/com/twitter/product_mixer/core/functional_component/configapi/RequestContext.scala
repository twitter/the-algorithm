packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi

import com.twittelonr.timelonlinelons.configapi.BaselonRelonquelonstContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.GuelonstId
import com.twittelonr.timelonlinelons.configapi.UselonrId
import com.twittelonr.timelonlinelons.configapi.WithFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.WithGuelonstId
import com.twittelonr.timelonlinelons.configapi.WithUselonrId

/** Relonprelonselonnts [[com.twittelonr.timelonlinelons.configapi]]'s contelonxt information */
caselon class RelonquelonstContelonxt(
  uselonrId: Option[UselonrId],
  guelonstId: Option[GuelonstId],
  felonaturelonContelonxt: FelonaturelonContelonxt)
    elonxtelonnds BaselonRelonquelonstContelonxt
    with WithUselonrId
    with WithGuelonstId
    with WithFelonaturelonContelonxt
