package com.twitter.tweet_mixer.candidate_source.cached_candidate_source

import com.twitter.io.Buf
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.util.Transformer
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.util.Return

trait MemcachedCandidateSource[Request, Key, Value, Response]
    extends CandidateSource[Request, Response] {

  val memcache: MemcacheStitchClient

  val TTL: Int // memcache TTL

  def keyTransformer(key: Key): String

  val valueTransformer: Transformer[Seq[Value], Buf]

  def enableCache(request: Request): Boolean = true

  def getKeys(request: Request): Stitch[Seq[Key]] // get keys from request

  def getCandidatesFromStore(
    key: Key
  ): Stitch[Seq[Value]] // get set of candidates from underlying store

  def postProcess(
    request: Request,
    keys: Seq[Key],
    results: Seq[Seq[Value]]
  ): Seq[Response] // postProcess and convert to Response type

  override def apply(request: Request): Stitch[Seq[Response]] = {
    OffloadFuturePools.offloadStitch {
      getKeys(request).flatMap { keys =>
        if (keys.isEmpty) Stitch.value(Seq.empty)
        else {
          val resultsStitch = if (enableCache(request)) {
            Stitch
              .collectToTry(keys.map(getCandidates)).map(_.map {
                case Return(result) => result
                case _ => Seq.empty[Value]
              })
          } else {
            Stitch
              .collectToTry(keys.map(getAndSetCandidates)).map(_.map {
                case Return(result) => result
                case _ => Seq.empty[Value]
              })
          }
          resultsStitch
            .map(postProcess(request, keys, _))
            .handle { case _ => Seq.empty }
        }
      }
    }
  }

  private def getCandidates(
    key: Key
  ): Stitch[Seq[Value]] = {
    memcache
      .get(keyTransformer(key))
      .flatMap {
        case Some(value) =>
          Stitch
            .value(
              valueTransformer
                .from(value)
                .getOrElse(Seq.empty)
            ).rescue {
              case _ => getAndSetCandidates(key)
            }
        case None => getAndSetCandidates(key)
      }
  }

  private def getAndSetCandidates(
    key: Key
  ): Stitch[Seq[Value]] = {
    val memcacheKey: String = keyTransformer(key)
    val memcacheSetFunction: Seq[Value] => Stitch[Unit] = { result =>
      Stitch.async(memcache.set(memcacheKey, valueTransformer.to(result).getOrElse(Buf.Empty), TTL))
    }
    getCandidatesFromStore(key).applyEffect(memcacheSetFunction)
  }
}
