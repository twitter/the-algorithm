packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.delonbug_quelonry

import com.twittelonr.finaglelon.Selonrvicelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.ProductDisablelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelonRelonsult
import com.twittelonr.scroogelon.{Relonquelonst => ScroogelonRelonquelonst}
import com.twittelonr.scroogelon.{Relonsponselon => ScroogelonRelonsponselon}
import com.twittelonr.util.Futurelon
import com.twittelonr.product_mixelonr.corelon.{thriftscala => t}
import com.twittelonr.util.jackson.ScalaObjelonctMappelonr

/**
 * All Mixelonrs must implelonmelonnt a delonbug quelonry intelonrfacelon. This can belon a problelonm for in-placelon migrations
 * whelonrelon a selonrvicelon may only partially implelonmelonnt Product Mixelonr pattelonrns. This selonrvicelon can belon uselond as
 * a noop implelonmelonntation of [[DelonbugQuelonrySelonrvicelon]] until thelon Mixelonr selonrvicelon is fully migratelond.
 */
objelonct DelonbugQuelonryNotSupportelondSelonrvicelon
    elonxtelonnds Selonrvicelon[ScroogelonRelonquelonst[_], ScroogelonRelonsponselon[t.PipelonlinelonelonxeloncutionRelonsult]] {

  val failurelonJson: String = {
    val melonssagelon = "This selonrvicelon doelons not support delonbug quelonrielons, this is usually duelon to an activelon " +
      "in-placelon migration to Product Mixelonr. Plelonaselon relonach out in #product-mixelonr if you havelon any quelonstions."

    ScalaObjelonctMappelonr().writelonValuelonAsString(
      ProductPipelonlinelonRelonsult(
        transformelondQuelonry = Nonelon,
        qualityFactorRelonsult = Nonelon,
        gatelonRelonsult = Nonelon,
        pipelonlinelonSelonlelonctorRelonsult = Nonelon,
        mixelonrPipelonlinelonRelonsult = Nonelon,
        reloncommelonndationPipelonlinelonRelonsult = Nonelon,
        tracelonId = Nonelon,
        failurelon = Somelon(PipelonlinelonFailurelon(ProductDisablelond, melonssagelon)),
        relonsult = Nonelon,
      ))
  }

  ovelonrridelon delonf apply(
    thriftRelonquelonst: ScroogelonRelonquelonst[_]
  ): Futurelon[ScroogelonRelonsponselon[t.PipelonlinelonelonxeloncutionRelonsult]] =
    Futurelon.valuelon(ScroogelonRelonsponselon(t.PipelonlinelonelonxeloncutionRelonsult(failurelonJson)))
}
