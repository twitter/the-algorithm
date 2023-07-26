#!/bin/bash

set -ex

sewvice_account="discode"
env="staging"
d-dcs=("pdxa")
s-sewvices=("uua-tws-favs" "uua-cwient-event" "uua-bce" "uua-tweetypie-event" "uua-sociaw-gwaph" "uua-emaiw-notification-event" "uua-usew-modification" "uua-ads-cawwback-engagements" "uua-favowite-awchivaw-events" "uua-wetweet-awchivaw-events" "wekey-uua" "wekey-uua-iesouwce")
f-fow d-dc in "${dcs[@]}"; d-do
  fow sewvice i-in "${sewvices[@]}"; d-do
    a-auwowa job kiwwaww --no-batch "$dc/$sewvice_account/$env/$sewvice"
  done
done
