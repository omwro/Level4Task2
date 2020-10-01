package nl.omererdem.madlevel4task2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.*
import java.util.*

class GameFragment : Fragment() {
    private lateinit var gamesRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesRepository = GameRepository(requireContext())
        initView()
    }

    private fun initView() {
        btnRock.setOnClickListener {
            play(0)
        }
        btnPaper.setOnClickListener {
            play(1)
        }
        btnScissor.setOnClickListener {
            play(2)
        }
    }

    private fun play(answerUser: Int) {
        Log.i("DEBUG BUTTON", "Test")
        val answerPc = (0..3).random()
        
        val result = gameDecider(answerUser, answerPc)
        val game = Game(null, answerUser, answerPc, Calendar.getInstance().time, result)

        saveGame(game)
        updateView(game)
    }

    private fun gameDecider(answerUser: Int, answerPc: Int): Int {
        if ((answerUser == 0 && answerPc == 2) ||
            (answerUser == 1 && answerPc == 0) ||
            (answerUser == 2 && answerPc == 1)) {
            return 0
        } else if (answerUser == answerPc) {
            return 1
        } else {
            return 2
        }
    }

    private fun saveGame(game: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gamesRepository.insertGame(game)
            }
        }
    }

    private fun updateView(game: Game) {
        tvResult.text = resultStringMap[game.result]
        imgPcResult.setImageResource(
            imagesMap[game.answerPc] ?: error(R.string.internal_error.toString())
        )
        imgYouResult.setImageResource(
            imagesMap[game.answerUser] ?: error(R.string.internal_error.toString())
        )
    }
}