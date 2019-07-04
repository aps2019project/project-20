package Model;

import Presenter.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SavedBattle {

    private String date;
    private String AIHeroName;
    private Integer number;
    private Battle battle;

    public SavedBattle(Battle battle) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        this.date = formattedDate;
        this.battle = battle;
        this.number=0;
        this.AIHeroName = battle.getPlayersDeck()[1].getHero().getName();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public String getAIHeroName() {
        return AIHeroName;
    }

    public void setAIHeroName(String AIHeroName) {
        this.AIHeroName = AIHeroName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void saveBattleInToFile(String accountName, String path){
        try {
        Account account = Account.searchAccountInFile(accountName,path);
        account.getSavedBattles().add(0,this);
        account.saveInToFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeBattleFromFile(String accountName, String path){
        try {
            Account account = Account.searchAccountInFile(accountName,path);
            for (int i = 0; i < account.getSavedBattles().size(); i++) {
                if(account.getSavedBattles().get(i).getDate().equals(this.getDate())){
                    account.getSavedBattles().remove(i);
                    break;
                }
            }
            account.saveInToFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
