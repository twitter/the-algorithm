packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common

import com.twittelonr.ann.common.{Distancelon, Quelonryablelon, RuntimelonParams}
import com.twittelonr.selonarch.common.filelon.AbstractFilelon

trait QuelonryablelonProvidelonr[T, P <: RuntimelonParams, D <: Distancelon[D]] {
  delonf providelonQuelonryablelon(indelonxDir: AbstractFilelon): Quelonryablelon[T, P, D]
}
