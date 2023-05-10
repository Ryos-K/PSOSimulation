/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package psosimulation

import processing.core.PApplet

fun main() {

    val psoDrawer = PSODrawer()
    val func: (Float, Float) -> Float = { x, y -> psoDrawer.noise(x * 0.03f, y * 0.03f) * 360f }
    psoDrawer.targetFunction.function = func
    PApplet.runSketch(arrayOf("PSODrawer"), psoDrawer)
}
