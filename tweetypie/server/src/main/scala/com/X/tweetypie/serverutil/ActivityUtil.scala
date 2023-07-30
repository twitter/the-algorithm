package com.X.tweetypie.serverutil

import com.X.util.Activity
import com.X.util.Closable
import com.X.util.Var
import com.X.util.Witness

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
