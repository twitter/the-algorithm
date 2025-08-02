package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

case class Title(tetwittert: String) {
  lazy val toThrift: t.Title = {
    t.Title(tetwittert)
  }
}
