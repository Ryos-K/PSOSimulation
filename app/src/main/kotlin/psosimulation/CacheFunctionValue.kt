package psosimulation

class CacheFunctionValue(
        _function: (Float, Float) -> Float,
        private val width: Int,
        private val height: Int,
) {
    private val cache = Array(width) { Array(height) { 0f } }
    var function = _function
        set(value) {
            field = value
            updateCache()
        }

    init {
        updateCache()
    }

    operator fun invoke(x: Float, y: Float): Float {
        if (x in 0f..width.toFloat() && y in 0f..height.toFloat())
                return cache[x.toInt()][y.toInt()]
        else return 640f
    }

    public fun updateCache() {
        repeat(height) { y ->
            repeat(width) { x -> cache[x][y] = function(x.toFloat(), y.toFloat()) }
        }
    }
}
