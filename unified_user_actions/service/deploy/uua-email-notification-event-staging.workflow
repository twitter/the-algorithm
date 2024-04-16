{
  "role": "d scode",
  "na ": "uua-ema l-not f cat on-event-stag ng",
  "conf g-f les": [
    "uua-ema l-not f cat on-event.aurora"
  ],
  "bu ld": {
    "play": true,
    "dependenc es": [
      {
        "role": "packer",
        "na ": "packer-cl ent-no-pex",
        "vers on": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "na ": "bundle",
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-ema l-not f cat on-event"
      },
      {
        "type": "packer",
        "na ": "uua-ema l-not f cat on-event-stag ng",
        "art fact": "./d st/uua-ema l-not f cat on-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-ema l-not f cat on-event-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-ema l-not f cat on-event"
        }
      ]
    }
  ]
}
