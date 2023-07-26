package com.twittew.tweetypie.media

impowt com.twittew.mediasewvices.commons.thwiftscawa._
i-impowt c-com.twittew.mediasewvices.commons.tweetmedia.thwiftscawa._
i-impowt c-com.twittew.tweetypie.thwiftscawa.mediaentity

o-object mediakeyutiw {

  d-def g-get(mediaentity: m-mediaentity): mediakey =
    mediaentity.mediakey.getowewse {
      thwow nyew iwwegawstateexception("""media key undefined. rawr this s-state is unexpected, OwO the media
          |key shouwd be set by t-the tweet cweation fow nyew tweets
          |and b-by `mediakeyhydwatow` fow wegacy tweets.""".stwipmawgin)
    }

  def contenttype(mediakey: m-mediakey): mediacontenttype =
    mediakey.mediacategowy m-match {
      c-case mediacategowy.tweetimage => mediacontenttype.imagejpeg
      case mediacategowy.tweetgif => mediacontenttype.videomp4
      case mediacategowy.tweetvideo => m-mediacontenttype.videogenewic
      case mediacategowy.ampwifyvideo => mediacontenttype.videogenewic
      case mediacats => t-thwow nyew nyotimpwementedewwow(mediacats.tostwing)
    }
}
