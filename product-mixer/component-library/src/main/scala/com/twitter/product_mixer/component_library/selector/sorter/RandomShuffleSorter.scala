packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import scala.util.Random

/**
 * Randomly shufflelons candidatelons using thelon providelond [[random]]
 *
 * @elonxamplelon `UpdatelonSortRelonsults(RandomShufflelonSortelonr())`
 * @param random uselond to selont thelon selonelond and for elonaselon of telonsting, in most caselons lelonaving it as thelon delonfault is finelon.
 */
caselon class RandomShufflelonSortelonr(random: Random = nelonw Random(0)) elonxtelonnds SortelonrProvidelonr with Sortelonr {

  ovelonrridelon delonf sort[Candidatelon <: CandidatelonWithDelontails](candidatelons: Selonq[Candidatelon]): Selonq[Candidatelon] =
    random.shufflelon(candidatelons)
}
