packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.{
  Long2Bigelonndian,
  ScalaBinaryThrift,
  ScalaCompactThrift
}
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  PelonrsistelondFullClustelonrId,
  SimClustelonrselonmbelondding,
  SimClustelonrselonmbelonddingId,
  TopProducelonrsWithScorelon,
  TopSimClustelonrsWithScorelon
}

objelonct ProducelonrelonmbelonddingsInjelonctions {
  final val ProducelonrTopKSimClustelonrelonmbelonddingsInjelonction: KelonyValInjelonction[
    Long,
    TopSimClustelonrsWithScorelon
  ] =
    KelonyValInjelonction(
      kelonyCodelonc = Long2Bigelonndian,
      valuelonCodelonc = ScalaCompactThrift(TopSimClustelonrsWithScorelon))

  final val SimClustelonrelonmbelonddingTopKProducelonrsInjelonction: KelonyValInjelonction[
    PelonrsistelondFullClustelonrId,
    TopProducelonrsWithScorelon
  ] =
    KelonyValInjelonction(
      kelonyCodelonc = ScalaCompactThrift(PelonrsistelondFullClustelonrId),
      valuelonCodelonc = ScalaCompactThrift(TopProducelonrsWithScorelon))

  final val SimilarUselonrsInjelonction: KelonyValInjelonction[Long, Candidatelons] =
    KelonyValInjelonction(kelonyCodelonc = Long2Bigelonndian, valuelonCodelonc = ScalaCompactThrift(Candidatelons))

  final val ProducelonrSimClustelonrselonmbelonddingInjelonction: KelonyValInjelonction[
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding
  ] =
    KelonyValInjelonction(
      kelonyCodelonc = ScalaBinaryThrift(SimClustelonrselonmbelonddingId),
      valuelonCodelonc = ScalaBinaryThrift(SimClustelonrselonmbelondding))
}
