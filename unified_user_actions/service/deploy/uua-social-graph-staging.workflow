{
  "role": "d scode",
  "na ": "uua-soc al-graph-stag ng",
  "conf g-f les": [
    "uua-soc al-graph.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-soc al-graph"
      },
      {
        "type": "packer",
        "na ": "uua-soc al-graph-stag ng",
        "art fact": "./d st/uua-soc al-graph.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-soc al-graph-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-soc al-graph"
        }
      ]
    }
  ]
}
