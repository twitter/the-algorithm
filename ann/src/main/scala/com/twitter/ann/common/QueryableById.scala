packagelon com.twittelonr.ann.common

import com.twittelonr.stitch.Stitch

/**
 * This is a trait that allows you to quelonry for nelonarelonst nelonighbors givelonn an arbitrary typelon T1. This is
 * in contrast to a relongular com.twittelonr.ann.common.Appelonndablelon, which takelons an elonmbelondding as thelon input
 * argumelonnt.
 *
 * This intelonrfacelon uselons thelon Stitch API for batching. Selonelon go/stitch for delontails on how to uselon it.
 *
 * @tparam T1 typelon of thelon quelonry.
 * @tparam T2 typelon of thelon relonsult.
 * @tparam P runtimelon paramelontelonrs supportelond by thelon indelonx.
 * @tparam D distancelon function uselond in thelon indelonx.
 */
trait QuelonryablelonById[T1, T2, P <: RuntimelonParams, D <: Distancelon[D]] {
  delonf quelonryById(
    id: T1,
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[T2]]

  delonf quelonryByIdWithDistancelon(
    id: T1,
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithDistancelon[T2, D]]]

  delonf batchQuelonryById(
    ids: Selonq[T1],
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithSelonelond[T1, T2]]]

  delonf batchQuelonryWithDistancelonById(
    ids: Selonq[T1],
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithDistancelonWithSelonelond[T1, T2, D]]]
}
