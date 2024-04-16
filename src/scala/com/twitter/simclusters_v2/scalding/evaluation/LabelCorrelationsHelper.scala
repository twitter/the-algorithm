package com.tw ter.s mclusters_v2.scald ng.evaluat on

 mport com.tw ter.algeb rd.AveragedValue
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l

/**
 * Ut l y object for correlat on  asures bet en t  algor hm scores and t  user engage nts,
 * such as t  number of L kes.
 */
object LabelCorrelat ons lper {

  pr vate def toDouble(bool: Boolean): Double = {
     f (bool) 1.0 else 0.0
  }

  /**
   * G ven a p pe of labeled t ets, calculate t  cos ne s m lar y bet en t  algor hm scores
   * and users' favor e engage nts.
   */
  def cos neS m lar yForL ke(labeledT ets: TypedP pe[LabeledT et]): Execut on[Double] = {
    labeledT ets
      .map { t et => (toDouble(t et.labels. sL ked), t et.algor hmScore.getOrElse(0.0)) }
      .to erableExecut on.map {  er => Ut l.cos neS m lar y( er. erator) }
  }

  /**
   * G ven a p pe of labeled t ets, calculate cos ne s m lar y bet en algor hm score and users'
   * favor es engage nts, on a per user bas s, and return t  average of all cos ne
   * s m lar  es across all users.
   */
  def cos neS m lar yForL kePerUser(labeledT ets: TypedP pe[LabeledT et]): Execut on[Double] = {
    val avg = AveragedValue.aggregator.composePrepare[(Un , Double)](_._2)

    labeledT ets
      .map { t et =>
        (
          t et.targetUser d,
          Seq((toDouble(t et.labels. sL ked), t et.algor hmScore.getOrElse(0.0)))
        )
      }
      .sumByKey
      .map {
        case (user d, seq) =>
          ((), Ut l.cos neS m lar y(seq. erator))
      }
      .aggregate(avg)
      .getOrElseExecut on(0.0)
  }

  /**
   * Calculates t  Pearson correlat on coeff c ent for t  algor hm scores and user's favor e
   * engage nt. Note t  funct on call tr ggers a wr eToD sk execut on.
   */
  def pearsonCoeff c entForL ke(labeledT ets: TypedP pe[LabeledT et]): Execut on[Double] = {
    labeledT ets
      .map { t et => (toDouble(t et.labels. sL ked), t et.algor hmScore.getOrElse(0.0)) }
      .to erableExecut on.map {  er => Ut l.computeCorrelat on( er. erator) }
  }
}
