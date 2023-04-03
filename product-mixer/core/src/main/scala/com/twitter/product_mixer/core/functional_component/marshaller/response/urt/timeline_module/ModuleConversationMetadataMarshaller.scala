packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonConvelonrsationMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonConvelonrsationMelontadataMarshallelonr @Injelonct() (
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr) {

  delonf apply(
    modulelonConvelonrsationMelontadata: ModulelonConvelonrsationMelontadata
  ): urt.ModulelonConvelonrsationMelontadata = urt.ModulelonConvelonrsationMelontadata(
    allTwelonelontIds = modulelonConvelonrsationMelontadata.allTwelonelontIds,
    socialContelonxt = modulelonConvelonrsationMelontadata.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
    elonnablelonDelonduplication = modulelonConvelonrsationMelontadata.elonnablelonDelonduplication
  )
}
