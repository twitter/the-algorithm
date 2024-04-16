package un f ed_user_act ons.adapter.src.test.scala.com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.TestF xtures.UserMod f cat onEventF xture
 mport com.tw ter.un f ed_user_act ons.adapter.user_mod f cat on.UserMod f cat onAdapter
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks

class UserMod f cat onAdapterSpec extends Test w h TableDr venPropertyC cks {
  test("User Create") {
    new UserMod f cat onEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        assert(UserMod f cat onAdapter.adaptEvent(userCreate) === Seq(expectedUuaUserCreate))
      }
    }
  }

  test("User Update") {
    new UserMod f cat onEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        assert(UserMod f cat onAdapter.adaptEvent(userUpdate) === Seq(expectedUuaUserUpdate))
      }
    }
  }
}
