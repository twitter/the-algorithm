package com.twitter.tweet_mixer.utils

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Utils {
  type TweetId = Long
  type UserId = Long
  def interleave[Candidate, Key](
    candidates: Seq[Seq[Candidate]],
    keyFunction: Candidate => Key
  ): Seq[Candidate] = {

    // copy candidates into a mutable map so this method is thread-safe
    val candidatesPerSequence = candidates.map { tweetCandidates =>
      mutable.Queue() ++= tweetCandidates
    }

    val seen = mutable.Set[Key]()

    val candidateSeqQueue = mutable.Queue() ++= candidatesPerSequence

    val result = ArrayBuffer[Candidate]()

    while (candidateSeqQueue.nonEmpty) {
      val candidatesQueue = candidateSeqQueue.head

      if (candidatesQueue.nonEmpty) {
        val candidate = candidatesQueue.dequeue()
        val candidateKey = keyFunction(candidate)
        if (!seen.contains(candidateKey)) {
          result += candidate
          seen.add(candidateKey)
          candidateSeqQueue.enqueue(
            candidateSeqQueue.dequeue()
          ) // move this Seq to end
        }
      } else {
        candidateSeqQueue.dequeue() //finished processing this Seq
      }
    }
    //convert result to immutable seq implicitly
    result
  }

  def randomizedTTL(ttlSeconds: Int, earlyExpiration: Double = 0.2): Int = {
    (ttlSeconds - ttlSeconds * Math.min(earlyExpiration, 1.0) * Random.nextDouble()).toInt
  }

  def generateRandomIntBits(input: Seq[Int]): Seq[Int] = {
    (1 to input.length).map { _ =>
      val randomFloat = Random.nextFloat() * 2 - 1
      java.lang.Float.floatToIntBits(randomFloat)
    }
  }
}
