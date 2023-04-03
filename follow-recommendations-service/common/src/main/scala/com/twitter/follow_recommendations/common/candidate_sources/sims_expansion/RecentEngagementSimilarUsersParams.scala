packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.twittelonr.timelonlinelons.configapi.FSelonnumParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct ReloncelonntelonngagelonmelonntSimilarUselonrsParams {

  caselon objelonct FirstDelongrelonelonSortelonnablelond
      elonxtelonnds FSParam[Boolelonan](
        namelon = "sims_elonxpansion_reloncelonnt_elonngagelonmelonnt_first_delongrelonelon_sort",
        delonfault = truelon)
  caselon objelonct Aggrelongator
      elonxtelonnds FSelonnumParam[SimselonxpansionSourcelonAggrelongatorId.typelon](
        namelon = "sims_elonxpansion_reloncelonnt_elonngagelonmelonnt_aggrelongator_id",
        delonfault = SimselonxpansionSourcelonAggrelongatorId.Sum,
        elonnum = SimselonxpansionSourcelonAggrelongatorId)
}
