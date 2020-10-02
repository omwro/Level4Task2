package nl.omererdem.madlevel4task2.model

import nl.omererdem.madlevel4task2.R.drawable.*

enum class Handmove() {
    ROCK {
        override fun getImage(): Int = rock
        override fun getId(): Int = 0
    },
    PAPER {
        override fun getImage(): Int = paper
        override fun getId(): Int = 1
    },
    SCISSOR {
        override fun getImage(): Int = scissors
        override fun getId(): Int = 2
    };

    abstract fun getImage(): Int
    abstract fun getId(): Int

    companion object {
        fun findHandmove(id: Int): Handmove? {
            for (handmove in values()) {
                if (handmove.getId() == id) {
                    return handmove
                }
            }
            return null
        }
    }
}