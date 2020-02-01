package fgj20.game.repair;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Repair implements ApplicationListener {
	private SpriteBatch batch;
	private Texture playerTexture;
	private Rectangle playerRect;
	private float playerSpeed;

	private Music backGroundMusic;
	private Sound ballDrop;

	private Texture start;
	private boolean startVisible;

	private BitmapFont font;

	private Animation<TextureRegion> walkAnimation;
	private Texture walkSheet;
	TextureRegion currentFrame;

	private Animation<TextureRegion> shimmer;
	private Texture shimmer1;
	private Texture shimmer2;
	TextureRegion shimmerFrame;

	private Animation<TextureRegion> smokeAnimation;
	private Texture smoke1Texture;
	private Texture smoke2Texture;
	private Texture smoke3Texture;
	TextureRegion smokeFrame;

	private float stateTime = 1.0f;

	private int shroomsFound;
	private int shroomsReturned;

	private Texture sky;
	private Texture ground;

	private Texture treeTexture;
	private Rectangle treeRect;
	private Texture waterMonster;
	private Rectangle monsterRect;

	private Texture altarTexture;
	private Rectangle altarRect;

	private boolean findShrooms;
	private Texture item1Texture;
	private Texture item2Texture;
	private Texture item3Texture;
	private Rectangle itemRect;

	private Texture house1Texture;
	private Texture house2Texture;
	private Texture house3Texture;
	private Rectangle houseRect;

	private Texture ball1Texture;
	private Rectangle ball1Rect;
	private Texture ball2Texture;
	private Rectangle ball2Rect;
	private Texture ball3Texture;
	private Rectangle ball3Rect;

	private OrthographicCamera camera;
	private float cameraX;
	private float cameraY;
	
	@Override
	public void create () {
		findShrooms = false;
		startVisible = true;
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		batch = new SpriteBatch();

		start = new Texture(Gdx.files.internal("startpicture.png"));

		backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("adventure.mp3"));
		backGroundMusic.setLooping(true);
		backGroundMusic.play();

		ballDrop = Gdx.audio.newSound(Gdx.files.internal("kilings.mp3"));

		playerTexture = new Texture(Gdx.files.internal("Shroom.png"));
		playerRect = new Rectangle(0, 0,
				playerTexture.getWidth() / 5f,
				playerTexture.getHeight() / 3.846f);
		playerSpeed = 450.0f;

		house1Texture = new Texture(Gdx.files.internal("house1.png"));
		house2Texture = new Texture(Gdx.files.internal("house2.png"));
		house3Texture = new Texture(Gdx.files.internal("house3.png"));
		houseRect = new Rectangle(700, -175,
				house3Texture.getWidth() / 3.0f,
				house3Texture.getHeight()/ 3.0f);

		smoke1Texture = new Texture(Gdx.files.internal("smoke1.png"));
		smoke2Texture = new Texture(Gdx.files.internal("smoke2.png"));
		smoke3Texture = new Texture(Gdx.files.internal("smoke3.png"));

		TextureRegion[] smokeFrames = new TextureRegion[3];
		smokeFrames[0] = new TextureRegion(smoke1Texture);
		smokeFrames[1] = new TextureRegion(smoke2Texture);
		smokeFrames[2] = new TextureRegion(smoke3Texture);

		ball1Texture = new Texture(Gdx.files.internal("ball1.png"));
		ball1Rect = new Rectangle(1492.0f, -78.0f,
				ball1Texture.getWidth() / 3.0f,
				ball1Texture.getHeight() / 3.0f);
		ball2Texture = new Texture(Gdx.files.internal("ball2.png"));
		ball2Rect = new Rectangle(1572.0f, -78.0f,
				ball2Texture.getWidth() / 3.0f,
				ball2Texture.getHeight() / 3.0f);
		ball3Texture = new Texture(Gdx.files.internal("ball3.png"));
		ball3Rect = new Rectangle(1652.0f, -78.0f,
				ball3Texture.getWidth() / 3.0f,
				ball3Texture.getHeight() / 3.0f);

		altarTexture = new Texture(Gdx.files.internal("altar.png"));
		altarRect = new Rectangle(1500, -175,
				altarTexture.getWidth() / 5f,
				altarTexture.getHeight() / 3.846f);

		item1Texture = new Texture(Gdx.files.internal("shroom1.png"));
		item2Texture = new Texture(Gdx.files.internal("shroom2.png"));
		item3Texture = new Texture(Gdx.files.internal("shroom3.png"));
		itemRect = new Rectangle(0, 0, 0, 0);

		sky = new Texture(Gdx.files.internal("sky2.png"));
		ground = new Texture(Gdx.files.internal("ground2.png"));
		treeTexture = new Texture(Gdx.files.internal("tree.png"));
		treeRect = new Rectangle(2500, -320,
				treeTexture.getWidth() * 1.7f,
				treeTexture.getHeight() * 1.7f);
		waterMonster = new Texture(Gdx.files.internal("watermonster.png"));
		monsterRect = new Rectangle(-970, -180,
				waterMonster.getWidth(),
				waterMonster.getHeight());

		walkSheet = new Texture(Gdx.files.internal("shroomWalk.png"));
		shimmer1 = new Texture(Gdx.files.internal("shimmer1.png"));
		shimmer2 = new Texture(Gdx.files.internal("shimmer2.png"));

		TextureRegion[][] tmp = TextureRegion.split(
				walkSheet,
				walkSheet.getWidth() / 5,
				walkSheet.getHeight() / 2);

		TextureRegion[] walkFrames = transformTo1D(tmp, 2, 5);

		TextureRegion[] shimmerFrames = new TextureRegion[2];
		shimmerFrames[0] = new TextureRegion(shimmer1);
		shimmerFrames[1] = new TextureRegion(shimmer2);


		smokeAnimation = new Animation(0.15f, (Object[])smokeFrames);
		walkAnimation = new Animation(1/10f, (Object[])walkFrames);
		shimmer = new Animation(1/10f, (Object[])shimmerFrames);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2000, 1400);
	}

	private TextureRegion[] transformTo1D(TextureRegion[][] tmp, int rows, int cols) {
		TextureRegion[] frames
				= new TextureRegion[cols*rows];

		int index = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		return frames;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		shimmerFrame = shimmer.getKeyFrame(stateTime, true);
		smokeFrame = smokeAnimation.getKeyFrame(stateTime, false);

		if(!startVisible) {
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && playerRect.x < 3830.29f) {
				playerRect.x += Gdx.graphics.getDeltaTime() * playerSpeed;

			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && playerRect.x > -898.51904f) {
				playerRect.x -= Gdx.graphics.getDeltaTime() * playerSpeed;

			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				playerRect.y = 150;
				while(playerRect.y > 0) {
					playerRect.y -= Gdx.graphics.getDeltaTime() * 5;
				}
			}
		}

		if(playerRect.getX() < -103f || playerRect.getX() > 2900f) {
			if(playerRect.getY() < 153.43001f) {
				cameraY = playerRect.getY();
			}
		} else {
			cameraX = playerRect.getX();
			cameraY = playerRect.getY();
		}
		camera.position.set(cameraX + 100, cameraY + 400, 0);
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		if(startVisible) {
			batch.draw(start, -670, -300, start.getWidth() * 1.5f, start.getHeight() * 1.5f);
			if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				startVisible = false;
				start.dispose();
			}
		} else {
			batch.draw(sky, -1000, -150, sky.getWidth(), sky.getHeight());
			batch.draw(ground, -1000, -300, ground.getWidth(), ground.getHeight());
			batch.draw(waterMonster,
					monsterRect.x,
					monsterRect.y,
					monsterRect.width,
					monsterRect.getHeight());
			batch.draw(treeTexture,
					treeRect.x,
					treeRect.y,
					treeRect.width,
					treeRect.height);

			if(shroomsReturned == 1) {
				batch.draw(house1Texture,
						houseRect.x,
						houseRect.y - 100,
						houseRect.width,
						houseRect.height);
			} else if (shroomsReturned == 2) {
				batch.draw(house2Texture,
						houseRect.x - 100,
						houseRect.y - 100,
						houseRect.width,
						houseRect.height);
			} else if (shroomsReturned > 2) {
				batch.draw(house3Texture,
						houseRect.x,
						houseRect.y,
						houseRect.width,
						houseRect.height);
			}

			font.getData().setScale(1.8f);
			font.setColor(Color.WHITE);

			batch.draw(altarTexture,
					altarRect.x,
					altarRect.y,
					altarRect.width,
					altarRect.height);

			if(shroomsReturned > 0) {
				batch.draw(ball1Texture,
						ball1Rect.x,
						ball1Rect.y,
						ball1Rect.width,
						ball1Rect.height);
				if(shroomsReturned > 1) {
					batch.draw(ball2Texture,
							ball2Rect.x,
							ball2Rect.y,
							ball2Rect.width,
							ball2Rect.height);
					if (shroomsReturned > 2) {
						batch.draw(ball3Texture,
								ball3Rect.x,
								ball3Rect.y,
								ball3Rect.width,
								ball3Rect.height);
					}
				}
			}

			if(findShrooms) {
				if(shroomsFound == 0) {
					itemRect = new Rectangle(9, -150,
							item1Texture.getWidth() / 2f,
							item1Texture.getHeight() / 1.53f);
					batch.draw(item1Texture,
							itemRect.x,
							itemRect.y,
							itemRect.width,
							itemRect.height);
				} else if(shroomsFound == 1 && shroomsReturned == 1) {
					itemRect = new Rectangle(3500, -150,
							item1Texture.getWidth() / 2f,
							item1Texture.getHeight() / 1.53f);
					batch.draw(item2Texture,
							itemRect.x,
							itemRect.y,
							itemRect.width,
							itemRect.height);
					item1Texture.dispose();
				} else if(shroomsFound == 2 && shroomsReturned == 2) {
					itemRect = new Rectangle(-745, -150,
							item3Texture.getWidth() / 2f,
							item3Texture.getHeight() / 1.53f);
					batch.draw(item3Texture,
							itemRect.x,
							itemRect.y,
							itemRect.width,
							itemRect.height);
					item2Texture.dispose();
				}
			}

			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				batch.draw(currentFrame,
						playerRect.x,
						-150,
						playerRect.width,
						playerRect.height);
			} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				batch.draw(currentFrame,
						playerRect.x,
						-150,
						playerRect.width,
						playerRect.height);
			} else {
				batch.draw(playerTexture,
						playerRect.x,
						-150,
						playerRect.width,
						playerRect.height);
			}

			if(playerRect.overlaps(altarRect)) {
				if(shroomsFound == 0 && !findShrooms) {
					font.draw(batch, "Press DOWN to interract with things", altarRect.x - 400, altarRect.y + 500);

					if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
						findShrooms = true;
					}
				}else if(findShrooms) {
					if(shroomsFound == 0) {
						font.draw(batch, "Get 3 shrooms to fix your house!", altarRect.x - 400, altarRect.y + 500);
					} else if(shroomsFound == 1 && shroomsReturned == 0) {
						font.draw(batch, "Return the shrooms to the altar when found!", altarRect.x - 400, altarRect.y + 500);
						if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
							batch.draw(smokeFrame,
									houseRect.x,
									-150,
									houseRect.width,
									houseRect.height);

							ballDrop.play();
							shroomsReturned++;
						}
					} else if(shroomsReturned == 1) {
						font.draw(batch, "Get 2 more shrooms to fix your house!", altarRect.x - 400, altarRect.y + 500);
						if(shroomsFound == 2) {
							if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
								batch.draw(smokeFrame,
										houseRect.x,
										-150,
										houseRect.width,
										houseRect.height);

								ballDrop.play();
								shroomsReturned++;
							}
						}
					} else {
						font.draw(batch, "Get 1 last shroom to fix your house!", altarRect.x - 400, altarRect.y + 500);
						if(shroomsFound == 3) {
							if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
								batch.draw(smokeFrame,
										houseRect.x,
										-150,
										houseRect.width,
										houseRect.height);

								ballDrop.play();
								shroomsReturned++;
								findShrooms = false;
							}
						}
					}
				} else if(shroomsReturned < 4){
					font.draw(batch, "Yay! You fixed your house!", altarRect.x - 300, altarRect.y + 500);
					if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
						shroomsReturned++;
					}

					item3Texture.dispose();
				}

				batch.draw(shimmerFrame,
						playerRect.x,
						-150,
						playerRect.width,
						playerRect.height);
			}

			if(findShrooms) {
				if(playerRect.overlaps(itemRect)) {
					if(shroomsFound == 0) {
						font.draw(batch, "Pick up", itemRect.x - 75, itemRect.y + 500);
					}
					batch.draw(shimmerFrame,
							playerRect.x,
							-150,
							playerRect.width,
							playerRect.height);
					if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
						shroomsFound++;
					}
				}
			}

			if(shroomsReturned == 4) {
				if(playerRect.overlaps(houseRect)) {
					font.draw(batch, "End game?", houseRect.x + 350, houseRect.y + 500);

					batch.draw(shimmerFrame,
							playerRect.x,
							-150,
							playerRect.width,
							playerRect.height);
					if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
						playerRect = new Rectangle(0, 0, 0, 0);
						shroomsReturned++;
					}
				}
			}
		}
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		walkSheet.dispose();
		playerTexture.dispose();
		ground.dispose();
		altarTexture.dispose();
		sky.dispose();
		shimmer2.dispose();
		shimmer1.dispose();
		smoke1Texture.dispose();
		smoke2Texture.dispose();
		smoke3Texture.dispose();
		ball1Texture.dispose();
		ball2Texture.dispose();
		ball3Texture.dispose();
	}
}
