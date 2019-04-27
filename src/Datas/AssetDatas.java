package Datas;

import Model.*;

public class AssetDatas {
    //spells ID:starts from 4000
    //Heroes ID:starts from 2000
    //Items ID:starts from 1000
    //Minions ID:starts from 3000

    //spells
    private static Spell totalDisarm = new Spell("TotalDisarm","Disarming a Card of Enemy forever",1000,4000,0, Spell.TargetType.ENEMY,"totalDisarmAction");
    private static Spell areaDispel = new Spell("AreaDispel","Destroy Positive Buffs of Enemy Cards And Negative Buffs Of your Cards",1500,4001,2, Spell.TargetType.CELLS,2,"areaDispelAction");
    private static Spell empower = new Spell("empower","Improve َAp Of One Of Your Cards",250,4002,1, Spell.TargetType.PLAYER,"empowerAction");
    private static Spell fireball = new Spell("fireball","Do damage to Card Of An Enemy",400,4003,1, Spell.TargetType.ENEMY,"fireballAction");
    private static Spell godStrength = new Spell("fireball","Increase AP Of Your Hero",450,4004,2, Spell.TargetType.PLAYER,"godStrengthAction");
    private static Spell hellFire = new Spell("hellFire","For two turns Creates a Fiery House In Targeted Cells",600,4005,3, Spell.TargetType.CELLS,2,"hellFireAction");
    private static Spell lightingBolt = new Spell("lightingBolt","Do damage to Hero Of An Enemy",1250,4006,2, Spell.TargetType.ENEMY,"lightingBoltAction");
    private static Spell poisonLake = new Spell("poisonLake","For one turn Creates a Poisonous House In Targeted Cells",900,4007,5, Spell.TargetType.CELLS,3,"poisonLakeAction");
    private static Spell madness = new Spell("madness","For three turns Improve Ap Of Your Card But Make It Disarm",650,4008,0, Spell.TargetType.PLAYER,"madnessAction");
    private static Spell allDisarm = new Spell("allDisarm","For one turn Disarms the All Cards Of Enemy",2000,4009,9, Spell.TargetType.ENEMY,"allDisarmAction");
    private static Spell allPoison = new Spell("allPoison","For four turns Disarms the All Cards Of Enemy",1500,4010,8, Spell.TargetType.ENEMY,"allPoisonAction");
    private static Spell dispel = new Spell("dispel","Destroy Positive Buffs of Enemy Cards And Negative Buffs Of your Cards",2100,4011,0, Spell.TargetType.WHOLE_OF_GROUND,"dispelAction");
    private static Spell healthWithProfit = new Spell("healthWithProfit","For Three Turns Gives Two Holy Buffs But Give Weakness Buff To Your Card",2250,4012,0, Spell.TargetType.PLAYER,"healthWithProfitAction");
    private static Spell ghazaBokhorJoonBegiri = new Spell("ghazaBokhorJoonBegiri","Give Power Buff To Your Card Which Equals With It's Current Hp Forever",2500,4013,2, Spell.TargetType.PLAYER,"ghazaBokhorJoonBegiriAction");
    private static Spell allPower = new Spell("allPower","Give Power Buff To All Of Your Cards Forever",2000,4014,4, Spell.TargetType.PLAYER,"allPowerAction");
    private static Spell allAttack = new Spell("allAttack","Do damage To All Of Enemies Cards",1500,4015,4, Spell.TargetType.ENEMY,"allAttackAction");
    private static Spell weakening = new Spell("weakening","Give Weakness Buff  To A Card Of Enemy",1000,4016,1, Spell.TargetType.ENEMY,"weakeningAction");
    private static Spell sacrifice = new Spell("sacrifice","Give All Hp Of A Minion To Your Hero",1600,4017,3, Spell.TargetType.PLAYER,"sacrificeAction");
    private static Spell kingsGuard = new Spell("kingsGuard","Kill A Card Of Enemy",1750,4018,3, Spell.TargetType.ENEMY,"kingsGuardAction");
    private static Spell shock = new Spell("shock","Stunning Card Of Enemy For Two Turns",1200,4019,1, Spell.TargetType.ENEMY,"shockAction");

    //minions
    private static Minion farsArcher = new Minion("farsArcher","farsArcher",300,3000,7,4,6,2,AttackType.Ranged, Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion farsSwordsman = new Minion("farsSwordsman","farsSwordsman",400,3001,0,4,6,2,AttackType.Melee,Minion.ActivateTimeOfSpecialPower.PASSIVE,"farsSwordsmanAction");
    private static Minion farsSpear = new Minion("farsSpear","farsSpear",500,3002,3,3,5,1,AttackType.Hybrid,Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion farsHorseman = new Minion("farsHorseman","farsSpear",200,3003,0,6,10,4,AttackType.Melee,Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion farsChampion = new Minion("farsChampion","farsChampion",600,3004,0,6,24,9,AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_ATTACK,"farsChampionAction");
    private static Minion farsChief = new Minion("farsChief","farsChief",800,3005,0,4,12,7,AttackType.Melee,Minion.ActivateTimeOfSpecialPower.COMBO,"farsChiefAction");
    private static Minion TooraniArcher = new Minion("TooraniArcher","TooraniArcher",500,3006,5,4,3,1,AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion TooraniCatapult = new Minion("TooraniCatapult","TooraniCatapult",500,3007,7,2,4,1,AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion TooraniSpear = new Minion("TooraniSpear","TooraniSpear",600,3008,3,4,4,1,AttackType.Hybrid,Minion.ActivateTimeOfSpecialPower.NOTHING,"NoAction");
    private static Minion TooraniSpy = new Minion("TooraniSpy","TooraniSpy",700,3009,0,6,6,4,AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_ATTACK,"TooraniSpyAction");


    private static Minion tooraniSwampy = new Minion("tooraniSwampy", "tooraniSwampy", 450, 3010, 0, 10, 3, 2, AttackType.Melee, Minion.ActivateTimeOfSpecialPower.NOTHING, "");
    private static Minion tooraniPrince = new Minion("tooraniPrince", "sor any not torani plus 4 attackpower", 800, 3011, 0, 10, 6, 6, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.COMBO, "combo");
    private static Minion blackGoblin = new Minion("blackGoblin", "", 300, 3012, 7, 10, 14, 9, AttackType.Hybrid,Minion.ActivateTimeOfSpecialPower.NOTHING, "");
    private static Minion throwStoneGiant = new Minion("throwStoneGiant", "", 300, 3013, 7, 12, 12, 9, AttackType.Ranged, Minion.ActivateTimeOfSpecialPower.NOTHING,"");
    private static Minion eagle = new Minion("eagle", "plus 10 power buff with increase HP", 300, 3014, 3, 2, 0, 2, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.PASSIVE, "passive");
    private static Minion hogRiderGoblin = new Minion("hogRiderGoblin", "", 300, 3015, 0, 8, 16, 6, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.NOTHING, "");
    private static Minion oneEyeGiant = new Minion("oneEyeGiant", "on death time attack to all minion around 8 cells with 2 AP", 500, 3016, 3, 11, 12, 7, AttackType.Hybrid,Minion.ActivateTimeOfSpecialPower.ON_DEATH, "on death");
    private static Minion poisonSnake = new Minion("poisonSnake", "for 3 turn poisoned enemy", 300, 3017, 4, 6, 5, 4, AttackType.Ranged, Minion.ActivateTimeOfSpecialPower.ON_ATTACK, "on attack");
    private static Minion throwFireDragon = new Minion("throwFireDragon", "", 250, 3018, 4, 5, 9, 5, AttackType.Ranged, Minion.ActivateTimeOfSpecialPower.NOTHING,"");
    private static Minion drainLion = new Minion("drainLion", "no effect from holly buff", 600, 3019, 0, 8, 1, 2, AttackType.Melee, Minion.ActivateTimeOfSpecialPower.ON_ATTACK,"on attack");
    private static Minion giantSnake = new Minion("giantSnake", "not holly buff", 500, 3020, 5, 7, 14, 8, AttackType.Ranged, Minion.ActivateTimeOfSpecialPower.ON_RESPAWN,"on respawn");
    private static Minion whiteWolf = new Minion("whiteWolf", "4 and 6 decrease minion HP for two after turn", 400, 3021, 0, 2, 8, 5, AttackType.Melee, Minion.ActivateTimeOfSpecialPower.ON_ATTACK,"on attack");
    private static Minion tiger = new Minion("tiger", "decrease 8 HP in after turn from minion that attacked", 400, 3022, 0, 2, 6, 4, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_ATTACK, "on attack");
    private static Minion wolf = new Minion("wolf", "decrease 6 HP in after turn from minion that attacked", 400, 3023, 0, 1, 6, 3, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_ATTACK, "on attack");
    private static Minion wizard = new Minion("wizard", "plus 2 AP and one weakness (decrease 1 HP)  for around minion", 550, 3024, 3, 4, 5, 4, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.PASSIVE, "passive");
    private static Minion greatWizard = new Minion("greatWizard", "plus 2 AP and holly buff  for around minion", 550, 3025, 5, 6, 6, 6, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.PASSIVE, "passive");
    private static Minion elf = new Minion("elf", "for all own minions passive 1 AP power buff", 500, 3026, 4, 4, 10, 5, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.PASSIVE, "passive");
    private static Minion wildBoar = new Minion("wildBoar", "no effect by disarm", 500, 3027, 0, 14, 10, 6, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_DEFEND, "on defend");
    private static Minion piran = new Minion("piran", "no effect by poison", 400, 3028, 0, 12, 20, 8, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_DEFEND, "on defend");
    private static Minion giv = new Minion("giv", "no effect by negative buff", 450, 3029, 5, 7, 5, 4, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.ON_DEFEND, "on defend");
    private static Minion bahman = new Minion("bahman", "random decrease enemy minion decrease 16 HP", 450, 3030, 0, 9, 16, 8, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_RESPAWN, "on respawn");
    private static Minion ashkbos = new Minion("ashkbos", "no effect by enemy that attack power lower than", 400, 3031, 0, 8, 14, 7, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_DEFEND, "on defend");
    private static Minion iraj = new Minion("iraj", "", 500, 3032, 3, 20, 6, 4, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.NOTHING, "");
    private static Minion bigGiant = new Minion("bigGiant", "", 600, 3033, 2, 8, 30, 9, AttackType.Hybrid,Minion.ActivateTimeOfSpecialPower.NOTHING, "");
    private static Minion twoHeadGiant = new Minion("twoHeadGiant", "destroy all holly buff minion that attacked", 550, 3034, 0, 4, 10, 4, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.PASSIVE, "passive");
    private static Minion coldMother = new Minion("coldMother", "stun around minion ", 500, 3035, 5, 4, 3, 3, AttackType.Ranged,Minion.ActivateTimeOfSpecialPower.ON_RESPAWN, "on respawn");
    private static Minion steelArmor  = new Minion("steelArmor", "convert randomly to enemy minions ", 650, 3036, 0, 1, 1, 3, AttackType.Melee, Minion.ActivateTimeOfSpecialPower.ON_RESPAWN,"on respawn");
    private static Minion siavash  = new Minion("siavash", "on death attack 6 AP to enemy hero", 350, 3037, 0, 5, 8, 4, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.ON_DEATH, "on death");
    private static Minion kingGiant  = new Minion("kingGiant", "for any giant take part in combo enemy 1 turn disarm", 600, 3038, 0, 4, 10, 5, AttackType.Melee, Minion.ActivateTimeOfSpecialPower.COMBO, "combo");
    private static Minion arjangGoblin  = new Minion("arjangGoblin", "fo any goblin take part in attack add 3 to weakness", 600, 3039, 0, 6, 6, 3, AttackType.Melee,Minion.ActivateTimeOfSpecialPower.COMBO, "combo");
}
