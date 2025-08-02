package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.DisclosureType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.Earned
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.Issue
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.NoDisclosure
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.Political
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class DisclosureTypeMarshaller @Inject() () {

  def apply(disclosureType: DisclosureType): urt.DisclosureType = disclosureType match {
    case NoDisclosure => urt.DisclosureType.NoDisclosure
    case Political => urt.DisclosureType.Political
    case Earned => urt.DisclosureType.Earned
    case Issue => urt.DisclosureType.Issue
  }
}
