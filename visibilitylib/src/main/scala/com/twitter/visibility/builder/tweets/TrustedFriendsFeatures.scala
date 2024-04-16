package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.TrustedFr endsL st d
 mport com.tw ter.v s b l y.common.TrustedFr endsS ce
 mport com.tw ter.v s b l y.features.T et sTrustedFr endT et
 mport com.tw ter.v s b l y.features.V e r sTrustedFr endOfT etAuthor
 mport com.tw ter.v s b l y.features.V e r sTrustedFr endT etAuthor

class TrustedFr endsFeatures(trustedFr endsS ce: TrustedFr endsS ce) {

  pr vate[bu lder] def v e r sTrustedFr end(
    t et: T et,
    v e r d: Opt on[Long]
  ): St ch[Boolean] =
    (trustedFr endsL st d(t et), v e r d) match {
      case (So (tfL st d), So (user d)) =>
        trustedFr endsS ce. sTrustedFr end(tfL st d, user d)
      case _ => St ch.False
    }

  pr vate[bu lder] def v e r sTrustedFr endL stOwner(
    t et: T et,
    v e r d: Opt on[Long]
  ): St ch[Boolean] =
    (trustedFr endsL st d(t et), v e r d) match {
      case (So (tfL st d), So (user d)) =>
        trustedFr endsS ce. sTrustedFr endL stOwner(tfL st d, user d)
      case _ => St ch.False
    }

  pr vate[bu lder] def trustedFr endsL st d(t et: T et): Opt on[TrustedFr endsL st d] =
    t et.trustedFr endsControl.map(_.trustedFr endsL st d)

  def forT et(
    t et: T et,
    v e r d: Opt on[Long]
  ): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(
      T et sTrustedFr endT et,
      t et.trustedFr endsControl. sDef ned
    ).w hFeature(
        V e r sTrustedFr endT etAuthor,
        v e r sTrustedFr endL stOwner(t et, v e r d)
      ).w hFeature(
        V e r sTrustedFr endOfT etAuthor,
        v e r sTrustedFr end(t et, v e r d)
      )
  }

  def forT etOnly(t et: T et): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(T et sTrustedFr endT et, t et.trustedFr endsControl. sDef ned)
  }

}
