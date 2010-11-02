
package com.badlogic.gdx.backends.desktop;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LwjglCanvas implements Application {
	LwjglGraphics graphics;
	LwjglAudio audio;
	LwjglFiles files;
	LwjglInput input;
	final ApplicationListener listener;
	Thread mainLoopThread;
	Canvas canvas;
	Timer timer = new Timer("LwjglCanvas Timer");

	public LwjglCanvas (ApplicationListener listener, boolean useGL2) {
		LwjglNativesLoader.load();

		canvas = new Canvas() {
			private final Dimension minSize = new Dimension();

			public final void addNotify () {
				super.addNotify();
				EventQueue.invokeLater(new Runnable() {
					public void run () {
						start();
					}
				});
			}

			public Dimension getMinimumSize () {
				return minSize;
			}
		};

		canvas.setSize(100, 100);
		canvas.setMinimumSize(new Dimension(2, 2));
		graphics = new LwjglGraphics(canvas, useGL2);
		audio = new LwjglAudio();
		files = new LwjglFiles();
		input = new LwjglInput();
		this.listener = listener;

		Gdx.app = this;
		Gdx.graphics = graphics;
		Gdx.audio = audio;
		Gdx.files = files;
		Gdx.input = input;
	}

	public Canvas getCanvas () {
		return canvas;
	}

	@Override public Audio getAudio () {
		return audio;
	}

	@Override public Files getFiles () {
		return files;
	}

	@Override public Graphics getGraphics () {
		return graphics;
	}

	@Override public Input getInput () {
		return input;
	}

	@Override public ApplicationType getType () {
		return ApplicationType.Desktop;
	}

	@Override public int getVersion () {
		return 0;
	}

	@Override public void log (String tag, String message) {
		System.out.println(tag + ": " + message);
	}

	void start () {
		try {
			graphics.setupDisplay();
		} catch (LWJGLException e) {
			throw new GdxRuntimeException(e);
		}

		Keyboard.enableRepeatEvents(true);

		listener.create();
		listener.resize(graphics.getWidth(), graphics.getHeight());

		final Runnable runnable = new Runnable() {
			int lastWidth = graphics.getWidth();
			int lastHeight = graphics.getHeight();

			public void run () {
				graphics.updateTime();
				input.update();

				if (lastWidth != graphics.getWidth() || lastHeight != graphics.getHeight()) {
					lastWidth = graphics.getWidth();
					lastHeight = graphics.getHeight();
					listener.resize(lastWidth, lastHeight);
				}

				listener.render();
				input.processEvents(null);
				Display.update();
				Display.sync(60);
			}
		};

		timer.schedule(new TimerTask() {
			public void run () {
				try {
					EventQueue.invokeAndWait(runnable);
				} catch (Exception ex) {
					throw new GdxRuntimeException(ex);
				}
			}
		}, 0, 16);
	}

	public void stop () {
		timer.cancel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				listener.pause();
				listener.dispose();
				Display.destroy();
			}
		});
	}
}