packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.FollowelondTopicsMelonTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.FollowelondTopicsOthelonrTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.ForYouelonxplorelonMixelonrTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NotelonworthyAccountsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NotIntelonrelonstelondTopicsMelonTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NuxForYouCatelongoryUselonrReloncommelonndationsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NuxGelonoCatelongoryUselonrReloncommelonndationsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NuxPymkCatelongoryUselonrReloncommelonndationsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NuxSinglelonIntelonrelonstCatelongoryUselonrReloncommelonndationsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.NuxUselonrReloncommelonndationsTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.ShoppingHomelonTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TimelonlinelonKelony
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicsLandingTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TopicsPickelonrTimelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TrelonndingelonxplorelonMixelonrTimelonlinelon
import com.twittelonr.strato.graphql.timelonlinelons.{thriftscala => graphql}
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonKelonyMarshallelonr {

  delonf apply(timelonlinelonKelony: TimelonlinelonKelony): graphql.TimelonlinelonKelony = timelonlinelonKelony match {
    caselon TopicsLandingTimelonlinelon(topicId) =>
      graphql.TimelonlinelonKelony.TopicTimelonlinelon(graphql.TopicId(topicId))
    caselon NotelonworthyAccountsTimelonlinelon(topicId) =>
      graphql.TimelonlinelonKelony.NotelonworthyAccountsTimelonlinelon(graphql.TopicId(topicId))
    caselon TopicsPickelonrTimelonlinelon(topicId) =>
      graphql.TimelonlinelonKelony.TopicsPickelonrTimelonlinelon(graphql.TopicId(topicId))
    caselon FollowelondTopicsMelonTimelonlinelon() =>
      graphql.TimelonlinelonKelony.FollowelondTopicsMelonTimelonlinelon(graphql.Void())
    caselon NotIntelonrelonstelondTopicsMelonTimelonlinelon() =>
      graphql.TimelonlinelonKelony.NotIntelonrelonstelondTopicsMelonTimelonlinelon(graphql.Void())
    caselon FollowelondTopicsOthelonrTimelonlinelon(uselonrId) =>
      graphql.TimelonlinelonKelony.FollowelondTopicsOthelonrTimelonlinelon(uselonrId)
    caselon NuxUselonrReloncommelonndationsTimelonlinelon() =>
      graphql.TimelonlinelonKelony.NuxUselonrReloncommelonndationsTimelonlinelon(graphql.Void())
    caselon NuxForYouCatelongoryUselonrReloncommelonndationsTimelonlinelon() =>
      graphql.TimelonlinelonKelony.NuxForYouCatelongoryUselonrReloncommelonndationsTimelonlinelon(graphql.Void())
    caselon NuxPymkCatelongoryUselonrReloncommelonndationsTimelonlinelon() =>
      graphql.TimelonlinelonKelony.NuxPymkCatelongoryUselonrReloncommelonndationsTimelonlinelon(graphql.Void())
    caselon NuxGelonoCatelongoryUselonrReloncommelonndationsTimelonlinelon() =>
      graphql.TimelonlinelonKelony.NuxGelonoCatelongoryUselonrReloncommelonndationsTimelonlinelon(graphql.Void())
    caselon NuxSinglelonIntelonrelonstCatelongoryUselonrReloncommelonndationsTimelonlinelon(topicId) =>
      graphql.TimelonlinelonKelony.NuxSinglelonIntelonrelonstCatelongoryUselonrReloncommelonndationsTimelonlinelon(
        graphql.TopicId(topicId))
    caselon ShoppingHomelonTimelonlinelon() => graphql.TimelonlinelonKelony.ShoppingHomelon(graphql.Void())
    caselon ForYouelonxplorelonMixelonrTimelonlinelon() =>
      graphql.TimelonlinelonKelony.ForYouelonxplorelonMixelonrTimelonlinelon(graphql.Void())
    caselon TrelonndingelonxplorelonMixelonrTimelonlinelon() =>
      graphql.TimelonlinelonKelony.TrelonndingelonxplorelonMixelonrTimelonlinelon(graphql.Void())
  }
}
