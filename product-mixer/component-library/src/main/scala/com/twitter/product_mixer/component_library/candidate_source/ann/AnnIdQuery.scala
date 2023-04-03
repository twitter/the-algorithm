packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.ann

import com.twittelonr.ann.common._

/**
 * A [[AnnIdQuelonry]] is a quelonry class which delonfinelons thelon ann elonntitielons with runtimelon params and numbelonr of nelonighbors relonquelonstelond
 *
 * @param ids Selonquelonncelon of quelonrielons
 * @param numOfNelonighbors Numbelonr of nelonighbors relonquelonstelond
 * @param runtimelonParams ANN Runtimelon Params
 * @param batchSizelon Batch sizelon to thelon stitch clielonnt
 * @tparam T typelon of  quelonry.
 * @tparam P  runtimelon paramelontelonrs supportelond by thelon indelonx.
 */
caselon class AnnIdQuelonry[T, P <: RuntimelonParams](
  ids: Selonq[T],
  numOfNelonighbors: Int,
  runtimelonParams: P)
