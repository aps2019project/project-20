package Datas;

import Model.*;

import static Model.AttackType.*;
import static Model.Minion.ActivateTimeOfSpecialPower.*;
import static Model.Spell.TargetType.*;

public class AssetDatas {
    //Spells ID: starts from 4000
    //Heroes ID: starts from 2000
    //Items ID: starts from 1000
    //Minions ID: starts from 3000

    //spells
    private static Spell totalDisarm = new Spell("totalDisarm","Disarming a Card of Enemy forever",1000,4000,0, ENEMY);
    private static Spell areaDispel = new Spell("areaDispel","Destroy Positive Buffs of Enemy Cards And Negative Buffs Of your Cards",1500,4001,2, CELLS,2);
    private static Spell empower = new Spell("empower","Improve َAp Of One Of Your Cards",250,4002,1, PLAYER);
    private static Spell fireball = new Spell("fireball","Do damage to Card Of An Enemy",400,4003,1, ENEMY);
    private static Spell godStrength = new Spell("fireball","Increase AP Of Your Hero",450,4004,2, PLAYER);
    private static Spell hellFire = new Spell("hellFire","For two turns Creates a Fiery House In Targeted Cells",600,4005,3, CELLS, 2);
    private static Spell lightingBolt = new Spell("lightingBolt","Do damage to Hero Of An Enemy",1250,4006,2, ENEMY);
    private static Spell poisonLake = new Spell("poisonLake","For one turn Creates a Poisonous House In Targeted Cells",900,4007,5, CELLS, 3);
    private static Spell madness = new Spell("madness","For three turns Improve Ap Of Your Card But Make It Disarm",650,4008,0, PLAYER);
    private static Spell allDisarm = new Spell("allDisarm","For one turn Disarms the All Cards Of Enemy",2000,4009,9, ENEMY);
    private static Spell allPoison = new Spell("allPoison","For four turns Disarms the All Cards Of Enemy",1500,4010,8, ENEMY);
    private static Spell dispel = new Spell("dispel","Destroy Positive Buffs of Enemy Cards And Negative Buffs Of your Cards",2100,4011,0, WHOLE_OF_GROUND);
    private static Spell healthWithProfit = new Spell("healthWithProfit","For Three Turns Gives Two Holy Buffs But Give Weakness Buff To Your Card",2250,4012,0, PLAYER);
    private static Spell ghazaBokhorJoonBegiri = new Spell("powerUp","Give Power Buff To Your Card Which Equals With It's Current Hp Forever",2500,4013,2, PLAYER);
    private static Spell allPower = new Spell("allPower","Give Power Buff To All Of Your Cards Forever",2000,4014,4, PLAYER);
    private static Spell allAttack = new Spell("allAttack","Do damage To All Of Enemies Cards",1500,4015,4, ENEMY);
    private static Spell weakening = new Spell("weakening","Give Weakness Buff  To A Card Of Enemy",1000,4016,1, ENEMY);
    private static Spell sacrifice = new Spell("sacrifice","Give All Hp Of A Minion To Your Hero",1600,4017,3, PLAYER);
    private static Spell kingsGuard = new Spell("kingsGuard","Kill A Card Of Enemy",1750,4018,3, ENEMY);
    private static Spell shock = new Spell("shock","Stunning Card Of Enemy For Two Turns",1200,4019,1, ENEMY);

    //minions
    private static Minion farsArcher = new Minion("farsArcher","farsArcher",300,3000,7,4,6,2, RANGED);
    private static Minion farsSwordsman = new Minion("farsSwordsman","farsSwordsman",400,3001,0,4,6,2, MELEE, ON_ATTACK);
    private static Minion farsSpear = new Minion("farsSpear","farsSpear",500,3002,3,3,5,1, HYBRID);
    private static Minion farsHorseman = new Minion("farsHorseman","farsSpear",200,3003,0,6,10,4, MELEE);
    private static Minion farsChampion = new Minion("farsChampion","farsChampion",600,3004,0,6,24,9, MELEE, ON_ATTACK);
    private static Minion farsChief = new Minion("farsChief","farsChief",800,3005,0,4,12,7, MELEE, COMBO);
    private static Minion TooraniArcher = new Minion("TooraniArcher","TooraniArcher",500,3006,5,4,3,1, RANGED);
    private static Minion TooraniCatapult = new Minion("TooraniCatapult","TooraniCatapult",500,3007,7,2,4,1, RANGED);
    private static Minion TooraniSpear = new Minion("TooraniSpear","TooraniSpear",600,3008,3,4,4,1, HYBRID);
    private static Minion TooraniSpy = new Minion("TooraniSpy","TooraniSpy",700,3009,0,6,6,4, MELEE, ON_ATTACK);
    private static Minion TooraniSwampy = new Minion("tooraniSwampy", "tooraniSwampy", 450, 3010, 0, 10, 3, 2, MELEE);
    private static Minion TooraniPrince = new Minion("tooraniPrince", "sor any not torani plus 4 attackpower", 800, 3011, 0, 10, 6, 6, MELEE,COMBO);
    private static Minion blackGoblin = new Minion("blackGoblin", "", 300, 3012, 7, 10, 14, 9, HYBRID);
    private static Minion throwStoneGiant = new Minion("throwStoneGiant", "", 300, 3013, 7, 12, 12, 9, RANGED);
    private static Minion eagle = new Minion("eagle", "plus 10 power buff with increase HP", 300, 3014, 3, 2, 0, 2, RANGED, PASSIVE);
    private static Minion hogRiderGoblin = new Minion("hogRiderGoblin", "", 300, 3015, 0, 8, 16, 6, MELEE);
    private static Minion oneEyeGiant = new Minion("oneEyeGiant", "on death time attack to all minion around 8 cells with 2 AP", 500, 3016, 3, 11, 12, 7, HYBRID,ON_DEATH);
    private static Minion poisonSnake = new Minion("poisonSnake", "for 3 turn poisoned enemy", 300, 3017, 4, 6, 5, 4, RANGED, ON_ATTACK);
    private static Minion throwFireDragon = new Minion("throwFireDragon", "", 250, 3018, 4, 5, 9, 5, RANGED);
    private static Minion drainLion = new Minion("drainLion", "no effect from holly buff", 600, 3019, 0, 8, 1, 2, MELEE, ON_ATTACK);
    private static Minion giantSnake = new Minion("giantSnake", "not holly buff", 500, 3020, 5, 7, 14, 8, RANGED, ON_SPAWN);
    private static Minion whiteWolf = new Minion("whiteWolf", "4 and 6 decrease minion HP for two after turn", 400, 3021, 0, 2, 8, 5, MELEE, ON_ATTACK);
    private static Minion tiger = new Minion("tiger", "decrease 8 HP in after turn from minion that attacked", 400, 3022, 0, 2, 6, 4, MELEE, ON_ATTACK);
    private static Minion wolf = new Minion("wolf", "decrease 6 HP in after turn from minion that attacked", 400, 3023, 0, 1, 6, 3, MELEE, ON_ATTACK);
    private static Minion wizard = new Minion("wizard", "plus 2 AP and one weakness (decrease 1 HP)  for around minion", 550, 3024, 3, 4, 5, 4, RANGED, PASSIVE);
    private static Minion greatWizard = new Minion("greatWizard", "plus 2 AP and holly buff  for around minion", 550, 3025, 5, 6, 6, 6, RANGED, PASSIVE);
    private static Minion elf = new Minion("elf", "for all own minions passive 1 AP power buff", 500, 3026, 4, 4, 10, 5, RANGED, PASSIVE);
    private static Minion wildBoar = new Minion("wildBoar", "no effect by disarm", 500, 3027, 0, 14, 10, 6, MELEE, ON_DEFEND);
    private static Minion piran = new Minion("piran", "no effect by poison", 400, 3028, 0, 12, 20, 8, MELEE, ON_DEFEND);
    private static Minion giv = new Minion("giv", "no effect by negative buff", 450, 3029, 5, 7, 5, 4, RANGED,ON_DEFEND);
    private static Minion bahman = new Minion("bahman", "random decrease enemy minion decrease 16 HP", 450, 3030, 0, 9, 16, 8, MELEE, ON_SPAWN);
    private static Minion ashkbos = new Minion("ashkbos", "no effect by enemy that attack power lower than", 400, 3031, 0, 8, 14, 7, MELEE,ON_DEFEND);
    private static Minion iraj = new Minion("iraj", "", 500, 3032, 3, 20, 6, 4, RANGED);
    private static Minion bigGiant = new Minion("bigGiant", "", 600, 3033, 2, 8, 30, 9, HYBRID);
    private static Minion twoHeadGiant = new Minion("twoHeadGiant", "destroy all holly buff minion that attacked", 550, 3034, 0, 4, 10, 4, MELEE, ON_ATTACK);
    private static Minion coldMother = new Minion("coldMother", "stun around minion ", 500, 3035, 5, 4, 3, 3, RANGED, ON_SPAWN);
    private static Minion steelArmor  = new Minion("steelArmor", "convert randomly to enemy minions ", 650, 3036, 0, 1, 1, 3, MELEE, PASSIVE);
    private static Minion siavash  = new Minion("siavash", "on death attack 6 AP to enemy hero", 350, 3037, 0, 5, 8, 4, MELEE,ON_DEATH);
    private static Minion kingGiant  = new Minion("kingGiant", "for any giant take part in combo enemy 1 turn disarm", 600, 3038, 0, 4, 10, 5, MELEE, COMBO);
    private static Minion arjangGoblin  = new Minion("arjangGoblin", "fo any goblin take part in attack add 3 to weakness", 600, 3039, 0, 6, 6, 3, MELEE, COMBO);

    //Heroes
    private static Hero whiteDamn = new Hero("whiteGoblin", 8000, 2000, 0, 4, 50, 1, 2, MELEE);
    private static Hero simorgh = new Hero("simorgh", 9000, 2001, 0, 4, 50, 3, 8, MELEE);
    private static Hero sevenHeadDragon = new Hero("sevenHeadDragon", 8000, 2002, 0, 4, 50, 0, 1, MELEE);
    private static Hero rakhsh = new Hero("rakhsh", 8000, 2003, 0, 4, 50, 1, 2, MELEE);
    private static Hero zahhak = new Hero("zahhak", 10000, 2004, 0, 2, 50, 1, 3, MELEE);
    private static Hero kaveh = new Hero("kaveh", 8000, 2005, 0, 4, 50, 1, 3, MELEE);
    private static Hero arash = new Hero("arash", 10000, 2006, 6, 2, 30, 2, 2, RANGED);
    private static Hero legend = new Hero("legend", 11000, 2007, 3, 3, 40, 1, 2, RANGED);
    private static Hero esfandiar = new Hero("esfandiar", 12000, 2008, 3, 3, 35, 0, 1, HYBRID);
    private static Hero rostam = new Hero("rostam", 8000, 2009, 4, 7, 55, HYBRID);

    //Items
    private static Item knowledgeCrown = new Item("KnowledgeCrown", "Increases Mana by 1 unit in the first three turns.", 300, 1000);
    private static Item namoos_e_separ = new Item("namoos_e_separ", "Activates 12 holy buffs to the own hero.", 4000, 1001);
    private static Item damoolArch = new Item("damoolArch", "While striking, the own hero disarms enemy warrior for 1 turn(Only for ranged and hybrid).", 30000, 1002);
    private static Item nooshdaroo = new Item("nooshdaroo", "Increases a random warrior's HP by 6 units.", 1003);
    private static Item twoHornArrow = new Item("twoHornArrow", "Increases random ranged or hybrid warrior's AP by 2 units.", 1004);
    private static Item simorghWing = new Item("simorghWing", "Decreases enemy hero's AP by 2 units(Only for ranged and hybrid enemy hero).", 3500, 1005);
    private static Item elixir = new Item("elixir", "Increases HP by 3 units and activates a power buff increasing a random minion's AP by 3 units.", 1006);
    private static Item manaMixture = new Item("manaMixture", "Increases Mana by 3 units.", 1007);
    private static Item invulnerableMixture = new Item("invulnerableMixture", "Applies 10 holy buffs to own random warrior.", 1008);
    private static Item deathCurse = new Item("deathCurse", "Enables a minion to strike 8 AP to one of its nearest random warriors.", 1009);
    private static Item randomDamage = new Item("randomDamage", "Increases AP of a random warrior in the battleground by 2 units.", 1010);
    private static Item terrorHood = new Item("terrorHood", "Activates a weakness buff decreasing a random enemy warrior's AP by 2 units.", 5000, 1011);
    private static Item bladesOfAgility = new Item("bladesOfAgility", "Increases a random warrior's AP by 6 units.", 1012);
    private static Item kingWisdom = new Item("kingWisdom", "Increases mana per turn by 1 unit.", 9000, 1013);
    private static Item assassinationDagger  = new Item("assassinationDagger", "When spawning any own warrior, strikes enemy's hero by 1 unit.", 15000, 1014);
    private static Item poisonousDagger  = new Item("poisonousDagger", "While striking, own warrior poisons enemy warrior for one turn.", 7000, 1015);
    private static Item shockHammer  = new Item("shockHammer", "While striking, the own hero disarms enemy warrior for one turn.", 15000, 1016);
    private static Item soulEater = new Item("soulEater", "When each own warrior dies, a power buff increasing one own warrior's AP by 1 unit is activated.", 25000, 1017);
    private static Item baptism = new Item("baptism", "Each minion has holy buff for 2 turns after being spawned.", 20000, 1018);
    private static Item chineseSword = new Item("chineseSword", "Increases warrior's AP by 5 units(Only for melee).", 1019);

    public static Spell getTotalDisarm() {
        return totalDisarm;
    }

    public static Spell getAreaDispel() {
        return areaDispel;
    }

    public static Spell getEmpower() {
        return empower;
    }

    public static Spell getFireball() {
        return fireball;
    }

    public static Spell getGodStrength() {
        return godStrength;
    }

    public static Spell getHellFire() {
        return hellFire;
    }

    public static Spell getLightingBolt() {
        return lightingBolt;
    }

    public static Spell getPoisonLake() {
        return poisonLake;
    }

    public static Spell getMadness() {
        return madness;
    }

    public static Spell getAllDisarm() {
        return allDisarm;
    }

    public static Spell getAllPoison() {
        return allPoison;
    }

    public static Spell getDispel() {
        return dispel;
    }

    public static Spell getHealthWithProfit() {
        return healthWithProfit;
    }

    public static Spell getGhazaBokhorJoonBegiri() {
        return ghazaBokhorJoonBegiri;
    }

    public static Spell getAllPower() {
        return allPower;
    }

    public static Spell getAllAttack() {
        return allAttack;
    }

    public static Spell getWeakening() {
        return weakening;
    }

    public static Spell getSacrifice() {
        return sacrifice;
    }

    public static Spell getKingsGuard() {
        return kingsGuard;
    }

    public static Spell getShock() {
        return shock;
    }

    public static Minion getFarsArcher() {
        return farsArcher;
    }

    public static Minion getFarsSwordsman() {
        return farsSwordsman;
    }

    public static Minion getFarsSpear() {
        return farsSpear;
    }

    public static Minion getFarsHorseman() {
        return farsHorseman;
    }

    public static Minion getFarsChampion() {
        return farsChampion;
    }

    public static Minion getFarsChief() {
        return farsChief;
    }

    public static Minion getTooraniArcher() {
        return TooraniArcher;
    }

    public static Minion getTooraniCatapult() {
        return TooraniCatapult;
    }

    public static Minion getTooraniSpear() {
        return TooraniSpear;
    }

    public static Minion getTooraniSpy() {
        return TooraniSpy;
    }

    public static Minion getTooraniSwampy() {
        return TooraniSwampy;
    }

    public static Minion getTooraniPrince() {
        return TooraniPrince;
    }

    public static Minion getBlackGoblin() {
        return blackGoblin;
    }

    public static Minion getThrowStoneGiant() {
        return throwStoneGiant;
    }

    public static Minion getEagle() {
        return eagle;
    }

    public static Minion getHogRiderGoblin() {
        return hogRiderGoblin;
    }

    public static Minion getOneEyeGiant() {
        return oneEyeGiant;
    }

    public static Minion getPoisonSnake() {
        return poisonSnake;
    }

    public static Minion getThrowFireDragon() {
        return throwFireDragon;
    }

    public static Minion getDrainLion() {
        return drainLion;
    }

    public static Minion getGiantSnake() {
        return giantSnake;
    }

    public static Minion getWhiteWolf() {
        return whiteWolf;
    }

    public static Minion getTiger() {
        return tiger;
    }

    public static Minion getWolf() {
        return wolf;
    }

    public static Minion getWizard() {
        return wizard;
    }

    public static Minion getGreatWizard() {
        return greatWizard;
    }

    public static Minion getElf() {
        return elf;
    }

    public static Minion getWildBoar() {
        return wildBoar;
    }

    public static Minion getPiran() {
        return piran;
    }

    public static Minion getGiv() {
        return giv;
    }

    public static Minion getBahman() {
        return bahman;
    }

    public static Minion getAshkbos() {
        return ashkbos;
    }

    public static Minion getIraj() {
        return iraj;
    }

    public static Minion getBigGiant() {
        return bigGiant;
    }

    public static Minion getTwoHeadGiant() {
        return twoHeadGiant;
    }

    public static Minion getColdMother() {
        return coldMother;
    }

    public static Minion getSteelArmor() {
        return steelArmor;
    }

    public static Minion getSiavash() {
        return siavash;
    }

    public static Minion getKingGiant() {
        return kingGiant;
    }

    public static Minion getArjangGoblin() {
        return arjangGoblin;
    }

    public static Hero getWhiteDamn() {
        return whiteDamn;
    }

    public static Hero getSimorgh() {
        return simorgh;
    }

    public static Hero getSevenHeadDragon() {
        return sevenHeadDragon;
    }

    public static Hero getRakhsh() {
        return rakhsh;
    }

    public static Hero getZahhak() {
        return zahhak;
    }

    public static Hero getKaveh() {
        return kaveh;
    }

    public static Hero getArash() {
        return arash;
    }

    public static Hero getLegend() {
        return legend;
    }

    public static Hero getEsfandiar() {
        return esfandiar;
    }

    public static Hero getRostam() {
        return rostam;
    }

    public static Item getKnowledgeCrown() {
        return knowledgeCrown;
    }

    public static Item getNamoos_e_separ() {
        return namoos_e_separ;
    }

    public static Item getDamoolArch() {
        return damoolArch;
    }

    public static Item getNooshdaroo() {
        return nooshdaroo;
    }

    public static Item getTwoHornArrow() {
        return twoHornArrow;
    }

    public static Item getSimorghWing() {
        return simorghWing;
    }

    public static Item getElixir() {
        return elixir;
    }

    public static Item getManaMixture() {
        return manaMixture;
    }

    public static Item getInvulnerableMixture() {
        return invulnerableMixture;
    }

    public static Item getDeathCurse() {
        return deathCurse;
    }

    public static Item getRandomDamage() {
        return randomDamage;
    }

    public static Item getTerrorHood() {
        return terrorHood;
    }

    public static Item getBladesOfAgility() {
        return bladesOfAgility;
    }

    public static Item getKingWisdom() {
        return kingWisdom;
    }

    public static Item getAssassinationDagger() {
        return assassinationDagger;
    }

    public static Item getPoisonousDagger() {
        return poisonousDagger;
    }

    public static Item getShockHammer() {
        return shockHammer;
    }

    public static Item getSoulEater() {
        return soulEater;
    }

    public static Item getBaptism() {
        return baptism;
    }

    public static Item getChineseSword() {
        return chineseSword;
    }
}
