{
  "role": "d scode",
  "na ": "uua-favor e-arch val-events-prod",
  "conf g-f les": [
    "uua-favor e-arch val-events.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-favor e-arch val-events"
      },
      {
        "type": "packer",
        "na ": "uua-favor e-arch val-events",
        "art fact": "./d st/uua-favor e-arch val-events.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-favor e-arch val-events-prod-atla",
          "key": "atla/d scode/prod/uua-favor e-arch val-events"
        },
        {
          "na ": "uua-favor e-arch val-events-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-favor e-arch val-events"
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
