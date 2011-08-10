/*
Copyright 2008-2011 Gephi
Authors : Antonio Patriarca <antoniopatriarca@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.visualization.rendering.pipeline;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import javax.media.opengl.GL;
import org.gephi.visualization.api.vizmodel.VizModel;
import org.gephi.visualization.data.FrameData;
import org.gephi.visualization.rendering.RenderingEngine;
import org.openide.util.Lookup;

/**
 *
 * @author Antonio Patriarca <antoniopatriarca@gmail.com>
 */
public class Pipeline {
    // Viewport
    private int screenX, screenY;
    private int screenWidth, screenHeight;
    
    // Text Renderer for statistics display
    private final TextRenderer textRenderer;
    
    private final VizModel model;
    
    private final RenderingEngine engine;

    public Pipeline(RenderingEngine engine, VizModel model) {
        this.screenX = 0;
        this.screenY = 0;
        this.screenWidth = 1;
        this.screenHeight = 1;
        
        this.engine = engine;
        
        this.textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 14));
        
        this.model = model;
    }
    
    public boolean init(GL gl) {
        return true;
    }
    
    public void reshape(GL gl, int x, int y, int width, int height) {
        this.screenX = x;
        this.screenY = y;
        this.screenWidth = width;
        this.screenHeight = height > 0 ? height : 1;
    }
    
    public void draw(GL gl, FrameData frameData) {
        // sets general states like background color
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        // screenshots ..
        
        // screen
        gl.glViewport(this.screenX, this.screenY, this.screenWidth, this.screenHeight);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // draw graph
     
        if (this.model.isShowFPS()) {
            this.textRenderer.setColor(Color.BLACK);
            this.textRenderer.beginRendering(this.screenWidth, this.screenHeight);
            
            this.textRenderer.draw("FPS: " + engine.getComputedFPS(), 0, screenHeight - 20);
            
            this.textRenderer.endRendering();
        }
    }
    
    public void dispose(GL gl) {
        
    }
}