package com.tw ter.ann.dataflow.offl ne

 mport com.tw ter.beam.sc mas.Sc maF eldNa 

case class GroupedEmbedd ngData(
  @Sc maF eldNa ("ent y d") ent y d: Opt on[Long],
  @Sc maF eldNa ("embedd ng") embedd ng: Seq[Double],
  @Sc maF eldNa ("group d") group d: Opt on[Str ng],
) extends BaseEmbedd ngData
