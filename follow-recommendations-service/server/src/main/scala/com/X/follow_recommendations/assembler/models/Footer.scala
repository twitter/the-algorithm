package com.X.follow_recommendations.assembler.models

import com.X.follow_recommendations.{thriftscala => t}

case class Footer(action: Option[Action]) {
  lazy val toThrift: t.Footer = {
    t.Footer(action.map(_.toThrift))
  }
}
