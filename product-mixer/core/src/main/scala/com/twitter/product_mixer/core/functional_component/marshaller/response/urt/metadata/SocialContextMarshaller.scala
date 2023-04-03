packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxt
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SocialContelonxtMarshallelonr @Injelonct() (
  gelonnelonralContelonxtMarshallelonr: GelonnelonralContelonxtMarshallelonr,
  topicContelonxtMarshallelonr: TopicContelonxtMarshallelonr) {

  delonf apply(socialContelonxt: SocialContelonxt): urt.SocialContelonxt =
    socialContelonxt match {
      caselon gelonnelonralContelonxtBannelonr: GelonnelonralContelonxt =>
        gelonnelonralContelonxtMarshallelonr(gelonnelonralContelonxtBannelonr)
      caselon topicContelonxtBannelonr: TopicContelonxt =>
        topicContelonxtMarshallelonr(topicContelonxtBannelonr)
    }
}
