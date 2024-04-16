package com.tw ter. nteract on_graph.sc o.agg_negat ve

 mport com.tw ter.beam. o.dal.DALOpt ons
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport org.apac .beam.sdk.opt ons.Descr pt on
 mport org.apac .beam.sdk.opt ons.Val dat on.Requ red

tra   nteract onGraphNegat veOpt on extends DALOpt ons w h DateRangeOpt ons {
  @Requ red
  @Descr pt on("Output path for stor ng t  f nal dataset")
  def getOutputPath: Str ng
  def setOutputPath(value: Str ng): Un 

  @Descr pt on("BQ dataset pref x")
  def getBqDataset: Str ng
  def setBqDataset(value: Str ng): Un 

}
