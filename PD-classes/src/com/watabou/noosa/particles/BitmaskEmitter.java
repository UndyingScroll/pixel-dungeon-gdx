/*
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa.particles;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;

import com.watabou.utils.RectF;

import com.watabou.gltextures.SmartTexture;
import com.watabou.noosa.Image;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

public class BitmaskEmitter extends Emitter {

	// DON'T USE WITH COMPLETELY TRANSPARENT IMAGES!!!
	
	private SmartTexture map;
	private Pixmap pixmap;
	private int mapW;
	private int mapH;

	public BitmaskEmitter( Image target ) {
		super();

		this.target = target;

		map = target.texture;
		mapW = map.bitmap.getWidth();
		mapH = map.bitmap.getHeight();

		TextureData td = map.bitmap.getTextureData();
		if (!td.isPrepared()) {
			td.prepare();
		}
		pixmap = td.consumePixmap();
	}

	@Override
	protected void emit( int index ) {

		RectF frame = ((Image)target).frame();
		float ofsX = frame.left * mapW;
		float ofsY = frame.top * mapH;

		float x, y;
		do {
			x = Random.Float( frame.width() ) * mapW;
			y = Random.Float( frame.height() ) * mapH;
		} while ((pixmap.getPixel( (int)(x + ofsX), (int)(y + ofsY) ) & 0x000000FF) == 0);

		factory.emit( this, index,
			target.x + x * target.scale.x,
			target.y + y * target.scale.y );
	}
}