package com.tw ter.t  l neranker.cl ents.content_features_cac 

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.cl ents. mcac _common._
 mport com.tw ter.t  l nes.content_features.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Durat on

/**
 * Content features w ll be stored by t et d
 */
class ContentFeatures mcac Bu lder(
  conf g: Storehaus mcac Conf g,
  ttl: Durat on,
  statsRece ver: StatsRece ver) {
  pr vate[t ] val scalaToThr ft nject on:  nject on[ContentFeatures, thr ft.ContentFeatures] =
     nject on.bu ld[ContentFeatures, thr ft.ContentFeatures](_.toThr ft)(
      ContentFeatures.tryFromThr ft)

  pr vate[t ] val thr ftToBytes nject on:  nject on[thr ft.ContentFeatures, Array[Byte]] =
    CompactScalaCodec(thr ft.ContentFeatures)

  pr vate[t ]  mpl c  val value nject on:  nject on[ContentFeatures, Array[Byte]] =
    scalaToThr ft nject on.andT n(thr ftToBytes nject on)

  pr vate[t ] val underly ngBu lder =
    new  mcac StoreBu lder[T et d, ContentFeatures](
      conf g = conf g,
      scopeNa  = "contentFeaturesCac ",
      statsRece ver = statsRece ver,
      ttl = ttl
    )

  def bu ld(): Store[T et d, ContentFeatures] = underly ngBu lder.bu ld()
}
