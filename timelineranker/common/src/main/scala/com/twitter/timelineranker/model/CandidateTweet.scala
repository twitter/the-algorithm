package com.tw ter.t  l neranker.model

 mport com.tw ter.search.common.features.thr ftscala.Thr ftT etFeatures
 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.t etyp e.thr ftscala

object Cand dateT et {
  val DefaultFeatures: Thr ftT etFeatures = Thr ftT etFeatures()

  def fromThr ft(cand date: thr ft.Cand dateT et): Cand dateT et = {
    val t et: thr ftscala.T et = cand date.t et.getOrElse(
      throw new  llegalArgu ntExcept on(s"Cand dateT et.t et must have a value")
    )
    val features = cand date.features.getOrElse(
      throw new  llegalArgu ntExcept on(s"Cand dateT et.features must have a value")
    )

    Cand dateT et(HydratedT et(t et), features)
  }
}

/**
 * A cand date T et and assoc ated  nformat on.
 * Model object for Cand dateT et thr ft struct.
 */
case class Cand dateT et(hydratedT et: HydratedT et, features: Thr ftT etFeatures) {

  def toThr ft: thr ft.Cand dateT et = {
    thr ft.Cand dateT et(
      t et = So (hydratedT et.t et),
      features = So (features)
    )
  }
}
