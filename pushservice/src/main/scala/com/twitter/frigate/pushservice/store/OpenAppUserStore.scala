package com.twitter.frigate.pushservice.store

import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.rux.open_app.UsersInOpenAppDdgOnUserClientColumn

object OpenAppUserStore {
  def apply(stratoClient: Client): ReadableStore[Long, Boolean] = {
    val fetcher = new UsersInOpenAppDdgOnUserClientColumn(stratoClient).fetcher
    StratoFetchableStore.withUnitView(fetcher).mapValues(_ => true)
  }
}
