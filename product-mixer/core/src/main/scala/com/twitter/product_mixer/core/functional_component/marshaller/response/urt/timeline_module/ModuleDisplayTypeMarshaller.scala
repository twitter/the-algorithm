packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.Carouselonl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.CompactCarouselonl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ConvelonrsationTrelonelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.GridCarouselonl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.Velonrtical
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.VelonrticalConvelonrsation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.VelonrticalWithContelonxtLinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.VelonrticalGrid
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class ModulelonDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(displayTypelon: ModulelonDisplayTypelon): urt.ModulelonDisplayTypelon = displayTypelon match {
    caselon Velonrtical => urt.ModulelonDisplayTypelon.Velonrtical
    caselon Carouselonl => urt.ModulelonDisplayTypelon.Carouselonl
    caselon VelonrticalWithContelonxtLinelon => urt.ModulelonDisplayTypelon.VelonrticalWithContelonxtLinelon
    caselon VelonrticalConvelonrsation => urt.ModulelonDisplayTypelon.VelonrticalConvelonrsation
    caselon ConvelonrsationTrelonelon => urt.ModulelonDisplayTypelon.ConvelonrsationTrelonelon
    caselon GridCarouselonl => urt.ModulelonDisplayTypelon.GridCarouselonl
    caselon CompactCarouselonl => urt.ModulelonDisplayTypelon.CompactCarouselonl
    caselon VelonrticalGrid => urt.ModulelonDisplayTypelon.VelonrticalGrid
  }
}
