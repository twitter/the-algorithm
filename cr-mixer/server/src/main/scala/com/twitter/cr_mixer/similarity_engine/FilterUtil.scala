package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

object F lterUt l {

  /** Returns a l st of t ets that are generated less than `maxT etAgeH s` h s ago */
  def t etAgeF lter(
    cand dates: Seq[T etW hScore],
    maxT etAgeH s: Durat on
  ): Seq[T etW hScore] = {
    // T et  Ds are approx mately chronolog cal (see http://go/snowflake),
    // so   are bu ld ng t  earl est t et  d once
    // T  per-cand date log c  re t n be cand date.t et d > earl estPerm tedT et d, wh ch  s far c aper.
    // See @cyao's phab on CrM xer gener c age f lter for reference https://phabr cator.tw ter.b z/D903188
    val earl estT et d = Snowflake d.f rst dFor(T  .now - maxT etAgeH s)
    cand dates.f lter { cand date => cand date.t et d >= earl estT et d }
  }

  /** Returns a l st of t et s ces that are generated less than `maxT etAgeH s` h s ago */
  def t etS ceAgeF lter(
    cand dates: Seq[S ce nfo],
    maxT etS gnalAgeH sParam: Durat on
  ): Seq[S ce nfo] = {
    // T et  Ds are approx mately chronolog cal (see http://go/snowflake),
    // so   are bu ld ng t  earl est t et  d once
    // T  f lter appl es to s ce s gnals. So  cand date s ce calls can be avo ded  f s ce s gnals
    // can be f ltered.
    val earl estT et d = Snowflake d.f rst dFor(T  .now - maxT etS gnalAgeH sParam)
    cand dates.f lter { cand date =>
      cand date. nternal d match {
        case  nternal d.T et d(t et d) => t et d >= earl estT et d
        case _ => false
      }
    }
  }
}
