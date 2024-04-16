package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object T  l neEntry {
  def fromThr ft(entry: thr ft.T  l neEntry): T  l neEntry = {
    entry match {
      case thr ft.T  l neEntry.T et(e) => T et.fromThr ft(e)
      case thr ft.T  l neEntry.T etyp eT et(e) => new HydratedT etEntry(e)
      case _ => throw new  llegalArgu ntExcept on(s"Unsupported type: $entry")
    }
  }
}

tra  T  l neEntry {
  def toT  l neEntryThr ft: thr ft.T  l neEntry
  def throw f nval d(): Un 
}
