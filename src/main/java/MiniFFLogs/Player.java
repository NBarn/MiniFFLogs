package MiniFFLogs;

public class Player {

    String firstName;
    String lastName;
    String job;
    String date;
    float dmg_perc;
    float dps;
    float crit_hit_perc;
    float dh_perc;
    float healing;
    float crit_heal_perc;
    float overheal_perc;
    float crit_dh_perc;
    int deaths;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDmg_perc(float dmg_perc) {
        this.dmg_perc = dmg_perc;
    }

    public void setDps(float dps) {
        this.dps = dps;
    }

    public void setCrit_hit_perc(float crit_hit_perc) {
        this.crit_hit_perc = crit_hit_perc;
    }

    public void setDh_perc(float dh_perc) {
        this.dh_perc = dh_perc;
    }

    public void setHealing(float healing) {
        this.healing = healing;
    }

    public void setCrit_heal_perc(float crit_heal_perc) {
        this.crit_heal_perc = crit_heal_perc;
    }

    public void setOverheal_perc(float overheal_perc) {
        this.overheal_perc = overheal_perc;
    }

    public void setCrit_dh_perc(float crit_dh_perc) {
        this.crit_dh_perc = crit_dh_perc;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder(firstName + " " + lastName + " " + job + " " + date + " " + dmg_perc);
        return strB.toString();
    }

}
