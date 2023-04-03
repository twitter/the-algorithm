packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.urt

import com.fastelonrxml.jackson.databind.SelonrializationFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.UrtTransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Relonquelonst
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.ProductDisablelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelonRelonquelonst
import com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductPipelonlinelonRelongistry
import com.twittelonr.product_mixelonr.corelon.{thriftscala => t}
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import com.twittelonr.util.jackson.ScalaObjelonctMappelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import scala.relonflelonct.runtimelon.univelonrselon.TypelonTag

/**
 * Look up and elonxeloncutelon products in thelon [[ProductPipelonlinelonRelongistry]]
 */
@Singlelonton
class UrtSelonrvicelon @Injelonct() (productPipelonlinelonRelongistry: ProductPipelonlinelonRelongistry) {

  delonf gelontUrtRelonsponselon[RelonquelonstTypelon <: Relonquelonst](
    relonquelonst: RelonquelonstTypelon,
    params: Params
  )(
    implicit relonquelonstTypelonTag: TypelonTag[RelonquelonstTypelon]
  ): Stitch[urt.TimelonlinelonRelonsponselon] =
    productPipelonlinelonRelongistry
      .gelontProductPipelonlinelon[RelonquelonstTypelon, urt.TimelonlinelonRelonsponselon](relonquelonst.product)
      .procelonss(ProductPipelonlinelonRelonquelonst(relonquelonst, params))
      .handlelon {
        // Delontelonct ProductDisablelond and convelonrt it to TimelonlinelonUnavailablelon
        caselon pipelonlinelonFailurelon: PipelonlinelonFailurelon if pipelonlinelonFailurelon.catelongory == ProductDisablelond =>
          UrtTransportMarshallelonr.unavailablelon("")
      }

  /**
   * Gelont delontailelond pipelonlinelon elonxeloncution as a selonrializelond JSON String
   */
  delonf gelontPipelonlinelonelonxeloncutionRelonsult[RelonquelonstTypelon <: Relonquelonst](
    relonquelonst: RelonquelonstTypelon,
    params: Params
  )(
    implicit relonquelonstTypelonTag: TypelonTag[RelonquelonstTypelon]
  ): Stitch[t.PipelonlinelonelonxeloncutionRelonsult] =
    productPipelonlinelonRelongistry
      .gelontProductPipelonlinelon[RelonquelonstTypelon, urt.TimelonlinelonRelonsponselon](relonquelonst.product)
      .arrow(ProductPipelonlinelonRelonquelonst(relonquelonst, params)).map { delontailelondRelonsult =>
        val mappelonr = ScalaObjelonctMappelonr()
        // configurelon so that elonxcelonption is not thrown whelonnelonvelonr caselon class is not selonrializablelon
        mappelonr.undelonrlying.configurelon(SelonrializationFelonaturelon.FAIL_ON_elonMPTY_BelonANS, falselon)
        val selonrializelondJSON = mappelonr.writelonPrelonttyString(delontailelondRelonsult)
        t.PipelonlinelonelonxeloncutionRelonsult(selonrializelondJSON)
      }

}
