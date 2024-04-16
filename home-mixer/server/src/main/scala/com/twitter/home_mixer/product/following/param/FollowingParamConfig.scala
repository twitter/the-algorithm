package com.tw ter.ho _m xer.product.follow ng.param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam._
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Follow ngParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableFollow ngProduct
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val booleanFSOverr des =
    Seq(
      EnableFl p nject onModuleCand dateP pel neParam,
      EnableWhoToFollowCand dateP pel neParam,
      EnableAdsCand dateP pel neParam,
      EnableFastAds,
    )

  overr de val bounded ntFSOverr des = Seq(
    Fl p nl ne nject onModulePos  on,
    WhoToFollowPos  onParam,
    ServerMaxResultsParam
  )

  overr de val str ngFSOverr des = Seq(WhoToFollowD splayLocat onParam)

  overr de val boundedDurat onFSOverr des = Seq(WhoToFollowM n nject on ntervalParam)

  overr de val enumFSOverr des = Seq(WhoToFollowD splayType dParam)
}
