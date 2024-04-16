package com.tw ter.ann.dataflow.offl ne

 mport com.tw ter.beam.sc mas.Sc maF eldNa 

case class FlatEmbedd ngData(
  @Sc maF eldNa ("ent y d") ent y d: Opt on[Long],
  @Sc maF eldNa ("embedd ng") embedd ng: Seq[Double])
    extends BaseEmbedd ngData
