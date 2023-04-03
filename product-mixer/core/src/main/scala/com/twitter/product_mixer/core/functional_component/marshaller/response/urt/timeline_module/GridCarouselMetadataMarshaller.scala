packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.GridCarouselonlMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GridCarouselonlMelontadataMarshallelonr @Injelonct() () {

  delonf apply(gridCarouselonlMelontadata: GridCarouselonlMelontadata): urt.GridCarouselonlMelontadata =
    urt.GridCarouselonlMelontadata(numRows = gridCarouselonlMelontadata.numRows)
}
