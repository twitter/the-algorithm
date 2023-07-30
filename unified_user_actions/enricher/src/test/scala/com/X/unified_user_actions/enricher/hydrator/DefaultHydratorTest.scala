package com.X.unified_user_actions.enricher.hydrator

import com.google.common.cache.CacheBuilder
import com.X.dynmap.DynMap
import com.X.graphql.thriftscala.GraphQlRequest
import com.X.graphql.thriftscala.GraphQlResponse
import com.X.graphql.thriftscala.GraphqlExecutionService
import com.X.inject.Test
import com.X.unified_user_actions.enricher.EnricherFixture
import com.X.unified_user_actions.enricher.FatalException
import com.X.unified_user_actions.enricher.hcache.LocalCache
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentIdType
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.X.unified_user_actions.thriftscala.AuthorInfo
import com.X.util.Await
import com.X.util.Future
import org.mockito.ArgumentMatchers
import org.mockito.MockitoSugar

class DefaultHydratorTest extends Test with MockitoSugar {

  trait Fixtures extends EnricherFixture {
    val cache = new LocalCache[EnrichmentKey, DynMap](
      underlying = CacheBuilder
        .newBuilder()
        .maximumSize(10)
        .build[EnrichmentKey, Future[DynMap]]())

    val client = mock[GraphqlExecutionService.FinagledClient]
    val key = EnrichmentKey(EnrichmentIdType.TweetId, 1L)
    val envelop = EnrichmentEnvelop(123L, mkUUATweetEvent(1L), tweetInfoEnrichmentPlan)

    def mkGraphQLResponse(authorId: Long): GraphQlResponse =
      GraphQlResponse(
        Some(
          s"""
           |{
           |  "data": {
           |    "tweet_result_by_rest_id": {
           |      "result": {
           |        "core": {
           |          "user": {
           |            "legacy": {
           |              "id_str": "$authorId"
           |            }
           |          }
           |        }
           |      }
           |    }
           |  }
           |}
           |""".stripMargin
        ))
  }

  test("non-fatal errors should proceed as normal") {
    new Fixtures {
      val hydrator = new DefaultHydrator(cache, client)

      // when graphql client encounter any exception
      when(client.graphql(ArgumentMatchers.any[GraphQlRequest]))
        .thenReturn(Future.exception(new IllegalStateException("any exception")))

      val actual =
        Await.result(hydrator.hydrate(EnrichmentInstruction.TweetEnrichment, Some(key), envelop))

      // then the original envelop is expected
      assert(envelop == actual)
    }
  }

  test("fatal errors should return a future exception") {
    new Fixtures {
      val hydrator = new DefaultHydrator(cache, client)

      // when graphql client encounter a fatal exception
      when(client.graphql(ArgumentMatchers.any[GraphQlRequest]))
        .thenReturn(Future.exception(new FatalException("fatal exception") {}))

      val actual = hydrator.hydrate(EnrichmentInstruction.TweetEnrichment, Some(key), envelop)

      // then a failed future is expected
      assertFailedFuture[FatalException](actual)
    }
  }

  test("author_id should be hydrated from graphql respond") {
    new Fixtures {
      val hydrator = new DefaultHydrator(cache, client)

      when(client.graphql(ArgumentMatchers.any[GraphQlRequest]))
        .thenReturn(Future.value(mkGraphQLResponse(888L)))

      val actual = hydrator.hydrate(EnrichmentInstruction.TweetEnrichment, Some(key), envelop)

      assertFutureValue(
        actual,
        envelop.copy(uua = mkUUATweetEvent(1L, Some(AuthorInfo(Some(888L))))))
    }
  }

  test("when AuthorInfo is populated, there should be no hydration") {
    new Fixtures {
      val hydrator = new DefaultHydrator(cache, client)

      when(client.graphql(ArgumentMatchers.any[GraphQlRequest]))
        .thenReturn(Future.value(mkGraphQLResponse(333L)))

      val expected = envelop.copy(uua =
        mkUUATweetEvent(tweetId = 3L, author = Some(AuthorInfo(authorId = Some(222)))))
      val actual = hydrator.hydrate(EnrichmentInstruction.TweetEnrichment, Some(key), expected)

      assertFutureValue(actual, expected)
    }
  }
}
