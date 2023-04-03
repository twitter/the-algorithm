packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails

/**
 * Relonvelonrselon candidatelons.
 *
 * @elonxamplelon `UpdatelonSortRelonsults(RelonvelonrselonSortelonr())`
 */
objelonct RelonvelonrselonSortelonr elonxtelonnds SortelonrProvidelonr with Sortelonr {

  ovelonrridelon delonf sort[Candidatelon <: CandidatelonWithDelontails](candidatelons: Selonq[Candidatelon]): Selonq[Candidatelon] =
    candidatelons.relonvelonrselon
}
