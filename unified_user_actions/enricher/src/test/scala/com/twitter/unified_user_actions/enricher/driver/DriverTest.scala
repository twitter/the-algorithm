package com.twitter.unified_user_actions.enricher.driver

import com.twitter.inject.Test
import com.twitter.unified_user_actions.enricher.EnricherFitwitterture
import com.twitter.unified_user_actions.enricher.hydrator.Hydrator
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentIdType
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentPlan
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentStage
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentStageStatus
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentStageType
import com.twitter.unified_user_actions.enricher.partitioner.Partitioner
import com.twitter.util.Await
import com.twitter.util.Future
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import scala.collection.mutable

class DriverTest etwittertends Test with Matchers with BeforeAndAfter {
  object EtwitterecutionContetwittert {
    var etwitterecutionCount = 0
  }

  before {
    EtwitterecutionContetwittert.etwitterecutionCount = 0
  }

  trait Fitwittertures etwittertends EnricherFitwitterture {
    val repartitionTweet = mkStage()
    val repartitionNotiTweet =
      mkStage(instructions = Seq(EnrichmentInstruction.NotificationTweetEnrichment))
    val hydrateTweet = mkStage(stageType = EnrichmentStageType.Hydration)
    val hydrateTweetMultiInstructions = mkStage(
      stageType = EnrichmentStageType.Hydration,
      instructions = Seq(
        EnrichmentInstruction.NotificationTweetEnrichment,
        EnrichmentInstruction.TweetEnrichment,
        EnrichmentInstruction.NotificationTweetEnrichment,
        EnrichmentInstruction.TweetEnrichment
      )
    )
    val hydrateNotiTweet = mkStage(
      stageType = EnrichmentStageType.Hydration,
      instructions = Seq(EnrichmentInstruction.NotificationTweetEnrichment))
    val key1 = EnrichmentKey(EnrichmentIdType.TweetId, 123L)
    val tweet1 = mkUUATweetEvent(981L)
    val hydrator = new MockHydrator
    val partitioner = new MockPartitioner
    val outputTopic = "output"
    val partitionTopic = "partition"

    def complete(
      enrichmentStage: EnrichmentStage,
      outputTopic: Option[String] = None
    ): EnrichmentStage = {
      enrichmentStage.copy(status = EnrichmentStageStatus.Completion, outputTopic = outputTopic)
    }

    def mkPlan(enrichmentStages: EnrichmentStage*): EnrichmentPlan = {
      EnrichmentPlan(enrichmentStages)
    }

    def mkStage(
      status: EnrichmentStageStatus = EnrichmentStageStatus.Initialized,
      stageType: EnrichmentStageType = EnrichmentStageType.Repartition,
      instructions: Seq[EnrichmentInstruction] = Seq(EnrichmentInstruction.TweetEnrichment)
    ): EnrichmentStage = {
      EnrichmentStage(status, stageType, instructions)
    }

    trait EtwitterecutionCount {
      val callMap: mutable.Map[Int, (EnrichmentInstruction, EnrichmentEnvelop)] =
        mutable.Map[Int, (EnrichmentInstruction, EnrichmentEnvelop)]()

      def recordEtwitterecution(instruction: EnrichmentInstruction, envelop: EnrichmentEnvelop): Unit = {
        EtwitterecutionContetwittert.etwitterecutionCount = EtwitterecutionContetwittert.etwitterecutionCount + 1
        callMap.put(EtwitterecutionContetwittert.etwitterecutionCount, (instruction, envelop))
      }
    }

    class MockHydrator etwittertends Hydrator with EtwitterecutionCount {
      def hydrate(
        instruction: EnrichmentInstruction,
        key: Option[EnrichmentKey],
        envelop: EnrichmentEnvelop
      ): Future[EnrichmentEnvelop] = {
        recordEtwitterecution(instruction, envelop)
        Future(envelop.copy(envelopId = EtwitterecutionContetwittert.etwitterecutionCount))
      }
    }

    class MockPartitioner etwittertends Partitioner with EtwitterecutionCount {
      def repartition(
        instruction: EnrichmentInstruction,
        envelop: EnrichmentEnvelop
      ): Option[EnrichmentKey] = {
        recordEtwitterecution(instruction, envelop)
        Some(EnrichmentKey(EnrichmentIdType.TweetId, EtwitterecutionContetwittert.etwitterecutionCount))
      }
    }
  }

  test("single partitioning plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)
      // given a simple plan that only repartition the input and nothing else
      val plan = mkPlan(repartitionTweet)

      (1L to 10).foreach(id => {
        val envelop = EnrichmentEnvelop(id, tweet1, plan)

        // when
        val actual = Await.result(driver.etwitterecute(Some(key1), Future(envelop)))

        val etwitterpectedKey = Some(key1.copy(id = id))
        val etwitterpectedValue =
          envelop.copy(plan = mkPlan(complete(repartitionTweet, Some(partitionTopic))))

        // then the result should have a new partitioned key, with the envelop unchanged etwittercept the plan is complete
        // however, the output topic is the partitionTopic (since this is only a partitioning stage)
        assert((etwitterpectedKey, etwitterpectedValue) == actual)
      })
    }
  }

  test("multi-stage partitioning plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)
      // given a plan that chain multiple repartition stages together
      val plan = mkPlan(repartitionTweet, repartitionNotiTweet)
      val envelop1 = EnrichmentEnvelop(1L, tweet1, plan)

      // when 1st partitioning trip
      val actual1 = Await.result(driver.etwitterecute(Some(key1), Future(envelop1)))

      // then the result should have a new partitioned key, with the envelop unchanged etwittercept the
      // 1st stage of the plan is complete
      val etwitterpectedKey1 = key1.copy(id = 1L)
      val etwitterpectedValue1 =
        envelop1.copy(plan =
          mkPlan(complete(repartitionTweet, Some(partitionTopic)), repartitionNotiTweet))

      assert((Some(etwitterpectedKey1), etwitterpectedValue1) == actual1)

      // then, we reuse the last result to etwitterercise the logics on the driver again for the 2st trip
      val actual2 = Await.result(driver.etwitterecute(Some(etwitterpectedKey1), Future(etwitterpectedValue1)))
      val etwitterpectedKey2 = key1.copy(id = 2L)
      val etwitterpectedValue2 =
        envelop1.copy(plan = mkPlan(
          complete(repartitionTweet, Some(partitionTopic)),
          complete(repartitionNotiTweet, Some(partitionTopic))))

      assert((Some(etwitterpectedKey2), etwitterpectedValue2) == actual2)
    }
  }

  test("single hydration plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)
      // given a simple plan that only hydrate the input and nothing else
      val plan = mkPlan(hydrateTweet)

      (1L to 10).foreach(id => {
        val envelop = EnrichmentEnvelop(id, tweet1, plan)

        // when
        val actual = Await.result(driver.etwitterecute(Some(key1), Future(envelop)))

        val etwitterpectedValue =
          envelop.copy(envelopId = id, plan = mkPlan(complete(hydrateTweet, Some(outputTopic))))

        // then the result should have the same key, with the envelop hydrated & the plan is complete
        // the output topic should be the final topic since this is a hydration stage and the plan is complete
        assert((Some(key1), etwitterpectedValue) == actual)
      })
    }
  }

  test("single hydration with multiple instructions plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)
      // given a simple plan that only hydrate the input and nothing else
      val plan = mkPlan(hydrateTweetMultiInstructions)
      val envelop = EnrichmentEnvelop(0L, tweet1, plan)

      // when
      val actual = Await.result(driver.etwitterecute(Some(key1), Future(envelop)))
      val etwitterpectedValue = envelop.copy(
        envelopId = 4L, // hydrate is called 4 times for 4 instructions in 1 stage
        plan = mkPlan(complete(hydrateTweetMultiInstructions, Some(outputTopic))))

      // then the result should have the same key, with the envelop hydrated & the plan is complete
      // the output topic should be the final topic since this is a hydration stage and the plan is complete
      assert((Some(key1), etwitterpectedValue) == actual)
    }
  }

  test("multi-stage hydration plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)
      // given a plan that only hydrate twice
      val plan = mkPlan(hydrateTweet, hydrateNotiTweet)
      val envelop = EnrichmentEnvelop(1L, tweet1, plan)

      // when
      val actual = Await.result(driver.etwitterecute(Some(key1), Future(envelop)))

      // then the result should have the same key, with the envelop hydrated. since there's no
      // partitioning stages, the driver will just recurse until all the hydration is done,
      // then output to the final topic
      val etwitterpectedValue =
        envelop.copy(
          envelopId = 2L,
          plan = mkPlan(
            complete(hydrateTweet),
            complete(
              hydrateNotiTweet,
              Some(outputTopic)
            ) // only the last stage has the output topic
          ))

      assert((Some(key1), etwitterpectedValue) == actual)
    }
  }

  test("multi-stage partition+hydration plan works") {
    new Fitwittertures {
      val driver = new EnrichmentDriver(Some(outputTopic), partitionTopic, hydrator, partitioner)

      // given a plan that repartition then hydrate twice
      val plan = mkPlan(repartitionTweet, hydrateTweet, repartitionNotiTweet, hydrateNotiTweet)
      var curEnvelop = EnrichmentEnvelop(1L, tweet1, plan)
      var curKey = key1

      // stage 1, partitioning on tweet should be correct
      var actual = Await.result(driver.etwitterecute(Some(curKey), Future(curEnvelop)))
      var etwitterpectedKey = curKey.copy(id = 1L)
      var etwitterpectedValue = curEnvelop.copy(
        plan = mkPlan(
          complete(repartitionTweet, Some(partitionTopic)),
          hydrateTweet,
          repartitionNotiTweet,
          hydrateNotiTweet))

      assert((Some(etwitterpectedKey), etwitterpectedValue) == actual)
      curEnvelop = actual._2
      curKey = actual._1.get

      // stage 2-3, hydrating on tweet should be correct
      // and since the netwittert stage after hydration is a repartition, it will does so correctly
      actual = Await.result(driver.etwitterecute(Some(curKey), Future(curEnvelop)))
      etwitterpectedKey = curKey.copy(id = 3) // repartition is done in stage 3
      etwitterpectedValue = curEnvelop.copy(
        envelopId = 2L, // hydration is done in stage 2
        plan = mkPlan(
          complete(repartitionTweet, Some(partitionTopic)),
          complete(hydrateTweet),
          complete(repartitionNotiTweet, Some(partitionTopic)),
          hydrateNotiTweet)
      )

      assert((Some(etwitterpectedKey), etwitterpectedValue) == actual)
      curEnvelop = actual._2
      curKey = actual._1.get

      // then finally, stage 4 would output to the final topic
      actual = Await.result(driver.etwitterecute(Some(curKey), Future(curEnvelop)))
      etwitterpectedKey = curKey // nothing's changed in the key
      etwitterpectedValue = curEnvelop.copy(
        envelopId = 4L,
        plan = mkPlan(
          complete(repartitionTweet, Some(partitionTopic)),
          complete(hydrateTweet),
          complete(repartitionNotiTweet, Some(partitionTopic)),
          complete(hydrateNotiTweet, Some(outputTopic))
        )
      )

      assert((Some(etwitterpectedKey), etwitterpectedValue) == actual)
    }
  }
}
