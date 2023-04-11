package com.twitter.visibility.models

trait UnitOfDiversion {

  def apply: (String, Any)
}

object UnitOfDiversion {
  case class ConversationId(conversationId: Long) extends UnitOfDiversion {
    override def apply: (String, Any) = ("conversation_id", conversationId)
  }

  case class TweetId(tweetId: Long) extends UnitOfDiversion {
    override def apply: (String, Any) = ("tweet_id", tweetId)
  }
}
