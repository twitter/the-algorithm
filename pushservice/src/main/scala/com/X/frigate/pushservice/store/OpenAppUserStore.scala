package com.X.frigate.pushservice.store

import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.strato.generated.client.rux.open_app.UsersInOpenAppDdgOnUserClientColumn

object OpenAppUserStore {
  def apply(stratoClient: Client): ReadableStore[Long, Boolean] = {
    val fetcher = new UsersInOpenAppDdgOnUserClientColumn(stratoClient).fetcher
    StratoFetchableStore.withUnitView(fetcher).mapValues(_ => true)
  }
}
