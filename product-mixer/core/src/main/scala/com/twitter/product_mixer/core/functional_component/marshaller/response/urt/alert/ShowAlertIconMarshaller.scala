packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.DownArrow
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtIcon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.UpArrow
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class ShowAlelonrtIconMarshallelonr @Injelonct() () {

  delonf apply(alelonrtIcon: ShowAlelonrtIcon): urt.ShowAlelonrtIcon = alelonrtIcon match {
    caselon UpArrow => urt.ShowAlelonrtIcon.UpArrow
    caselon DownArrow => urt.ShowAlelonrtIcon.DownArrow
  }
}
