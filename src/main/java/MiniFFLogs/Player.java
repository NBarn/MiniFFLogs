package MiniFFLogs;

import java.util.Dictionary;
import java.util.HashMap;

public class Player {

    private static HashMap<String, Boolean> staticMembers= new HashMap<String, Boolean>() {{
        put("Val Guerra", true);
        put("Kabuki Elysium", true);
        put("Kilia Sunder", true);
        put("Jirou Thirteen", true);
        put("Haki Shen", true);
        put("Josen Tiamat", true);
        put("Bankai Getsugatenso", true);
    }};

    private String name;
    private String job;
    private String date;
    private float dmg_perc;
    private float dps;
    private float hps;
    private float crit_hit_perc;
    private float dh_perc;
    private float healing;
    private float crit_heal_perc;
    private float overheal_perc;
    private float crit_dh_perc;


    private int duration;
    private int deaths;

    public String getName() {
        return name;
    }
    public void setFirstName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public float getDmg_perc() {
        return dmg_perc;
    }
    public void setDmg_perc(float dmg_perc) {
        this.dmg_perc = dmg_perc;
    }

    public float getDps() {
        return dps;
    }
    public void setDps(float dps) {
        this.dps = dps;
    }

    public float getHps() {
        return hps;
    }
    public void setHps(float hps) {
        this.hps = hps;
    }

    public float getCrit_hit_perc() {
        return crit_hit_perc;
    }
    public void setCrit_hit_perc(float crit_hit_perc) {
        this.crit_hit_perc = crit_hit_perc;
    }

    public float getDh_perc() {
        return dh_perc;
    }
    public void setDh_perc(float dh_perc) {
        this.dh_perc = dh_perc;
    }

    public float getHealing() {
        return healing;
    }
    public void setHealing(float healing) {
        this.healing = healing;
    }

    public float getCrit_heal_perc() {
        return crit_heal_perc;
    }
    public void setCrit_heal_perc(float crit_heal_perc) {
        this.crit_heal_perc = crit_heal_perc;
    }

    public float getOverheal_perc() {
        return overheal_perc;
    }
    public void setOverheal_perc(float overheal_perc) {
        this.overheal_perc = overheal_perc;
    }

    public float getCrit_dh_perc() {
        return crit_dh_perc;
    }
    public void setCrit_dh_perc(float crit_dh_perc) {
        this.crit_dh_perc = crit_dh_perc;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDeaths() {
        return deaths;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public static boolean isStaticMember(String name) {
        return staticMembers.get(name) != null;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder(name + " " + job + " " + date + " " + dmg_perc);
        return strB.toString();
    }

}
