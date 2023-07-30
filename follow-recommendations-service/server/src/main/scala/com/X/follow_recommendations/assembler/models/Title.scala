package com.X.follow_recommendations.assembler.models

import com.X.follow_recommendations.{thriftscala => t}

case class Title(text: String) {
  lazy val toThrift: t.Title = {
    t.Title(text)
  }
}
