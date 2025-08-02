package com.twitter.product_mitwitterer.shared_library.http_client

case class HttpHostPort(host: String, port: Int) {
  override val toString: String = s"$host:$port"
}
