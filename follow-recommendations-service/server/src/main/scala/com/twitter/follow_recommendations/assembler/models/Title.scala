package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

case class Title(text: String) {
  lazy val toThrift: t.Title = {
    t.Title(text)
  }
}
