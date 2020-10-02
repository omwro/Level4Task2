package nl.omererdem.madlevel4task2.ui

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
import nl.omererdem.madlevel4task2.*
import nl.omererdem.madlevel4task2.model.Game
import nl.omererdem.madlevel4task2.model.Handmove
import nl.omererdem.madlevel4task2.model.Handmove.*
import nl.omererdem.madlevel4task2.model.Result
import nl.omererdem.madlevel4task2.repository.GameRepository
import nl.omererdem.madlevel4task2.utils.GameAdapter
import nl.omererdem.madlevel4task2.utils.resultStringMap
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
            play(ROCK)
        }
        btnPaper.setOnClickListener {
            play(PAPER)
        }
        btnScissor.setOnClickListener {
            play(SCISSOR)
        }
    }

    private fun play(answerUser: Handmove) {
        val answerPc = listOf(ROCK, PAPER, SCISSOR).random()
        
        val result = gameDecider(answerUser, answerPc)
        val game = Game(null, answerUser.getId(), answerPc.getId(), Calendar.getInstance().time, result.getId())
        Log.i("Play", game.toString())
        saveGame(game)
        updateView(game)
    }

    private fun gameDecider(answerUser: Handmove, answerPc: Handmove): Result {
        if ((answerUser == ROCK && answerPc == SCISSOR) ||
            (answerUser == PAPER && answerPc == ROCK) ||
            (answerUser == SCISSOR && answerPc == PAPER)) {
            return Result.WON
        } else if (answerUser == answerPc) {
            return Result.DRAW
        } else {
            return Result.LOST
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
        val resulttxt = Result.findResult(game.result)
        val answerPcImage = Handmove.findHandmove(game.answerPc)
        val answerUserImage = Handmove.findHandmove(game.answerUser)
        if (resulttxt != null && answerPcImage != null && answerUserImage != null) {
            tvResult.text = resulttxt.getString()
            imgPcResult.setImageResource(answerPcImage.getImage())
            imgYouResult.setImageResource(answerUserImage.getImage())
        }
    }
}