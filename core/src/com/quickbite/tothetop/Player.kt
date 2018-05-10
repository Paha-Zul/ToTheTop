package com.quickbite.tothetop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Player {
    private val speed = 400f
    private val gravity = 100f
    private val jumpSpeed = 30f

    private val graphic = Sprite(Texture(Gdx.files.internal("badlogic.jpg")))
    private val position = Vector2(100f, 200f)
    private var velocity = Vector2()

    private val boundingBox:Rectangle = Rectangle(position.x, position.y, 32f, 32f)

    private var moveLeft = false
    private var moveRight = false
    private var jump = false

    private var colliding = false

    fun moveLeft(){
        moveLeft = true
    }

    fun moveRight(){
        moveRight = true
    }

    fun jump(){
        if(onPlatform())
            jump = true
    }

    fun update(delta:Float){
        if(!onPlatform()) {
            velocity.y -= gravity * delta
        }else{
            velocity.y  = 0f
        }

        if(moveRight)
            velocity.x += speed*delta
        if(moveLeft)
            velocity.x -= speed*delta
        if(jump)
            velocity.y  += jumpSpeed

        moveLeft = false
        moveRight = false
        jump = false

        position.add(velocity)

        boundingBox.setPosition(position)

        val result = PlatformManager.collideAndSlide(boundingBox, velocity)
        colliding = result.first
        position.set(result.second)

        velocity.x = 0f
    }

    fun draw(batch: SpriteBatch){
        graphic.setPosition(position.x, position.y)
        batch.draw(graphic, position.x, position.y, 32f, 32f)
    }

    fun onPlatform():Boolean = colliding

    private fun checkCollisions(){

    }
}