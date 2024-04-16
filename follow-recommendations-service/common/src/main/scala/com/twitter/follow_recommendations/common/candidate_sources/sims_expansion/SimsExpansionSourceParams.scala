package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on
 mport com.tw ter.t  l nes.conf gap .FSEnumParam

object S msExpans onS ceParams {
  case object Aggregator
      extends FSEnumParam[S msExpans onS ceAggregator d.type](
        na  = "s ms_expans on_aggregator_ d",
        default = S msExpans onS ceAggregator d.Sum,
        enum = S msExpans onS ceAggregator d)
}

object S msExpans onS ceAggregator d extends Enu rat on {
  type Aggregator d = Value
  val Sum: Aggregator d = Value("sum")
  val Max: Aggregator d = Value("max")
  val Mult Decay: Aggregator d = Value("mult _decay")
}
