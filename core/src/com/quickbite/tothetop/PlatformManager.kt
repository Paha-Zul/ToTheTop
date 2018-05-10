package com.quickbite.tothetop

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

object PlatformManager {
    private val platforms = mutableListOf<Platform>()

    private val tmpRect = Rectangle()

    private var nextY = Constants.DIST_BETWEEN_PLATFORMS

    private const val minGap = 75f
    private const val maxGap = 100f
    private const val minPlatform = Constants.BLOCK_SIZE*2

    init{
        platforms += Platform(0f, Constants.DIST_BETWEEN_PLATFORMS, 480f, Constants.BLOCK_SIZE)
        nextY += Constants.DIST_BETWEEN_PLATFORMS
    }

    fun update(delta:Float){

        decideSpawn()
    }

    private fun decideSpawn(){
        val topOfCam = MyGame.camera.position.y + MyGame.camera.viewportHeight

        if(topOfCam < nextY)
            return

        val viewWidth = MyGame.camera.viewportWidth
        val numPlatforms = MathUtils.random(2, 3)
        val maxPlatformWidth = viewWidth - minGap/(numPlatforms-1)

        var offset = 0f

        for(i in 1..numPlatforms){
            var randomWidth = MathUtils.random(minPlatform, maxPlatformWidth)
            if(i == numPlatforms)
                randomWidth = viewWidth - offset

            //Make it a multiple of BLOCK_SIZE
            randomWidth = (Constants.BLOCK_SIZE*(Math.ceil(randomWidth/Constants.BLOCK_SIZE.toDouble()))).toInt().toFloat()

            val gapSize = MathUtils.random(minGap, maxGap)
            platforms += Platform(offset, nextY, randomWidth, Constants.BLOCK_SIZE)

            offset += randomWidth + gapSize
        }

        nextY += Constants.DIST_BETWEEN_PLATFORMS
    }

    fun draw(batch:SpriteBatch){
        platforms.forEach { it.draw(batch) }
    }

    fun debugRender(renderer:ShapeRenderer){
        platforms.forEach { it.debugDraw(renderer) }
    }

    /**
     * Gets the current object the incoming rectangle is colliding with
     * @param box The rectangle to collide with any obstacle
     * @return The object the box is colliding with, null if there was no collision
     */
    fun getCollidingWith(box:Rectangle):Platform?{
        platforms.forEach {
            if(it.isCollidingWith(box))
                return it
        }

        return null
    }

    /**
     * Collides and slides with whatever the incoming rectangle may be colliding with
     * @param box The rectangle to collide with obstacles
     * @param velocity The velocity of the rectangle. This is to predict a step ahead for collision
     * @return A triple containing true/false if colliding, the updated position after colliding, and the object (may be null) it collided with
     */
    fun collideAndSlide(box:Rectangle, velocity:Vector2):Triple<Boolean, Vector2, Platform?>{
        tmpRect.set(box.x+velocity.x, box.y+velocity.y, box.width, box.height)
        platforms.forEach {
            if(it.isCollidingWith(tmpRect)) {
                val position = Vector2()

                //Test the four corners to see where we are precisely
                val bl = it.box.contains(Vector2(box.x, box.y))
                val br = it.box.contains(Vector2(box.x + box.width, box.y))
                val tl = it.box.contains(Vector2(box.x, box.y + box.height))
                val tr = it.box.contains(Vector2(box.x + box.width, box.y + box.height))

                if(box.y < it.box.y)
                    position.set(box.x, it.box.y - it.box.getHeight()) //Put us right below the box
                else
                    position.set(box.x, it.box.y + it.box.getHeight() - 2f) //Put us right on top of the box

                return Triple(true, position, it)
            }
        }

        return Triple(false, Vector2(box.x, box.y), null)
    }

    fun getWillCollideWith(other:Rectangle, velocity: Vector2):Platform?{
//        tempPosition.set(other.x+velocity.x, other.y+velocity.y)
        tmpRect.set(other.x+velocity.x, other.y+velocity.y, other.width, other.height)
        platforms.forEach {
            if(it.isCollidingWith(tmpRect))
                return it
        }

        return null
    }
}