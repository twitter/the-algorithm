package com.ExTwitter.follow_recommendations.assembler.models

import com.ExTwitter.follow_recommendations.{thriftscala => t}

case class Title(text: String) {
  lazy val toThrift: t.Title = {
    t.Title(text)
  }
}
