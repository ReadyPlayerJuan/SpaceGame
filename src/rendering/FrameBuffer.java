package rendering;

/**
 * Copyright (c) 2012, Matt DesLauriers All rights reserved.
 *
 *	Redistribution and use in source and binary forms, with or without
 *	modification, are permitted provided that the following conditions are met:
 *
 *	* Redistributions of source code must retain the above copyright notice, this
 *	  list of conditions and the following disclaimer.
 *
 *	* Redistributions in binary
 *	  form must reproduce the above copyright notice, this list of conditions and
 *	  the following disclaimer in the documentation and/or other materials provided
 *	  with the distribution.
 *
 *	* Neither the name of the Matt DesLauriers nor the names
 *	  of his contributors may be used to endorse or promote products derived from
 *	  this software without specific prior written permission.
 *
 *	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *	AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *	IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *	ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *	LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *	CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *	SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *	INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *	CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *	ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *	POSSIBILITY OF SUCH DAMAGE.
 */

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import main.SettingType;
import main.Settings;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class FrameBuffer {

    private int fboID;
    private int textureID;
    private int width, height;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;


        //texture
        glEnable(GL_TEXTURE_2D);
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        ByteBuffer buf = BufferUtils.createByteBuffer(width * height * 4);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glPixelStorei(GL_PACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);


        //frame buffer
        fboID = glGenFramebuffersEXT();
        glBindFramebufferEXT(GL_FRAMEBUFFER, fboID);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);
        int result = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER);
        if (result != GL_FRAMEBUFFER_COMPLETE) {
            glBindFramebufferEXT(GL_FRAMEBUFFER, 0);
            glDeleteFramebuffers(fboID);

            System.exit(0);//throw new Exception("" + result);
        }
        glBindFramebufferEXT(GL_FRAMEBUFFER, 0);
    }

    public int getID() {
        return fboID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTexture() {
        return textureID;
    }

    public void bindFrameBuffer() {
        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 0, 1);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboID);
    }

    public void unbindFrameBuffer() {
        glViewport(0, 0, Settings.get(SettingType.RESOLUTION_WIDTH),
                Settings.get(SettingType.RESOLUTION_HEIGHT));
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Settings.get(SettingType.RESOLUTION_WIDTH),
                Settings.get(SettingType.RESOLUTION_HEIGHT), 0, 0, 1);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }

    public void cleanUp() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        glDeleteFramebuffersEXT(fboID);
        glDeleteTextures(textureID);
    }
}