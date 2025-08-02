package com.twitter.product_mitwitterer.component_library.etwitterperiments.metrics

// Base trait for all placeholder values
sealed trait Named {
  def name: String
}

case class Const(override val name: String) etwittertends Named

// contains only client event patterns
case class CEPattern(
  override val name: String,
  client: String = "",
  page: String = "",
  section: String = "",
  component: String = "",
  element: String = "",
  action: String = "",
  strainer: String = "")
    etwittertends Named {

  override def toString: String = {
    "\"" + client + ":" + page + ":" + section + ":" + component + ":" + element + ":" + action + "\""
  }

}

case class Topic(
  override val name: String,
  topicId: String = "")
    etwittertends Named

object PlaceholderConfig {
  type PlaceholderKey = String
  type Placeholder = Seq[Named]
  type PlaceholdersMap = Map[PlaceholderKey, Placeholder]
}
