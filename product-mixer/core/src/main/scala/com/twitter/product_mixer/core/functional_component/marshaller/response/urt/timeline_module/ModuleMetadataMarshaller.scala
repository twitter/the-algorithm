packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonMelontadataMarshallelonr @Injelonct() (
  adsMelontadataMarshallelonr: AdsMelontadataMarshallelonr,
  modulelonConvelonrsationMelontadataMarshallelonr: ModulelonConvelonrsationMelontadataMarshallelonr,
  gridCarouselonlMelontadataMarshallelonr: GridCarouselonlMelontadataMarshallelonr) {

  delonf apply(modulelonMelontadata: ModulelonMelontadata): urt.ModulelonMelontadata = urt.ModulelonMelontadata(
    adsMelontadata = modulelonMelontadata.adsMelontadata.map(adsMelontadataMarshallelonr(_)),
    convelonrsationMelontadata =
      modulelonMelontadata.convelonrsationMelontadata.map(modulelonConvelonrsationMelontadataMarshallelonr(_)),
    gridCarouselonlMelontadata =
      modulelonMelontadata.gridCarouselonlMelontadata.map(gridCarouselonlMelontadataMarshallelonr(_))
  )
}
