package com.twitter.tweetypie.contetwittert

import com.twitter.contetwittert.TwitterContetwittert
import com.twitter.finagle.Filter
import com.twitter.finagle.Service
import com.twitter.finagle.SimpleFilter
import com.twitter.finagle.contetwittert.Contetwitterts
import com.twitter.io.Buf
import com.twitter.io.Buf.ByteArray.Owned
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.graphql.common.core.GraphQlClientApplication
import com.twitter.util.Try
import java.nio.charset.StandardCharsets.UTF_8
import scala.util.matching.Regetwitter

/**
 * Contetwittert and filters to help track callers of Tweetypie's endpoints. This contetwittert and its
 * filters were originally added to provide visibility into callers of Tweetypie who are
 * using the birdherd library to access tweets.
 *
 * This contetwittert data is intended to be marshalled by callers to Tweetypie, but then the
 * contetwittert data is stripped (moved from broadcast to local). This happens so that the
 * contetwittert data is not forwarded down tweetypie's backend rpc chains, which often result
 * in transitive calls back into tweetypie. This effectively creates single-hop marshalling.
 */
object TweetypieContetwittert {
  // Bring Tweetypie permitted TwitterContetwittert into scope
  val TwitterContetwittert: TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)

  case class Cttwitter(via: String)
  val Empty = Cttwitter("")

  object Broadcast {
    private[this] object Key etwittertends Contetwitterts.broadcast.Key[Cttwitter](id = Cttwitter.getClass.getName) {

      override def marshal(value: Cttwitter): Buf =
        Owned(value.via.getBytes(UTF_8))

      override def tryUnmarshal(buf: Buf): Try[Cttwitter] =
        Try(Cttwitter(new String(Owned.etwittertract(buf), UTF_8)))
    }

    private[TweetypieContetwittert] def current(): Option[Cttwitter] =
      Contetwitterts.broadcast.get(Key)

    def currentOrElse(default: Cttwitter): Cttwitter =
      current().getOrElse(default)

    def letClear[T](f: => T): T =
      Contetwitterts.broadcast.letClear(Key)(f)

    def let[T](cttwitter: Cttwitter)(f: => T): T =
      if (Empty == cttwitter) {
        letClear(f)
      } else {
        Contetwitterts.broadcast.let(Key, cttwitter)(f)
      }

    // cttwitter has to be by name so we can re-evaluate it for every request (for usage in ServiceTwitter.scala)
    def filter(cttwitter: => Cttwitter): Filter.TypeAgnostic =
      new Filter.TypeAgnostic {
        override def toFilter[Req, Rep]: Filter[Req, Rep, Req, Rep] =
          (request: Req, service: Service[Req, Rep]) => Broadcast.let(cttwitter)(service(request))
      }
  }

  object Local {
    private[this] val Key =
      new Contetwitterts.local.Key[Cttwitter]

    private[TweetypieContetwittert] def let[T](cttwitter: Option[Cttwitter])(f: => T): T =
      cttwitter match {
        case Some(cttwitter) if cttwitter != Empty => Contetwitterts.local.let(Key, cttwitter)(f)
        case None => Contetwitterts.local.letClear(Key)(f)
      }

    def current(): Option[Cttwitter] =
      Contetwitterts.local.get(Key)

    def filter[Req, Rep]: SimpleFilter[Req, Rep] =
      (request: Req, service: Service[Req, Rep]) => {
        val cttwitter = Broadcast.current()
        Broadcast.letClear(Local.let(cttwitter)(service(request)))
      }

    private[this] def clientAppIdToName(clientAppId: Long) =
      GraphQlClientApplication.AllById.get(clientAppId).map(_.name).getOrElse("nonTOO")

    private[this] val pathRegetwitteres: Seq[(Regetwitter, String)] = Seq(
      ("timeline_conversation_.*_json".r, "timeline_conversation__slug__json"),
      ("user_timeline_.*_json".r, "user_timeline__user__json"),
      ("[0-9]{2,}".r, "_id_")
    )

    // `contetwittert.via` will either be a string like: "birdherd" or "birdherd:/1.1/statuses/show/123.json,
    // depending on whether birdherd code was able to determine the path of the request.
    private[this] def getViaAndPath(via: String): (String, Option[String]) =
      via.split(":", 2) match {
        case Array(via, path) =>
          val sanitizedPath = path
            .replace('/', '_')
            .replace('.', '_')

          // Apply each regetwitter in turn
          val normalizedPath = pathRegetwitteres.foldLeft(sanitizedPath) {
            case (path, (regetwitter, replacement)) => regetwitter.replaceAllIn(path, replacement)
          }

          (via, Some(normalizedPath))
        case Array(via) => (via, None)
      }

    def trackStats[U](scopes: StatsReceiver*): Unit =
      for {
        tweetypieCttwitter <- TweetypieContetwittert.Local.current()
        (via, pathOpt) = getViaAndPath(tweetypieCttwitter.via)
        twitterCttwitter <- TwitterContetwittert()
        clientAppId <- twitterCttwitter.clientApplicationId
      } yield {
        val clientAppName = clientAppIdToName(clientAppId)
        scopes.foreach { stats =>
          val cttwitterStats = stats.scope("contetwittert")
          val viaStats = cttwitterStats.scope("via", via)
          viaStats.scope("all").counter("requests").incr()
          val viaClientStats = viaStats.scope("by_client", clientAppName)
          viaClientStats.counter("requests").incr()
          pathOpt.foreach { path =>
            val viaPathStats = viaStats.scope("by_path", path)
            viaPathStats.counter("requests").incr()
          }
        }
      }
  }
}
