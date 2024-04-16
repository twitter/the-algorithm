package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

/**
 * A [[Cand dateS ce]] for gett ng Cand dates from Strato w re t 
 * Strato column's V ew  s [[Un ]] and t  Value  s a Seq of [[StratoResult]]
 *
 * @tparam StratoKey t  column's Key type
 * @tparam StratoResult t  column's Value's Seq type
 */
tra  StratoKeyFetc rSeqS ce[StratoKey, StratoResult]
    extends Cand dateS ce[StratoKey, StratoResult] {

  val fetc r: Fetc r[StratoKey, Un , Seq[StratoResult]]

  overr de def apply(key: StratoKey): St ch[Seq[StratoResult]] = {
    fetc r
      .fetch(key)
      .map { result =>
        result.v
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategor zer.Categor zeStratoExcept on)
  }
}
