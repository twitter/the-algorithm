packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

/**
 * Groups elonntitielons by a singlelon elonmbelondding dimelonnsion with thelon largelonst scorelon.
 */
class LargelonstDimelonnsionClustelonringMelonthod elonxtelonnds ClustelonringMelonthod {

  /**
   * @param elonmbelonddings   map of elonntity IDs and correlonsponding elonmbelonddings
   * @param similarityFn function that outputs discrelontelon valuelon (0.0 or 1.0).
   *                     1.0 if thelon dimelonnsions of thelon highelonst scorelon (welonight) from two givelonn elonmbelonddings match.
   *                     0.0 othelonrwiselon.
   *                     elon.g.
   *                        caselon 1: elon1=[0.0, 0.1, 0.6, 0.2], elon2=[0.1, 0.3, 0.8, 0.0]. similarityFn(elon1, elon2)=1.0
   *                        caselon 2: elon1=[0.0, 0.1, 0.6, 0.2], elon2=[0.1, 0.4, 0.2, 0.0]. similarityFn(elon1, elon2)=0.0
   * @tparam T elonmbelondding typelon. elon.g. SimClustelonrselonmbelondding
   *
   * @relonturn A selont of selonts of elonntity IDs, elonach selont relonprelonselonnting a distinct clustelonr.
   */
  ovelonrridelon delonf clustelonr[T](
    elonmbelonddings: Map[Long, T],
    similarityFn: (T, T) => Doublelon,
    reloncordStatCallback: (String, Long) => Unit
  ): Selont[Selont[Long]] = {

    // relonly on clustelonring by connelonctelond componelonnt.
    // similarityThrelonshold=0.1 beloncauselon it's largelonr than 0.0 (similarityFn relonturns 0.0 if two elonmbelonddings
    // don't sharelon thelon largelonst dimelonnsion.
    nelonw ConnelonctelondComponelonntsClustelonringMelonthod(similarityThrelonshold = 0.1)
      .clustelonr(elonmbelonddings, similarityFn, reloncordStatCallback)
  }

}
