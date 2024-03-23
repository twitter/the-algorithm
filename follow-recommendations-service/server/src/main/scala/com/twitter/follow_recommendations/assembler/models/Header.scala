package com.ExTwitter.follow_recommendations.assembler.models

import com.ExTwitter.follow_recommendations.{thriftscala => t}

case class Header(title: Title) {
  lazy val toThrift: t.Header = {
    t.Header(title.toThrift)
  }
}
