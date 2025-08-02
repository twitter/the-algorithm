package com.twitter.visibility.interfaces.tweets

import com.twitter.finagle.contetwittert.Contetwitterts
import com.twitter.io.Buf
import com.twitter.io.BufByteWriter
import com.twitter.io.ByteReader
import com.twitter.util.Future
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try

case class TweetypieContetwittert(
  isQuotedTweet: Boolean,
  isRetweet: Boolean,
  hydrateConversationControl: Boolean)

object TweetypieContetwittert {

  def let[U](value: TweetypieContetwittert)(f: => Future[U]): Future[U] =
    Contetwitterts.broadcast.let(TweetypieContetwittertKey, value)(f)

  def get(): Option[TweetypieContetwittert] =
    Contetwitterts.broadcast.get(TweetypieContetwittertKey)
}

object TweetypieContetwittertKey
    etwittertends Contetwitterts.broadcast.Key[TweetypieContetwittert](
      "com.twitter.visibility.interfaces.tweets.TweetypieContetwittert"
    ) {

  override def marshal(value: TweetypieContetwittert): Buf = {
    val bw = BufByteWriter.fitwittered(1)
    val byte =
      ((if (value.isQuotedTweet) 1 else 0) << 0) |
        ((if (value.isRetweet) 1 else 0) << 1) |
        ((if (value.hydrateConversationControl) 1 else 0) << 2)
    bw.writeByte(byte)
    bw.owned()
  }

  override def tryUnmarshal(buf: Buf): Try[TweetypieContetwittert] = {
    if (buf.length != 1) {
      Throw(
        new IllegalArgumentEtwitterception(
          s"Could not etwittertract Boolean from Buf. Length ${buf.length} but required 1"
        )
      )
    } else {
      val byte: Byte = ByteReader(buf).readByte()
      Return(
        TweetypieContetwittert(
          isQuotedTweet = ((byte & 1) == 1),
          isRetweet = ((byte & 2) == 2),
          hydrateConversationControl = ((byte & 4) == 4)
        )
      )
    }
  }
}
