package com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext

sealed tra  R chTextFormat {
  def na : Str ng
}

case object Pla n extends R chTextFormat {
  overr de val na : Str ng = "Pla n"
}

case object Strong extends R chTextFormat {
  overr de val na : Str ng = "Strong"
}
