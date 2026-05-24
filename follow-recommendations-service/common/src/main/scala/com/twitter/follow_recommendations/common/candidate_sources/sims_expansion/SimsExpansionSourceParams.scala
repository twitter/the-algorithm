package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion
import com.twitter.timelines.configapi.FSEnumParam

object SimsExpansionSourceParams {
  case object Aggregator
      extends FSEnumParam[SimsExpansionSourceAggregatorId.type](
        name = "sims_expansion_aggregator_id",
        default = SimsExpansionSourceAggregatorId.Sum,
        enum = SimsExpansionSourceAggregatorId)
}

object SimsExpansionSourceAggregatorId extends Enumeration {
  type AggregatorId = Value
  val Sum: AggregatorId = Value("sum")
  val Max: AggregatorId = Value("max")
  val MultiDecay: AggregatorId = Value("multi_decay")
}
