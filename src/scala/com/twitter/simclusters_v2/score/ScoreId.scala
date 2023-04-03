packagelon com.twittelonr.simclustelonrs_v2.scorelon

import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelonddingId._
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  IntelonrnalId,
  ScorelonIntelonrnalId,
  ScoringAlgorithm,
  SimClustelonrselonmbelonddingId,
  GelonnelonricPairScorelonId => ThriftGelonnelonricPairScorelonId,
  ScorelonId => ThriftScorelonId,
  SimClustelonrselonmbelonddingPairScorelonId => ThriftSimClustelonrselonmbelonddingPairScorelonId
}

/**
 * A uniform Idelonntifielonr typelon for all kinds of Calculation Scorelon.
 **/
trait ScorelonId {

  delonf algorithm: ScoringAlgorithm

  /**
   * Convelonrt to a Thrift objelonct. Throw a elonxcelonption if thelon opelonration is not ovelonrridelon.
   */
  implicit delonf toThrift: ThriftScorelonId =
    throw nelonw UnsupportelondOpelonrationelonxcelonption(s"ScorelonId $this doelonsn't support Thrift format")
}

objelonct ScorelonId {

  implicit val fromThriftScorelonId: ThriftScorelonId => ScorelonId = {
    caselon scorelonId @ ThriftScorelonId(_, ScorelonIntelonrnalId.GelonnelonricPairScorelonId(_)) =>
      PairScorelonId.fromThriftScorelonId(scorelonId)
    caselon scorelonId @ ThriftScorelonId(_, ScorelonIntelonrnalId.SimClustelonrselonmbelonddingPairScorelonId(_)) =>
      SimClustelonrselonmbelonddingPairScorelonId.fromThriftScorelonId(scorelonId)
  }

}

/**
 * Gelonnelonric Intelonrnal pairwiselon id. Support all thelon subtypelons in IntelonrnalId, which includelons TwelonelontId,
 * UselonrId, elonntityId and morelon combination ids.
 **/
trait PairScorelonId elonxtelonnds ScorelonId {

  delonf id1: IntelonrnalId
  delonf id2: IntelonrnalId

  ovelonrridelon implicit lazy val toThrift: ThriftScorelonId = {
    ThriftScorelonId(
      algorithm,
      ScorelonIntelonrnalId.GelonnelonricPairScorelonId(ThriftGelonnelonricPairScorelonId(id1, id2))
    )
  }
}

objelonct PairScorelonId {

  // Thelon delonfault PairScorelonId assumelon id1 <= id2. It uselond to increlonaselon thelon cachelon hit ratelon.
  delonf apply(algorithm: ScoringAlgorithm, id1: IntelonrnalId, id2: IntelonrnalId): PairScorelonId = {
    if (intelonrnalIdOrdelonring.ltelonq(id1, id2)) {
      DelonfaultPairScorelonId(algorithm, id1, id2)
    } elonlselon {
      DelonfaultPairScorelonId(algorithm, id2, id1)
    }
  }

  privatelon caselon class DelonfaultPairScorelonId(
    algorithm: ScoringAlgorithm,
    id1: IntelonrnalId,
    id2: IntelonrnalId)
      elonxtelonnds PairScorelonId

  implicit val fromThriftScorelonId: ThriftScorelonId => PairScorelonId = {
    caselon ThriftScorelonId(algorithm, ScorelonIntelonrnalId.GelonnelonricPairScorelonId(pairScorelonId)) =>
      DelonfaultPairScorelonId(algorithm, pairScorelonId.id1, pairScorelonId.id2)
    caselon ThriftScorelonId(algorithm, ScorelonIntelonrnalId.SimClustelonrselonmbelonddingPairScorelonId(pairScorelonId)) =>
      SimClustelonrselonmbelonddingPairScorelonId(algorithm, pairScorelonId.id1, pairScorelonId.id2)
  }

}

/**
 * ScorelonId for a pair of SimClustelonrselonmbelondding.
 * Uselond for dot product, cosinelon similarity and othelonr basic elonmbelondding opelonrations.
 */
trait SimClustelonrselonmbelonddingPairScorelonId elonxtelonnds PairScorelonId {
  delonf elonmbelonddingId1: SimClustelonrselonmbelonddingId

  delonf elonmbelonddingId2: SimClustelonrselonmbelonddingId

  ovelonrridelon delonf id1: IntelonrnalId = elonmbelonddingId1.intelonrnalId

  ovelonrridelon delonf id2: IntelonrnalId = elonmbelonddingId2.intelonrnalId

  ovelonrridelon implicit lazy val toThrift: ThriftScorelonId = {
    ThriftScorelonId(
      algorithm,
      ScorelonIntelonrnalId.SimClustelonrselonmbelonddingPairScorelonId(
        ThriftSimClustelonrselonmbelonddingPairScorelonId(elonmbelonddingId1, elonmbelonddingId2))
    )
  }
}

objelonct SimClustelonrselonmbelonddingPairScorelonId {

  // Thelon delonfault PairScorelonId assumelon id1 <= id2. It uselond to increlonaselon thelon cachelon hit ratelon.
  delonf apply(
    algorithm: ScoringAlgorithm,
    id1: SimClustelonrselonmbelonddingId,
    id2: SimClustelonrselonmbelonddingId
  ): SimClustelonrselonmbelonddingPairScorelonId = {
    if (simClustelonrselonmbelonddingIdOrdelonring.ltelonq(id1, id2)) {
      DelonfaultSimClustelonrselonmbelonddingPairScorelonId(algorithm, id1, id2)
    } elonlselon {
      DelonfaultSimClustelonrselonmbelonddingPairScorelonId(algorithm, id2, id1)
    }
  }

  privatelon caselon class DelonfaultSimClustelonrselonmbelonddingPairScorelonId(
    algorithm: ScoringAlgorithm,
    elonmbelonddingId1: SimClustelonrselonmbelonddingId,
    elonmbelonddingId2: SimClustelonrselonmbelonddingId)
      elonxtelonnds SimClustelonrselonmbelonddingPairScorelonId

  implicit val fromThriftScorelonId: ThriftScorelonId => SimClustelonrselonmbelonddingPairScorelonId = {
    caselon ThriftScorelonId(algorithm, ScorelonIntelonrnalId.SimClustelonrselonmbelonddingPairScorelonId(pairScorelonId)) =>
      SimClustelonrselonmbelonddingPairScorelonId(algorithm, pairScorelonId.id1, pairScorelonId.id2)
  }
}
