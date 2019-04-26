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

}
