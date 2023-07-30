package com.X.tweetypie.repository

import com.X.container.thriftscala.MaterializeAsTweetFieldsRequest
import com.X.container.thriftscala.MaterializeAsTweetRequest
import com.X.container.{thriftscala => ccs}
import com.X.stitch.SeqGroup
import com.X.stitch.Stitch
import com.X.tweetypie.Return
import com.X.tweetypie.{thriftscala => tp}
import com.X.tweetypie.backends
import com.X.tweetypie.thriftscala.GetTweetFieldsResult
import com.X.tweetypie.thriftscala.GetTweetResult
import com.X.util.Future
import com.X.util.Try

/**
 * A special kind of tweet is that, when [[tp.Tweet.underlyingCreativesContainerId]] is presented.
 * tweetypie will delegate hydration of this tweet to creatives container service.
 */
object CreativesContainerMaterializationRepository {

  type GetTweetType =
    (ccs.MaterializeAsTweetRequest, Option[tp.GetTweetOptions]) => Stitch[tp.GetTweetResult]

  type GetTweetFieldsType =
    (
      ccs.MaterializeAsTweetFieldsRequest,
      tp.GetTweetFieldsOptions
    ) => Stitch[tp.GetTweetFieldsResult]

  def apply(
    materializeAsTweet: backends.CreativesContainerService.MaterializeAsTweet
  ): GetTweetType = {
    case class RequestGroup(opts: Option[tp.GetTweetOptions])
        extends SeqGroup[ccs.MaterializeAsTweetRequest, tp.GetTweetResult] {
      override protected def run(
        keys: Seq[MaterializeAsTweetRequest]
      ): Future[Seq[Try[GetTweetResult]]] =
        materializeAsTweet(ccs.MaterializeAsTweetRequests(keys, opts)).map {
          res: Seq[GetTweetResult] => res.map(Return(_))
        }
    }

    (request, options) => Stitch.call(request, RequestGroup(options))
  }

  def materializeAsTweetFields(
    materializeAsTweetFields: backends.CreativesContainerService.MaterializeAsTweetFields
  ): GetTweetFieldsType = {
    case class RequestGroup(opts: tp.GetTweetFieldsOptions)
        extends SeqGroup[ccs.MaterializeAsTweetFieldsRequest, tp.GetTweetFieldsResult] {
      override protected def run(
        keys: Seq[MaterializeAsTweetFieldsRequest]
      ): Future[Seq[Try[GetTweetFieldsResult]]] =
        materializeAsTweetFields(ccs.MaterializeAsTweetFieldsRequests(keys, opts)).map {
          res: Seq[GetTweetFieldsResult] => res.map(Return(_))
        }
    }

    (request, options) => Stitch.call(request, RequestGroup(options))
  }
}
