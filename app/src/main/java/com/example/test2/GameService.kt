package com.example.test2

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test2.data.Game
import com.example.test2.data.GameState
import com.example.test2.data.CurrentGame
import com.google.gson.Gson
import org.json.JSONObject

const val gameUrl = "generic-game-service.herokuapp.com"

/*Unit: Unit in Kotlin corresponds to the void in Java. ... But unlike void,
Unit is a real class (Singleton) with only one instance. Nothing: Nothing is
a type in Kotlin that represents “a value that never exists”, that means just
“no value at all”.
 */
typealias GameServiceCallback = (state: Game?, errorCode: Int?) -> Unit
// fun _noe(state: GameState?, errorCode: Int?) { // code without return value

object GameService {

    private enum class APIEndpoints(val url: String){
        CREATE_GAME("%1s%2s%3s".format("https://", gameUrl, "/Game")),
        //JOIN_GAME("%1s%2s%3s".format("https://", gameUrl, "/Game/{gameId}/join")),
        //POLL_GAME("%1s%2s%3s".format("https://", gameUrl, "/Game/{gameId}/poll")),
        UPDATE_GAME("%1s%2s%3s".format("https://", gameUrl, "/Game/732s1i/update"))
    }
    class GameEngine() {
        lateinit var playerId: String
        lateinit var playerName: String
        //lateinit var gameState:List<Int>
        lateinit var gameId: String

        var gameState: GameState = mutableListOf(
                mutableListOf(0, 0, 0),
                mutableListOf(0, 0, 0),
                mutableListOf(0, 0, 0)
        )

        var shameGame: GameState = mutableListOf(
                mutableListOf(0, 0, 0),
                mutableListOf(0, 0, 0),
                mutableListOf(0, 0, 0)
        )

        fun startGame() {
            var game = Game("Helge", "5", gameState)
            //myRequest(playerName,gameState)
            var myCallback: GameServiceCallback = { x, y -> Log.i("GameServiceCallback","${x} returned ${y}") }

            //myRequest(game.playerName, game.state, myCallback)
            update(game.player, game.state, myCallback)
        }




        // ToDo: gjør noe



        fun myRequest(playerName: String, gameState: GameState, callback: GameServiceCallback) {
            /*val myName = playerName
            val myState = gameState

            val gson = Gson()
            var myGame = Game(playerName, "0", gameState)
            val myJsonString = gson.toJson(myGame)
            Log.i("GameEngine.request", "${myJsonString.toString()}")*/
            gameId = "qk68k"

            val myRequestData = JSONObject()
            myRequestData.put("player", playerName)
            myRequestData.put("state", gameState)

            // val myCustomData = "{\"player\": \"Super Mario\",\"state\": [[0,0,0],[0,0,0],[0,0,0]]}"
            var url = APIEndpoints.CREATE_GAME.url

            val myRequest = object: JsonObjectRequest(Request.Method.POST, url, myRequestData,
                    {
                        val game = Gson().fromJson(it.toString(0), Game::class.java)
                        //callback(game,null)
                    },{
                //callback(null, it.networkResponse.statusCode)
                var networkResp = it.networkResponse.statusCode.toString()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Game-Service-Key"] = "yHSNBWpygM"
                    return headers
                }
            }
        }

        fun update(playerName: String, gameState: GameState, callback: GameServiceCallback) {
            var url = APIEndpoints.UPDATE_GAME.url
            /*val myName = playerName
            val myState = gameState

            val gson = Gson()
            var myGame = Game(playerName, "0", gameState)
            val myJsonString = gson.toJson(myGame)
            Log.i("GameEngine.request", "${myJsonString.toString()}")*/

            val myUpdatePair1 = Pair<String,Any>("state", gameState)
            val myUpdatePair2 = Pair("player", playerName.toString())
            val myUpdateData = JSONObject()
            myUpdateData.put("Player name", playerName)
            myUpdateData.put("state", gameState)
            val gson = Gson()

            shameGame = mutableListOf(
                    mutableListOf(0, 1, 0),
                    mutableListOf(0, 0, 0),
                    mutableListOf(0, 0, 0)
            )
            var newGameState = gameState
            newGameState = mutableListOf(
                    mutableListOf(1, 2, 1),
                    mutableListOf(2, 1, 2),
                    mutableListOf(1, 2, 1)
            )

            val currentGame = CurrentGame(playerName, newGameState )
            val currentGameJson = gson.toJson(currentGame)
            val objectGameJson = JSONObject(currentGameJson)



            val myRequest = object: JsonObjectRequest(Request.Method.POST, url, objectGameJson,
                    {
                        val game = Gson().fromJson(it.toString(0), Game::class.java)
                        //callback(game,null)
                    },{
                //callback(null, it.networkResponse.statusCode)
                var networkResp = it.networkResponse.statusCode.toString()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Game-Service-Key"] = "d7nHIrOU5C"
                    return headers
                }
            }
        }
    }
}

