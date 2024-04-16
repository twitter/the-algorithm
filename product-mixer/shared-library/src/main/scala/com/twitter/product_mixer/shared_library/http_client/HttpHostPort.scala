package com.tw ter.product_m xer.shared_l brary.http_cl ent

case class HttpHostPort(host: Str ng, port:  nt) {
  overr de val toStr ng: Str ng = s"$host:$port"
}
