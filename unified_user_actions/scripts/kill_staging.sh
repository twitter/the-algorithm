#!/bin/bash

set -ex

service_account="discode"
env="staging"
dcs=("pdxa")
services=("uua-tls-favs" "uua-client-event" "uua-bce" "uua-tweetypie-event" "uua-social-graph" "uua-email-notification-event" "uua-user-modification" "uua-ads-callback-engagements" "uua-favorite-archival-events" "uua-retweet-archival-events" "rekey-uua" "rekey-uua-iesource")
for dc in "${dcs[@]}"; do
  for service in "${services[@]}"; do
    aurora job killall --no-batch "$dc/$service_account/$env/$service"
  done
done
