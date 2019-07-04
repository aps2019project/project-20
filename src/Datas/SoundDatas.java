package Datas;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;

public class SoundDatas {
    public static final String MENU_MUSIC_1 = "sfx/music_mainmenu_lyonar.m4a";
    public static final String MENU_MUSIC_2 = "sfx/music_tutorial.m4a";
    public static final String MENU_MUSIC_3 = "sfx/music_playmode.m4a";
    public static final String MENU_MUSIC_4 = "sfx/music_gauntlet.m4a";
    public static final String ERROR_DIALOG = "sfx/sfx_ui_error.m4a";
    public static final String NORMAL_DIALOG = "sfx/sfx_ui_cardburn.m4a";
    public static final String PAGE_CHANGING = "sfx/sfx_ui_nextpage.m4a";
    public static final String BUTTON_PRESS = "sfx/sfx_ui_select.m4a";
    public static final String BUTTON_MOUSEOVER = "sfx/sfx_ui_in_game_hover.m4a";
    public static final String TAB_PRESS = "sfx/sfx_ui_tab_in.m4a";
    public static final String SELECT_ITEM = "sfx/sfx_ui_modalwindow_swoosh_enter.m4a";
    public static final String DIALOG_YES_BUTTON = "sfx/sfx_ui_tab_out.m4a";
    public static final String DIALOG_NO_BUTTON = "sfx/sfx_unit_onclick.m4a";

    public static MediaPlayer getRandomSound(){
        switch (new Random().nextInt(4)+1){
            case 1:
                return new MediaPlayer(new Media(new File(SoundDatas.MENU_MUSIC_1).toURI().toString()));
            case 2:
                return new MediaPlayer(new Media(new File(SoundDatas.MENU_MUSIC_2).toURI().toString()));
            case 3:
                return new MediaPlayer(new Media(new File(SoundDatas.MENU_MUSIC_3).toURI().toString()));
            default:
                return new MediaPlayer(new Media(new File(SoundDatas.MENU_MUSIC_4).toURI().toString()));
        }
    }

    public static void playSFX(String name){
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(name).toURI().toString()));
        mediaPlayer.setVolume(1);
        mediaPlayer.play();
        mediaPlayer.play();
    }
}
