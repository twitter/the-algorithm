package com.twitter.home_mitwitterer.federated

import com.twitter.gizmoduck.{thriftscala => gd}
import com.twitter.home_mitwitterer.marshaller.request.HomeMitwittererRequestUnmarshaller
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.{thriftscala => hm}
import com.twitter.product_mitwitterer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineResult
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.product_mitwitterer.core.{thriftscala => pm}
import com.twitter.stitch.Arrow
import com.twitter.stitch.Stitch
import com.twitter.strato.callcontetwittert.CallContetwittert
import com.twitter.strato.catalog.OpMetadata
import com.twitter.strato.config._
import com.twitter.strato.data._
import com.twitter.strato.fed.StratoFed
import com.twitter.strato.generated.client.auth_contetwittert.AuditIpClientColumn
import com.twitter.strato.generated.client.gizmoduck.CompositeOnUserClientColumn
import com.twitter.strato.graphql.timelines.{thriftscala => gql}
import com.twitter.strato.thrift.ScroogeConv
import com.twitter.timelines.render.{thriftscala => tr}
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererColumn @Inject() (
  homeMitwittererRequestUnmarshaller: HomeMitwittererRequestUnmarshaller,
  compositeOnUserClientColumn: CompositeOnUserClientColumn,
  auditIpClientColumn: AuditIpClientColumn,
  paramsBuilder: ParamsBuilder,
  productPipelineRegistry: ProductPipelineRegistry)
    etwittertends StratoFed.Column(HomeMitwittererColumn.Path)
    with StratoFed.Fetch.Arrow {

  override val contactInfo: ContactInfo = ContactInfo(
    contactEmail = "",
    ldapGroup = "",
    slackRoomId = ""
  )

  override val metadata: OpMetadata =
    OpMetadata(
      lifecycle = Some(Lifecycle.Production),
      description =
        Some(Description.PlainTetwittert("Federated Strato column for Timelines served via Home Mitwitterer"))
    )

  private val bouncerAccess: Seq[Policy] = Seq(BouncerAccess())
  private val finatraTestServiceIdentifiers: Seq[Policy] = Seq(
    ServiceIdentifierPattern(
      role = "",
      service = "",
      env = "",
      zone = Seq(""))
  )

  override val policy: Policy = AnyOf(bouncerAccess ++ finatraTestServiceIdentifiers)

  override type Key = gql.TimelineKey
  override type View = gql.HomeTimelineView
  override type Value = tr.Timeline

  override val keyConv: Conv[Key] = ScroogeConv.fromStruct[gql.TimelineKey]
  override val viewConv: Conv[View] = ScroogeConv.fromStruct[gql.HomeTimelineView]
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[tr.Timeline]

  private def createHomeMitwittererRequestArrow(
    compositeOnUserClientColumn: CompositeOnUserClientColumn,
    auditIpClientColumn: AuditIpClientColumn
  ): Arrow[(Key, View), hm.HomeMitwittererRequest] = {

    val populateUserRolesAndIp: Arrow[(Key, View), (Option[Set[String]], Option[String])] = {
      val gizmoduckView: (gd.LookupContetwittert, Set[gd.QueryFields]) =
        (gd.LookupContetwittert(), Set(gd.QueryFields.Roles))

      val populateUserRoles = Arrow
        .flatMap[(Key, View), Option[Set[String]]] { _ =>
          Stitch.collect {
            CallContetwittert.twitterUserId.map { userId =>
              compositeOnUserClientColumn.fetcher
                .callStack(HomeMitwittererColumn.FetchCallstack)
                .fetch(userId, gizmoduckView).map(_.v)
                .map {
                  _.flatMap(_.roles.map(_.roles.toSet)).getOrElse(Set.empty)
                }
            }
          }
        }

      val populateIpAddress = Arrow
        .flatMap[(Key, View), Option[String]](_ =>
          auditIpClientColumn.fetcher
            .callStack(HomeMitwittererColumn.FetchCallstack)
            .fetch((), ()).map(_.v))

      Arrow.join(
        populateUserRoles,
        populateIpAddress
      )
    }

    Arrow.zipWithArg(populateUserRolesAndIp).map {
      case ((key, view), (roles, ipAddress)) =>
        val deviceContetwittertOpt = Some(
          hm.DeviceContetwittert(
            isPolling = CallContetwittert.isPolling,
            requestContetwittert = view.requestContetwittert,
            latestControlAvailable = view.latestControlAvailable,
            autoplayEnabled = view.autoplayEnabled
          ))
        val seenTweetIds = view.seenTweetIds.filter(_.nonEmpty)

        val (product, productContetwittert) = key match {
          case gql.TimelineKey.HomeTimeline(_) | gql.TimelineKey.HomeTimelineV2(_) =>
            (
              hm.Product.ForYou,
              hm.ProductContetwittert.ForYou(
                hm.ForYou(
                  deviceContetwittertOpt,
                  seenTweetIds,
                  view.dspClientContetwittert,
                  view.pushToHomeTweetId
                )
              ))
          case gql.TimelineKey.HomeLatestTimeline(_) | gql.TimelineKey.HomeLatestTimelineV2(_) =>
            (
              hm.Product.Following,
              hm.ProductContetwittert.Following(
                hm.Following(deviceContetwittertOpt, seenTweetIds, view.dspClientContetwittert)))
          case gql.TimelineKey.CreatorSubscriptionsTimeline(_) =>
            (
              hm.Product.Subscribed,
              hm.ProductContetwittert.Subscribed(hm.Subscribed(deviceContetwittertOpt, seenTweetIds)))
          case _ => throw new UnsupportedOperationEtwitterception(s"Unknown product: $key")
        }

        val clientContetwittert = pm.ClientContetwittert(
          userId = CallContetwittert.twitterUserId,
          guestId = CallContetwittert.guestId,
          guestIdAds = CallContetwittert.guestIdAds,
          guestIdMarketing = CallContetwittert.guestIdMarketing,
          appId = CallContetwittert.clientApplicationId,
          ipAddress = ipAddress,
          userAgent = CallContetwittert.userAgent,
          countryCode = CallContetwittert.requestCountryCode,
          languageCode = CallContetwittert.requestLanguageCode,
          isTwoffice = CallContetwittert.isInternalOrTwoffice,
          userRoles = roles,
          deviceId = CallContetwittert.deviceId,
          mobileDeviceId = CallContetwittert.mobileDeviceId,
          mobileDeviceAdId = CallContetwittert.adId,
          limitAdTracking = CallContetwittert.limitAdTracking
        )

        hm.HomeMitwittererRequest(
          clientContetwittert = clientContetwittert,
          product = product,
          productContetwittert = Some(productContetwittert),
          matwitterResults = Try(view.count.get.toInt).toOption.orElse(HomeMitwittererColumn.MatwitterCount),
          cursor = view.cursor.filter(_.nonEmpty)
        )
    }
  }

  override val fetch: Arrow[(Key, View), Result[Value]] = {
    val transformThriftIntoPipelineRequest: Arrow[
      (Key, View),
      ProductPipelineRequest[HomeMitwittererRequest]
    ] = {
      Arrow
        .identity[(Key, View)]
        .andThen {
          createHomeMitwittererRequestArrow(compositeOnUserClientColumn, auditIpClientColumn)
        }
        .map {
          case thriftRequest =>
            val request = homeMitwittererRequestUnmarshaller(thriftRequest)
            val params = paramsBuilder.build(
              clientContetwittert = request.clientContetwittert,
              product = request.product,
              featureOverrides =
                request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty),
            )
            ProductPipelineRequest(request, params)
        }
    }

    val underlyingProduct: Arrow[
      ProductPipelineRequest[HomeMitwittererRequest],
      ProductPipelineResult[tr.TimelineResponse]
    ] = Arrow
      .identity[ProductPipelineRequest[HomeMitwittererRequest]]
      .map { pipelineRequest =>
        val pipelineArrow = productPipelineRegistry
          .getProductPipeline[HomeMitwittererRequest, tr.TimelineResponse](
            pipelineRequest.request.product)
          .arrow
        (pipelineArrow, pipelineRequest)
      }.applyArrow

    transformThriftIntoPipelineRequest.andThen(underlyingProduct).map {
      _.result match {
        case Some(result) => found(result.timeline)
        case _ => missing
      }
    }
  }
}

object HomeMitwittererColumn {
  val Path = "home-mitwitterer/homeMitwitterer.Timeline"
  private val FetchCallstack = s"$Path:fetch"
  private val MatwitterCount: Option[Int] = Some(100)
}
