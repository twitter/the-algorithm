package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.{
  Onl neAggregat onStoresTra ,
  RealT  AggregateStore
}

object T  l nesOnl neAggregat onConf g
    extends T  l nesOnl neAggregat onDef n  onsTra 
    w h Onl neAggregat onStoresTra  {

   mport T  l nesOnl neAggregat onS ces._

  overr de lazy val Product onStore = RealT  AggregateStore(
     mcac DataSet = "t  l nes_real_t  _aggregates",
     sProd = true,
    cac TTL = 5.days
  )

  overr de lazy val Stag ngStore = RealT  AggregateStore(
     mcac DataSet = "t mcac _t  l nes_real_t  _aggregates",
     sProd = false,
    cac TTL = 5.days
  )

  overr de lazy val  nputS ce = t  l nesOnl neAggregateS ce

  /**
   * AggregateToCompute: T  def nes t  complete set of aggregates to be
   *    computed by t  aggregat on job and to be stored  n  mcac .
   */
  overr de lazy val AggregatesToCompute = ProdAggregates ++ Stag ngAggregates
}
