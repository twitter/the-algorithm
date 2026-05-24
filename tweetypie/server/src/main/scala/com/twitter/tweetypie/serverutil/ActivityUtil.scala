package com.twitter.tweetypie.serverutil

import com.twitter.util.Activity
import com.twitter.util.Closable
import com.twitter.util.Var
import com.twitter.util.Witness

object ActivityUtil {

  /**
   * Makes the composition strict up to the point where it is called.
   * Compositions based on the returned activity will have
   * the default lazy behavior.
   */
  def strict[T](activity: Activity[T]): Activity[T] = {
    val state = Var(Activity.Pending: Activity.State[T])
    val event = activity.states

    Closable.closeOnCollect(event.register(Witness(state)), state)

    new Activity(state)
  }
}
