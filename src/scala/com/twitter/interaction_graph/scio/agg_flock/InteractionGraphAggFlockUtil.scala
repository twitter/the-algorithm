package com.tw ter. nteract on_graph.sc o.agg_flock

 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.algeb rd.M n
 mport com.tw ter.flockdb.tools.datasets.flock.thr ftscala.FlockEdge
 mport com.tw ter. nteract on_graph.sc o.common. nteract onGraphRaw nput
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport java.t  . nstant
 mport java.t  .temporal.ChronoUn 
 mport org.joda.t  . nterval

object  nteract onGraphAggFlockUt l {

  def getFlockFeatures(
    edges: SCollect on[FlockEdge],
    featureNa : FeatureNa ,
    date nterval:  nterval
  ): SCollect on[ nteract onGraphRaw nput] = {
    edges
      .w hNa (s"${featureNa .toStr ng} - Convert ng flock edge to  nteract on graph  nput")
      .map { edge =>
        // NOTE: getUpdatedAt g ves t    n t  seconds resolut on
        // Because   use .extend() w n read ng t  data s ce, t  updatedAt t   m ght be larger than t  dateRange.
        //   need to cap t m, ot rw se, DateUt l.d ffDays g ves  ncorrect results.
        val start = (edge.updatedAt * 1000L).m n(date nterval.getEnd.to nstant.getM ll s)
        val end = date nterval.getStart.to nstant.getM ll s
        val age = ChronoUn .DAYS.bet en(
           nstant.ofEpochM ll (start),
           nstant.ofEpochM ll (end)
        ) + 1
         nteract onGraphRaw nput(edge.s ce d, edge.dest nat on d, featureNa , age.to nt, 1.0)
      }

  }

  def getMutualFollowFeature(
    flockFollowFeature: SCollect on[ nteract onGraphRaw nput]
  ): SCollect on[ nteract onGraphRaw nput] = {
    flockFollowFeature
      .w hNa ("Convert FlockFollows to Mutual Follows")
      .map {  nput =>
        val s ce d =  nput.src
        val dest d =  nput.dst

         f (s ce d < dest d) {
          Tuple2(s ce d, dest d) -> Tuple2(Set(true), M n( nput.age)) // true  ans follow
        } else {
          Tuple2(dest d, s ce d) -> Tuple2(Set(false), M n( nput.age)) // false  ans follo d_by
        }
      }
      .sumByKey
      .flatMap {
        case (( d1,  d2), (followSet, m nAge))  f followSet.s ze == 2 =>
          val age = m nAge.get
          Seq(
             nteract onGraphRaw nput( d1,  d2, FeatureNa .NumMutualFollows, age, 1.0),
             nteract onGraphRaw nput( d2,  d1, FeatureNa .NumMutualFollows, age, 1.0))
        case _ =>
          N l
      }
  }

}
