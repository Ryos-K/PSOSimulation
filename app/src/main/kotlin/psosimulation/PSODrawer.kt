/*
PSODrawer.kt
PSODrawer is a class that draws the Particle Swarm Optimization simulation
It recives a target function = {R^2 -> R} and a PSOSimulator instance on initialization
*/

package psosimulation

import processing.core.*

class PSODrawer(
        private val width: Int = 400,
        private val height: Int = 400,
        private val frameRate: Float = 10f,
        val targetFunction: CacheFunctionValue = CacheFunctionValue({ _, _ -> 0f }, 400, 400),
        val psoSimulator: PSOSimulator =
                PSOSimulator(
                        targetFunction = targetFunction,
                        dimensions = intArrayOf(width, height)
                )
) : PApplet() {

    override fun settings() {
        size(width, height)
    }
    override fun setup() {
        colorMode(HSB, 360f, 100f, 100f)
        background(127)
        frameRate(frameRate)
        noStroke()
        psoSimulator.sprinkleParticles()
    }

    override fun draw() {
        background(127)
        drawContour()

        psoSimulator.nextIteration()
        psoSimulator.particles.forEach { particle ->
            fill(0f, 100f, 100f)
            ellipse(particle.position[0], particle.position[1], 5f, 5f)
        }

        // fill(180f, 100f, 100f)
        // ellipse(200f, 200f, 100f, 100f)

        // captureFrame
        if (frameCount < 100) saveFrame("frames/####.png")
    }

    private fun drawContour() {
        repeat(height) { y ->
            repeat(width) { x ->
                val value = targetFunction(x.toFloat(), y.toFloat())
                fill(value, 100f, 100f)
                ellipse(x.toFloat(), y.toFloat(), 1f, 1f)
            }
        }
    }
}
