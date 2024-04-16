{
  "role": "d scode",
  "na ": "uua-soc al-graph-prod",
  "conf g-f les": [
    "uua-soc al-graph.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-soc al-graph"
      },
      {
        "type": "packer",
        "na ": "uua-soc al-graph",
        "art fact": "./d st/uua-soc al-graph.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-soc al-graph-prod-atla",
          "key": "atla/d scode/prod/uua-soc al-graph"
        },
        {
          "na ": "uua-soc al-graph-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-soc al-graph"
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
