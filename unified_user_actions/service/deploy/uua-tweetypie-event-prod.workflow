{
  "role": "d scode",
  "na ": "uua-t etyp e-event-prod",
  "conf g-f les": [
    "uua-t etyp e-event.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-t etyp e-event"
      },
      {
        "type": "packer",
        "na ": "uua-t etyp e-event",
        "art fact": "./d st/uua-t etyp e-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-t etyp e-event-prod-atla",
          "key": "atla/d scode/prod/uua-t etyp e-event"
        },
        {
          "na ": "uua-t etyp e-event-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-t etyp e-event"
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
