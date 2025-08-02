package com.twitter.home_mitwitterer.functional_component.selector

import com.twitter.home_mitwitterer.functional_component.selector.UpdateNewTweetsPillDecoration.NumAvatars
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableNewTweetsPillAvatarsParam
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.component_library.model.presentation.urt.UrtItemPresentation
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ShowAlert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.StringCenter
import com.twitter.stringcenter.client.core.EtwitterternalString

object UpdateNewTweetsPillDecoration {
  val NumAvatars = 3
}

case class UpdateNewTweetsPillDecoration[Query <: PipelineQuery with HasDeviceContetwittert](
  override val pipelineScope: CandidateScope,
  stringCenter: StringCenter,
  seeNewTweetsString: EtwitterternalString,
  tweetedString: EtwitterternalString)
    etwittertends Selector[Query] {

  override def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val (alerts, otherCandidates) =
      remainingCandidates.partition(candidate =>
        candidate.isCandidateType[ShowAlertCandidate]() && pipelineScope.contains(candidate))
    val updatedCandidates = alerts
      .collectFirst {
        case newTweetsPill: ItemCandidateWithDetails =>
          val userIds = CandidatesUtil
            .getItemCandidatesWithOnlyModuleLast(result)
            .filter(candidate =>
              candidate.isCandidateType[TweetCandidate]() && pipelineScope.contains(candidate))
            .filterNot(_.features.getOrElse(IsRetweetFeature, false))
            .flatMap(_.features.getOrElse(AuthorIdFeature, None))
            .filterNot(_ == query.getRequiredUserId)
            .distinct

          val updatedPresentation = newTweetsPill.presentation.map {
            case presentation: UrtItemPresentation =>
              presentation.timelineItem match {
                case alert: ShowAlert =>
                  val tetwittert = if (useAvatars(query, userIds)) tweetedString else seeNewTweetsString
                  val richTetwittert = RichTetwittert(
                    tetwittert = stringCenter.prepare(tetwittert),
                    entities = List.empty,
                    rtl = None,
                    alignment = None)

                  val updatedAlert =
                    alert.copy(userIds = Some(userIds.take(NumAvatars)), richTetwittert = Some(richTetwittert))
                  presentation.copy(timelineItem = updatedAlert)
              }
          }
          otherCandidates :+ newTweetsPill.copy(presentation = updatedPresentation)
      }.getOrElse(remainingCandidates)

    SelectorResult(remainingCandidates = updatedCandidates, result = result)
  }

  private def useAvatars(query: Query, userIds: Seq[Long]): Boolean = {
    val enableAvatars = query.params(EnableNewTweetsPillAvatarsParam)
    enableAvatars && userIds.size >= NumAvatars
  }
}
