{
  "role": "d scode",
  "na ": "uua-tls-favs-stag ng",
  "conf g-f les": [
    "uua-tls-favs.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-tls-favs"
      },
      {
        "type": "packer",
        "na ": "uua-tls-favs-stag ng",
        "art fact": "./d st/uua-tls-favs.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-tls-favs-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-tls-favs"
        }
      ]
    }
  ]
}
