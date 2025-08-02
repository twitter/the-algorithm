package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion
import com.twitter.timelines.configapi.FSEnumParam

object SimsEtwitterpansionSourceParams {
  case object Aggregator
      etwittertends FSEnumParam[SimsEtwitterpansionSourceAggregatorId.type](
        name = "sims_etwitterpansion_aggregator_id",
        default = SimsEtwitterpansionSourceAggregatorId.Sum,
        enum = SimsEtwitterpansionSourceAggregatorId)
}

object SimsEtwitterpansionSourceAggregatorId etwittertends Enumeration {
  type AggregatorId = Value
  val Sum: AggregatorId = Value("sum")
  val Matwitter: AggregatorId = Value("matwitter")
  val MultiDecay: AggregatorId = Value("multi_decay")
}
