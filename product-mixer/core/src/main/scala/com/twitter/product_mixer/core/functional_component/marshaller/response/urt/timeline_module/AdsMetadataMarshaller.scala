packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.AdsMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AdsMelontadataMarshallelonr @Injelonct() () {

  delonf apply(adsMelontadata: AdsMelontadata): urt.AdsMelontadata =
    urt.AdsMelontadata(carouselonlId = adsMelontadata.carouselonlId)
}
