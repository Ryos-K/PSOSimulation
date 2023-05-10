package psosimulation

import kotlin.math.*

data class Particle(
        val position: FloatArray,
        val velocity: FloatArray,
        var PersonalBestPosition: FloatArray,
)

class PSOSimulator(
        val particles: List<Particle> =
                List(28) {
                    Particle(FloatArray(2) { 0f }, FloatArray(2) { 0f }, FloatArray(2) { 0f })
                },
        val targetFunction: CacheFunctionValue = CacheFunctionValue({ _, _ -> 0f }, 400, 400),
        val grobalAcceleration: Float = 0.05f,
        val personalAcceleration: Float = 0.12f,
        val inertia: Float = 0.1f,
        val dimensions: IntArray
) {
    var grobalBestPosition = FloatArray(dimensions.size) { 0f }

    init {
        particles.forEach { particle ->
            val position = particle.position
            val value = targetFunction(position[0], position[1])
            if (value < targetFunction(grobalBestPosition[0], grobalBestPosition[1])) {
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
            particle.position.forEachIndexed { index, position ->
                particle.position[index] = position + particle.velocity[index]
                val value = targetFunction(particle.position[0], particle.position[1])
                if (value <
                                targetFunction(
                                        particle.PersonalBestPosition[0],
                                        particle.PersonalBestPosition[1]
                                )
                ) {
                    particle.PersonalBestPosition = particle.position.clone()
                }
                if (value < targetFunction(grobalBestPosition[0], grobalBestPosition[1])) {
                    grobalBestPosition = particle.position.clone()
                }
            }
        }
    }
}
