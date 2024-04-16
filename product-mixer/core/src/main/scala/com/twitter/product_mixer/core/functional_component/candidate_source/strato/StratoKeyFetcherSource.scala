package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

/**
 * A [[Cand dateS ce]] for gett ng Cand dates from Strato w re t 
 * Strato column's V ew  s [[Un ]] and t  Value  s a [[StratoValue]]
 *
 * A `stratoResultTransfor r` must be def ned to convert t  [[StratoValue]]  nto a Seq of [[Cand date]]
 *
 *  f   need to extract features from t  [[StratoValue]] (l ke a cursor),
 * use [[StratoKeyFetc rW hS ceFeaturesS ce]]  nstead.
 *
 * @tparam StratoKey t  column's Key type
 * @tparam StratoValue t  column's Value type
 */
tra  StratoKeyFetc rS ce[StratoKey, StratoValue, Cand date]
    extends Cand dateS ce[StratoKey, Cand date] {

  val fetc r: Fetc r[StratoKey, Un , StratoValue]

  /**
   * Transforms t  value type returned by Strato  nto a Seq[Cand date].
   *
   * T  m ght be as s mple as `Seq(stratoResult)`  f   always return ng a s ngle cand date.
   *
   * Often,   just extracts a Seq from w h n a larger wrapper object.
   *
   *  f t re  s global  tadata that   need to  nclude,   can z p   w h t  cand dates,
   * return ng so th ng l ke Seq((cand ate,  tadata), (cand date,  tadata)) etc.
   */
  protected def stratoResultTransfor r(stratoResult: StratoValue): Seq[Cand date]

  overr de def apply(key: StratoKey): St ch[Seq[Cand date]] = {
    fetc r
      .fetch(key)
      .map { result =>
        result.v
          .map(stratoResultTransfor r)
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategor zer.Categor zeStratoExcept on)
  }
}
