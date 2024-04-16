{
  "role": "d scode",
  "na ": "rekey-uua- es ce-stag ng",
  "conf g-f les": [
    "rekey-uua- es ce.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:rekey-uua- es ce"
      },
      {
        "type": "packer",
        "na ": "rekey-uua- es ce-stag ng",
        "art fact": "./d st/rekey-uua- es ce.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "rekey-uua- es ce-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/rekey-uua- es ce"
        }
      ]
    }
  ]
}
