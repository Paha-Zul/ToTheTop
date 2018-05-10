package com.quickbite.tothetop.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.quickbite.tothetop.GameScreenInputProcessor
import com.quickbite.tothetop.MyGame
import com.quickbite.tothetop.PlatformManager
import com.quickbite.tothetop.Player

class GameScreen : Screen {
    val player = Player()

    override fun show() {
        Gdx.input.inputProcessor = GameScreenInputProcessor(this)
        MyGame.camera.position.set(0f, -100f, 0f)
    }

    override fun render(delta: Float) {
        update(delta)
        draw()
    }

    private fun update(delta:Float){
        player.update(delta)
        checkMove()
        PlatformManager.update(delta)
        MyGame.camera.translate(0f, 1f)
    }

    private fun draw(){
        MyGame.batch.begin()
        MyGame.batch.projectionMatrix = MyGame.camera.combined
        player.draw(MyGame.batch)
        PlatformManager.draw(MyGame.batch)
        MyGame.batch.end()

        MyGame.shapeRenderer.projectionMatrix = MyGame.camera.combined
        MyGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        PlatformManager.debugRender(MyGame.shapeRenderer)
        MyGame.shapeRenderer.end()
    }

    fun checkMove(){
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            player.moveLeft()
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            player.moveRight()
        if(Gdx.input.isTouched ){
            if(Gdx.input.x < MyGame.cameraViewport.screenWidth*0.2f)
                player.moveLeft()
            else if(Gdx.input.x > MyGame.cameraViewport.screenWidth*0.8f)
                player.moveRight()
        }
    }

    override fun hide() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }
}