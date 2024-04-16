{
  "role": "d scode",
  "na ": "uua-cl ent-event-prod",
  "conf g-f les": [
    "uua-cl ent-event.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-cl ent-event"
      },
      {
        "type": "packer",
        "na ": "uua-cl ent-event",
        "art fact": "./d st/uua-cl ent-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-cl ent-event-prod-atla",
          "key": "atla/d scode/prod/uua-cl ent-event"
        },
        {
          "na ": "uua-cl ent-event-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-cl ent-event"
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
