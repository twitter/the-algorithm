packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding

/**
 * SimilarityFunctions providelon commonly uselond similarity functions that this clustelonring library nelonelonds.
 */
objelonct SimilarityFunctions {
  delonf simClustelonrsCosinelonSimilarity: (SimClustelonrselonmbelondding, SimClustelonrselonmbelondding) => Doublelon =
    (elon1, elon2) => elon1.cosinelonSimilarity(elon2)

  delonf simClustelonrsMatchingLargelonstDimelonnsion: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon = (elon1, elon2) => {
    val doelonsMatchLargelonstDimelonnsion: Boolelonan = elon1
      .topClustelonrIds(1)
      .elonxists { id1 =>
        elon2.topClustelonrIds(1).contains(id1)
      }

    if (doelonsMatchLargelonstDimelonnsion) 1.0
    elonlselon 0.0
  }

  delonf simClustelonrsFuzzyJaccardSimilarity: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon = (elon1, elon2) => {
    elon1.fuzzyJaccardSimilarity(elon2)
  }
}
