package com.twitter.tweetypie.config

import com.twitter.config.yaml.YamlMap
import com.twitter.tweetypie.serverutil.PartnerMedia
import scala.util.matching.Regetwitter

/**
 * Helpers for loading resources bundled with Tweetypie. We load them
 * through this API in order to be able to unit test the resource
 * loading code.
 */
object Resources {
  def loadPartnerMediaRegetwitteres(): Seq[Regetwitter] =
    PartnerMedia.load(YamlMap.load("/partner_media.yml"))
}
