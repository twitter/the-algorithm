package com.tw ter.ho _m xer.product.subscr bed.param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.subscr bed.param.Subscr bedParam._
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Subscr bedParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableSubscr bedProduct
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val bounded ntFSOverr des = Seq(
    ServerMaxResultsParam
  )
}
