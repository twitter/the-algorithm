package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object T  l neEntryEnvelope {
  def fromThr ft(entryEnvelope: thr ft.T  l neEntryEnvelope): T  l neEntryEnvelope = {
    T  l neEntryEnvelope(
      entry = T  l neEntry.fromThr ft(entryEnvelope.entry)
    )
  }
}

case class T  l neEntryEnvelope(entry: T  l neEntry) {

  throw f nval d()

  def toThr ft: thr ft.T  l neEntryEnvelope = {
    thr ft.T  l neEntryEnvelope(entry.toT  l neEntryThr ft)
  }

  def throw f nval d(): Un  = {
    entry.throw f nval d()
  }
}
