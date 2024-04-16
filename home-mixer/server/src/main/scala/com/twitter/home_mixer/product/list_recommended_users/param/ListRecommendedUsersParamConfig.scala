package com.tw ter.ho _m xer.product.l st_recom nded_users.param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.Excluded dsMaxLengthParam
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.SupportedCl entFSNa 
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stRecom ndedUsersParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableL stRecom ndedUsersProduct
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val bounded ntFSOverr des = Seq(
    ServerMaxResultsParam,
    Excluded dsMaxLengthParam
  )
}
