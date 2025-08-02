package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFitwittertures.InteractionEventsFitwittertures
import com.twitter.unified_user_actions.adapter.uua_aggregates.RekeyUuaFromInteractionEventsAdapter
import com.twitter.util.Time
import org.scalatest.prop.TableDrivenPropertyChecks

class RekeyUuaFromInteractionEventsAdapterSpec etwittertends Test with TableDrivenPropertyChecks {
  test("ClientTweetRenderImpressions") {
    new InteractionEventsFitwittertures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(
          RekeyUuaFromInteractionEventsAdapter.adaptEvent(baseInteractionEvent) === Seq(
            etwitterpectedBaseKeyedUuaTweet))
      }
    }
  }

  test("Filter out logged out users") {
    new InteractionEventsFitwittertures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(RekeyUuaFromInteractionEventsAdapter.adaptEvent(loggedOutInteractionEvent) === Nil)
      }
    }
  }

  test("Filter out detail impressions") {
    new InteractionEventsFitwittertures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(
          RekeyUuaFromInteractionEventsAdapter.adaptEvent(detailImpressionInteractionEvent) === Nil)
      }
    }
  }
}
