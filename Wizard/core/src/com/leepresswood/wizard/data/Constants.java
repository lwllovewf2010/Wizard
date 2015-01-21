package com.leepresswood.wizard.data;

import com.badlogic.gdx.Gdx;


public class Constants
{
	//Paddle
	public static final int PADDLE_MAX_SIZE = 2;
	public static final int PADDLE_MIN_SIZE = -PADDLE_MAX_SIZE;	
	public static final float PADDLE_SIZE_INCREMENT_AMOUNT = Gdx.graphics.getWidth() * 0.05f;	//Size jump of the paddle during an increment or decrement.
	public static final float PADDLE_START_HEIGHT_PERCENT = 0.03f;										//Pixel height is a percent of the screen. This is the start percent.
	public static final float PADDLE_START_WIDTH_PERCENT = 0.2f;										//Pixel width is a percent of the screen. This is the start percent.
	public static final float PADDLE_START_WIDTH = Gdx.graphics.getWidth() * Constants.PADDLE_START_WIDTH_PERCENT;
	public static final float PADDLE_START_HEIGHT = Gdx.graphics.getHeight() * Constants.PADDLE_START_HEIGHT_PERCENT;
	public static final float PADDLE_START_POSITION_X_PERCENT = 0.5f;									//Where the paddle starts as a percent of the screen's width.
	public static final float PADDLE_START_POSITION_Y_PERCENT = 0.2f;									//Where the paddle starts as a percent of the screen's height.
	
	//Balls
	public static final float BALL_SIZE = PADDLE_START_HEIGHT;
	public static final float BALL_SPEED_START = Gdx.graphics.getHeight() / 1.8f;					//Constant multiplier is 1/s, where S is number of seconds it should take to go from bottom of screen to top.
	public static final float BALL_SPEEDUP_AMOUNT = 1.15f;
	public static final int BALL_MAX_SPEEDUP_COUNT = 3;
	public static final int BALL_MAX_SLOWDOWN_COUNT = BALL_MAX_SPEEDUP_COUNT;
	public static final float BALL_MIN_UP_ANGLE = 50f;
	public static final float BALL_MAX_UP_ANGLE = 180f - BALL_MIN_UP_ANGLE;	
	
	//Blocks
	public static final int BLOCK_MAX_BLOCKS_ACROSS = 8;
	public static final int BLOCK_MAX_BLOCKS_DOWN = 8;
	public static final float BLOCK_GAP = 0f;
	public static final float BLOCK_WIDTH = (Gdx.graphics.getWidth() - (BLOCK_MAX_BLOCKS_DOWN - 1) * BLOCK_GAP) / (float) BLOCK_MAX_BLOCKS_ACROSS;
	public static final float BLOCK_HEIGHT = BLOCK_WIDTH / 2.5f;

	//Powerups
	public static final float POWERUP_SIZE = PADDLE_START_WIDTH;
	public static final float POWERUP_SPEED = Gdx.graphics.getHeight() / 3f;
	public static final float POWERUP_CHANCE = 0.33f;
	public static final int POWERUP_NUMBER = 5;
}
