packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ProductIdelonntifielonr

trait Product elonxtelonnds Componelonnt {

  /**
   * Idelonntifielonr namelons on products can belon uselond to crelonatelon Felonaturelon Switch rulelons by product,
   * which uselonful if buckelonting occurs in a componelonnt sharelond by multiplelon products.
   *
   * @selonelon [[com.twittelonr.product_mixelonr.corelon.product.ProductParamConfig.supportelondClielonntFSNamelon]]
   */
  ovelonrridelon val idelonntifielonr: ProductIdelonntifielonr

  /**
   * To support StringCelonntelonr, ovelonrridelon this val to `Somelon("namelon-of-string-celonntelonr-projelonct")` and
   * includelon thelon `ProductScopelonStringCelonntelonrModulelon` in thelon selonrvelonr's modulelons list
   */
  val stringCelonntelonrProjelonct: Option[String] = Nonelon
}

trait HasProduct {
  delonf product: Product
}
