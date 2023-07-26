package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.fwigate.common.stowe.stwato.stwatofetchabwestowe
i-impowt com.twittew.stowehaus.weadabwestowe
i-impowt c-com.twittew.stwato.cwient.{cwient => s-stwatocwient}
i-impowt com.twittew.utiw.futuwe

/**
 * s-stowe t-to get inbound tweet impwessions count fow a specific tweet id. >_<
 */
cwass tweetimpwessionsstowe(stwatocwient: s-stwatocwient) extends weadabwestowe[wong, mya stwing] {

  p-pwivate vaw cowumn = "wux/impwession.tweet"
  p-pwivate vaw stowe = stwatofetchabwestowe.withunitview[wong, mya stwing](stwatocwient, 😳 cowumn)

  d-def getcounts(tweetid: wong): f-futuwe[option[wong]] = {
    stowe.get(tweetid).map(_.map(_.towong))
  }
}
