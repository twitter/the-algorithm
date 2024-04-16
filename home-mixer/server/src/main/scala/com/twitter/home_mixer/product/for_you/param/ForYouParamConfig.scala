package com.tw ter.ho _m xer.product.for_ .param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.for_ .param.For Param._
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For ParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableFor Product
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val booleanDec derOverr des = Seq(
    EnableScoredT etsCand dateP pel neParam
  )

  overr de val booleanFSOverr des = Seq(
    ClearCac OnPtr.EnableParam,
    EnableFl p nject onModuleCand dateP pel neParam,
    EnablePushToHo M xerP pel neParam,
    EnableScoredT etsM xerP pel neParam,
    EnableServedCand dateKafkaPubl sh ngParam,
    EnableT  l neScorerCand dateP pel neParam,
    EnableTop cSoc alContextF lterParam,
    EnableVer f edAuthorSoc alContextBypassParam,
    EnableWhoToFollowCand dateP pel neParam,
    EnableWhoToSubscr beCand dateP pel neParam,
    EnableT etPrev ewsCand dateP pel neParam,
    EnableClearCac OnPushToHo 
  )

  overr de val bounded ntFSOverr des = Seq(
    AdsNumOrgan c emsParam,
    ClearCac OnPtr.M nEntr esParam,
    Fl p nl ne nject onModulePos  on,
    ServerMaxResultsParam,
    WhoToFollowPos  onParam,
    WhoToSubscr bePos  onParam,
    T etPrev ewsPos  onParam,
    T etPrev ewsMaxCand datesParam
  )

  overr de val str ngFSOverr des = Seq(
    WhoToFollowD splayLocat onParam,
    Exper  ntStatsParam
  )

  overr de val boundedDurat onFSOverr des = Seq(
    WhoToFollowM n nject on ntervalParam,
    WhoToSubscr beM n nject on ntervalParam,
    T etPrev ewsM n nject on ntervalParam
  )

  overr de val enumFSOverr des = Seq(
    WhoToFollowD splayType dParam,
    WhoToSubscr beD splayType dParam
  )
}
