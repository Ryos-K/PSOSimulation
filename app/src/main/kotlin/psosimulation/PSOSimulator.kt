package psosimulation

import kotlin.math.*

data class Particle(
        val position: FloatArray,
        val velocity: FloatArray,
        var PersonalBestPosition: FloatArray,
)

class PSOSimulator(
        val particles: List<Particle> = List(20) {
            Particle(
                    FloatArray(2) { 0f },
                    FloatArray(2) { 0f },
                    FloatArray(2) { 0f }
            )
        }
        val targetFunction: (Float, Float) -> Float,
        val grobalAcceleration: Float,
        val personalAcceleration: Float,
        val inertia: Float,
        val dimensions: IntArray
) {
    var grobalBestPosition = FloatArray(dimensions.size) { 0f }

    init {
        particles.forEach { particle ->
            val position = particle.position
            val value = targetFunction(position[0], position[1])
            if (value > targetFunction(grobalBestPosition[0], grobalBestPosition[1])) {
                grobalBestPosition = position
            }
            particle.PersonalBestPosition = position
        }
    }

    fun sprinkleParticles() {
        particles.forEach { particle ->
            particle.position.forEachIndexed { index, _ ->
                particle.position[index] = Math.random().toFloat() * dimensions[index]
                particle.velocity[index] = 0f
                particle.PersonalBestPosition[index] = particle.position[index]
            }
        }
    }

    fun nextIteration() {
        particles.forEach { particle ->
            particle.velocity.forEachIndexed { index, velocity ->
                particle.velocity[index] =
                        inertia * velocity +
                                personalAcceleration *
                                        Math.random().toFloat() *
                                        (particle.PersonalBestPosition[index] -
                                                particle.position[index]) +
                                grobalAcceleration *
                                        Math.random().toFloat() *
                                        (grobalBestPosition[index] - particle.position[index])
            }
        }
    }
}
