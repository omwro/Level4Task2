package nl.omererdem.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.*
import nl.omererdem.madlevel4task2.R
import nl.omererdem.madlevel4task2.model.Game
import nl.omererdem.madlevel4task2.repository.GameRepository
import nl.omererdem.madlevel4task2.utils.GameAdapter

class HistoryFragment: Fragment() {
    private lateinit var navController: NavController
    private lateinit var gamesRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setHasOptionsMenu(true)
        initView()
        gamesRepository = GameRepository(requireContext())
        getGamesFromDatabase()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_history, menu)
        val actionbar = (activity as AppCompatActivity).supportActionBar
        if (actionbar != null) {
            actionbar.setTitle(R.string.history_title)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionDeleteBtn -> {
                clearHistory()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        rvHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameAdapter
    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gamesRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }
    }

    private fun clearHistory() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gamesRepository.getAllGames()
            }
            for (game in games) {
                gamesRepository.deleteGame(game)
            }
            this@HistoryFragment.games.clear()
            gameAdapter.notifyDataSetChanged()
        }
    }
}