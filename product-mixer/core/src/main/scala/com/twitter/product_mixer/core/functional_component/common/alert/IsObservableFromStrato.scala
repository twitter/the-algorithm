package com.tw ter.product_m xer.core.funct onal_component.common.alert

/**
 *  nd cates that an [[Alert]] can be passed to [[StratoColumnAlert]]. Not all [[Alert]]s can be
 * Strato alerts s nce   ab l y to observe from Strato's perspect ve  s l m ed by t  ava lable
 *  tr cs.
 *
 * @note can only be used  n conjunct on w h [[Alert]]
 */
tra   sObservableFromStrato { _: Alert => }
