package unified_user_actions.adapter.src.test.scala.com.X.unified_user_actions.adapter

import com.X.inject.Test
import com.X.unified_user_actions.adapter.TestFixtures.UserModificationEventFixture
import com.X.unified_user_actions.adapter.user_modification.UserModificationAdapter
import com.X.util.Time
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
