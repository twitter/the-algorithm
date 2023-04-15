package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.adapter.TestFixtures.BCEFixture
import com.twitter.unified_user_actions.adapter.behavioral_client_event.BehavioralClientEventAdapter
import com.twitter.unified_user_actions.thriftscala._
import com.twitter.util.Time
import org.scalatest.prop.TableDrivenPropertyChecks

class BehavioralClientEventAdapterSpec extends Test with TableDrivenPropertyChecks {

  test("basic event conversion should be correct") {
    new BCEFixture {
      Time.withTimeAt(frozenTime) { _ =>
        val tests = Table(
          ("event", "expected", "description"),
          (
            makeBCEEvent(),
            makeUUAImpressEvent(productSurface = Some(ProductSurface.TweetDetailsPage)),
            "tweet_details conversion"),
          (makeBCEProfileImpressEvent(), makeUUAProfileImpressEvent(), "profile conversion"),
          (
            makeBCEVideoFullscreenImpressEvent(),
            makeUUAVideoFullscreenImpressEvent(),
            "fullscreen_video conversion"),
          (
            makeBCEImageFullscreenImpressEvent(),
            makeUUAImageFullscreenImpressEvent(),
            "fullscreen_image conversion"),
        )
        forEvery(tests) { (input: FlattenedEventLog, expected: UnifiedUserAction, desc: String) =>
          assert(Seq(expected) === BehavioralClientEventAdapter.adaptEvent(input), desc)
        }
      }
    }
  }

  test(
    "tweet_details is NOT missing productSurface[Info] when empty breadcrumb components and breadcrumbs tweets id") {
    new BCEFixture {
      Time.withTimeAt(frozenTime) { _ =>
        val input = makeBCEEvent(v1BreadcrumbViewTypeHierarchy = None, v1BreadcrumbTweetIds = None)
        val expected =
          makeUUAImpressEvent(
            productSurface = Some(ProductSurface.TweetDetailsPage),
            breadcrumbViews = None,
            breadcrumbTweets = None)
        val actual = BehavioralClientEventAdapter.adaptEvent(input)

        assert(Seq(expected) === actual)
      }
    }
  }

  test("tweet_details is not missing productSurface[Info] when only breadcrumb tweets is empty") {
    new BCEFixture {
      Time.withTimeAt(frozenTime) { _ =>
        val input = makeBCEEvent(v1BreadcrumbTweetIds = None)
        val expected = makeUUAImpressEvent(
          productSurface = Some(ProductSurface.TweetDetailsPage),
          breadcrumbViews = Some(viewBreadcrumbs),
          breadcrumbTweets = None
        )
        val actual = BehavioralClientEventAdapter.adaptEvent(input)

        assert(Seq(expected) === actual)
      }
    }
  }

  test("unsupported events should be skipped") {
    new BCEFixture {
      val unsupportedPage = "unsupported_page"
      val unsupportedAction = "unsupported_action"
      val supportedNamespaces = Table(
        ("page", "actions"),
        ("tweet_details", Seq("impress")),
        ("profile", Seq("impress")),
      )

      forAll(supportedNamespaces) { (page: String, actions: Seq[String]) =>
        actions.foreach { supportedAction =>
          assert(
            BehavioralClientEventAdapter
              .adaptEvent(
                makeBCEEvent(
                  currentPage = Some(unsupportedPage),
                  actionName = Some(supportedAction))).isEmpty)

          assert(BehavioralClientEventAdapter
            .adaptEvent(
              makeBCEEvent(currentPage = Some(page), actionName = Some(unsupportedAction))).isEmpty)
        }
      }
    }
  }

  test("event w/ missing info should be skipped") {
    new BCEFixture {
      val eventsWithMissingInfo = Table(
        ("event", "description"),
        (null.asInstanceOf[FlattenedEventLog], "null event"),
        (makeBCEEvent(v2Impress = None), "impression event missing v2Impress"),
        (makeBCEEvent(v1TweetIds = None), "tweet event missing v1TweetIds"),
        (makeBCEProfileImpressEvent(v1UserIds = None), "profile event missing v1UserIds"),
        (
          makeBCEVideoFullscreenImpressEvent(v1BreadcrumbTweetIds = None),
          "fullscreen_video event missing v1BreadcrumbTweetIds"),
        (
          makeBCEImageFullscreenImpressEvent(v1BreadcrumbTweetIds = None),
          "fullscreen_image event missing v1BreadcrumbTweetIds"),
      )

      forEvery(eventsWithMissingInfo) { (event: FlattenedEventLog, desc: String) =>
        assert(
          BehavioralClientEventAdapter
            .adaptEvent(event).isEmpty,
          desc)
      }
    }
  }

  test("use eventCreateAtMs when driftAdjustedTimetampMs is empty") {
    new BCEFixture {
      Time.withTimeAt(frozenTime) { _ =>
        val input = makeBCEEvent(
          context = makeBCEContext(driftAdjustedEventCreatedAtMs = None)
        )
        val expected = makeUUAImpressEvent(
          createTs = eventCreatedTime,
          productSurface = Some(ProductSurface.TweetDetailsPage))
        val actual = BehavioralClientEventAdapter.adaptEvent(input)

        assert(Seq(expected) === actual)
      }
    }
  }
}
