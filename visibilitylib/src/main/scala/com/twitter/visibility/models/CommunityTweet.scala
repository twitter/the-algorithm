package com.tw ter.v s b l y.models

 mport com.tw ter.t etyp e.thr ftscala.Commun  es
 mport com.tw ter.t etyp e.thr ftscala.T et

object Commun yT et {
  def getCommun y d(commun  es: Commun  es): Opt on[Commun y d] =
    commun  es.commun y ds. adOpt on

  def getCommun y d(t et: T et): Opt on[Commun y d] =
    t et.commun  es.flatMap(getCommun y d)

  def apply(t et: T et): Opt on[Commun yT et] =
    getCommun y d(t et).map { commun y d =>
      val author d = t et.coreData.get.user d
      Commun yT et(t et, commun y d, author d)
    }
}

case class Commun yT et(
  t et: T et,
  commun y d: Commun y d,
  author d: Long)
