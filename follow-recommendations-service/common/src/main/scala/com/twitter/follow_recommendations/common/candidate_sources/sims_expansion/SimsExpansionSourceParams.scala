packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion
import com.twittelonr.timelonlinelons.configapi.FSelonnumParam

objelonct SimselonxpansionSourcelonParams {
  caselon objelonct Aggrelongator
      elonxtelonnds FSelonnumParam[SimselonxpansionSourcelonAggrelongatorId.typelon](
        namelon = "sims_elonxpansion_aggrelongator_id",
        delonfault = SimselonxpansionSourcelonAggrelongatorId.Sum,
        elonnum = SimselonxpansionSourcelonAggrelongatorId)
}

objelonct SimselonxpansionSourcelonAggrelongatorId elonxtelonnds elonnumelonration {
  typelon AggrelongatorId = Valuelon
  val Sum: AggrelongatorId = Valuelon("sum")
  val Max: AggrelongatorId = Valuelon("max")
  val MultiDeloncay: AggrelongatorId = Valuelon("multi_deloncay")
}
