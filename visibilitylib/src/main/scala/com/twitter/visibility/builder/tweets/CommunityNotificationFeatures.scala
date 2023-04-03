packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.notificationselonrvicelon.modelonl.notification.ActivityNotification
import com.twittelonr.notificationselonrvicelon.modelonl.notification.MelonntionNotification
import com.twittelonr.notificationselonrvicelon.modelonl.notification.MelonntionQuotelonNotification
import com.twittelonr.notificationselonrvicelon.modelonl.notification.Notification
import com.twittelonr.notificationselonrvicelon.modelonl.notification.QuotelonTwelonelontNotification
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.TwelonelontSourcelon
import com.twittelonr.visibility.felonaturelons.NotificationIsOnCommunityTwelonelont
import com.twittelonr.visibility.modelonls.CommunityTwelonelont

objelonct CommunityNotificationFelonaturelons {
  delonf ForNonCommunityTwelonelontNotification: FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(NotificationIsOnCommunityTwelonelont, falselon)
  }
}

class CommunityNotificationFelonaturelons(
  twelonelontSourcelon: TwelonelontSourcelon,
  elonnablelonCommunityTwelonelontHydration: Gatelon[Long],
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("community_notification_felonaturelons")
  privatelon[this] val relonquelonstsCountelonr = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")
  privatelon[this] val hydrationsCountelonr = scopelondStatsReloncelonivelonr.countelonr("hydrations")
  privatelon[this] val notificationIsOnCommunityTwelonelontCountelonr =
    scopelondStatsReloncelonivelonr.scopelon(NotificationIsOnCommunityTwelonelont.namelon).countelonr("truelon")
  privatelon[this] val notificationIsNotOnCommunityTwelonelontCountelonr =
    scopelondStatsReloncelonivelonr.scopelon(NotificationIsOnCommunityTwelonelont.namelon).countelonr("falselon")

  delonf forNotification(notification: Notification): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonstsCountelonr.incr()
    val isCommunityTwelonelontRelonsult = gelontTwelonelontIdOption(notification) match {
      caselon Somelon(twelonelontId) if elonnablelonCommunityTwelonelontHydration(notification.targelont) =>
        hydrationsCountelonr.incr()
        twelonelontSourcelon
          .gelontTwelonelont(twelonelontId)
          .map {
            caselon Somelon(twelonelont) if CommunityTwelonelont(twelonelont).nonelonmpty =>
              notificationIsOnCommunityTwelonelontCountelonr.incr()
              truelon
            caselon _ =>
              notificationIsNotOnCommunityTwelonelontCountelonr.incr()
              falselon
          }
      caselon _ => Stitch.Falselon
    }
    _.withFelonaturelon(NotificationIsOnCommunityTwelonelont, isCommunityTwelonelontRelonsult)
  }

  privatelon[this] delonf gelontTwelonelontIdOption(notification: Notification): Option[Long] = {
    notification match {
      caselon n: MelonntionNotification => Somelon(n.melonntioningTwelonelontId)
      caselon n: MelonntionQuotelonNotification => Somelon(n.melonntioningTwelonelontId)
      caselon n: QuotelonTwelonelontNotification => Somelon(n.quotelondTwelonelontId)
      caselon n: ActivityNotification[_] if n.visibilityTwelonelonts.contains(n.objelonctId) => Somelon(n.objelonctId)
      caselon _ => Nonelon
    }
  }
}
