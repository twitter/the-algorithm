packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color.RoselonttaColorMarshallelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtIconDisplayInfo

@Singlelonton
class ShowAlelonrtIconDisplayInfoMarshallelonr @Injelonct() (
  showAlelonrtIconMarshallelonr: ShowAlelonrtIconMarshallelonr,
  roselonttaColorMarshallelonr: RoselonttaColorMarshallelonr,
) {

  delonf apply(alelonrtIconDisplayInfo: ShowAlelonrtIconDisplayInfo): urt.ShowAlelonrtIconDisplayInfo =
    urt.ShowAlelonrtIconDisplayInfo(
      icon = showAlelonrtIconMarshallelonr(alelonrtIconDisplayInfo.icon),
      tint = roselonttaColorMarshallelonr(alelonrtIconDisplayInfo.tint),
    )

}
