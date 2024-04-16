{
  "role": "d scode",
  "na ": "uua-ret et-arch val-events-stag ng",
  "conf g-f les": [
    "uua-ret et-arch val-events.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-ret et-arch val-events"
      },
      {
        "type": "packer",
        "na ": "uua-ret et-arch val-events-stag ng",
        "art fact": "./d st/uua-ret et-arch val-events.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-ret et-arch val-events-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-ret et-arch val-events"
        }
      ]
    }
  ]
}
