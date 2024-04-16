package com.tw ter. nteract on_graph.sc o.agg_all

 mport com.tw ter.beam. o.dal.DALOpt ons
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport org.apac .beam.sdk.opt ons.Default
 mport org.apac .beam.sdk.opt ons.Descr pt on
 mport org.apac .beam.sdk.opt ons.Val dat on.Requ red

tra   nteract onGraphAggregat onOpt on extends DALOpt ons w h DateRangeOpt ons {
  @Requ red
  @Descr pt on("Output path for stor ng t  f nal dataset")
  def getOutputPath: Str ng
  def setOutputPath(value: Str ng): Un 

  @Descr pt on(" nd cates DAL wr e env ron nt. Can be set to dev/stg dur ng local val dat on")
  @Default.Str ng("PROD")
  def getDALWr eEnv ron nt: Str ng
  def setDALWr eEnv ron nt(value: Str ng): Un 

  @Descr pt on("Number of shards/part  ons for sav ng t  f nal dataset.")
  @Default. nteger(16)
  def getNumberOfShards:  nteger
  def setNumberOfShards(value:  nteger): Un 

  @Descr pt on("BQ Table na  for read ng scores from")
  def getBqTableNa : Str ng
  def setBqTableNa (value: Str ng): Un 

  @Descr pt on("max dest nat on  ds that   w ll store for real graph features  n TL")
  def getMaxDest nat on ds:  nteger
  def setMaxDest nat on ds(value:  nteger): Un 

  @Descr pt on("true  f gett ng scores from BQ  nstead of DAL-based dataset  n GCS")
  def getScoresFromBQ: Boolean
  def setScoresFromBQ(value: Boolean): Un 
}
