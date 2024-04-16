package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

/**
 * A [[Cand dateS ce]] for gett ng Cand dates from Strato w re t 
 * Strato column's V ew  s [[StratoV ew]] and t  Value  s a [[StratoValue]]
 *
 * A `stratoResultTransfor r` must be def ned to convert t  [[StratoValue]]  nto a Seq of [[Cand date]]
 *
 *  f   need to extract features from t  [[StratoValue]] (l ke a cursor),
 * use [[StratoKeyV ewFetc rW hS ceFeaturesS ce]]  nstead.
 *
 * @tparam StratoKey t  column's Key type
 * @tparam StratoV ew t  column's V ew type
 * @tparam StratoValue t  column's Value type
 */
tra  StratoKeyV ewFetc rS ce[StratoKey, StratoV ew, StratoValue, Cand date]
    extends Cand dateS ce[StratoKeyV ew[StratoKey, StratoV ew], Cand date] {

  val fetc r: Fetc r[StratoKey, StratoV ew, StratoValue]

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
  protected def stratoResultTransfor r(
    stratoKey: StratoKey,
    stratoResult: StratoValue
  ): Seq[Cand date]

  overr de def apply(
    request: StratoKeyV ew[StratoKey, StratoV ew]
  ): St ch[Seq[Cand date]] = {
    fetc r
      .fetch(request.key, request.v ew)
      .map { result =>
        result.v
          .map((stratoResult: StratoValue) => stratoResultTransfor r(request.key, stratoResult))
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategor zer.Categor zeStratoExcept on)
  }
}
