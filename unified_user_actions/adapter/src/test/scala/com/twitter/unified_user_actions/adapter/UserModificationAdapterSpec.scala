package unified_user_actions.adapter.src.test.scala.com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFitwittertures.UserModificationEventFitwitterture
import com.twitter.unified_user_actions.adapter.user_modification.UserModificationAdapter
import com.twitter.util.Time
import org.scalatest.prop.TableDrivenPropertyChecks

class UserModificationAdapterSpec etwittertends Test with TableDrivenPropertyChecks {
  test("User Create") {
    new UserModificationEventFitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        assert(UserModificationAdapter.adaptEvent(userCreate) === Seq(etwitterpectedUuaUserCreate))
      }
    }
  }

  test("User Update") {
    new UserModificationEventFitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        assert(UserModificationAdapter.adaptEvent(userUpdate) === Seq(etwitterpectedUuaUserUpdate))
      }
    }
  }
}
