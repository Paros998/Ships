package com.ourshipsgame.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.game.GameScreen;
import com.ourshipsgame.game.GameSettings;
import com.ourshipsgame.handlers.Constant;

public class Hud implements Constant {

    // Fields
    private GameImageButton gameMenuButton, repeatButton, playButton;
    private Sprite playButtonGreenStyle;
    private TextField playerNameTextField;
    private Dialog playersSetNameDialog;
    private String playersName;
    public Table layoutTable;
    private Stage stage;
    private Skin skin;
    public GameSettings gameSettings;
    public GameScreen gameScreen;
    public Main game;

    // Constructor
    public Hud(AssetManager manager, Main game, GameScreen gameScreen) {
        skin = new Skin();
        skin = manager.get("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json", Skin.class);

        stage = new Stage(new ScreenViewport());

        this.game = game;
        this.gameScreen = gameScreen;

        // Close button
        Texture[] buttonStyles = new Texture[2];
        buttonStyles[0] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear.png", Texture.class);
        buttonStyles[1] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear-press.png", Texture.class);

        Sprite[] buttonStylesSprites = new Sprite[2];

        setButtonsSprites(buttonStyles, buttonStylesSprites, 1.55f);

        gameMenuButton = new GameImageButton(GAME_WIDTH - 10, GAME_HEIGHT - 3, this, buttonStylesSprites);
        gameMenuButton.setOptionsListener();

        // Repeat button
        buttonStyles[0] = manager.get("core/assets/ui/reverse-button.png", Texture.class);
        buttonStyles[1] = manager.get("core/assets/ui/reverse-button-pressed.png", Texture.class);

        setButtonsSprites(buttonStyles, buttonStylesSprites, 6.5f);

        repeatButton = new GameImageButton(buttonStylesSprites);

        // Play button
        buttonStyles[0] = manager.get("core/assets/ui/ready-button.png", Texture.class);
        buttonStyles[1] = manager.get("core/assets/ui/ready-button-pressed.png", Texture.class);
        playButtonGreenStyle = new Sprite(manager.get("core/assets/ui/ready-button-go.png", Texture.class));

        setButtonsSprites(buttonStyles, buttonStylesSprites, 6.5f);

        playButton = new GameImageButton(buttonStylesSprites);

        // Player Name textfield
        playersName = "Player";
        playersSetNameDialog = new Dialog("Enter Your Name", skin) {

            {
                playerNameTextField = new TextField(playersName, skin);
                this.text("");
                this.row();
                this.add(playerNameTextField);
                this.row();
                this.getCells().removeIndex(1);
                this.add(this.getButtonTable());
                this.button("Accept");
            }

            @Override
            protected void result(final Object act) {
                playersName = playerNameTextField.getText();
                playerNameTextField = null;
            }
        };

        playersSetNameDialog.show(stage).setPosition(GAME_WIDTH / 2 + 150, 150);

        // Table for play, repeat buttons
        layoutTable = new Table();
        layoutTable.bottom();
        layoutTable.setFillParent(true);
        layoutTable.add(playButton).expandX().padLeft(830f).padBottom(8);
        layoutTable.add(repeatButton).expandX().padRight(830f).padBottom(8);

        stage.addActor(gameMenuButton);
        stage.addActor(layoutTable);
    }

    
    /** 
     * @param textures
     * @param sprites
     * @param factor
     */
    // Methods
    private void setButtonsSprites(Texture[] textures, Sprite[] sprites, float factor) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(textures[i]);
            sprites[i].setSize(sprites[i].getWidth() / factor, sprites[i].getHeight() / factor);
        }
    }

    public void update() {
        stage.act();
        stage.draw();
    }

    
    /** 
     * @param batch
     */
    public void render(SpriteBatch batch) {
        // uiBar.getSprite().draw(batch);
    }

    
    /** 
     * @return GameImageButton
     */
    // Getters
    public GameImageButton getRepeatButton() {
        return repeatButton;
    }

    
    /** 
     * @return GameImageButton
     */
    public GameImageButton getPlayButton() {
        return playButton;
    }

    
    /** 
     * @return String
     */
    public String getPlayersName() {
        return playersName;
    }

    
    /** 
     * @return Sprite
     */
    public Sprite getPlayButtonGreenStyle() {
        return playButtonGreenStyle;
    }

    
    /** 
     * @return Dialog
     */
    public Dialog getPlayersSetNameDialog() {
        return playersSetNameDialog;
    }

    
    /** 
     * @return Stage
     */
    public Stage getStage() {
        return stage;
    }

    
    /** 
     * @return Skin
     */
    public Skin getSkin() {
        return skin;
    }

    
    /** 
     * @return boolean
     */
    public boolean isPasued() {
        return gameMenuButton.getGameMenuState();
    }
}
