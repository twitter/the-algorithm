{
  "role": "d scode",
  "na ": "rekey-uua-stag ng",
  "conf g-f les": [
    "rekey-uua.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:rekey-uua"
      },
      {
        "type": "packer",
        "na ": "rekey-uua-stag ng",
        "art fact": "./d st/rekey-uua.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "rekey-uua-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/rekey-uua"
        }
      ]
    }
  ]
}
