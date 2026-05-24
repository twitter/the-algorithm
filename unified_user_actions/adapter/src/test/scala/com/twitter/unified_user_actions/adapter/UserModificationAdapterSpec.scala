package unified_user_actions.adapter.src.test.scala.com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFixtures.UserModificationEventFixture
import com.twitter.unified_user_actions.adapter.user_modification.UserModificationAdapter
import com.twitter.util.Time
import org.scalatest.prop.TableDrivenPropertyChecks

class UserModificationAdapterSpec extends Test with TableDrivenPropertyChecks {
  test("User Create") {
    new UserModificationEventFixture {
      Time.withTimeAt(frozenTime) { _ =>
        assert(UserModificationAdapter.adaptEvent(userCreate) === Seq(expectedUuaUserCreate))
      }
    }
  }

  test("User Update") {
    new UserModificationEventFixture {
      Time.withTimeAt(frozenTime) { _ =>
        assert(UserModificationAdapter.adaptEvent(userUpdate) === Seq(expectedUuaUserUpdate))
      }
    }
  }
}
