package nl.omererdem.madlevel4task2.model

enum class Result() {
    WON {
        override fun getString(): String = "You won!"
        override fun getId(): Int = 1
    },
    DRAW {
        override fun getString(): String = "A draw!"
        override fun getId(): Int = 0
    },
    LOST {
        override fun getString(): String = "You Lost!"
        override fun getId(): Int = -1
    };

    abstract fun getString(): String
    abstract fun getId(): Int

    companion object {
        fun findResult(id: Int): Result? {
            for (result in values()) {
                if (result.getId() == id) {
                    return result
                }
            }
            return null
        }
    }
}