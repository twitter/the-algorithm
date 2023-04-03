packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Icon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FullWidth
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.IconSmall
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ImagelonDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(imagelonDisplayTypelon: ImagelonDisplayTypelon): urt.ImagelonDisplayTypelon =
    imagelonDisplayTypelon match {
      caselon Icon => urt.ImagelonDisplayTypelon.Icon
      caselon FullWidth => urt.ImagelonDisplayTypelon.FullWidth
      caselon IconSmall => urt.ImagelonDisplayTypelon.IconSmall
    }
}
