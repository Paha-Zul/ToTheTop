package com.quickbite.tothetop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector3
import com.quickbite.tothetop.screens.GameScreen

class GameScreenInputProcessor(val screen: GameScreen) : InputProcessor {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {

        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.SPACE -> screen.player.jump()
        }

        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val y = MyGame.cameraViewport.screenHeight - screenY
        println("ScreenY: $y, screenHeight: ${MyGame.cameraViewport.screenHeight}")
        if(y <= MyGame.cameraViewport.screenHeight*0.20f) {
            println("Jump")
            screen.player.jump()
        }

        return false
    }
}