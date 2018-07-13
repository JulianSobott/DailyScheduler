package main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import datahandling.Data;
import datahandling.DataHandler;
import datahandling.ServerCommunicator;
import datahandling.Task;

public class Main extends ApplicationAdapter {
	public enum DeviceMode {
		DESKTOP, MOBILE
	}
	DeviceMode deviceMode;
	
	SpriteBatch sb;
	ShapeRenderer sr;
	OrthographicCamera cam;
	Texture img;
	MainScreen mainScreen;
	
	//data
	public DataHandler dataHandler;
	
	public Main(DeviceMode deviceMode) {
		this.deviceMode = deviceMode;
	}
	
	@Override
	public void create () {
		dataHandler = new DataHandler();
		//dataHandler.addNewTask(new Task());
		//dataHandler.save();
		dataHandler.load();
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		mainScreen = new MainScreen();
		
		// event handling
				InputMultiplexer multiplexer = new InputMultiplexer();
				multiplexer.addProcessor(mainScreen);
				if (deviceMode == DeviceMode.DESKTOP)
					multiplexer.addProcessor(new InputAdapter() {

						@Override
						public boolean touchUp(int screenX, int screenY, int pointer, int button) {
							return false;
							
						}

						@Override
						public boolean touchDragged(int screenX, int screenY, int pointer) {
							return false;
							
						}

						@Override
						public boolean touchDown(int screenX, int screenY, int pointer, int button) {
							return false;
						}

						@Override
						public boolean scrolled(int amount) {

							return false;
						}
					});
				if (deviceMode == DeviceMode.MOBILE)
					multiplexer.addProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {

						@Override
						public boolean zoom(float initialDistance, float distance) {
						
							return true;
						}

						@Override
						public boolean panStop(float x, float y, int pointer, int button) {

							return true;
						}

						@Override
						public boolean pan(float x, float y, float deltaX, float deltaY) {
							
							return true;
						}
					}));
				Gdx.input.setInputProcessor(multiplexer);
				
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.4f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sr.setProjectionMatrix(cam.combined);
		sb.setProjectionMatrix(cam.combined);
		mainScreen.render(sr, sb);
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		sr.dispose();
	}
}
