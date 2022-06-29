package com.orangeink.trending.util

val successfulResponse = """
    [
      {
        "author": "D3Ext",
        "name": "WEF",
        "avatar": "https://github.com/D3Ext.png",
        "url": "https://github.com/D3Ext/WEF",
        "description": "Wi-Fi Exploitation Framework",
        "language": "Hack",
        "languageColor": "#878787",
        "stars": 543,
        "forks": 57,
        "currentPeriodStars": 70,
        "builtBy": [
          {
            "username": "D3Ext",
            "href": "https://github.com/D3Ext",
            "avatar": "https://avatars.githubusercontent.com/u/67125397"
          },
          {
            "username": "ultrazar",
            "href": "https://github.com/ultrazar",
            "avatar": "https://avatars.githubusercontent.com/u/48863881"
          }
        ]
      }
    ]
""".trimIndent()

val errorResponse = "Error"