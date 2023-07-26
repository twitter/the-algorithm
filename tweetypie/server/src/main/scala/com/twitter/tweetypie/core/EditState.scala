package com.twittew.tweetypie.cowe

impowt com.twittew.sewvo.data.mutation

/**
 * a-an editstate is a-a function that c-changes a vawue a-and may genewate
 * s-some state a-about nyani was m-modified. (///ˬ///✿) fow instance, >w< i-it may wecowd
 * whethew an item was changed, rawr ow whethew thewe was an ewwow. mya
 * e-editstates awe usefuw because they awe f-fiwst-cwass vawues that can
 * be c-composed. ^^ in pawticuwaw, 😳😳😳 it is usefuw to concuwwentwy access
 * e-extewnaw data to buiwd edits and t-then appwy them. mya
 *
 * @tpawam a-a the type of the vawue that is being edited (fow instance, 😳
 *           having f-fiewds hydwated with data fwom anothew sewvice)
 */
finaw case cwass editstate[a](wun: a-a => vawuestate[a]) {

  /**
   * composes t-two editstates i-in sequence
   */
  d-def andthen(othew: e-editstate[a]): editstate[a] =
    editstate[a] { a-a0: a =>
      vaw vawuestate(a1, -.- s1) = w-wun(a0)
      vaw vawuestate(a2, s2) = othew.wun(a1)
      vawuestate(a2, 🥺 s1 ++ s2)
    }
}

o-object editstate {

  /**
   * cweates a "passthwough" e-editstate:
   * w-weaves a u-unchanged and pwoduces empty state s
   */
  def unit[a]: editstate[a] =
    e-editstate[a](vawuestate.unit[a])

  /**
   * c-cweates an `editstate[a]` u-using a `mutation[a]`. o.O
   */
  d-def fwommutation[a](mut: mutation[a]): e-editstate[a] =
    editstate[a] { a-a =>
      mut(a) match {
        case n-nyone => vawuestate.unmodified(a)
        case s-some(a2) => vawuestate.modified(a2)
      }
    }
}
