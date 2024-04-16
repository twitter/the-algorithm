{
  "role": "d scode",
  "na ": "uua-ema l-not f cat on-event-prod",
  "conf g-f les": [
    "uua-ema l-not f cat on-event.aurora"
  ],
  "bu ld": {
    "play": true,
    "tr gger": {
      "cron-sc dule": "0 17 * * 2"
    },
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
        "na ": "uua-ema l-not f cat on-event",
        "art fact": "./d st/uua-ema l-not f cat on-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-ema l-not f cat on-event-prod-atla",
          "key": "atla/d scode/prod/uua-ema l-not f cat on-event"
        },
        {
          "na ": "uua-ema l-not f cat on-event-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-ema l-not f cat on-event"
        }
      ]
    }
  ],
  "subscr pt ons": [
      {
        "type": "SLACK",
        "rec p ents": [
          {
            "to": "d scode-oncall"
          }
        ],
        "events": ["WORKFLOW_SUCCESS"]
      },
      {
        "type": "SLACK",
        "rec p ents": [{
          "to": "d scode-oncall"
        }],
        "events": ["*FA LED"]
      }
    ]
}
