package tiborsimon.javagame.ui;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Slick2D API-n belüli fontkezelést leegyszerűsítő osztály.
 * @author Brandon Reid
 *
 */
public class SimpleFont {
	private UnicodeFont font;

	public SimpleFont(String fontName, int style, int size, Color color) throws SlickException {
		this(new Font(fontName, style, size), color);
	}

	public SimpleFont(String fontName, int style, int size) throws SlickException {
		this(new Font(fontName, style, size));
	}

	public SimpleFont(Font font) throws SlickException {
		this(font, Color.white);
	}

	@SuppressWarnings("unchecked")
	public SimpleFont(Font font, Color color) throws SlickException {
		PrintStream original = System.out;
		try {
		    System.setOut(new PrintStream(new OutputStream() {
		                public void write(int b) {
		                    //DO NOTHING
		                }
		            }));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		////////////////////////////////////////////////
		this.font = new UnicodeFont(font);
		ColorEffect colorEffect = new ColorEffect(color);
		this.font.getEffects().add(colorEffect);
		this.font.addNeheGlyphs();
		this.font.loadGlyphs();
		////////////////////////////////////////////////
		
		try {
			System.setOut(original);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void setColor(Color color) throws SlickException {
		PrintStream original = System.out;
		try {
		    System.setOut(new PrintStream(new OutputStream() {
		                public void write(int b) {
		                    //DO NOTHING
		                }
		            }));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		////////////////////////////////////////////////
		font.getEffects().clear();
		font.getEffects().add(new ColorEffect(color));
		font.clearGlyphs();
		font.addNeheGlyphs();
		font.loadGlyphs();
		////////////////////////////////////////////////
		
		try {
			System.setOut(original);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public UnicodeFont get() {
		return font;
	}
}