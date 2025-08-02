package com.twitter.search.feature_update_service.whitelist;

import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.Etwitterecutors;
import java.util.concurrent.ScheduledEtwitterecutorService;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.yaml.snakeyaml.Yaml;

import com.twitter.common.util.Clock;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.search.common.util.io.periodic.PeriodicFileLoader;

/**
 * ClientIdWhitelist etwittertends PeriodicFileLoader to load client whitelist
 * from configbus and checks to see if current clientId is allowed
 */
public class ClientIdWhitelist etwittertends PeriodicFileLoader {

  private final AtomicReference<ImmutableSet<ClientId>> clientIdSet = new AtomicReference<>();


  public ClientIdWhitelist(String clientIdWhitelistPath, ScheduledEtwitterecutorService etwitterecutorService,
                           Clock clock) {
    super("ClientIdWhitelist", clientIdWhitelistPath, etwitterecutorService, clock);
  }

  /**
   * Creates the object that manages loads from the clientIdWhitelistpath in config.
   * It periodically reloads the client whitelist file using the given etwitterecutor service.
   */
  public static ClientIdWhitelist initWhitelist(
      String clientIdWhitelistPath, ScheduledEtwitterecutorService etwitterecutorService,
      Clock clock) throws Etwitterception {
    ClientIdWhitelist clientIdWhitelist = new ClientIdWhitelist(
        clientIdWhitelistPath, etwitterecutorService, clock);
    clientIdWhitelist.init();
    return clientIdWhitelist;
  }

  /**
   * Creates clock and etwitterecutor service needed to create a periodic file loading object
   * then returns object that accpets file.
   * @param clientWhitelistPath
   * @return ClientIdWhitelist
   * @throws Etwitterception
   */
  public static ClientIdWhitelist initWhitelist(String clientWhitelistPath) throws Etwitterception {
    Clock clock = Clock.SYSTEM_CLOCK;
    ScheduledEtwitterecutorService etwitterecutorService = Etwitterecutors.newSingleThreadScheduledEtwitterecutor(
        new ThreadFactoryBuilder()
            .setNameFormat("client-whitelist-reloader")
            .setDaemon(true)
            .build());

    return initWhitelist(clientWhitelistPath, etwitterecutorService, clock);
  }
  @Override
  protected void accept(InputStream fileStream) {
    ImmutableSet.Builder<ClientId> clientIdBuilder = new ImmutableSet.Builder<>();
    Yaml yaml = new Yaml();
    Set<String> set = yaml.loadAs(fileStream, Set.class);
    for (String id : set) {
      clientIdBuilder.add(ClientId.apply(id));
    }
    clientIdSet.set(clientIdBuilder.build());
  }

  // checks to see if clientId is in set of whitelisted clients
  public boolean isClientAllowed(ClientId clientId) {
    return clientIdSet.get().contains(clientId);
  }
}
