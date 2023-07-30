package com.X.frigate.pushservice.model.ibis

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.ibis2.lib.util.JsonMarshal
import com.X.util.Future

trait CustomConfigurationMapForIbis {
  self: PushCandidate =>

  lazy val customConfigMapsJsonFut: Future[String] = {
    customFieldsMapFut.map { customFields =>
      JsonMarshal.toJson(customFields)
    }
  }

  lazy val customConfigMapsFut: Future[Map[String, String]] = {
    if (self.target.isLoggedOutUser) {
      Future.value(Map.empty[String, String])
    } else {
      customConfigMapsJsonFut.map { customConfigMapsJson =>
        Map("custom_config" -> customConfigMapsJson)
      }
    }
  }
}
