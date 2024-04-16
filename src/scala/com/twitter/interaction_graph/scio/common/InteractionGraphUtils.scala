package com.tw ter. nteract on_graph.sc o.common

 mport com.tw ter. nteract on_graph.thr ftscala.T  Ser esStat st cs

object  nteract onGraphUt ls {
  f nal val M N_FEATURE_VALUE = Math.pow(0.955, 60)
  f nal val MAX_DAYS_RETENT ON = 60L
  f nal val M LL SECONDS_PER_DAY = 1000 * 60 * 60 * 24

  def updateT  Ser esStat st cs(
    t  Ser esStat st cs: T  Ser esStat st cs,
    currValue: Double,
    alpha: Double
  ): T  Ser esStat st cs = {
    val numNonZeroDays = t  Ser esStat st cs.numNonZeroDays + 1

    val delta = currValue - t  Ser esStat st cs. an
    val updated an = t  Ser esStat st cs. an + delta / numNonZeroDays
    val m2ForVar ance = t  Ser esStat st cs.m2ForVar ance + delta * (currValue - updated an)
    val ewma = alpha * currValue + t  Ser esStat st cs.ewma

    t  Ser esStat st cs.copy(
       an = updated an,
      m2ForVar ance = m2ForVar ance,
      ewma = ewma,
      numNonZeroDays = numNonZeroDays
    )
  }

  def addToT  Ser esStat st cs(
    t  Ser esStat st cs: T  Ser esStat st cs,
    currValue: Double
  ): T  Ser esStat st cs = {
    t  Ser esStat st cs.copy(
       an = t  Ser esStat st cs. an + currValue,
      ewma = t  Ser esStat st cs.ewma + currValue
    )
  }

}
