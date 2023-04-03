packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TitlelonNavBar
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TitlelonNavBarMarshallelonr @Injelonct() (
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr) {

  delonf apply(titlelonNavBar: TitlelonNavBar): urp.TitlelonNavBar =
    urp.TitlelonNavBar(
      titlelon = titlelonNavBar.titlelon,
      subtitlelon = titlelonNavBar.subtitlelon,
      clielonntelonvelonntInfo = titlelonNavBar.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_))
    )
}
