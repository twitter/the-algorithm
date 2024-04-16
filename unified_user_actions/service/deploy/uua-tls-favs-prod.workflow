{
  "role": "d scode",
  "na ": "uua-tls-favs-prod",
  "conf g-f les": [
    "uua-tls-favs.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-tls-favs"
      },
      {
        "type": "packer",
        "na ": "uua-tls-favs",
        "art fact": "./d st/uua-tls-favs.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-tls-favs-prod-atla",
          "key": "atla/d scode/prod/uua-tls-favs"
        },
        {
          "na ": "uua-tls-favs-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-tls-favs"
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
