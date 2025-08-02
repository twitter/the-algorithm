package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

case class Action(tetwittert: String, actionURL: String) {
  lazy val toThrift: t.Action = {
    t.Action(tetwittert, actionURL)
  }
}
