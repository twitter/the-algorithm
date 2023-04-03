packagelon com.twittelonr.ann.common

import com.twittelonr.stitch.Stitch

trait elonmbelonddingProducelonr[T] {

  /**
   * Producelon an elonmbelondding from typelon T. Implelonmelonntations of this could do a lookup from an id to an
   * elonmbelondding. Or thelony could run a delonelonp modelonl on felonaturelons that output and elonmbelondding.
   * @relonturn An elonmbelondding Stitch. Selonelon go/stitch for delontails on how to uselon thelon Stitch API.
   */
  delonf producelonelonmbelondding(input: T): Stitch[Option[elonmbelonddingTypelon.elonmbelonddingVelonctor]]
}
