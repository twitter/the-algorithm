package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

/**
 * A [[Cand dateS ce]] for gett ng Cand dates from Strato w re t 
 * Strato column's V ew  s [[StratoV ew]] and t  Value  s a Seq of [[StratoResult]]
 *
 * @tparam StratoKey t  column's Key type
 * @tparam StratoV ew t  column's V ew type
 * @tparam StratoResult t  column's Value's Seq type
 */
tra  StratoKeyV ewFetc rSeqS ce[StratoKey, StratoV ew, StratoResult]
    extends Cand dateS ce[StratoKeyV ew[StratoKey, StratoV ew], StratoResult] {

  val fetc r: Fetc r[StratoKey, StratoV ew, Seq[StratoResult]]

  overr de def apply(
    request: StratoKeyV ew[StratoKey, StratoV ew]
  ): St ch[Seq[StratoResult]] = {
    fetc r
      .fetch(request.key, request.v ew)
      .map { result =>
        result.v
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategor zer.Categor zeStratoExcept on)
  }
}
