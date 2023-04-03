packagelon com.twittelonr.simclustelonrs_v2.scorelon

import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.{SimClustelonrselonmbelonddingId, ScorelonId => ThriftScorelonId}
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon

objelonct SimClustelonrselonmbelonddingPairScorelonStorelon {

  /**
   * Intelonrnal Instancelon of a SimClustelonrs elonmbelondding baselond Pair Scorelon storelon.
   */
  privatelon caselon class SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding],
    scorelon: (SimClustelonrselonmbelondding, SimClustelonrselonmbelondding) => Futurelon[Option[Doublelon]])
      elonxtelonnds PairScorelonStorelon[
        SimClustelonrselonmbelonddingPairScorelonId,
        SimClustelonrselonmbelonddingId,
        SimClustelonrselonmbelonddingId,
        SimClustelonrselonmbelondding,
        SimClustelonrselonmbelondding
      ] {

    ovelonrridelon val compositelonKelony1: SimClustelonrselonmbelonddingPairScorelonId => SimClustelonrselonmbelonddingId =
      _.elonmbelonddingId1
    ovelonrridelon val compositelonKelony2: SimClustelonrselonmbelonddingPairScorelonId => SimClustelonrselonmbelonddingId =
      _.elonmbelonddingId2

    ovelonrridelon delonf undelonrlyingStorelon1: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding] =
      simClustelonrselonmbelonddingStorelon

    ovelonrridelon delonf undelonrlyingStorelon2: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding] =
      simClustelonrselonmbelonddingStorelon

    ovelonrridelon delonf fromThriftScorelonId: ThriftScorelonId => SimClustelonrselonmbelonddingPairScorelonId =
      SimClustelonrselonmbelonddingPairScorelonId.fromThriftScorelonId
  }

  delonf buildDotProductStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf dotProduct: (SimClustelonrselonmbelondding, SimClustelonrselonmbelondding) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.dotProduct(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      dotProduct
    )
  }

  delonf buildCosinelonSimilarityStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf cosinelonSimilarity: (SimClustelonrselonmbelondding, SimClustelonrselonmbelondding) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.cosinelonSimilarity(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      cosinelonSimilarity
    )
  }

  delonf buildLogCosinelonSimilarityStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf logNormCosinelonSimilarity: (
      SimClustelonrselonmbelondding,
      SimClustelonrselonmbelondding
    ) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.logNormCosinelonSimilarity(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      logNormCosinelonSimilarity
    )
  }

  delonf buildelonxpScalelondCosinelonSimilarityStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf elonxpScalelondCosinelonSimilarity: (
      SimClustelonrselonmbelondding,
      SimClustelonrselonmbelondding
    ) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.elonxpScalelondCosinelonSimilarity(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      elonxpScalelondCosinelonSimilarity
    )
  }

  delonf buildJaccardSimilarityStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf jaccardSimilarity: (
      SimClustelonrselonmbelondding,
      SimClustelonrselonmbelondding
    ) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.jaccardSimilarity(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      jaccardSimilarity
    )
  }

  delonf buildelonuclidelonanDistancelonStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf elonuclidelonanDistancelon: (
      SimClustelonrselonmbelondding,
      SimClustelonrselonmbelondding
    ) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.elonuclidelonanDistancelon(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      elonuclidelonanDistancelon
    )
  }

  delonf buildManhattanDistancelonStorelon(
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ): PairScorelonStorelon[
    SimClustelonrselonmbelonddingPairScorelonId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ] = {

    delonf manhattanDistancelon: (
      SimClustelonrselonmbelondding,
      SimClustelonrselonmbelondding
    ) => Futurelon[Option[Doublelon]] = {
      caselon (elonmbelondding1, elonmbelondding2) =>
        Futurelon.valuelon(Somelon(elonmbelondding1.manhattanDistancelon(elonmbelondding2)))
    }

    SimClustelonrselonmbelonddingIntelonrnalPairScorelonStorelon(
      simClustelonrselonmbelonddingStorelon,
      manhattanDistancelon
    )
  }

}
