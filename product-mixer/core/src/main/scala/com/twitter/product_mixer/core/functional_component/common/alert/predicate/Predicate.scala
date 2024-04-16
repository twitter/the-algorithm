package com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate

/**
 * [[Pred cate]]s w ll tr gger  f t   tr c's value  s past t 
 * `threshold` for `datapo ntsPastThreshold` or more datapo nts
 *  n a g ven `durat on`
 *
 * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate Pred cate]]
 */
tra  Pred cate {

  /** @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate OPERATOR]] */
  val operator: Operator

  /** @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate THRESHOLD]] */
  val threshold: Double

  /**
   * T  number of datapo nts  n a g ven durat on beyond t  threshold that w ll tr gger an alert
   * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate DATAPO NTS]]
   */
  val datapo ntsPastThreshold:  nt

  /**
   * @note  f us ng a [[ tr cGranular y]] of [[M nutes]] t n t  must be >= 3
   * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate DURAT ON]]
   */
  val durat on:  nt

  /**
   * Spec f es t   tr c granular y
   * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate DURAT ON]]
   */
  val  tr cGranular y:  tr cGranular y

  requ re(
    datapo ntsPastThreshold > 0,
    s"`datapo ntsPastThreshold` must be > 0 but got `datapo ntsPastThreshold` = $datapo ntsPastThreshold"
  )

  requ re(
    datapo ntsPastThreshold <= durat on,
    s"`datapo ntsPastThreshold` must be <= than `durat on. nM nutes` but got `datapo ntsPastThreshold` = $datapo ntsPastThreshold `durat on` = $durat on"
  )
  requ re(
     tr cGranular y != M nutes || durat on >= 3,
    s"Pred cate durat ons must be at least 3 m nutes but got $durat on"
  )
}

/** [[ThroughputPred cate]]s are pred cates that can tr gger w n t  throughput  s too low or h gh */
tra  ThroughputPred cate extends Pred cate
