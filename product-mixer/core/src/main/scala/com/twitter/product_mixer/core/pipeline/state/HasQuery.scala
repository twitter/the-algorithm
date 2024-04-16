package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  HasQuery[Query <: P pel neQuery, T] {
  def query: Query
  def updateQuery(query: Query): T
}
