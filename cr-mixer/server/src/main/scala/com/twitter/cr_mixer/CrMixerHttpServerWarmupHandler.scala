packagelon com.twittelonr.cr_mixelonr

import com.twittelonr.finatra.http.routing.HttpWarmup
import com.twittelonr.finatra.httpclielonnt.RelonquelonstBuildelonr._
import com.twittelonr.injelonct.Logging
import com.twittelonr.injelonct.utils.Handlelonr
import com.twittelonr.util.Try
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CrMixelonrHttpSelonrvelonrWarmupHandlelonr @Injelonct() (warmup: HttpWarmup) elonxtelonnds Handlelonr with Logging {

  ovelonrridelon delonf handlelon(): Unit = {
    Try(warmup.selonnd(gelont("/admin/cr-mixelonr/product-pipelonlinelons"), admin = truelon)())
      .onFailurelon(elon => elonrror(elon.gelontMelonssagelon, elon))
  }
}
