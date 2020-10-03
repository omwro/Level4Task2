package nl.omererdem.madlevel4task2.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import nl.omererdem.madlevel4task2.R
import nl.omererdem.madlevel4task2.model.Game
import nl.omererdem.madlevel4task2.model.Handmove
import nl.omererdem.madlevel4task2.model.Result

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(game: Game) {
            val resulttxt = Result.findResult(game.result)
            val pcResultImage = Handmove.findHandmove(game.answerPc)
            val userResultImage = Handmove.findHandmove(game.answerUser)
            if (resulttxt !== null && pcResultImage != null && userResultImage != null) {
                itemView.tvDate.text = game.createdOn.toString()
                itemView.tvResult.text = resulttxt.getString()
                itemView.imgPcResult.setImageResource(pcResultImage.getImage())
                itemView.imgYouResult.setImageResource(userResultImage.getImage())
            } else {
                Log.e("GameAdapter", "something goes wrong $game")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}