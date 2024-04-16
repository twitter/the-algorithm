package com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate

/**
 * Spec f es t   tr c granular y
 *
 * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate DURAT ON]]
 */
sealed tra   tr cGranular y { val un : Str ng }

/**
 * Use m nutely  tr cs and have alert durat ons  n terms of m nutes
 *
 *  .e. for a [[Pred cate]]  f [[Pred cate.datapo ntsPastThreshold]] = 5 and [[Pred cate.durat on]] = 10
 * t n t  alert w ll tr gger  f t re are at least 5 '''m nutely'''  tr c po nts that are past t  threshold
 *  n any 10 '''m nute''' per od
 */
case object M nutes extends  tr cGranular y { overr de val un : Str ng = "m" }

/**
 * Use h ly  tr cs and have alert durat ons  n terms of h s
 *
 *  .e. for a [[Pred cate]]  f [[Pred cate.datapo ntsPastThreshold]] = 5 and [[Pred cate.durat on]] = 10
 * t n t  alert w ll tr gger  f t re are at least 5 '''h ly'''  tr c po nts that are past t  threshold
 *  n any 10 '''h ''' per od
 */
case object H s extends  tr cGranular y { overr de val un : Str ng = "h" }

/**
 * Use da ly  tr cs and have alert durat ons  n terms of days
 *
 *  .e. for a [[Pred cate]]  f [[Pred cate.datapo ntsPastThreshold]] = 5 and [[Pred cate.durat on]] = 10
 * t n t  alert w ll tr gger  f t re are at least 5 '''da ly'''  tr c po nts that are past t  threshold
 *  n any 10 '''day''' per od
 */
case object Days extends  tr cGranular y { overr de val un : Str ng = "d" }
