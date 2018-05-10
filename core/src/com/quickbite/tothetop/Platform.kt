package com.quickbite.tothetop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

class Platform(x:Float, y:Float, width:Float, height:Float) {

    companion object {
        val graphic = Texture(Gdx.files.internal("dirt_block.png"))
    }

    val box = Rectangle(x, y, width, height)

    fun isCollidingWith(other:Rectangle) = other.overlaps(box)

    fun draw(batch:SpriteBatch){
        val numDraws = (box.width/32f).toInt()
        for(i in 0 until numDraws){
            batch.draw(graphic, box.x + i*Constants.BLOCK_SIZE, box.y, Constants.BLOCK_SIZE, box.height)
        }
    }

    fun debugDraw(renderer:ShapeRenderer){
        renderer.rect(box.x, box.y, box.width, box.height)
    }
}