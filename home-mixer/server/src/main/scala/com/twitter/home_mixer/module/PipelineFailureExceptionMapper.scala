packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.twittelonr.finatra.thrift.elonxcelonptions.elonxcelonptionMappelonr
import com.twittelonr.homelon_mixelonr.{thriftscala => t}
import com.twittelonr.injelonct.Logging
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.ProductDisablelond
import com.twittelonr.scroogelon.Thriftelonxcelonption
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton

@Singlelonton
class PipelonlinelonFailurelonelonxcelonptionMappelonr
    elonxtelonnds elonxcelonptionMappelonr[PipelonlinelonFailurelon, Thriftelonxcelonption]
    with Logging {

  delonf handlelonelonxcelonption(throwablelon: PipelonlinelonFailurelon): Futurelon[Thriftelonxcelonption] = {
    throwablelon match {
      // SlicelonSelonrvicelon (unlikelon UrtSelonrvicelon) throws an elonxcelonption whelonn thelon relonquelonstelond product is disablelond
      caselon PipelonlinelonFailurelon(ProductDisablelond, relonason, _, _) =>
        Futurelon.elonxcelonption(
          t.ValidationelonxcelonptionList(elonrrors =
            Selonq(t.Validationelonxcelonption(t.ValidationelonrrorCodelon.ProductDisablelond, relonason))))
      caselon _ =>
        elonrror("Unhandlelond PipelonlinelonFailurelon", throwablelon)
        Futurelon.elonxcelonption(throwablelon)
    }
  }
}
