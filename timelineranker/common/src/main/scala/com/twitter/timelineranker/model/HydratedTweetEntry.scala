package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.t etyp e.{thr ftscala => t etyp e}

/**
 * Enables HydratedT et entr es to be  ncluded  n a T  l ne.
 */
class HydratedT etEntry(t et: t etyp e.T et) extends HydratedT et(t et) w h T  l neEntry {

  def t (hydratedT et: HydratedT et) = t (hydratedT et.t et)

  overr de def toT  l neEntryThr ft: thr ft.T  l neEntry = {
    thr ft.T  l neEntry.T etyp eT et(t et)
  }

  overr de def throw f nval d(): Un  = {
    // No val dat on perfor d.
  }
}
