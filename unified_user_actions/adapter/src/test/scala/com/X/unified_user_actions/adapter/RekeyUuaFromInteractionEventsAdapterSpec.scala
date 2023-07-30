package com.X.unified_user_actions.adapter

import com.X.inject.Test
import com.X.unified_user_actions.adapter.TestFixtures.InteractionEventsFixtures
import com.X.unified_user_actions.adapter.uua_aggregates.RekeyUuaFromInteractionEventsAdapter
import com.X.util.Time
import org.scalatest.prop.TableDrivenPropertyChecks

class RekeyUuaFromInteractionEventsAdapterSpec extends Test with TableDrivenPropertyChecks {
  test("ClientTweetRenderImpressions") {
    new InteractionEventsFixtures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(
          RekeyUuaFromInteractionEventsAdapter.adaptEvent(baseInteractionEvent) === Seq(
            expectedBaseKeyedUuaTweet))
      }
    }
  }

  test("Filter out logged out users") {
    new InteractionEventsFixtures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(RekeyUuaFromInteractionEventsAdapter.adaptEvent(loggedOutInteractionEvent) === Nil)
      }
    }
  }

  test("Filter out detail impressions") {
    new InteractionEventsFixtures {
      Time.withTimeAt(frozenTime) { _ =>
        assert(
          RekeyUuaFromInteractionEventsAdapter.adaptEvent(detailImpressionInteractionEvent) === Nil)
      }
    }
  }
}
