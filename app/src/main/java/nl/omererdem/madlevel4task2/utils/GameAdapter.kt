package nl.omererdem.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import nl.omererdem.madlevel4task2.model.Game

val resultStringMap: Map<Int, String> = mapOf(
    0 to R.string.you_won.toString(),
    1 to R.string.you_draw.toString(),
    2 to R.string.you_lost.toString()
)

val imagesMap: Map<Int, Int> = mapOf(
    0 to R.drawable.rock,
    1 to R.drawable.paper,
    2 to R.drawable.scissors
)

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(game: Game) {
            itemView.tvResult.text = resultStringMap[game.result]
            itemView.tvDate.text = game.createdOn.toString()
            itemView.imgPcResult.setImageResource(
                imagesMap[game.answerPc] ?: error(R.string.internal_error.toString())
            )
            itemView.imgYouResult.setImageResource(
                imagesMap[game.answerUser] ?: error(R.string.internal_error.toString())
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}