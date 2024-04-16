package com.tw ter.product_m xer.core.product

 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry.ParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.BooleanDec derParam

tra  ProductParamConf g extends ParamConf g w h ProductParamConf gBu lder {

  /**
   * T  enabled dec der param can to be used to qu ckly d sable a Product v a Dec der
   *
   * T  value must correspond to t  dec ders conf gured  n t  `res ces/conf g/dec der.yml` f le
   */
  val enabledDec derKey: Dec derKeyNa 

  /**
   * T  supported cl ent feature sw ch param can be used w h a Feature Sw ch to control t 
   * rollout of a new Product from dogfood to exper  nt to product on
   *
   * FeatureSw c s are conf gured by def n ng both a [[com.tw ter.t  l nes.conf gap .Param]]  n code
   * and  n an assoc ated `.yml` f le  n t  __conf g repo__.
   *
   * T  `.yml` f le path  s determ ned by t  `feature_sw c s_path`  n y  aurora f le and tge Product na 
   * so t  result ng path  n t  __conf g repo__  s essent ally `s"{feature_sw c s_path}/{snakeCase(Product. dent f er)}"`
   */
  val supportedCl entFSNa : Str ng

  object EnabledDec derParam extends BooleanDec derParam(enabledDec derKey)

  object SupportedCl entParam
      extends FSParam(
        na  = supportedCl entFSNa ,
        default = false
      )
}
