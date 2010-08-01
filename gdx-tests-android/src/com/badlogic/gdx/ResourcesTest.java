/*******************************************************************************
 * Copyright 2010 mzechner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.badlogic.gdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.Font;
import com.badlogic.gdx.graphics.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.SpriteBatch;
import com.badlogic.gdx.graphics.Font.FontStyle;

public class ResourcesTest extends AndroidApplication implements RenderListener
{
	Font font;
	SpriteBatch batch;
	ImmediateModeRenderer renderer;
	
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		initialize( true );
		getGraphics().setRenderListener( this );
	}

	@Override
	public void dispose(Application app) 
	{
		font.dispose();
		batch.dispose();		
	}

	@Override
	public void render(Application app) 
	{	
		if( font != null )
		{
			font.dispose();
			batch.dispose();
		}
		
		font = app.getGraphics().newFont( "Arial", 12, FontStyle.Plain, true );
		batch = new SpriteBatch( app.getGraphics() );
		renderer = new ImmediateModeRenderer( app.getGraphics().getGL10() );
	}

	@Override
	public void surfaceChanged(Application app, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(Application app) {
		// TODO Auto-generated method stub
		
	}
}
