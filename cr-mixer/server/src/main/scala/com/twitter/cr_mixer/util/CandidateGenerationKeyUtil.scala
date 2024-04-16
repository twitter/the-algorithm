package com.tw ter.cr_m xer.ut l

 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.thr ftscala.Cand dateGenerat onKey
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng ne
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.ut l.T  

object Cand dateGenerat onKeyUt l {
  pr vate val PlaceholderUser d = 0L // t  default value w ll not be used

  pr vate val DefaultS ce nfo: S ce nfo = S ce nfo(
    s ceType = S ceType.RequestUser d,
    s ceEventT   = None,
     nternal d =  nternal d.User d(PlaceholderUser d)
  )

  def toThr ft(
    cand dateGenerat on nfo: Cand dateGenerat on nfo,
    requestUser d: User d
  ): Cand dateGenerat onKey = {
    Cand dateGenerat onKey(
      s ceType = cand dateGenerat on nfo.s ce nfoOpt.getOrElse(DefaultS ce nfo).s ceType,
      s ceEventT   = cand dateGenerat on nfo.s ce nfoOpt
        .getOrElse(DefaultS ce nfo).s ceEventT  .getOrElse(T  .fromM ll seconds(0L)). nM ll s,
       d = cand dateGenerat on nfo.s ce nfoOpt
        .map(_. nternal d).getOrElse( nternal d.User d(requestUser d)),
      model d = cand dateGenerat on nfo.s m lar yEng ne nfo.model d.getOrElse(""),
      s m lar yEng neType =
        So (cand dateGenerat on nfo.s m lar yEng ne nfo.s m lar yEng neType),
      contr but ngS m lar yEng ne =
        So (cand dateGenerat on nfo.contr but ngS m lar yEng nes.map(se =>
          S m lar yEng ne(se.s m lar yEng neType, se.model d, se.score)))
    )
  }
}
