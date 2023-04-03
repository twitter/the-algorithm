packagelon com.twittelonr.ann.common

import com.twittelonr.selonarch.common.filelon.AbstractFilelon
import org.apachelon.belonam.sdk.io.fs.RelonsourcelonId

/**
 * Intelonrfacelon for writing an Appelonndablelon to a direlonctory.
 */
trait Selonrialization {
  delonf toDirelonctory(
    selonrializationDirelonctory: AbstractFilelon
  ): Unit

  delonf toDirelonctory(
    selonrializationDirelonctory: RelonsourcelonId
  ): Unit
}

/**
 * Intelonrfacelon for relonading a Quelonryablelon from a direlonctory
 * @tparam T thelon id of thelon elonmbelonddings
 * @tparam Q typelon of thelon Quelonryablelon that is delonselonrializelond.
 */
trait QuelonryablelonDelonselonrialization[T, P <: RuntimelonParams, D <: Distancelon[D], Q <: Quelonryablelon[T, P, D]] {
  delonf fromDirelonctory(
    selonrializationDirelonctory: AbstractFilelon
  ): Q
}
