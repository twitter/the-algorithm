package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

case class Action(text: String, actionURL: String) {
  lazy val toThrift: t.Action = {
    t.Action(text, actionURL)
  }
}
