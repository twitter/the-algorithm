packagelon com.twittelonr.follow_reloncommelonndations.configapi.candidatelons

import com.twittelonr.timelonlinelons.configapi.BaselonRelonquelonstContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.NullFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.WithFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.WithUselonrId

/**
 * relonprelonselonnt thelon contelonxt for a reloncommelonndation candidatelon (producelonr sidelon)
 * @param uselonrId id of thelon reloncommelonndelond uselonr
 * @param felonaturelonContelonxt felonaturelon contelonxt
 */
caselon class CandidatelonUselonrContelonxt(
  ovelonrridelon val uselonrId: Option[Long],
  felonaturelonContelonxt: FelonaturelonContelonxt = NullFelonaturelonContelonxt)
    elonxtelonnds BaselonRelonquelonstContelonxt
    with WithUselonrId
    with WithFelonaturelonContelonxt
