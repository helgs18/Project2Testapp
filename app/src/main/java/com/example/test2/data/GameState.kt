package com.example.test2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
        {
            "player": "Player name",
            "state": [
            null
            ]
        }
 */

typealias GameState = MutableList<MutableList<Int>>

@Parcelize
class CurrentGame (
    var player: String,
    var state: GameState
        ): Parcelable

@Parcelize
class Game (
    var player: String,
    var gameId: String,
    var state: GameState
): Parcelable