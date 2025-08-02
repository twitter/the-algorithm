package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert

sealed trait RichTetwittertFormat {
  def name: String
}

case object Plain etwittertends RichTetwittertFormat {
  override val name: String = "Plain"
}

case object Strong etwittertends RichTetwittertFormat {
  override val name: String = "Strong"
}
