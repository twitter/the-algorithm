packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color.RoselonttaColorMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Badgelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class BadgelonMarshallelonr @Injelonct() (
  roselonttaColorMarshallelonr: RoselonttaColorMarshallelonr) {

  delonf apply(badgelon: Badgelon): urt.Badgelon = urt.Badgelon(
    telonxt = badgelon.telonxt,
    telonxtColorNamelon = badgelon.telonxtColorNamelon.map(roselonttaColorMarshallelonr(_)),
    backgroundColorNamelon = badgelon.backgroundColorNamelon.map(roselonttaColorMarshallelonr(_))
  )
}
