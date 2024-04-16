package com.tw ter.t etyp e
package hydrator

object Str pH ddenGeoCoord nates extends Mutat on[T et] {
  def apply(t et: T et): Opt on[T et] =
    for {
      coreData <- t et.coreData
      coords <- coreData.coord nates
       f !coords.d splay
      coreData2 = coreData.copy(coord nates = None)
    } y eld t et.copy(coreData = So (coreData2))
}
