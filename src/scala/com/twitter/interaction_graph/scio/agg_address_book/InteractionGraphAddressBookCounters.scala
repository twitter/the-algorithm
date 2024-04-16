package com.tw ter. nteract on_graph.sc o.agg_address_book

 mport com.spot fy.sc o.Sc o tr cs
 mport org.apac .beam.sdk. tr cs.Counter

tra   nteract onGraphAddressBookCountersTra  {
  val Na space = " nteract on Graph Address Book"

  def ema lFeature nc(): Un 

  def phoneFeature nc(): Un 

  def bothFeature nc(): Un 
}

/**
 * SC O counters are used to gat r run t   stat st cs
 */
case object  nteract onGraphAddressBookCounters extends  nteract onGraphAddressBookCountersTra  {
  val ema lFeatureCounter: Counter =
    Sc o tr cs.counter(Na space, "Ema l Feature")

  val phoneFeatureCounter: Counter =
    Sc o tr cs.counter(Na space, "Phone Feature")

  val bothFeatureCounter: Counter =
    Sc o tr cs.counter(Na space, "Both Feature")

  overr de def ema lFeature nc(): Un  = ema lFeatureCounter. nc()

  overr de def phoneFeature nc(): Un  = phoneFeatureCounter. nc()

  overr de def bothFeature nc(): Un  = bothFeatureCounter. nc()
}
