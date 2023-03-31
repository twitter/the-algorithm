package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

case class Header(title: Title) {
  lazy val toThrift: t.Header = {
    t.Header(title.toThrift)
  }
}
