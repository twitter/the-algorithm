package com.twitter.product_mitwitterer.component_library.candidate_source.social_graph

import com.twitter.product_mitwitterer.component_library.model.candidate.CursorType
import com.twitter.product_mitwitterer.component_library.model.candidate.NetwittertCursor
import com.twitter.product_mitwitterer.component_library.model.candidate.PreviousCursor
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.socialgraph.thriftscala
import com.twitter.socialgraph.thriftscala.IdsRequest
import com.twitter.socialgraph.thriftscala.IdsResult
import com.twitter.socialgraph.util.ByteBufferUtil
import com.twitter.strato.client.Fetcher
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

sealed trait SocialgraphResponse
case class SocialgraphResult(id: Long) etwittertends SocialgraphResponse
case class SocialgraphCursor(cursor: Long, cursorType: CursorType) etwittertends SocialgraphResponse

@Singleton
class SocialgraphCandidateSource @Inject() (
  override val fetcher: Fetcher[thriftscala.IdsRequest, Option[
    thriftscala.RequestContetwittert
  ], thriftscala.IdsResult])
    etwittertends StratoKeyViewFetcherSource[
      thriftscala.IdsRequest,
      Option[thriftscala.RequestContetwittert],
      thriftscala.IdsResult,
      SocialgraphResponse
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("Socialgraph")

  override def stratoResultTransformer(
    stratoKey: IdsRequest,
    stratoResult: IdsResult
  ): Seq[SocialgraphResponse] = {
    val prevCursor =
      SocialgraphCursor(ByteBufferUtil.toLong(stratoResult.pageResult.prevCursor), PreviousCursor)
    /* When an end cursor is passed to Socialgraph,
     * Socialgraph returns the start cursor. To prevent
     * clients from circularly fetching the timeline again,
     * if we see a start cursor returned from Socialgraph,
     * we replace it with an end cursor.
     */
    val netwittertCursor = ByteBufferUtil.toLong(stratoResult.pageResult.netwittertCursor) match {
      case SocialgraphCursorConstants.StartCursor =>
        SocialgraphCursor(SocialgraphCursorConstants.EndCursor, NetwittertCursor)
      case cursor => SocialgraphCursor(cursor, NetwittertCursor)
    }

    stratoResult.ids
      .map { id =>
        SocialgraphResult(id)
      } ++ Seq(netwittertCursor, prevCursor)
  }
}
