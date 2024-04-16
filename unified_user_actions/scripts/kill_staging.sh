#!/b n/bash

set -ex

serv ce_account="d scode"
env="stag ng"
dcs=("pdxa")
serv ces=("uua-tls-favs" "uua-cl ent-event" "uua-bce" "uua-t etyp e-event" "uua-soc al-graph" "uua-ema l-not f cat on-event" "uua-user-mod f cat on" "uua-ads-callback-engage nts" "uua-favor e-arch val-events" "uua-ret et-arch val-events" "rekey-uua" "rekey-uua- es ce")
for dc  n "${dcs[@]}"; do
  for serv ce  n "${serv ces[@]}"; do
    aurora job k llall --no-batch "$dc/$serv ce_account/$env/$serv ce"
  done
done
