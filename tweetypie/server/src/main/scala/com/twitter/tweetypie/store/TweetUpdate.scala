package com.tw ter.t etyp e
package store

 mport com.tw ter.t etyp e.thr ftscala._

object T etUpdate {

  /**
   * Cop es takedown  nformat on from t  s ce [[T et]]  nto [[Cac dT et]].
   *
   * Note that t   thod requ res t  s ce [[T et]] to have been loaded w h t  follow ng
   * add  onal f elds (wh ch happens for all paths that create [[Repl catedTakedown.Event]],  n
   * both [[TakedownHandler]] and [[UserTakedownHandler]]:
   * - T etyp eOnlyTakedownReasonsF eld
   * - T etyp eOnlyTakedownCountryCodesF eld
   * T   s done to ensure t  remote datacenter of a takedown does not  ncorrectly try to load
   * from MH as t  data  s already cac d.
   */
  def copyTakedownF eldsForUpdate(s ce: T et): Cac dT et => Cac dT et =
    ct => {
      val newCoreData = s ce.coreData.get
      val updatedCoreData = ct.t et.coreData.map(_.copy(hasTakedown = newCoreData.hasTakedown))
      ct.copy(
        t et = ct.t et.copy(
          coreData = updatedCoreData,
          t etyp eOnlyTakedownCountryCodes = s ce.t etyp eOnlyTakedownCountryCodes,
          t etyp eOnlyTakedownReasons = s ce.t etyp eOnlyTakedownReasons
        )
      )
    }

  def copyNsfwF eldsForUpdate(s ce: T et): T et => T et =
    t et => {
      val newCoreData = s ce.coreData.get
      val updatedCoreData =
        t et.coreData.map { core =>
          core.copy(nsfwUser = newCoreData.nsfwUser, nsfwAdm n = newCoreData.nsfwAdm n)
        }
      t et.copy(coreData = updatedCoreData)
    }
}
