package com.tw ter. nteract on_graph.sc o.agg_address_book

 mport com.tw ter.beam. o.dal.DALOpt ons
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport org.apac .beam.sdk.opt ons.Default
 mport org.apac .beam.sdk.opt ons.Descr pt on
 mport org.apac .beam.sdk.opt ons.Val dat on.Requ red

tra   nteract onGraphAddressBookOpt on extends DALOpt ons w h DateRangeOpt ons {
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
}
