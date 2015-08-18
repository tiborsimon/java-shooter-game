package tiborsimon.javagame.ui;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Interfészen használható szövegmező.
 * @author Tibor
 *
 */
public class TextField {
	private float x = 0, y = 0;

	private boolean active = false;

	private String text;

	private final static float WIDTH = 398.0f;
	private final static float HEIGHT = 58.0f;

	private final static float TEXT_OFFSET_X = 18;
	private final static float TEXT_OFFSET_Y = 10;

	private final static String BG_URL_BASE = "res\\textfield_bg_base.png";
	private final static String BG_URL_ACTIVE = "res\\textfield_bg_active.png";
	
	private final static int MAX_TEXT_LENGTH = 20;

	public TextField(float x, float y) {
		this.x = x;
		this.y = y;

		this.text = "";
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public String getText() {
		return new String(text);
	}

	public void render(Graphics g) throws SlickException {
		if (active) {
			g.drawImage(new Image(BG_URL_ACTIVE), x, y);
		} else {
			g.drawImage(new Image(BG_URL_BASE), x, y);
		}

		/*org.newdawn.slick.Font tempFont = g.getFont();
		SimpleFont font = new SimpleFont("Bitstream Vera Sans Mono", Font.BOLD, 30);
		g.setFont(font.get());
		Color color = g.getColor();
		g.setColor(Color.black);*/
		g.drawString(text, x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y);
		// g.setColor(color);
		//g.setFont(tempFont);
		

	}

	public void update(Input input, boolean activate, float mouseX, float mouseY) {
		if (activate) {
			mouseY = 600 - mouseY;
			if ((mouseX > x && mouseX < x + WIDTH)
					&& (mouseY > y && mouseY < y + HEIGHT)) {
				input.clearKeyPressedRecord();
				active = true;
			} else {
				active = false;
			}
		}

		if (active) {

			if (input.isKeyPressed(Input.KEY_ENTER)
					|| input.isKeyPressed(Input.KEY_ESCAPE)) {
				active = false;
			}
			if (input.isKeyPressed(Input.KEY_BACK)) {
				if (text.length() > 0) {
					text = text.substring(0, text.length() - 1);
				}
			}

			boolean shift = input.isKeyDown(Input.KEY_LSHIFT)
					|| input.isKeyDown(Input.KEY_RSHIFT);

			if (text.length() >= MAX_TEXT_LENGTH) {
				input.clearKeyPressedRecord();
				return;
			} else if (input.isKeyPressed(Input.KEY_0)) {
				text = text + "0";
			} else if (input.isKeyPressed(Input.KEY_1)) {
				text = text + "1";
			} else if (input.isKeyPressed(Input.KEY_2)) {
				text = text + "2";
			} else if (input.isKeyPressed(Input.KEY_3)) {
				text = text + "3";
			} else if (input.isKeyPressed(Input.KEY_4)) {
				text = text + "4";
			} else if (input.isKeyPressed(Input.KEY_5)) {
				text = text + "5";
			} else if (input.isKeyPressed(Input.KEY_6)) {
				text = text + "6";
			} else if (input.isKeyPressed(Input.KEY_7)) {
				text = text + "7";
			} else if (input.isKeyPressed(Input.KEY_8)) {
				text = text + "8";
			} else if (input.isKeyPressed(Input.KEY_9)) {
				text = text + "9";
			} else

			if (input.isKeyPressed(Input.KEY_A)) {
				text = text + (shift ? "A" : "a");
			} else if (input.isKeyPressed(Input.KEY_B)) {
				text = text + (shift ? "B" : "b");
			} else if (input.isKeyPressed(Input.KEY_C)) {
				text = text + (shift ? "C" : "c");
			} else if (input.isKeyPressed(Input.KEY_D)) {
				text = text + (shift ? "D" : "d");
			} else if (input.isKeyPressed(Input.KEY_E)) {
				text = text + (shift ? "E" : "e");
			} else if (input.isKeyPressed(Input.KEY_F)) {
				text = text + (shift ? "F" : "f");
			} else if (input.isKeyPressed(Input.KEY_G)) {
				text = text + (shift ? "G" : "g");
			} else if (input.isKeyPressed(Input.KEY_H)) {
				text = text + (shift ? "H" : "h");
			} else if (input.isKeyPressed(Input.KEY_I)) {
				text = text + (shift ? "I" : "i");
			} else if (input.isKeyPressed(Input.KEY_J)) {
				text = text + (shift ? "J" : "j");
			} else if (input.isKeyPressed(Input.KEY_K)) {
				text = text + (shift ? "K" : "k");
			} else if (input.isKeyPressed(Input.KEY_L)) {
				text = text + (shift ? "L" : "l");
			} else if (input.isKeyPressed(Input.KEY_M)) {
				text = text + (shift ? "M" : "m");
			} else if (input.isKeyPressed(Input.KEY_N)) {
				text = text + (shift ? "N" : "n");
			} else if (input.isKeyPressed(Input.KEY_O)) {
				text = text + (shift ? "O" : "o");
			} else if (input.isKeyPressed(Input.KEY_P)) {
				text = text + (shift ? "P" : "p");
			} else if (input.isKeyPressed(Input.KEY_Q)) {
				text = text + (shift ? "Q" : "q");
			} else if (input.isKeyPressed(Input.KEY_R)) {
				text = text + (shift ? "R" : "r");
			} else if (input.isKeyPressed(Input.KEY_S)) {
				text = text + (shift ? "S" : "s");
			} else if (input.isKeyPressed(Input.KEY_T)) {
				text = text + (shift ? "T" : "t");
			} else if (input.isKeyPressed(Input.KEY_U)) {
				text = text + (shift ? "U" : "u");
			} else if (input.isKeyPressed(Input.KEY_V)) {
				text = text + (shift ? "V" : "v");
			} else if (input.isKeyPressed(Input.KEY_W)) {
				text = text + (shift ? "W" : "w");
			} else if (input.isKeyPressed(Input.KEY_X)) {
				text = text + (shift ? "X" : "x");
			} else if (input.isKeyPressed(Input.KEY_Y)) {
				text = text + (shift ? "Y" : "y");
			} else if (input.isKeyPressed(Input.KEY_Z)) {
				text = text + (shift ? "Z" : "z");
			} else if (input.isKeyPressed(Input.KEY_PERIOD)) {
				text = text +  ".";
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				text = text + " ";
			}

		}
	}

}
