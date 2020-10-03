package nl.omererdem.madlevel4task2.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.*
import nl.omererdem.madlevel4task2.R
import nl.omererdem.madlevel4task2.model.Game
import nl.omererdem.madlevel4task2.repository.GameRepository
import nl.omererdem.madlevel4task2.utils.GameAdapter

class HistoryFragment: Fragment() {
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
        initView()
        gamesRepository = GameRepository(requireContext())
        getGamesFromDatabase()

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
            Log.i("gameslist", games.toString())
            gameAdapter.notifyDataSetChanged()
        }
    }
}