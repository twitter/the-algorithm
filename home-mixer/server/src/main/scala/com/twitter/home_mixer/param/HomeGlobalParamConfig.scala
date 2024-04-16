package com.tw ter.ho _m xer.param

 mport com.tw ter.ho _m xer.param.Ho GlobalParams._
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry.GlobalParamConf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Reg ster Params that do not relate to a spec f c product. See GlobalParamConf g -> ParamConf g
 * for hooks to reg ster Params based on type.
 */
@S ngleton
class Ho GlobalParamConf g @ nject() () extends GlobalParamConf g {

  overr de val booleanFSOverr des = Seq(
    AdsD sable nject onBasedOnUserRoleParam,
    EnableAdvert serBrandSafetySett ngsFeatureHydratorParam,
    Enable mpress onBloomF lter,
    EnableNahFeedback nfoParam,
    EnableNewT etsP llAvatarsParam,
    EnableScr beServedCand datesParam,
    EnableSendScoresToCl ent,
    EnableSoc alContextParam,
  )

  overr de val bounded ntFSOverr des = Seq(
    MaxNumberReplace nstruct onsParam,
    T  l nesPers stenceStoreMaxEntr esPerCl ent,
  )

  overr de val boundedDoubleFSOverr des = Seq(
     mpress onBloomF lterFalsePos  veRateParam
  )
}
