packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.slicelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Relonquelonst
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelonRelonquelonst
import com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductPipelonlinelonRelongistry
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.graphql.thriftscala.SlicelonRelonsult
import com.twittelonr.timelonlinelons.configapi.Params

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import scala.relonflelonct.runtimelon.univelonrselon.TypelonTag

/**
 * Look up and elonxeloncutelon Slicelon products in thelon [[ProductPipelonlinelonRelongistry]]
 */
@Singlelonton
class SlicelonSelonrvicelon @Injelonct() (productPipelonlinelonRelongistry: ProductPipelonlinelonRelongistry) {

  delonf gelontSlicelonRelonsponselon[RelonquelonstTypelon <: Relonquelonst](
    relonquelonst: RelonquelonstTypelon,
    params: Params
  )(
    implicit relonquelonstTypelonTag: TypelonTag[RelonquelonstTypelon]
  ): Stitch[SlicelonRelonsult] =
    productPipelonlinelonRelongistry
      .gelontProductPipelonlinelon[RelonquelonstTypelon, SlicelonRelonsult](relonquelonst.product)
      .procelonss(ProductPipelonlinelonRelonquelonst(relonquelonst, params))
}
