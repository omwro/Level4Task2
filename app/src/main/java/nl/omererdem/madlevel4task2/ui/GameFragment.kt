package nl.omererdem.madlevel4task2.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
import java.util.*

class GameFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var gamesRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private var totalWins = 0
    private var totalDraws = 0
    private var totalLooses = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setHasOptionsMenu(true)
        gamesRepository = GameRepository(requireContext())
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_game, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionHistoryBtn -> {
                navController.navigate(R.id.action_gameFragment_to_historyFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        calculateStatistics()
    }

    private fun play(answerUser: Handmove) {
        val answerPc = listOf(ROCK, PAPER, SCISSOR).random()
        
        val result = gameDecider(answerUser, answerPc)
        val game = Game(answerUser.getId(), answerPc.getId(), Calendar.getInstance().time, result)
        Log.i("Play", game.toString())
        saveGame(game)
        updateView(game)
    }

    private fun gameDecider(answerUser: Handmove, answerPc: Handmove): Result {
        return if ((answerUser == ROCK && answerPc == SCISSOR) ||
            (answerUser == PAPER && answerPc == ROCK) ||
            (answerUser == SCISSOR && answerPc == PAPER)) {
            Result.WON
        } else if (answerUser == answerPc) {
            Result.DRAW
        } else {
            Result.LOST
        }
    }

    private fun saveGame(game: Game) {
        mainScope.launch {
            withContext(Dispatchers.Main) {
                gamesRepository.insertGame(game)
                calculateStatistics()
            }
        }
    }

    private fun updateView(game: Game) {
        val answerPcImage = Handmove.findHandmove(game.answerPc)
        val answerUserImage = Handmove.findHandmove(game.answerUser)
        if (answerPcImage != null && answerUserImage != null) {
            tvResult.text = game.result.getString()
            imgPcResult.setImageResource(answerPcImage.getImage())
            imgYouResult.setImageResource(answerUserImage.getImage())
        }
    }

    private fun updateStatisticsView() {
        tvStatWin.text = getString(R.string.win, totalWins)
        tvStatDraw.text = getString(R.string.draw, totalDraws)
        tvStatLost.text = getString(R.string.loose, totalLooses)
    }

    private fun calculateStatistics() {
        totalWins = 0
        totalDraws = 0
        totalLooses = 0
        mainScope.launch {
            withContext(Dispatchers.Main) {
                for (game in gamesRepository.getAllGames()) {
                    when (game.result) {
                        Result.WON -> {
                            this@GameFragment.totalWins++
                        }
                        Result.DRAW -> {
                            this@GameFragment.totalDraws++
                        }
                        Result.LOST -> {
                            this@GameFragment.totalLooses++
                        }
                    }
                }
                updateStatisticsView()
            }
        }
    }
}