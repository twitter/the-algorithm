package com.tw ter.t etyp e.federated.columns

 mport com.tw ter.t etyp e.{thr ftscala => thr ft}

object Hydrat onOpt ons {

  def wr ePathHydrat onOpt ons(
    cardsPlatformKey: Opt on[Str ng]
  ) =
    thr ft.Wr ePathHydrat onOpt ons(
      // T  GraphQL AP  extracts or "l fts" t  Ap T et.card reference f eld from t 
      // Ap T et.card.url returned by T etyp e. T etyp e's card hydrat on bus ness log c
      // selects t  s ngle correct Card URL by f rst mak ng Expandodo.getCards2 requests for
      // t  T et's cardReference, or all of t  T et's URL ent  es  n cases w re T et
      // does not have a stored cardReference, and t n select ng t  last of t  hydrated
      // cards returned by Expandodo.
       ncludeCards = true,
      cardsPlatformKey = cardsPlatformKey,
      // T  GraphQL AP  only supports quoted t et results formatted per go/s mplequotedt et.
      s mpleQuotedT et = true,
    )
}
