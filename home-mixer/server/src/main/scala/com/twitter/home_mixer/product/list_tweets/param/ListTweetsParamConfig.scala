package com.tw ter.ho _m xer.product.l st_t ets.param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.l st_t ets.param.L stT etsParam.EnableAdsCand dateP pel neParam
 mport com.tw ter.ho _m xer.product.l st_t ets.param.L stT etsParam.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.product.l st_t ets.param.L stT etsParam.SupportedCl entFSNa 
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stT etsParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableL stT etsProduct
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val booleanFSOverr des =
    Seq(EnableAdsCand dateP pel neParam)

  overr de val bounded ntFSOverr des = Seq(
    ServerMaxResultsParam
  )
}
