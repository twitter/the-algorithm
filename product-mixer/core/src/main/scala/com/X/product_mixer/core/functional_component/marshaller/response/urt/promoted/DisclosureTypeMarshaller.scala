package com.X.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.X.product_mixer.core.model.marshalling.response.urt.promoted.DisclosureType
import com.X.product_mixer.core.model.marshalling.response.urt.promoted.Earned
import com.X.product_mixer.core.model.marshalling.response.urt.promoted.Issue
import com.X.product_mixer.core.model.marshalling.response.urt.promoted.NoDisclosure
import com.X.product_mixer.core.model.marshalling.response.urt.promoted.Political
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisclosureTypeMarshaller @Inject() () {

  def apply(disclosureType: DisclosureType): urt.DisclosureType = disclosureType match {
    case NoDisclosure => urt.DisclosureType.NoDisclosure
    case Political => urt.DisclosureType.Political
    case Earned => urt.DisclosureType.Earned
    case Issue => urt.DisclosureType.Issue
  }
}
